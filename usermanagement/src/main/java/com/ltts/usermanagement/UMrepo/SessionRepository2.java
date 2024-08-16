package com.ltts.usermanagement.UMrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.usermanagement.UMentity.Session;

public interface SessionRepository2 extends JpaRepository<Session, Long> {
    List<Session> findByUserId(Long userId);
}