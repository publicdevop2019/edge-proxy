package com.hw.repo;

import com.hw.entity.SecurityProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SecurityProfileRepo extends JpaRepository<SecurityProfile, Long> {
    List<SecurityProfile> findByResourceId(String resourceID);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE security_profile_list SET host = :host WHERE id IN (:ids)")
    void batchUpdateUrl(@Param("host") String host, @Param("ids") List<Long> ids);
}
