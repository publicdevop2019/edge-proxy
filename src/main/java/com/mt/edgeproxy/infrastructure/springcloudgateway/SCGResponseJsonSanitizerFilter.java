package com.mt.edgeproxy.infrastructure.springcloudgateway;

import com.google.json.JsonSanitizer;
import com.mt.edgeproxy.infrastructure.springcloudgateway.exception.ResourceCloseException;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class SCGResponseJsonSanitizerFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse decoratedResponse = responseJsonSanitizer(exchange);
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private ServerHttpResponse responseJsonSanitizer(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                HttpHeaders headers = originalResponse.getHeaders();
                if (MediaType.APPLICATION_JSON_UTF8.equals(headers.getContentType())) {
                    Flux<DataBuffer> flux;
                    if (body instanceof Mono) {
                        Mono<? extends DataBuffer> mono = (Mono<? extends DataBuffer>) body;
                        body = mono.flux();
                    }
                    if (body instanceof Flux) {
                        flux = (Flux<DataBuffer>) body;
                        return super.writeWith(flux.buffer().map(dataBuffers -> {
                            String responseBody = getResponseBody(dataBuffers);
                            String s2 = responseBody.replace("<", "&lt;");
                            String s3 = s2.replace(">", "&gt;");
                            String afterSanitize = JsonSanitizer.sanitize(s3);
                            if (headers.getContentLength() != afterSanitize.getBytes().length)
                                log.debug("sanitized response length before {} after {}", responseBody.getBytes().length, afterSanitize.getBytes().length);
                            headers.setContentLength(afterSanitize.getBytes().length);
                            return bufferFactory.wrap(afterSanitize.getBytes());
                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
    }

    public static String getResponseBody(List<DataBuffer> dataBuffers) {
        String responseBody;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            dataBuffers.forEach(i -> {
                byte[] array = new byte[i.readableByteCount()];
                i.read(array);
                DataBufferUtils.release(i);
                outputStream.write(array, 0, array.length);
            });
            responseBody = outputStream.toString();
        } catch (IOException e) {
            log.error("unable to close resource", e);
            throw new ResourceCloseException();
        }
        return responseBody;
    }
}
