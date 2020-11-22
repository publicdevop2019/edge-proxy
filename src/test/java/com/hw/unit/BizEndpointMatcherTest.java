package com.hw.unit;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
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
        AppBizEndpointCardRep appBizEndpointCardRep = new AppBizEndpointCardRep(securityProfile);
        BizEndpoint securityProfile2 = new BizEndpoint();
        securityProfile2.setPath("/api/profiles/**");
        AppBizEndpointCardRep appBizEndpointCardRep2 = new AppBizEndpointCardRep(securityProfile2);
        Optional<AppBizEndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        BizEndpoint securityProfile = new BizEndpoint();
        securityProfile.setPath("/api/profiles/**/addresses");
        AppBizEndpointCardRep appBizEndpointCardRep = new AppBizEndpointCardRep(securityProfile);
        Optional<AppBizEndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        BizEndpoint securityProfile = new BizEndpoint();
        securityProfile.setPath("/api/profiles/**/addresses");
        AppBizEndpointCardRep appBizEndpointCardRep = new AppBizEndpointCardRep(securityProfile);
        BizEndpoint securityProfile2 = new BizEndpoint();
        securityProfile2.setPath("/api/profiles/**/addresses/**");
        AppBizEndpointCardRep appBizEndpointCardRep2 = new AppBizEndpointCardRep(securityProfile2);
        Optional<AppBizEndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }
}
