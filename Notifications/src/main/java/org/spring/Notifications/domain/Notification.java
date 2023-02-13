package org.spring.Notifications.domain;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Notification {
    @Id
    private String email;
    private String message;
    private JSONObject todoLists;

    public Notification(String email, String message, JSONObject todoLists) {
        this.email = email;
        this.message = message;
        this.todoLists = todoLists;
    }

    public Notification() {
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(JSONObject todoLists) {
        this.todoLists = todoLists;
    }
}
