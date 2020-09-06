package com.hw.aggregate.revoke_token.model;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.shared.Auditable;
import com.hw.shared.EnumDBConverter;
import com.hw.shared.rest.IdBasedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Data
@Slf4j
@NoArgsConstructor
public class RevokeToken extends Auditable implements IdBasedEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long targetId;
    public static final String ENTITY_TARGET_ID = "targetId";
    @Column(nullable = false)
    private Long issuedAt;
    public static final String ENTITY_ISSUE_AT = "issuedAt";
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

    public static RevokeToken create(Long id, CreateRevokeTokenCommand command) {
        if (command.getId() == null)
            throw new IllegalArgumentException("id should not be empty");
        return new RevokeToken(id, command);
    }

    public static RevokeToken createForAdmin(Long id, CreateRevokeTokenCommand command) {
        if (command.getId() == null)
            throw new IllegalArgumentException("id should not be empty");
        if (command.getType().equals(TokenTypeEnum.Client))
            throw new IllegalArgumentException("type can only be user");
        return new RevokeToken(id, command);
    }

    public RevokeToken(Long id, CreateRevokeTokenCommand command) {
        this.id = id;
        this.issuedAt = Instant.now().getEpochSecond();
        this.type = command.getType();
        this.targetId = command.getId();
    }
}
