package com.hw.aggregate.revoke_token.command;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRevokeTokenCommand {
    private Long id;
    private RevokeToken.TokenTypeEnum type;


}
