package com.mt.edgeproxy.port.adapter.messaging;

import com.mt.edgeproxy.domain.DomainRegistry;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class EndpointMQListener {
    @PostConstruct
    private void load() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("mt_global_exchange", "topic");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, "mt_global_exchange", "oauth.external.system");
            channel.queueBind(queueName, "mt_global_exchange", "oauth.external.endpoint");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                log.debug("start refresh cached endpoints");
                DomainRegistry.endpointService().loadAllEndpoints();
                log.debug("cached endpoints refreshed");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            log.error("error in mq", e);
        }
    }
}
