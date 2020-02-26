package com.hw.repo;

import com.hw.entity.RevokeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeClientRepo extends JpaRepository<RevokeClient, Long> {
    RevokeClient findByGlobalId(String global);
}
