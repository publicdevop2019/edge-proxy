package com.hw.controller;

import com.hw.entity.SecurityProfile;
import com.hw.services.SecurityProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("proxy/security")
public class SecurityProfileController {

    @Autowired
    SecurityProfileServiceImpl securityProfileService;

    @PostMapping("profile")
    public ResponseEntity<?> create(@Valid @RequestBody SecurityProfile securityProfile, HttpServletRequest request) {
        return ResponseEntity.ok().header("Location", String.valueOf(securityProfileService.create(securityProfile, request).getId())).build();
    }

    @GetMapping("profiles")
    public List<SecurityProfile> readAll(HttpServletRequest request) {
        return securityProfileService.readAll(request);
    }

    @PutMapping("profile/{id}")
    public ResponseEntity<?> replace(@Valid @RequestBody SecurityProfile securityProfile, @PathVariable Long id, HttpServletRequest request) {
        securityProfileService.replace(securityProfile, id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("profile/batch/url")
    public ResponseEntity<?> batchUpdateUrl(@RequestParam("host") String host, @RequestParam("ids") String ids, HttpServletRequest request) {
        securityProfileService.batchUpdateUrl(host, ids, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("profile/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        securityProfileService.delete(id, request);
        return ResponseEntity.ok().build();
    }

}
