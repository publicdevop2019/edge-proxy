package com.hw.unit;

import com.hw.entity.SecurityProfile;
import com.hw.utility.SecurityProfileMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class SecurityProfileMatcherTest {

    @Test
    public void getSecurityProfileWhenMultipleSecurityProfileFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/100");
        SecurityProfile securityProfile2 = new SecurityProfile();
        securityProfile2.setLookupPath("/api/profiles/**");
        Optional<SecurityProfile> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/100", mostSpecificSecurityProfile.get().getLookupPath());
    }

    @Test
    public void getSecurityProfileWhenOneFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**/addresses");
        Optional<SecurityProfile> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getLookupPath());
    }

    @Test
    public void getSecurityProfileWhenTwoFound() {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setLookupPath("/api/profiles/**/addresses");
        SecurityProfile securityProfile2 = new SecurityProfile();
        securityProfile2.setLookupPath("/api/profiles/**/addresses/**");
        Optional<SecurityProfile> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(List.of(securityProfile2, securityProfile));
        Assert.assertEquals("/api/profiles/**/addresses", mostSpecificSecurityProfile.get().getLookupPath());
    }
}
