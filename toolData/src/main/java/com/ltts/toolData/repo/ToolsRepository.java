package com.ltts.toolData.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.toolData.model.Tools;


public interface ToolsRepository extends JpaRepository<Tools, Integer> {
}