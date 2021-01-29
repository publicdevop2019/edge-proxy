package com.mt.edgeproxy.infrastructure.springcloudgateway;

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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Component
public class SCGZipFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse decoratedResponse = zipResponse(exchange);
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -4;
    }

    private ServerHttpResponse zipResponse(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                Flux<DataBuffer> flux;
                if (body instanceof Mono) {
                    Mono<? extends DataBuffer> mono = (Mono<? extends DataBuffer>) body;
                    body = mono.flux();
                }
                if (body instanceof Flux) {
                    flux = (Flux<DataBuffer>) body;
                    return super.writeWith(flux.buffer().map(dataBuffers -> {
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
                        boolean minLength = responseBody.getBytes(StandardCharsets.UTF_8).length > 1024;
                        boolean isJson = false;
                        if (originalResponse.getHeaders().getContentType() != null
                                && originalResponse.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON_UTF8)) {
                            isJson = true;
                        }
                        if (minLength && isJson) {
                            byte[] compressed = new byte[0];
                            try {
                                compressed = compress(responseBody);
                            } catch (IOException e) {
                                log.error("error during compress", e);
                            }
                            log.debug("gzip response length before {} after {}", responseBody.length(), compressed.length);
                            originalResponse.getHeaders().setContentLength(compressed.length);
                            originalResponse.getHeaders().set(HttpHeaders.CONTENT_ENCODING, "gzip");
                            return bufferFactory.wrap(compressed);
                        } else {
                            return bufferFactory.wrap(responseBody.getBytes(StandardCharsets.UTF_8));
                        }
                    }));
                }
                return super.writeWith(body);
            }
        };
    }

    private byte[] compress(String data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data.getBytes());
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    public static String decompress(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        bis.close();
        return sb.toString();
    }
}
