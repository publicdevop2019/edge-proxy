package com.hw.integration;

import com.hw.EdgeProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

/**
 * this integration test requires oauth2service to be running
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdgeProxy.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlackListControllerTest {
    private String client_credentials = "client_credentials";
    private String valid_clientId = "oauth2-id";
    private String valid_clientSecret = "root";
    private String oauth2service = "8080";

    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void happy_receive_request_blacklist_client_then_block_client_old_request() {
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, valid_clientId, valid_clientSecret);
        String bearer = tokenResponse.getBody().getValue();

        String url = "http://localhost:" + randomServerPort + "proxy/blacklist"+"/client";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", UUID.randomUUID().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> hashMapHttpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        //@todo validate request is getting blocked
    }

    @Test
    public void happy_receive_request_blacklist_resourceOwner_then_block_resourceOwner_old_request() {
        //@todo use password flow
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, valid_clientId, valid_clientSecret);
        String bearer = tokenResponse.getBody().getValue();

        String url = "http://localhost:" + randomServerPort + "proxy/blacklist"+"/resourceOwner";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", UUID.randomUUID().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> hashMapHttpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }


    private ResponseEntity<DefaultOAuth2AccessToken> getTokenResponse(String grantType, String clientId, String clientSecret) {
        String url = "http://localhost:" + randomServerPort + "/" + "oauth/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, DefaultOAuth2AccessToken.class);
    }
}
