package org.spring.UserAuthentication.repository;

import org.spring.UserAuthentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
