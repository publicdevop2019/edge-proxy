package com.hw.aggregate.revoke_token.command;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CreateRevokeTokenCommand implements Serializable {
    private static final long serialVersionUID = 1;
    private String id;
    private RevokeToken.TokenTypeEnum type;


}
