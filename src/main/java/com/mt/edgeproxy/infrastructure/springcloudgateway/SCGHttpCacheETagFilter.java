package com.mt.edgeproxy.infrastructure.springcloudgateway;

import com.mt.edgeproxy.infrastructure.ETagStore;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class SCGHttpCacheETagFilter implements GlobalFilter, Ordered {
    @Autowired
    private ETagStore eTagStore;
    @Value("${app.product-svc.product.name}")
    private String productName;
    @Value("${app.product-svc.skus.name}")
    private String skuName;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = exchange.getRequest().getURI().getPath();
        if (!exchange.getRequest().getHeaders().getIfNoneMatch().isEmpty()) {
            String ifNoneMatch = exchange.getRequest().getHeaders().getIfNoneMatch().get(0);
            String query = exchange.getRequest().getURI().getQuery();
            String eTags = eTagStore.getETags(path, query);//save retrieved etag, as this operation is not idempotent
            if (HttpMethod.GET.equals(request.getMethod())
                    && eTags != null
                    && eTags.equals(ifNoneMatch)
                    && !path.contains("changes")
            ) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.NOT_MODIFIED);
                return response.setComplete();
            }
        } else {
            if (!HttpMethod.OPTIONS.equals(request.getMethod()) && !HttpMethod.GET.equals(request.getMethod())) {
                // POST PUT PATCH DELETE
                if (path.contains(productName)) {
                    // invalid sku cache when product change
                    eTagStore.clearResourceETag(skuName);
                }
                eTagStore.clearResourceETag(path);
            } else {
                if (HttpMethod.GET.equals(request.getMethod())) {
                    //generate etag with response
                    ServerHttpResponse decoratedResponse = getServerHttpResponse(exchange);
                    return chain.filter(exchange.mutate().response(decoratedResponse).build());
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }

    private ServerHttpResponse getServerHttpResponse(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                Flux<DataBuffer> flux;
                if (body instanceof Mono) {
                    Mono<? extends DataBuffer> mono = (Mono<? extends DataBuffer>) body;
                    body = mono.flux();
                } else if (body instanceof Flux) {
                    flux = (Flux<DataBuffer>) body;
                    return super.writeWith(flux.buffer().map(dataBuffers -> {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        dataBuffers.forEach(i -> {
                            byte[] array = new byte[i.readableByteCount()];
                            i.read(array);
                            DataBufferUtils.release(i);
                            outputStream.write(array, 0, array.length);
                        });
                        String result = outputStream.toString();
                        try {
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (HttpStatus.OK.equals(exchange.getResponse().getStatusCode())) {
                            // length of W/ + " + 0 + 32bits md5 hash + "
                            StringBuilder builder = new StringBuilder(37);
                            builder.append("W/");
                            builder.append("\"0");
                            DigestUtils.appendMd5DigestAsHex(result.getBytes(), builder);
                            builder.append('"');
                            String etag = builder.toString();
                            originalResponse.getHeaders().setETag(etag);
                            String path = exchange.getRequest().getURI().getPath();
                            String query = exchange.getRequest().getURI().getQuery();
                            eTagStore.setETags(path, query, etag);
                            log.info("response etag :{}", etag);
                        }
                        return bufferFactory.wrap(result.getBytes());
                    }));
                }
                return super.writeWith(body);
            }
        };
    }
    private ServerHttpResponse emptyHttpResponse(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                Flux<DataBuffer> flux;
                if (body instanceof Mono) {
                    Mono<? extends DataBuffer> mono = (Mono<? extends DataBuffer>) body;
                    body = mono.flux();
                } else if (body instanceof Flux) {
                    flux = (Flux<DataBuffer>) body;
                    return super.writeWith(flux.buffer().map(dataBuffers -> {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        dataBuffers.forEach(i -> {
                            byte[] array = new byte[i.readableByteCount()];
                            i.read(array);
                            DataBufferUtils.release(i);
                            outputStream.write(array, 0, array.length);
                        });
                        String result = outputStream.toString();
                        try {
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (HttpStatus.OK.equals(exchange.getResponse().getStatusCode())) {
                            // length of W/ + " + 0 + 32bits md5 hash + "
                            StringBuilder builder = new StringBuilder(37);
                            builder.append("W/");
                            builder.append("\"0");
                            DigestUtils.appendMd5DigestAsHex(result.getBytes(), builder);
                            builder.append('"');
                            String etag = builder.toString();
                            originalResponse.getHeaders().setETag(etag);
                            String path = exchange.getRequest().getURI().getPath();
                            String query = exchange.getRequest().getURI().getQuery();
                            eTagStore.setETags(path, query, etag);
                            log.info("response etag :{}", etag);
                        }
                        return bufferFactory.wrap(result.getBytes());
                    }));
                }
                return super.writeWith(body);
            }
        };
    }
}
