package com.ltts.usermanagement.UMrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltts.usermanagement.UMentity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

