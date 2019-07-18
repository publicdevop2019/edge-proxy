package com.hw.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.EdgeProxy;
import org.junit.Assert;
import org.junit.Ignore;
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

import java.util.HashMap;
import java.util.UUID;

/**
 * this integration test requires oauth2service to be running
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdgeProxy.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlackListControllerTest {
    private String client_credentials = "client_credentials";
    private String password = "password";
    private String valid_clientId = "oauth2-id";
    private String should_block_clientId_root = "block-id";
    private String should_block_clientId_non_root = "test-id";
    private String wrong_clientId = "register-id";
    private String valid_clientSecret = "root";
    private String oauth2service = "8080";

    @LocalServerPort
    int randomServerPort;

    private ObjectMapper mapper = new ObjectMapper();

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String username = "admin@gmail.com";
    private String userPwd = "root";

    /**
     * running test with spring runner can get away with security constrain
     */
    @Test
    @Ignore
    public void sad_receive_request_blacklist_client_then_block_client_old_request_access_proxy_endpoints() {
        String url = "http://localhost:" + randomServerPort + "/proxy/blacklist" + "/client";
        /**
         * before client get blacklisted, client is able to access proxy endpoints
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse1 = getTokenResponse(client_credentials, should_block_clientId_root, valid_clientSecret);
        String bearer1 = tokenResponse1.getBody().getValue();
        MultiValueMap<String, String> map1 = new LinkedMultiValueMap<>();
        map1.add("name", UUID.randomUUID().toString());
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> hashMapHttpEntity1 = new HttpEntity<>(map1, headers1);
        ResponseEntity<String> exchange1 = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange1.getStatusCode());


        /**
         * block client
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, valid_clientId, valid_clientSecret);
        String bearer = tokenResponse.getBody().getValue();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", should_block_clientId_root);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> hashMapHttpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());

        /**
         * after client get blacklisted, client with old token will get 401
         */
        ResponseEntity<String> exchange2 = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, exchange2.getStatusCode());

        /**
         * after client obtain new token from auth server, it can access resource again
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse3 = getTokenResponse(client_credentials, should_block_clientId_root, valid_clientSecret);
        String bearer3 = tokenResponse3.getBody().getValue();
        headers1.setBearerAuth(bearer3);
        HttpEntity<MultiValueMap<String, String>> hashMapHttpEntity3 = new HttpEntity<>(map1, headers1);
        ResponseEntity<String> exchange3 = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity3, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange3.getStatusCode());
    }

    @Test
    public void sad_receive_request_blacklist_client_then_block_client_old_request() throws JsonProcessingException {

        String url = "http://localhost:" + randomServerPort + "/proxy/blacklist" + "/client";
        String url2 = "http://localhost:" + randomServerPort + "/api/v1" + "/resourceOwners";
        /**
         * before client get blacklisted, client is able to access auth server non token endpoint
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse1 = getPwdTokenResponse(password, should_block_clientId_non_root, "", username, userPwd);
        String bearer1 = tokenResponse1.getBody().getValue();
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Object> hashMapHttpEntity1 = new HttpEntity<>(headers1);
        ResponseEntity<String> exchange1 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange1.getStatusCode());


        /**
         * block client
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, valid_clientId, valid_clientSecret);
        String bearer = tokenResponse.getBody().getValue();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", should_block_clientId_non_root);
        String s = mapper.writeValueAsString(stringStringHashMap);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> hashMapHttpEntity = new HttpEntity<>(s, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());

        /**
         * after client get blacklisted, client with old token will get 401
         */
        ResponseEntity<String> exchange2 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, exchange2.getStatusCode());

        /**
         * after client obtain new token from auth server, it can access resource again
         */
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse3 = getPwdTokenResponse(password, should_block_clientId_non_root, "", username, userPwd);
        String bearer3 = tokenResponse3.getBody().getValue();
        headers1.setBearerAuth(bearer3);
        HttpEntity<Object> hashMapHttpEntity3 = new HttpEntity<>(headers1);
        ResponseEntity<String> exchange3 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity3, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange3.getStatusCode());
    }

    /**
     * only root client and trusted client can add blacklist
     */
    @Test
    public void sad_receive_request_blacklist_from_wrong_client() throws JsonProcessingException {
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, wrong_clientId, "");
        String bearer = tokenResponse.getBody().getValue();

        String url = "http://localhost:" + randomServerPort + "/proxy/blacklist" + "/client";
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", UUID.randomUUID().toString());
        String s = mapper.writeValueAsString(stringStringHashMap);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> hashMapHttpEntity = new HttpEntity<>(s, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, exchange.getStatusCode());
    }

    @Test
    public void happy_receive_request_blacklist_resourceOwner_then_block_resourceOwner_old_request() {
        //@todo use password flow
        ResponseEntity<DefaultOAuth2AccessToken> tokenResponse = getTokenResponse(client_credentials, valid_clientId, valid_clientSecret);
        String bearer = tokenResponse.getBody().getValue();

        String url = "http://localhost:" + randomServerPort + "/proxy/blacklist" + "/resourceOwner";
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

    private ResponseEntity<DefaultOAuth2AccessToken> getPwdTokenResponse(String grantType, String clientId, String clientSecret, String username, String pwd) {
        String url = "http://localhost:" + randomServerPort + "/" + "oauth/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("username", username);
        params.add("password", pwd);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, DefaultOAuth2AccessToken.class);
    }
}
