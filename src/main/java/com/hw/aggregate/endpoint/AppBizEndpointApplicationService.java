package com.hw.aggregate.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.model.BizEndpointQueryRegistry;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.hw.shared.IdGenerator;
import com.hw.shared.idempotent.AppChangeRecordApplicationService;
import com.hw.shared.idempotent.ChangeRepository;
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
        return null;
    }

    @Override
    public AppBizEndpointCardRep getEntitySumRepresentation(BizEndpoint bizEndpoint) {
        return new AppBizEndpointCardRep(bizEndpoint);
    }

    @Override
    public Void getEntityRepresentation(BizEndpoint bizEndpoint) {
        return null;
    }

    @Override
    protected BizEndpoint createEntity(long id, Object command) {
        return null;
    }

    @Override
    public void preDelete(BizEndpoint bizEndpoint) {

    }

    @Override
    public void postDelete(BizEndpoint bizEndpoint) {

    }

    @Override
    protected void prePatch(BizEndpoint bizEndpoint, Map<String, Object> params, VoidTypedClass middleLayer) {

    }

    @Override
    protected void postPatch(BizEndpoint bizEndpoint, Map<String, Object> params, VoidTypedClass middleLayer) {

    }
}
