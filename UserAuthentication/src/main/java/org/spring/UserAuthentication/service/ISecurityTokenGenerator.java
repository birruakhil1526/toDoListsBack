package org.spring.UserAuthentication.service;

import org.spring.UserAuthentication.domain.User;

import java.util.Map;

public interface ISecurityTokenGenerator {
    Map<String,String> tokenGenerator(User user);
}
