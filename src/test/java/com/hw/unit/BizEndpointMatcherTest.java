package com.hw.unit;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.config.SecurityProfileMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class BizEndpointMatcherTest {

    @Test
    public void getSecurityProfileWhenMultipleSecurityProfileFound() {
        BizEndpoint securityProfile = new BizEndpoint();
        securityProfile.setPath("/api/profiles/100");
        BizEndpoint securityProfile2 = new BizEndpoint();
        securityProfile2.setPath("/api/profiles/**");
        Optional<BizEndpoint> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        BizEndpoint securityProfile = new BizEndpoint();
        securityProfile.setPath("/api/profiles/**/addresses");
        Optional<BizEndpoint> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        BizEndpoint securityProfile = new BizEndpoint();
        securityProfile.setPath("/api/profiles/**/addresses");
        BizEndpoint securityProfile2 = new BizEndpoint();
        securityProfile2.setPath("/api/profiles/**/addresses/**");
        Optional<BizEndpoint> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }
}
