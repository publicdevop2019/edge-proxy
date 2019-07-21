package com.hw.controller;

import com.hw.entity.SecurityProfile;
import com.hw.repo.SecurityProfileRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("proxy/security")
@PreAuthorize("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust')")
public class SecurityProfileController {

    @Autowired
    SecurityProfileRepo securityProfileRepo;

    @PostMapping("profile")
    public ResponseEntity<?> create(@Valid @RequestBody SecurityProfile securityProfile, HttpServletRequest request) {
        BlacklistController.blacklisted(request);
        SecurityProfile save = securityProfileRepo.save(securityProfile);
        return ResponseEntity.ok().header("Location", String.valueOf(save.getId())).build();
    }

    @GetMapping("profiles")
    public List<SecurityProfile> readAll(HttpServletRequest request) {
        BlacklistController.blacklisted(request);
        return securityProfileRepo.findAll();
    }

    @PutMapping("profile/{id}")
    public ResponseEntity<?> replace(@Valid @RequestBody SecurityProfile securityProfile, @PathVariable Long id, HttpServletRequest request) {
        BlacklistController.blacklisted(request);
        Optional<SecurityProfile> byId = securityProfileRepo.findById(id);
        if (byId.isEmpty())
            return ResponseEntity.badRequest().build();
        BeanUtils.copyProperties(securityProfile, byId.get());
        securityProfileRepo.save(byId.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("profile/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        BlacklistController.blacklisted(request);
        Optional<SecurityProfile> byId = securityProfileRepo.findById(id);
        if (byId.isEmpty())
            return ResponseEntity.badRequest().build();
        securityProfileRepo.delete(byId.get());
        return ResponseEntity.ok().build();
    }
}
