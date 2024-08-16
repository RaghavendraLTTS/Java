package com.ltts.toolData.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.toolData.model.Projects;


public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
}