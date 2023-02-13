package com.niit.TodoList.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User Already Exists in Database")
public class UserAlreadyExistsException extends Exception{
}
