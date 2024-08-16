package com.toolDataToDb.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toolDataToDb.Model.ProcessData;

@Repository
public interface ToolRepository extends JpaRepository<ProcessData, Integer> {
	
	List<ProcessData> findByProcessInstanceidAndToolname(String processInstanceId, String Toolname);

}
