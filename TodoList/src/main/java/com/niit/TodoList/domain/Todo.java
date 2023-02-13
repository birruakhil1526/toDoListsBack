package com.niit.TodoList.domain;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Todo {
    private String todoTitle;
    private String todoDescription;
    private String category;
    private String dueDate;
    private String priority;
    private boolean favorite;
}
