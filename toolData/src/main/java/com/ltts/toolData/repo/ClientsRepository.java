package com.ltts.toolData.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.toolData.model.Clients;


public interface ClientsRepository extends JpaRepository<Clients, Integer> {
}