package com.hw.aggregate.revoke_token.representation;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AppRevokeTokenCardRep {
    private Long id;
    private Long targetId;
    private Long issuedAt;

    public AppRevokeTokenCardRep(RevokeToken revokeToken) {
        BeanUtils.copyProperties(revokeToken, this);
    }

}
