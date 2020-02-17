package com.hw.controller;

import com.hw.clazz.BlacklistException;
import com.hw.clazz.InternalForwardHelper;
import com.hw.entity.RevokeClient;
import com.hw.entity.RevokeResourceOwner;
import com.hw.repo.RevokeClientRepo;
import com.hw.repo.RevokeResourceOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Map;

/**
 * @note zuul own endpoints are getting internal forward,
 * internal forwarding is having issue with form data,
 */
@RestController
@RequestMapping("proxy/blacklist")
public class BlacklistController {

    @Autowired
    RevokeClientRepo revokeClientRepo;

    @Autowired
    RevokeResourceOwnerRepo revokeResourceOwnerRepo;

    @PostMapping("client")
    public ResponseEntity<?> revokeClient(@RequestBody Map<String, String> stringStringMap, HttpServletRequest request) {
        InternalForwardHelper.forwardCheck(request);
        String name1 = stringStringMap.get("name");
        if (name1 == null)
            return ResponseEntity.badRequest().body(new BlacklistException("name should not be empty"));
        long epochSecond = Instant.now().getEpochSecond();
        RevokeClient by = revokeClientRepo.findByName(name1);
        /**if exist update issuedAt other wise create*/
        if (by == null) {
            RevokeClient revokeClient = new RevokeClient();
            revokeClient.setIssuedAt(epochSecond);
            revokeClient.setName(name1);
            revokeClientRepo.save(revokeClient);
        } else {
            by.setIssuedAt(epochSecond);
            revokeClientRepo.save(by);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("resourceOwner")
    public ResponseEntity<?> revokeResourceOwner(@RequestBody Map<String, String> stringStringMap, HttpServletRequest request) {
        InternalForwardHelper.forwardCheck(request);
        String name1 = stringStringMap.get("name");
        if (name1 == null)
            return ResponseEntity.badRequest().body(new BlacklistException("name should not be empty"));
        long epochSecond = Instant.now().getEpochSecond();
        RevokeResourceOwner byName = revokeResourceOwnerRepo.findByName(name1);
        /**if exist update issuedAt other wise create*/
        if (byName == null) {
            RevokeResourceOwner revokeResourceOwner = new RevokeResourceOwner();
            revokeResourceOwner.setIssuedAt(epochSecond);
            revokeResourceOwner.setName(name1);
            revokeResourceOwnerRepo.save(revokeResourceOwner);
        } else {
            byName.setIssuedAt(epochSecond);
            revokeResourceOwnerRepo.save(byName);
        }
        return ResponseEntity.ok().build();
    }

}
