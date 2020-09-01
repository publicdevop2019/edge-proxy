package com.hw.unit;

import com.hw.aggregate.endpoint.exception.DuplicateEndpointException;
import com.hw.config.InternalForwardHelper;
import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.BizEndpointRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.hw.aggregate.endpoint.RootBizEndpointApplicationService;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BizRootBizEndpointApplicationServiceTest {
    @InjectMocks
    RootBizEndpointApplicationService securityProfileService = new RootBizEndpointApplicationService();

    @Mock
    BizEndpointRepo securityProfileRepo;

    @Mock
    InternalForwardHelper internalForwardHelper;

    @Test(expected = DuplicateEndpointException.class)
    public void same_profile_can_not_be_added_twice() {
        BizEndpoint stored = getSecurityProfile();
        Mockito.doReturn(Arrays.asList(stored)).when(securityProfileRepo).findByResourceId(any(String.class));
        securityProfileService.create(stored, null);
    }

    @Test
    public void create_profile() {
        BizEndpoint storedProfile = getSecurityProfile();
        BizEndpoint newProfile = getSecurityProfile();
        Mockito.doReturn(Arrays.asList(storedProfile)).when(securityProfileRepo).findByResourceId(any(String.class));
        securityProfileService.create(newProfile, null);
    }

    private BizEndpoint getSecurityProfile() {
        BizEndpoint sp = new BizEndpoint();
        sp.setPath(UUID.randomUUID().toString());
        sp.setExpression(UUID.randomUUID().toString());
        sp.setMethod(UUID.randomUUID().toString());
        sp.setResourceId(UUID.randomUUID().toString());
        return sp;
    }
}
