package com.hw.aggregate.endpoint;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BizEndpointRepo extends JpaRepository<BizEndpoint, Long> {
    List<BizEndpoint> findByResourceId(String resourceID);
}
