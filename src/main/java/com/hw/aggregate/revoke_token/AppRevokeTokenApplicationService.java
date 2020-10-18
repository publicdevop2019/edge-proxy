package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.hw.aggregate.revoke_token.representation.AppRevokeTokenCardRep;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class AppRevokeTokenApplicationService extends DefaultRoleBasedRestfulService<RevokeToken, AppRevokeTokenCardRep, Void, VoidTypedClass> {

    @PostConstruct
    private void setUp() {
        entityClass = RevokeToken.class;
        role = RestfulQueryRegistry.RoleEnum.APP;
    }

    @Override
    public RevokeToken replaceEntity(RevokeToken revokeToken, Object command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AppRevokeTokenCardRep getEntitySumRepresentation(RevokeToken revokeToken) {
        return new AppRevokeTokenCardRep(revokeToken);
    }

    @Override
    public Void getEntityRepresentation(RevokeToken revokeToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected RevokeToken createEntity(long id, Object command) {
        return RevokeToken.create(id, (CreateRevokeTokenCommand) command);
    }

    @Override
    public void preDelete(RevokeToken revokeToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postDelete(RevokeToken revokeToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prePatch(RevokeToken revokeToken, Map<String, Object> params, VoidTypedClass middleLayer) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void postPatch(RevokeToken revokeToken, Map<String, Object> params, VoidTypedClass middleLayer) {
        throw new UnsupportedOperationException();
    }
}
