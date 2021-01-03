package com.mt.unit;

import com.mt.edgeproxy.infrastructure.EndpointFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class EndpointMatcherTest {

    @Test
    public void getSecurityProfileWhenMultipleSecurityProfileFound() {
        EndpointFilter.EndpointCardRepresentation appBizEndpointCardRep = new EndpointFilter.EndpointCardRepresentation();
        appBizEndpointCardRep.setPath("/api/profiles/100");
        EndpointFilter.EndpointCardRepresentation appBizEndpointCardRep2 = new EndpointFilter.EndpointCardRepresentation();
        appBizEndpointCardRep2.setPath("/api/profiles/**");
        Optional<EndpointFilter.EndpointCardRepresentation> mostSpecificSecurityProfile = EndpointFilter.EndpointMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        EndpointFilter.EndpointCardRepresentation appBizEndpointCardRep = new EndpointFilter.EndpointCardRepresentation();
        appBizEndpointCardRep.setPath("/api/profiles/**/addresses");
        Optional<EndpointFilter.EndpointCardRepresentation> mostSpecificSecurityProfile = EndpointFilter.EndpointMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        EndpointFilter.EndpointCardRepresentation appBizEndpointCardRep = new EndpointFilter.EndpointCardRepresentation();
        appBizEndpointCardRep.setPath("/api/profiles/**/addresses");
        EndpointFilter.EndpointCardRepresentation appBizEndpointCardRep2 = new EndpointFilter.EndpointCardRepresentation();
        appBizEndpointCardRep2.setPath("/api/profiles/**/addresses/**");
        Optional<EndpointFilter.EndpointCardRepresentation> mostSpecificSecurityProfile = EndpointFilter.EndpointMatcher.getMostSpecificSecurityProfile(List.of(appBizEndpointCardRep, appBizEndpointCardRep2));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getPath());
    }
}
