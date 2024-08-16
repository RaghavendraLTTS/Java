package com.ltts.toolData.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.toolData.model.ToolConfig;


public interface ToolRepo extends JpaRepository<ToolConfig, String> {
}
