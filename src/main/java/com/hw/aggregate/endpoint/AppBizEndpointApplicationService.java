package com.hw.aggregate.endpoint;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.hw.config.filter.EndpointFilter.EXCHANGE_RELOAD_EP_CACHE;

@Slf4j
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
    @Override
    public void afterWriteComplete(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_RELOAD_EP_CACHE, "fanout");
            channel.basicPublish(EXCHANGE_RELOAD_EP_CACHE, "", null, null);
            log.info("sent clean filter cache message");
        } catch (IOException | TimeoutException e) {
            log.error("error in mq", e);
        }
    }
}
