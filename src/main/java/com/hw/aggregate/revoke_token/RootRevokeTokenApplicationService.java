package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.hw.aggregate.revoke_token.representation.RootRevokeTokenCardRep;
import com.mt.common.rest.RoleBasedRestfulService;
import com.mt.common.rest.VoidTypedClass;
import com.mt.common.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Service;

@Service
public class RootRevokeTokenApplicationService extends RoleBasedRestfulService<RevokeToken, RootRevokeTokenCardRep, Void, VoidTypedClass> {
    {
        entityClass = RevokeToken.class;
        role = RestfulQueryRegistry.RoleEnum.ROOT;
    }

    @Override
    public RootRevokeTokenCardRep getEntitySumRepresentation(RevokeToken revokeToken) {
        return new RootRevokeTokenCardRep(revokeToken);
    }


    @Override
    protected RevokeToken createEntity(long id, Object command) {
        return RevokeToken.create(id, (CreateRevokeTokenCommand) command);
    }


}
