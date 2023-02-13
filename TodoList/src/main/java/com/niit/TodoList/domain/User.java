package com.niit.TodoList.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class User {

    @Id
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private List<Todo> todoList=new ArrayList<>();
    private List<Todo> importantTodo = new ArrayList<>();
    private List<Todo> completedTodos = new ArrayList<>();
    private List<Todo> archiveTodos=new ArrayList<>();
}
