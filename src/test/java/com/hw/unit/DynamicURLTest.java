package com.hw.unit;

import com.hw.entity.SecurityProfile;
import com.hw.filter.DynamicRouteFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DynamicURLTest {

    @Test
    public void getDynamicUrlParamsNoParams() {
        DynamicRouteFilter dynamicRouteFilter = new DynamicRouteFilter();
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles");
        List<String> dynamicUrlParams = dynamicRouteFilter.getDynamicUrlParams("/api/profiles", securityProfile);
        Assert.assertEquals(0, dynamicUrlParams.size());

        securityProfile.convertToURIFromString("http://localhost:8082/v1/api/profiles");
        String s = dynamicRouteFilter.updateTargetUrl(dynamicUrlParams, securityProfile.retrieveURIString());

        Assert.assertEquals(securityProfile.retrieveURIString(), s);
    }

    @Test
    public void getDynamicUrlParamsOnlyOneAtEnd() {
        DynamicRouteFilter dynamicRouteFilter = new DynamicRouteFilter();
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**");
        List<String> dynamicUrlParams = dynamicRouteFilter.getDynamicUrlParams("/api/profiles/100", securityProfile);
        Assert.assertEquals("100", dynamicUrlParams.get(0));

        securityProfile.convertToURIFromString("http://localhost:8082/v1/api/profiles/**");
        String s = dynamicRouteFilter.updateTargetUrl(dynamicUrlParams, securityProfile.retrieveURIString());

        Assert.assertEquals("http://localhost:8082/v1/api/profiles/100", s);
    }

    @Test
    public void getDynamicUrlParamsMiddle() {
        DynamicRouteFilter dynamicRouteFilter = new DynamicRouteFilter();
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**/addresses");
        List<String> dynamicUrlParams = dynamicRouteFilter.getDynamicUrlParams("/api/profiles/100/addresses", securityProfile);
        Assert.assertEquals("100", dynamicUrlParams.get(0));

        securityProfile.convertToURIFromString("http://localhost:8082/v1/api/profiles/**/addresses");
        String s = dynamicRouteFilter.updateTargetUrl(dynamicUrlParams, securityProfile.retrieveURIString());

        Assert.assertEquals("http://localhost:8082/v1/api/profiles/100/addresses", s);
    }

    @Test
    public void getDynamicUrlParamsMiddleAndEnd() {
        DynamicRouteFilter dynamicRouteFilter = new DynamicRouteFilter();
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**/addresses/**");
        List<String> dynamicUrlParams = dynamicRouteFilter.getDynamicUrlParams("/api/profiles/100/addresses/101", securityProfile);
        Assert.assertEquals("100", dynamicUrlParams.get(0));
        Assert.assertEquals("101", dynamicUrlParams.get(1));

        securityProfile.convertToURIFromString("http://localhost:8082/v1/api/profiles/**/addresses/**");
        String s = dynamicRouteFilter.updateTargetUrl(dynamicUrlParams, securityProfile.retrieveURIString());

        Assert.assertEquals("http://localhost:8082/v1/api/profiles/100/addresses/101", s);
    }

    @Test
    public void getDynamicUrlParamsMiddleAndEndThenMiddleEnd() {
        DynamicRouteFilter dynamicRouteFilter = new DynamicRouteFilter();
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**/addresses/**/city/**");
        List<String> dynamicUrlParams = dynamicRouteFilter.getDynamicUrlParams("/api/profiles/100/addresses/101/city/102", securityProfile);
        Assert.assertEquals("100", dynamicUrlParams.get(0));
        Assert.assertEquals("101", dynamicUrlParams.get(1));
        Assert.assertEquals("102", dynamicUrlParams.get(2));

        securityProfile.convertToURIFromString("http://localhost:8082/v1/api/profiles/**/addresses/**/city/**");
        String s = dynamicRouteFilter.updateTargetUrl(dynamicUrlParams, securityProfile.retrieveURIString());

        Assert.assertEquals("http://localhost:8082/v1/api/profiles/100/addresses/101/city/102", s);
    }
}
