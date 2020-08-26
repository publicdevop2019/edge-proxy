package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.model.RevokeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeClientRepo extends JpaRepository<RevokeClient, Long> {
    RevokeClient findByGlobalId(String global);
}
