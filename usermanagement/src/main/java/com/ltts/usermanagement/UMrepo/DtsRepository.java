package com.ltts.usermanagement.UMrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ltts.usermanagement.UMentity.dts;


public interface DtsRepository extends JpaRepository<dts, Long> {
    @Query("SELECT d FROM dts d WHERE d.exitTime IS NULL ORDER BY d.entryTime DESC")
    List<dts> findTopByExitTimeIsNullOrderByEntryTimeDesc();
}
