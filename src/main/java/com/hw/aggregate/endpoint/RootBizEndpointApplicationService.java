package com.hw.aggregate.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.endpoint.command.CreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.UpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.model.BizEndpointQueryRegistry;
import com.hw.aggregate.endpoint.model.RootBizEndpointPatchMiddleLayer;
import com.hw.aggregate.endpoint.representation.RootBizEndpointCardRep;
import com.hw.aggregate.endpoint.representation.RootBizEndpointRep;
import com.hw.shared.IdGenerator;
import com.hw.shared.idempotent.ChangeRepository;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class RootBizEndpointApplicationService extends DefaultRoleBasedRestfulService<BizEndpoint, RootBizEndpointCardRep, RootBizEndpointRep, RootBizEndpointPatchMiddleLayer> {

    @Autowired
    private AppBizEndpointApplicationService appBizEndpointApplicationService;

    @Autowired
    private BizEndpointQueryRegistry registry;
    @Autowired
    private IdGenerator idGenerator2;
    @Autowired
    private ChangeRepository changeRepository2;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BizEndpointRepo repo2;


    @PostConstruct
    private void setUp() {
        repo = repo2;
        queryRegistry = registry;
        entityClass = BizEndpoint.class;
        role = RestfulQueryRegistry.RoleEnum.ROOT;
        idGenerator = idGenerator2;
        changeRepository = changeRepository2;
        om = objectMapper;
        entityPatchSupplier = RootBizEndpointPatchMiddleLayer::new;
    }

    @Override
    public BizEndpoint replaceEntity(BizEndpoint bizEndpoint, Object command) {
        return bizEndpoint.replace((UpdateBizEndpointCommand) command);
    }

    @Override
    public RootBizEndpointCardRep getEntitySumRepresentation(BizEndpoint bizEndpoint) {
        return new RootBizEndpointCardRep(bizEndpoint);
    }

    @Override
    public RootBizEndpointRep getEntityRepresentation(BizEndpoint bizEndpoint) {
        return new RootBizEndpointRep(bizEndpoint);
    }

    @Override
    protected BizEndpoint createEntity(long id, Object command) {
        return BizEndpoint.create(id, (CreateBizEndpointCommand) command, appBizEndpointApplicationService);
    }

    @Override
    public void preDelete(BizEndpoint bizEndpoint) {

    }

    @Override
    public void postDelete(BizEndpoint bizEndpoint) {

    }

    @Override
    protected void prePatch(BizEndpoint bizEndpoint, Map<String, Object> params, RootBizEndpointPatchMiddleLayer middleLayer) {

    }

    @Override
    protected void postPatch(BizEndpoint bizEndpoint, Map<String, Object> params, RootBizEndpointPatchMiddleLayer middleLayer) {

    }

}
