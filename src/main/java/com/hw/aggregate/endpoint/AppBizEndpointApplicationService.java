package com.hw.aggregate.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.model.BizEndpointQueryRegistry;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.hw.shared.IdGenerator;
import com.hw.shared.idempotent.AppChangeRecordApplicationService;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class AppBizEndpointApplicationService extends DefaultRoleBasedRestfulService<BizEndpoint, AppBizEndpointCardRep, Void, VoidTypedClass> {
    @Autowired
    private BizEndpointQueryRegistry registry;
    @Autowired
    private IdGenerator idGenerator2;
    @Autowired
    private AppChangeRecordApplicationService changeRepository2;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BizEndpointRepo repo2;


    @PostConstruct
    private void setUp() {
        repo = repo2;
        queryRegistry = registry;
        entityClass = BizEndpoint.class;
        role = RestfulQueryRegistry.RoleEnum.APP;
        idGenerator = idGenerator2;
        appChangeRecordApplicationService = changeRepository2;
        om = objectMapper;
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
