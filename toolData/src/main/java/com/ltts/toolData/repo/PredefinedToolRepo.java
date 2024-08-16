package com.ltts.toolData.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ltts.toolData.model.PredefinedTools;


@Repository
public interface PredefinedToolRepo extends JpaRepository<PredefinedTools, String> {
	PredefinedTools findByToolname(String toolname);
}
