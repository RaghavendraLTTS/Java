package com.ltts.usermanagement.UMrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.usermanagement.UMentity.Session;
import com.ltts.usermanagement.UMentity.User;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findFirstByUserAndLogoutTimeIsNull(User user);// Find the first active session for the user
}