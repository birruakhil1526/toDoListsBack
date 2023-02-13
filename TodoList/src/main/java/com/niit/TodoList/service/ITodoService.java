package com.niit.TodoList.service;

import com.niit.TodoList.Exception.UserAlreadyExistsException;
import com.niit.TodoList.Exception.UserNotFoundException;
import com.niit.TodoList.domain.Todo;
import com.niit.TodoList.domain.User;

import java.util.List;

public interface ITodoService {

    public User registerUser(User user) throws UserAlreadyExistsException;

    public List<User> getAllUsers();

    public User oneUser(String email) throws UserNotFoundException;

    public User addTodotoUser(Todo todo, String email) throws UserNotFoundException;

    public boolean deleteTodo(String email, String todo) throws UserNotFoundException;

    public List<Todo> getAllBasedOnPriority(String priority, String email);

    public User addTodotoImportantList(String title, String email) throws UserNotFoundException;

    public boolean deleteImpTodo(String email, String todo) throws UserNotFoundException;

    public User addTodotoCompletedList(String title, String email) throws UserNotFoundException;

    public User updateTodo(Todo t, String email);

    public User addTodotoArchive(String title, String email) throws UserNotFoundException;

    public boolean unArchiveTodo(String email, String todo) throws UserNotFoundException;
}
