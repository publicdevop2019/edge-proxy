package com.hw.aggregate.revoke_token.representation;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import lombok.Data;

@Data
public class AppRevokeTokenCardRep {
    private Long id;
    private Long targetId;
    private Long issuedAt;

    public AppRevokeTokenCardRep(RevokeToken revokeToken) {
        this.id = revokeToken.getId();
        this.targetId = revokeToken.getTargetId();
        this.issuedAt = revokeToken.getIssuedAt();
    }

}
