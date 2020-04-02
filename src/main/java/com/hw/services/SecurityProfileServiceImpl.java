package com.hw.services;

import com.hw.clazz.InternalForwardHelper;
import com.hw.entity.SecurityProfile;
import com.hw.repo.SecurityProfileRepo;
import com.hw.shared.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecurityProfileServiceImpl {

    @Autowired
    SecurityProfileRepo securityProfileRepo;

    @Autowired
    InternalForwardHelper internalForwardHelper;

    /**
     * duplicated profiles are not allow bcz find most specific profile will fail
     * check if same profile exist, reject if exist
     *
     * @param securityProfile
     * @param request
     * @return
     */
    public SecurityProfile create(SecurityProfile securityProfile, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        if (securityProfileRepo.findByResourceId(securityProfile.getResourceId()).stream()
                .anyMatch(e -> e.getLookupPath().equalsIgnoreCase(securityProfile.getLookupPath()) &&
                        e.getExpression().equalsIgnoreCase(securityProfile.getExpression()) &&
                        e.getMethod().equalsIgnoreCase(securityProfile.getMethod()) &&
                        e.retrieveURIString().equalsIgnoreCase(securityProfile.retrieveURIString()))
        )
            throw new BadRequestException("same profile found");
        return securityProfileRepo.save(securityProfile);
    }

    public List<SecurityProfile> readAll(HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return securityProfileRepo.findAll();
    }

    public void replace(SecurityProfile securityProfile, Long id, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        Optional<SecurityProfile> byId = securityProfileRepo.findById(id);
        if (byId.isEmpty())
            throw new BadRequestException("unable to find profile");
        BeanUtils.copyProperties(securityProfile, byId.get());
        securityProfileRepo.save(byId.get());
    }

    public void batchUpdateUrl(Map<String, String> map, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        String ids = map.get("ids");
        securityProfileRepo.batchUpdateUrl(map.get("host"), Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList()));
    }

    public void delete(Long id, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        Optional<SecurityProfile> byId = securityProfileRepo.findById(id);
        if (byId.isEmpty())
            throw new BadRequestException("unable to find profile");
        securityProfileRepo.delete(byId.get());
    }
}
