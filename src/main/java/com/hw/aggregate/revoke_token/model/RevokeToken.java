package com.hw.aggregate.revoke_token.model;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.mt.common.Auditable;
import com.mt.common.EnumDBConverter;
import com.mt.common.rest.Aggregate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Data
@Slf4j
@NoArgsConstructor
public class RevokeToken extends Auditable implements Aggregate {
    public static final String ENTITY_TARGET_ID = "targetId";
    public static final String ENTITY_ISSUE_AT = "issuedAt";
    @Id
    private Long id;
    @Column(nullable = false)
    private String targetId;
    @Column(nullable = false)
    private Long issuedAt;
    @Convert(converter = TokenTypeEnum.DBConverter.class)
    @Column(nullable = false)
    private TokenTypeEnum type;
    @Version
    @Setter(AccessLevel.NONE)
    private Integer version;

    public RevokeToken(Long id, CreateRevokeTokenCommand command) {
        this.id = id;
        this.issuedAt = Instant.now().getEpochSecond();
        this.type = command.getType();
        this.targetId = command.getId();
    }

    public static RevokeToken create(Long id, CreateRevokeTokenCommand command) {
        if (command.getId() == null)
            throw new IllegalArgumentException("id should not be empty");
        return new RevokeToken(id, command);
    }

    public static RevokeToken createForAdmin(Long id, CreateRevokeTokenCommand command) {
        if (command.getId() == null)
            throw new IllegalArgumentException("id should not be empty");
        if (command.getType().equals(TokenTypeEnum.CLIENT))
            throw new IllegalArgumentException("type can only be user");
        return new RevokeToken(id, command);
    }

    public enum TokenTypeEnum {
        CLIENT,
        USER;

        public static class DBConverter extends EnumDBConverter<TokenTypeEnum> {
            public DBConverter() {
                super(TokenTypeEnum.class);
            }
        }
    }
}
