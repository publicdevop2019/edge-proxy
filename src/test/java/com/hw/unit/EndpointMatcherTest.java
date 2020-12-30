package com.hw.unit;

import com.hw.config.SecurityProfileMatcher;
import com.hw.config.filter.EndpointFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class EndpointMatcherTest {

    @Test
    public void getSecurityProfileWhenMultipleSecurityProfileFound() {
        EndpointFilter.EndpointCardRep appBizEndpointCardRep = new EndpointFilter.EndpointCardRep();
        appBizEndpointCardRep.setPath("/api/profiles/100");
        EndpointFilter.EndpointCardRep appBizEndpointCardRep2 = new EndpointFilter.EndpointCardRep();
        appBizEndpointCardRep2.setPath("/api/profiles/**");
        Optional<EndpointFilter.EndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        EndpointFilter.EndpointCardRep appBizEndpointCardRep = new EndpointFilter.EndpointCardRep();
        appBizEndpointCardRep.setPath("/api/profiles/**/addresses");
        Optional<EndpointFilter.EndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        EndpointFilter.EndpointCardRep appBizEndpointCardRep = new EndpointFilter.EndpointCardRep();
        appBizEndpointCardRep.setPath("/api/profiles/**/addresses");
        EndpointFilter.EndpointCardRep appBizEndpointCardRep2 = new EndpointFilter.EndpointCardRep();
        appBizEndpointCardRep2.setPath("/api/profiles/**/addresses/**");
        Optional<EndpointFilter.EndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }
}
