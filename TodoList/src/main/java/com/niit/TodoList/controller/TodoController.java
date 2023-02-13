package com.niit.TodoList.controller;

import com.niit.TodoList.Exception.UserAlreadyExistsException;
import com.niit.TodoList.Exception.UserNotFoundException;
import com.niit.TodoList.domain.Todo;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v2")
//@CrossOrigin(origins = "http://localhost:4200/")

public class TodoController {
    @Autowired
    private ITodoService todoService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        System.out.println("getting the request..");
        return new ResponseEntity<>(todoService.registerUser(user), HttpStatus.OK);
    }

    @PostMapping("/todo/{email}")
    public ResponseEntity<?> saveTodoToUser(@RequestBody Todo todo, @PathVariable String email) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.addTodotoUser(todo,email), HttpStatus.OK);
    }

    @GetMapping("/allTodos")
    public ResponseEntity<?> getAllTodos(){
        return new ResponseEntity<>(todoService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/getone/{email}")
    public ResponseEntity<?> oneUserTodos(@PathVariable String email) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.oneUser(email), HttpStatus.OK);
    }

    @DeleteMapping("/deleteallTodo/{email}/{todo}")
    public ResponseEntity<?> deleteTodoFromUser(@PathVariable String email, @PathVariable String todo) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.deleteTodo(email, todo), HttpStatus.OK);
    }

    @GetMapping("/todos/{priority}/{email}")
    public ResponseEntity<?> getBasedPriority(@PathVariable String priority, @PathVariable String email){
        return new ResponseEntity<>(todoService.getAllBasedOnPriority(priority, email), HttpStatus.OK);
    }

    @GetMapping("/imp/{email}/{title}")
    public ResponseEntity<?> impTodos(@PathVariable String email, @PathVariable String title) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.addTodotoImportantList(title,email), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{email}/{title}")
    public ResponseEntity<?> deleteImpTodo(@PathVariable String email, @PathVariable String title) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.deleteImpTodo(email,title), HttpStatus.OK);
    }
    @GetMapping("/completedtodo/{email}/{title}")
    public ResponseEntity<?> completedTodos(@PathVariable String email,@PathVariable String title) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.addTodotoCompletedList(title,email),HttpStatus.OK);
    }

    @PutMapping("/updatetodo/{email}")
    public ResponseEntity<?> updateTodos(@RequestBody Todo todo, @PathVariable String email){
        return new ResponseEntity<>(todoService.updateTodo(todo, email),HttpStatus.OK);
    }

    @GetMapping("/archivetodo/{email}/{title}")
    public ResponseEntity<?> addTodoToArchive(@PathVariable String email,@PathVariable String title) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.addTodotoArchive(title,email),HttpStatus.OK);
    }

    @DeleteMapping("/unarchivetodo/{email}/{title}")
    public ResponseEntity<?> unArchiveTodo(@PathVariable String email, @PathVariable String title) throws UserNotFoundException {
        return new ResponseEntity<>(todoService.unArchiveTodo(email,title), HttpStatus.OK);
    }



}
