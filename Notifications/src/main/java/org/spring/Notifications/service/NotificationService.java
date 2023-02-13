package org.spring.Notifications.service;

import org.json.simple.JSONObject;
import org.spring.Notifications.config.NotificationDTO;
import org.spring.Notifications.domain.Notification;
import org.spring.Notifications.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @RabbitListener(queues = "todo-queue")
    public void saveNotification(NotificationDTO notificationDTO){
        Notification notification = new Notification();
        System.out.println(notificationDTO.getJsonObject());
        String email = notificationDTO.getJsonObject().get("email").toString();
        if(notificationRepository.findById(email).isEmpty()){
            notification.setEmail(email);
        }
        notification.setMessage("Todo Deleted Successfully");
        notification.setTodoLists(notificationDTO.getJsonObject());
        notificationRepository.save(notification);

//        String email=notificationDTO.getJsonObject().get("email").toString();
//        Notification notification = null;
//        if(notificationRepository.findById(email).isEmpty()){
//             notification=new Notification();
//             notification.setEmail(email);
//             notification.setTodoLists(notificationDTO.getJsonObject());
//             notificationRepository.save(notification);
//        }
//        else{
//            notification=new Notification();
////          notification.setEmail(email);
//            notification.setTodoLists(notificationDTO.getJsonObject());
//            notificationRepository.save(notification);
//        }
    }

    public Notification getAlldeletedTodos(String email){
       Notification todos=  notificationRepository.findById(email).get();
       return todos;
    }
     public List<Notification> getall(){
        List<Notification> alltodos = notificationRepository.findAll();
        return alltodos;
     }


}
