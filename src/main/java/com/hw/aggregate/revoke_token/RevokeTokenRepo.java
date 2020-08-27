package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.model.RevokeToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeTokenRepo extends JpaRepository<RevokeToken, Long> {
}
