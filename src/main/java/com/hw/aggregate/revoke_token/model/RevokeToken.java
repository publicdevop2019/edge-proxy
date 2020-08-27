package com.hw.aggregate.revoke_token.model;

import com.hw.aggregate.revoke_token.RevokeTokenRepo;
import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.shared.Auditable;
import com.hw.shared.EnumDBConverter;
import com.hw.shared.rest.IdBasedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table
@Data
@Slf4j
@NoArgsConstructor
public class RevokeToken extends Auditable implements IdBasedEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long issuedAt;
    @Convert(converter = TokenTypeEnum.DBConverter.class)
    @Column(nullable = false)
    private TokenTypeEnum type;

    public enum TokenTypeEnum {
        Client,
        User;
        public static class DBConverter extends EnumDBConverter {
            public DBConverter() {
                super(TokenTypeEnum.class);
            }
        }
    }

    public static RevokeToken create(CreateRevokeTokenCommand command, RevokeTokenRepo repo) {
        Long id = command.getId();
        if (id == null)
            throw new IllegalArgumentException("id should not be empty");
        Optional<RevokeToken> byId = repo.findById(id);
        if (byId.isEmpty()) {
            return new RevokeToken(command);
        } else {
            RevokeToken revokeToken = byId.get();
            revokeToken.setIssuedAt(Instant.now().getEpochSecond());
            return revokeToken;
        }

    }
    public static RevokeToken createForAdmin(CreateRevokeTokenCommand command, RevokeTokenRepo repo) {
        Long id = command.getId();
        if (id == null)
            throw new IllegalArgumentException("id should not be empty");
        if(command.getType().equals(TokenTypeEnum.Client))
            throw new IllegalArgumentException("type can only be user");
        Optional<RevokeToken> byId = repo.findById(id);
        if (byId.isEmpty()) {
            return new RevokeToken(command);
        } else {
            RevokeToken revokeToken = byId.get();
            revokeToken.setIssuedAt(Instant.now().getEpochSecond());
            return revokeToken;
        }

    }

    public RevokeToken(CreateRevokeTokenCommand command) {
        this.id = command.getId();
        this.issuedAt = Instant.now().getEpochSecond();
        this.type=command.getType();
    }
}
