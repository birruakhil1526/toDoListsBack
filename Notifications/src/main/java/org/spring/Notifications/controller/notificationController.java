package org.spring.Notifications.controller;

import org.spring.Notifications.repository.NotificationRepository;
import org.spring.Notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class notificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("notification/{email}")
    public ResponseEntity<?> getAllDeletedTodos(@PathVariable  String email){
        return new ResponseEntity<>(notificationService.getAlldeletedTodos(email), HttpStatus.OK);
    }

    @GetMapping("notifications")
    public ResponseEntity<?> getall(){
        return new ResponseEntity<>(notificationService.getall(), HttpStatus.OK);
    }

}
