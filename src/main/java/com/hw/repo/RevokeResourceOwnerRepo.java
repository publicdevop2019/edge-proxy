package com.hw.repo;

import com.hw.entity.RevokeResourceOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeResourceOwnerRepo extends JpaRepository<RevokeResourceOwner, Long> {
    RevokeResourceOwner findByName(String name);
}
