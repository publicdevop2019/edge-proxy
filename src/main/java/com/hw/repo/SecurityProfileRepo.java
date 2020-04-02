package com.hw.repo;

import com.hw.entity.SecurityProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.List;

public interface SecurityProfileRepo extends JpaRepository<SecurityProfile, Long> {
    List<SecurityProfile> findByResourceId(String resourceID);
    @Query("UPDATE SecurityProfile SET host = 'abc' WHERE id IN (0,1)")
    void batchUpdateUrl(String resourceID);

}
