package com.hw.aggregate.revoke_token.representation;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RootRevokeTokenCardRep {
    private Long id;
    private Long targetId;
    private Long issuedAt;
    private RevokeToken.TokenTypeEnum type;

    public RootRevokeTokenCardRep(RevokeToken revokeToken) {
        BeanUtils.copyProperties(revokeToken, this);
    }
}
