package org.spring.UserAuthentication.service;

import org.spring.UserAuthentication.domain.User;
import org.spring.UserAuthentication.exception.PasswordMisMatchException;
import org.spring.UserAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        if(userRepository.findById(user.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String delete(String email) {
        if(userRepository.findById(email).isEmpty()){
            return "user not found";
        }
        userRepository.deleteById(email);
        return "user successfully deleted";
    }

    @Override
    public User loginCheck(String email, String password) throws PasswordMisMatchException {
        if (userRepository.findById(email).isPresent()){
            User user = userRepository.findById(email).get();
            if (user.getPassword().equals(password)){
                user.setPassword("PPP");
                return user;
            } else {
                throw new PasswordMisMatchException();
            }
        }
        return null;
    }
}
