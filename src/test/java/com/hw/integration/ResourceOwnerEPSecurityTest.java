package com.hw.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.EdgeProxy;
import com.hw.auth.entity.ResourceOwner;
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
 * this integration auth requires oauth2service to be running
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdgeProxy.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceOwnerEPSecurityTest {
    private String client_credentials = "client_credentials";
    private String invalid_clientId = "rightRoleNotSufficientResourceId";
    private String valid_empty_secret = "";
    public ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.USE_ANNOTATIONS, false).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    int randomServerPort;

    @Test
    public void sad_createUser_w_client_missing_right_role() throws JsonProcessingException {
        ResourceOwner user = getUser();
        ResponseEntity<DefaultOAuth2AccessToken> user1 = createUser(user, invalid_clientId);

        Assert.assertEquals(HttpStatus.FORBIDDEN, user1.getStatusCode());
    }

    private ResourceOwner getUser() {
        ResourceOwner resourceOwner = new ResourceOwner();
        resourceOwner.setPassword(UUID.randomUUID().toString().replace("-", ""));
        resourceOwner.setEmail(UUID.randomUUID().toString().replace("-", "") + "@gmail.com");
        return resourceOwner;
    }

    private ResponseEntity<DefaultOAuth2AccessToken> createUser(ResourceOwner user, String clientId) throws JsonProcessingException {
        String url = "http://localhost:" + randomServerPort + "/api" + "/resourceOwner";
        ResponseEntity<DefaultOAuth2AccessToken> registerTokenResponse = getRegisterTokenResponse(client_credentials, clientId, valid_empty_secret);
        String value = registerTokenResponse.getBody().getValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(value);
        String s = mapper.writeValueAsString(user);
        HttpEntity<String> request = new HttpEntity<>(s, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, DefaultOAuth2AccessToken.class);
    }

    private ResponseEntity<DefaultOAuth2AccessToken> getRegisterTokenResponse(String grantType, String clientId, String clientSecret) {
        String url = "http://localhost:" + randomServerPort + "/" + "oauth/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, DefaultOAuth2AccessToken.class);
    }

}
