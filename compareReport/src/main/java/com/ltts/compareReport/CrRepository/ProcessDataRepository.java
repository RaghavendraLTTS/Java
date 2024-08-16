package com.ltts.compareReport.CrRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.compareReport.CrEntity.ProcessData;

public interface ProcessDataRepository extends JpaRepository<ProcessData, Long> {

    List<ProcessData> findByToolnameAndTimestampBetween(String toolname, LocalDateTime startTime, LocalDateTime endTime);

}

