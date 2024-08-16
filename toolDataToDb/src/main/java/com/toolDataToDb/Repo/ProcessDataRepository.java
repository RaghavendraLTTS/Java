package com.toolDataToDb.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.toolDataToDb.Model.ProcessData;

public interface ProcessDataRepository extends JpaRepository<ProcessData, Long> {
}