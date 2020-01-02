package com.hw.unit;

import com.hw.entity.SecurityProfile;
import com.hw.utility.SecurityProfileMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SecurityProfileMatcherTest {

    @Test
    public void getSecurityProfileWhenMultipleSecurityProfileFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setPath("/api/profiles/100");
        SecurityProfile securityProfile2 = new SecurityProfile();
        securityProfile2.setPath("/api/profiles/**");
        SecurityProfile mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.getPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setPath("/api/profiles/**/addresses");
        SecurityProfile mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.getPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setPath("/api/profiles/**/addresses");
        SecurityProfile securityProfile2 = new SecurityProfile();
        securityProfile2.setPath("/api/profiles/**/addresses/**");
        SecurityProfile mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.getPath());
    }
}
