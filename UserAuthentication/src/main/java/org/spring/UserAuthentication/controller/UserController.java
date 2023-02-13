package org.spring.UserAuthentication.controller;

import org.spring.UserAuthentication.domain.User;
import org.spring.UserAuthentication.exception.PasswordMisMatchException;
import org.spring.UserAuthentication.service.ISecurityTokenGenerator;
import org.spring.UserAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/")

//@CrossOrigin("http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ISecurityTokenGenerator iSecurityTokenGenerator;

    @PostMapping("register")
    public ResponseEntity<?> save(@RequestBody User user){
        System.out.println("getting request from proxy");
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @DeleteMapping("user/{Id}")
    public ResponseEntity<?> deleteUser(String email){
        return new ResponseEntity<>(userService.delete(email),HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<?> loginCheck(@RequestBody User user) throws PasswordMisMatchException {
        Map<String, String> error = new HashMap<>();
        error.put("message","invalid");
        User login=userService.loginCheck(user.getEmail(), user.getPassword());
        if(login!=null){
            Map<String,String> loginMap=iSecurityTokenGenerator.tokenGenerator(login);
            return new ResponseEntity<>(loginMap,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
