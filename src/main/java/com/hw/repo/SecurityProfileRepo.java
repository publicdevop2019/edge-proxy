package com.hw.repo;

import com.hw.entity.SecurityProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityProfileRepo extends JpaRepository<SecurityProfile, Long> {
    List<SecurityProfile> findByResourceID(String resourceID);
}
