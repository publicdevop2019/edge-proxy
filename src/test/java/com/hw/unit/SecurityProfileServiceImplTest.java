package com.hw.unit;

import com.hw.clazz.InternalForwardHelper;
import com.hw.entity.SecurityProfile;
import com.hw.repo.SecurityProfileRepo;
import com.hw.shared.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.hw.services.SecurityProfileServiceImpl;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class SecurityProfileServiceImplTest {
    @InjectMocks
    SecurityProfileServiceImpl securityProfileService = new SecurityProfileServiceImpl();

    @Mock
    SecurityProfileRepo securityProfileRepo;

    @Mock
    InternalForwardHelper internalForwardHelper;

    @Test(expected = BadRequestException.class)
    public void same_profile_can_not_be_added_twice() {
        SecurityProfile stored = getSecurityProfile();
        Mockito.doReturn(Arrays.asList(stored)).when(securityProfileRepo).findByResourceId(any(String.class));
        securityProfileService.create(stored, null);
    }

    @Test
    public void create_profile() {
        SecurityProfile storedProfile = getSecurityProfile();
        SecurityProfile newProfile = getSecurityProfile();
        Mockito.doReturn(Arrays.asList(storedProfile)).when(securityProfileRepo).findByResourceId(any(String.class));
        securityProfileService.create(newProfile, null);
    }

    private SecurityProfile getSecurityProfile() {
        SecurityProfile sp = new SecurityProfile();
        sp.convertToURIFromString("http://ngform.com:8111/mockPath?search=mock&search2=mock2#frag1");
        sp.setLookupPath(UUID.randomUUID().toString());
        sp.setExpression(UUID.randomUUID().toString());
        sp.setMethod(UUID.randomUUID().toString());
        sp.setResourceId(UUID.randomUUID().toString());
        return sp;
    }
}
