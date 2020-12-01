package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.hw.shared.rest.RoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Service;

@Service
public class AdminRevokeTokenApplicationService extends RoleBasedRestfulService<RevokeToken, Void, Void, VoidTypedClass> {
    {
        entityClass = RevokeToken.class;
        role = RestfulQueryRegistry.RoleEnum.ROOT;
    }

    @Override
    protected RevokeToken createEntity(long id, Object command) {
        return RevokeToken.createForAdmin(id, (CreateRevokeTokenCommand) command);
    }

}
