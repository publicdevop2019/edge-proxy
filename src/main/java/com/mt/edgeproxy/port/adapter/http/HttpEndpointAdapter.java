package com.mt.edgeproxy.port.adapter.http;

import com.mt.edgeproxy.domain.Endpoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
@Component
public class HttpEndpointAdapter implements EndpointAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private void setOutgoingReqInterceptor(OutgoingReqInterceptor outgoingReqInterceptor) {
        restTemplate.setInterceptors(Collections.singletonList(outgoingReqInterceptor));
    }

    @Value("${manytree.url.endpoint}")
    private String endpointUrl;

    @Override
    public Set<Endpoint> fetchAllEndpoints() {
        Set<Endpoint> data = new HashSet<>();
        ResponseEntity<SumPagedRep<Endpoint>> exchange = restTemplate.exchange(endpointUrl + "?page=size:40,num:0", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        if (exchange.getStatusCode().is2xxSuccessful()) {
            SumPagedRep<Endpoint> body = exchange.getBody();
            if (body == null || body.getData().size() == 0)
                throw new IllegalStateException("unable to load endpoint profile from remote");
            data.addAll(body.getData());
            double l = (double) body.getTotalItemCount() / body.getData().size();
            double ceil = Math.ceil(l);
            int i = BigDecimal.valueOf(ceil).intValue();
            for (int a = 1; a < i; a++) {
                ResponseEntity<SumPagedRep<Endpoint>> exchange2 = restTemplate.exchange(endpointUrl + "?page=size:40,num:" + a, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
                SumPagedRep<Endpoint> body2 = exchange2.getBody();
                if (body2 == null || body2.getData().size() == 0)
                    throw new IllegalStateException("unable to load endpoint profile from remote");
                data.addAll(body2.getData());
            }
        }
        return data;
    }

    @Data
    public static class SumPagedRep<T> {
        protected List<T> data;
        protected Long totalItemCount;
    }

    @Component
    @Slf4j
    public static class OutgoingReqInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            if (null == MDC.get("UUID")) {
                String s = UUID.randomUUID().toString();
                log.debug("UUID not found for outgoing request, auto generate value {}", s);
                MDC.put("UUID", s);
            }
            httpRequest.getHeaders().set("UUID", MDC.get("UUID"));
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
    }
}
