package org.spring.UserAuthentication.service;

import org.spring.UserAuthentication.domain.User;
import org.spring.UserAuthentication.exception.PasswordMisMatchException;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> getAllUsers();
    String delete(String email);
    User loginCheck(String email,String password) throws PasswordMisMatchException;
}
