package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.model.RevokeResourceOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeResourceOwnerRepo extends JpaRepository<RevokeResourceOwner, Long> {
    RevokeResourceOwner findByGlobalId(String name);
}
