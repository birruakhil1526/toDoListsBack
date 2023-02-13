package org.spring.UserAuthentication.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.spring.UserAuthentication.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityTokenGenerator implements ISecurityTokenGenerator{
    @Override
    public Map<String, String> tokenGenerator(User user) {
        String JwtToken;
        JwtToken= Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"authenticator").compact();
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",JwtToken);
        tokenMap.put("email", user.getEmail());
        tokenMap.put("message","User successfully logged");
        return tokenMap;
    }
}
