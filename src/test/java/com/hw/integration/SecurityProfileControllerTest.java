package com.hw.integration;

import com.hw.EdgeProxy;
import com.hw.entity.SecurityProfile;
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

import java.net.URI;

/**
 * this integration auth requires oauth2service to be running
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdgeProxy.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityProfileControllerTest {
    private String client_credentials = "client_credentials";
    private String password = "password";
    private String valid_clientId = "oauth2-id";
    private String should_block_clientId_root = "block-id";
    private String should_block_clientId_non_root = "login-id";
    private String wrong_clientId = "register-id";
    private String login_clientId = "login-id";
    private String valid_clientSecret = "root";
    private String oauth2service = "8080";
    private String username_admin = "admin@gmail.com";
    private String username_root = "root@gmail.com";
    private String userPwd = "root";
    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void modify_existing_profile_to_prevent_access() {
        String url2 = "http://localhost:" + randomServerPort + "/api" + "/resourceOwners";
        /**
         * before modify, admin is able to access resourceOwner apis
         */
        ResponseEntity<DefaultOAuth2AccessToken> pwdTokenResponse = getPwdTokenResponse(password, login_clientId, "", username_admin, userPwd);
        String bearer1 = pwdTokenResponse.getBody().getValue();
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        HttpEntity<Object> hashMapHttpEntity1 = new HttpEntity<>(headers1);
        ResponseEntity<String> exchange1 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange1.getStatusCode());

        /**
         * modify profile to prevent admin access
         */
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setPath("/api/resourceOwners");
        securityProfile.setExpression("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()");
        securityProfile.setMethod("GET");
        securityProfile.setResourceID("oauth2-id");
        securityProfile.setUrl("http://localhost:8080/v1/api/resourceOwners");
        ResponseEntity<String> stringResponseEntity = updateProfile(securityProfile, 5L);
        Assert.assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());

        /**
         * after modify, admin is not able to access resourceOwner apis
         */
        ResponseEntity<String> exchange = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, exchange.getStatusCode());

        /**
         * modify profile to allow access
         */
        securityProfile.setExpression("hasRole('ROLE_ADMIN') and #oauth2.hasScope('trust') and #oauth2.isUser()");
        ResponseEntity<String> stringResponseEntity1 = updateProfile(securityProfile, 5L);
        Assert.assertEquals(HttpStatus.OK, stringResponseEntity1.getStatusCode());

        ResponseEntity<String> exchange2 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange2.getStatusCode());
    }

    @Test
    public void create_profile_to_prevent_access_then_delete_to_allow_access() {
        String url2 = "http://localhost:" + randomServerPort + "/api" + "/resourceOwners";
        /**
         * before modify, admin is able to access resourceOwner apis
         */
        ResponseEntity<DefaultOAuth2AccessToken> pwdTokenResponse = getPwdTokenResponse(password, login_clientId, "", username_admin, userPwd);
        String bearer1 = pwdTokenResponse.getBody().getValue();
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        HttpEntity<Object> hashMapHttpEntity1 = new HttpEntity<>(headers1);
        ResponseEntity<String> exchange1 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange1.getStatusCode());

        /**
         * modify profile to prevent admin access
         */
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setPath("/api/resourceOwners");
        securityProfile.setExpression("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()");
        securityProfile.setMethod("GET");
        securityProfile.setResourceID("oauth2-id");
        securityProfile.setUrl("http://localhost:8080/v1/api/resourceOwners");
        ResponseEntity<String> stringResponseEntity = createProfile(securityProfile);
        Assert.assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());

        /**
         * before modify, admin is able to access resourceOwner apis
         */
        ResponseEntity<String> exchange = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, exchange.getStatusCode());

        /**
         * delete created profile to allow access
         */
        HttpHeaders headers = stringResponseEntity.getHeaders();
        URI location = headers.getLocation();
        String s = location.toString();
        ResponseEntity<String> stringResponseEntity1 = deleteProfile(Long.valueOf(s));
        Assert.assertEquals(HttpStatus.OK, stringResponseEntity1.getStatusCode());

        ResponseEntity<String> exchange2 = restTemplate.exchange(url2, HttpMethod.GET, hashMapHttpEntity1, String.class);
        Assert.assertEquals(HttpStatus.OK, exchange2.getStatusCode());

    }

    private ResponseEntity<DefaultOAuth2AccessToken> getPwdTokenResponse(String grantType, String clientId, String clientSecret, String username, String pwd) {
        String url = "http://localhost:" + randomServerPort + "/" + "token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("username", username);
        params.add("password", pwd);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, DefaultOAuth2AccessToken.class);
    }

    private ResponseEntity<String> createProfile(SecurityProfile securityProfile) {
        ResponseEntity<DefaultOAuth2AccessToken> pwdTokenResponse2 = getPwdTokenResponse(password, login_clientId, "", username_root, userPwd);
        String bearer1 = pwdTokenResponse2.getBody().getValue();
        String url = "http://localhost:" + randomServerPort + "/proxy/security" + "/profile";
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        HttpEntity<SecurityProfile> hashMapHttpEntity1 = new HttpEntity<>(securityProfile, headers1);
        return restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity1, String.class);
    }

    private ResponseEntity<String> updateProfile(SecurityProfile securityProfile, Long id) {
        ResponseEntity<DefaultOAuth2AccessToken> pwdTokenResponse2 = getPwdTokenResponse(password, login_clientId, "", username_root, userPwd);
        String bearer1 = pwdTokenResponse2.getBody().getValue();
        String url = "http://localhost:" + randomServerPort + "/proxy/security" + "/profile/" + id;
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        HttpEntity<SecurityProfile> hashMapHttpEntity1 = new HttpEntity<>(securityProfile, headers1);
        return restTemplate.exchange(url, HttpMethod.PUT, hashMapHttpEntity1, String.class);
    }

    private ResponseEntity<String> deleteProfile(Long id) {
        ResponseEntity<DefaultOAuth2AccessToken> pwdTokenResponse2 = getPwdTokenResponse(password, login_clientId, "", username_root, userPwd);
        String bearer1 = pwdTokenResponse2.getBody().getValue();
        String url = "http://localhost:" + randomServerPort + "/proxy/security" + "/profile/" + id;
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(bearer1);
        HttpEntity<Object> hashMapHttpEntity1 = new HttpEntity<>(headers1);
        return restTemplate.exchange(url, HttpMethod.DELETE, hashMapHttpEntity1, String.class);
    }
}
