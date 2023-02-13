package com.niit.TodoList.proxy;

import com.niit.TodoList.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthenticationApp", url = "localhost:8084")
public interface ProxyAuthentication {
    @PostMapping("/v1/register")
    public ResponseEntity<?> saveUsertoAuthentication(@RequestBody User u);

}
