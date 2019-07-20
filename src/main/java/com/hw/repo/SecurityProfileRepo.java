package com.hw.repo;

import com.hw.entity.RevokeClient;
import com.hw.entity.SecurityProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityProfileRepo extends JpaRepository<SecurityProfile, Long> {
    RevokeClient findByResourceID(String resourceID);
}
