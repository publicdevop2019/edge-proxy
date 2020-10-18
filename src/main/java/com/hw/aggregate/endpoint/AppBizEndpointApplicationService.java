package com.hw.aggregate.endpoint;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class AppBizEndpointApplicationService extends DefaultRoleBasedRestfulService<BizEndpoint, AppBizEndpointCardRep, Void, VoidTypedClass> {

    @PostConstruct
    private void setUp() {
        entityClass = BizEndpoint.class;
        role = RestfulQueryRegistry.RoleEnum.APP;
    }

    @Override
    public BizEndpoint replaceEntity(BizEndpoint bizEndpoint, Object command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AppBizEndpointCardRep getEntitySumRepresentation(BizEndpoint bizEndpoint) {
        return new AppBizEndpointCardRep(bizEndpoint);
    }

    @Override
    public Void getEntityRepresentation(BizEndpoint bizEndpoint) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected BizEndpoint createEntity(long id, Object command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void preDelete(BizEndpoint bizEndpoint) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postDelete(BizEndpoint bizEndpoint) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prePatch(BizEndpoint bizEndpoint, Map<String, Object> params, VoidTypedClass middleLayer) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void postPatch(BizEndpoint bizEndpoint, Map<String, Object> params, VoidTypedClass middleLayer) {
        throw new UnsupportedOperationException();
    }
}
