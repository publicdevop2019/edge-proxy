package com.hw.controller;

import com.hw.entity.RevokeClient;
import com.hw.entity.RevokeResourceOwner;
import com.hw.repo.RevokeClientRepo;
import com.hw.repo.RevokeResourceOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("proxy/blacklist")
@PreAuthorize("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isClient()")
public class BlacklistController {

    @Autowired
    RevokeClientRepo revokeClientRepo;

    @Autowired
    RevokeResourceOwnerRepo revokeResourceOwnerRepo;

    @PostMapping("client")
    public ResponseEntity<?> revokeClient(@RequestParam() String name) {
        long epochSecond = Instant.now().getEpochSecond();
        RevokeClient by = revokeClientRepo.findByName(name);
        /**if exist update issuedAt other wise create*/
        if (by == null) {
            RevokeClient revokeClient = new RevokeClient();
            revokeClient.setIssuedAt(epochSecond);
            revokeClient.setName(name);
            revokeClientRepo.save(revokeClient);
        } else {
            by.setIssuedAt(epochSecond);
            revokeClientRepo.save(by);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("resourceOwner")
    public ResponseEntity<?> revokeResourceOwner(@RequestParam() String name) {
        long epochSecond = Instant.now().getEpochSecond();
        RevokeResourceOwner byName = revokeResourceOwnerRepo.findByName(name);
        /**if exist update issuedAt other wise create*/
        if (byName == null) {
            RevokeResourceOwner revokeResourceOwner = new RevokeResourceOwner();
            revokeResourceOwner.setIssuedAt(epochSecond);
            revokeResourceOwner.setName(name);
            revokeResourceOwnerRepo.save(revokeResourceOwner);
        } else {
            byName.setIssuedAt(epochSecond);
            revokeResourceOwnerRepo.save(byName);
        }
        return ResponseEntity.ok().build();
    }
}
