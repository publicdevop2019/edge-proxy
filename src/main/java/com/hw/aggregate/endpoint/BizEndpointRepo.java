package com.hw.aggregate.endpoint;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BizEndpointRepo extends JpaRepository<BizEndpoint, Long> {
}
