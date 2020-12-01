package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.hw.aggregate.revoke_token.representation.RootRevokeTokenCardRep;
import com.hw.shared.rest.RoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

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
