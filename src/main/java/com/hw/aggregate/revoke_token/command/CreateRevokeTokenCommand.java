package com.hw.aggregate.revoke_token.command;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;

@Data
public class CreateRevokeTokenCommand {
    private Long id;
    private RevokeToken.TokenTypeEnum type;


}
