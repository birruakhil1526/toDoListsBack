package com.niit.TodoList.service;

import com.niit.TodoList.Exception.UserAlreadyExistsException;
import com.niit.TodoList.Exception.UserNotFoundException;
import com.niit.TodoList.configuration.TodoDTO;
import com.niit.TodoList.domain.Todo;
import com.niit.TodoList.domain.User;

import com.niit.TodoList.proxy.ProxyAuthentication;
import com.niit.TodoList.repository.TodoRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoServiceImpl implements ITodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ProxyAuthentication proxyAuthentication;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (todoRepository.findById(user.getEmail()).isPresent()){
            System.out.println("exists");
            throw new UserAlreadyExistsException();
        }
        proxyAuthentication.saveUsertoAuthentication(user);
        System.out.println("after proxy");
        return todoRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return todoRepository.findAll();
    }

    @Override
    public User oneUser(String email) throws UserNotFoundException {
        if (todoRepository.findById(email).isPresent()){
        return todoRepository.findById(email).get();}
        else {
            throw new UserNotFoundException();
        }
    }


    @Override
    public User addTodotoUser(Todo todo, String email) throws UserNotFoundException {
        if (todoRepository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = todoRepository.findById(email).get();
        if(user.getTodoList()==null){
            user.setTodoList(new ArrayList<>());
        } else {
            List<Todo> todos = user.getTodoList();
            todos.add(todo);
            user.setTodoList(todos);
        }
        return todoRepository.save(user);
    }

    @Override
    public boolean deleteTodo(String email, String todo) throws UserNotFoundException {
        if (todoRepository.findById(email).isPresent()){
            List<Todo> rabbit = new ArrayList<>();
            User user = todoRepository.findById(email).get();
            List<Todo> allnormal = user.getTodoList();
            List<Todo> imptodo=user.getImportantTodo();
            Iterator<Todo> iterator=allnormal.iterator();
            Iterator<Todo> iterator1=imptodo.iterator();
            while (iterator.hasNext()){
                Todo t = iterator.next();
                if(t.getTodoTitle().equalsIgnoreCase(todo)){
                    TodoDTO todoDTO = new TodoDTO();
                    JSONObject jsonObject = new JSONObject();
                    rabbit.add(t);
                    jsonObject.put("Deletedtasks", rabbit);
                    jsonObject.put("email", email);
                    todoDTO.setJsonObject(jsonObject);
                    rabbitTemplate.convertAndSend(directExchange.getName(),"todo-routing", todoDTO);
                    iterator.remove();
                        while (iterator1.hasNext()){
                            if (iterator1.next().getTodoTitle().equalsIgnoreCase(todo)){
                                iterator1.remove();
                            }break;
                    }
                }
            }
            user.setImportantTodo(new ArrayList<>());
            user.setImportantTodo(imptodo);
            user.setTodoList(new ArrayList<>());
            user.setTodoList(allnormal);
            todoRepository.save(user);
            return true;
            }

        throw new UserNotFoundException();
    }

    @Override
    public List<Todo> getAllBasedOnPriority(String priority, String email) {
        User user = todoRepository.findById(email).get();
        List<Todo> list = user.getTodoList();
        List<Todo> newList= new ArrayList<>();
        for (Todo t: list){
            if (t.getPriority().equalsIgnoreCase(priority)){
                newList.add(t);
            }
        }
        System.out.println(newList);
        return newList;
    }

    @Override
    public User addTodotoImportantList(String title, String email) throws UserNotFoundException {
        if (todoRepository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = todoRepository.findById(email).get();
        if(user.getImportantTodo()==null){
            user.setImportantTodo(new ArrayList<>());
        } else {
            List<Todo> importantTodo = user.getImportantTodo();
            List<Todo> todos = user.getTodoList();
            for (Todo implTodo: todos){
                if (implTodo.getTodoTitle().equalsIgnoreCase(title)){
                    importantTodo.add(implTodo);
                    implTodo.setFavorite(true);
                }
            }
            user.setTodoList(todos);
            System.out.println(todos);
            user.setImportantTodo(importantTodo);
        }
        return todoRepository.save(user);
    }

    @Override
    public boolean deleteImpTodo(String email, String todo) throws UserNotFoundException {
        System.out.println(todo);
        System.out.println(email);
        if (todoRepository.findById(email).isPresent()) {
            User user = todoRepository.findById(email).get();
            List<Todo> allTodos = user.getTodoList();
            List<Todo> allImpTodos = user.getImportantTodo();
            System.out.println(allImpTodos);
            Iterator<Todo> iterator = allImpTodos.listIterator();
            Iterator<Todo> newTodo = allTodos.listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().getTodoTitle().equalsIgnoreCase(todo)) {
                    iterator.remove();
                    while (newTodo.hasNext()){
                        Todo t=newTodo.next();
                        if (t.getTodoTitle().equalsIgnoreCase(todo)){
                            t.setFavorite(false);
                        }
                    }
                }
            }
            user.setTodoList(new ArrayList<>());
            user.setTodoList(allTodos);
            user.setImportantTodo(new ArrayList<>());
            System.out.println(user.getImportantTodo());
            user.setImportantTodo(allImpTodos);
            System.out.println(user.getImportantTodo());
            todoRepository.save(user);
            return true;
        }
        throw new UserNotFoundException();
    }

    @Override
    public User addTodotoCompletedList(String title, String email) throws UserNotFoundException {
        if (todoRepository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = todoRepository.findById(email).get();
        if(user.getCompletedTodos()==null){
            user.setCompletedTodos(new ArrayList<>());
        } else{
//            List<Todo> archivedTodos=user.getArchiveTodos();
            List<Todo> completedTodos = user.getCompletedTodos();
            List<Todo> todos = user.getTodoList();
           Iterator<Todo> iterator = todos.listIterator();
           while (iterator.hasNext()){
               Todo itr = iterator.next();
               if (itr.getTodoTitle().equalsIgnoreCase(title)){
                   completedTodos.add(itr);
//                   archivedTodos.add(itr);
                   iterator.remove();
               }
           }
            user.setTodoList(new ArrayList<>());
            user.setTodoList(todos);
            user.setCompletedTodos(completedTodos);
        }
        return todoRepository.save(user);
    }

    @Override
    public User updateTodo(Todo t, String email) {
        System.out.println(t);
        System.out.println(email);
        User todolist= todoRepository.findById(email).get();
        if (todolist.getTodoList().isEmpty()){
            return null;
        }
        Todo oldtodo = null;
        List<Todo> data = todolist.getTodoList();
        for (Todo todo1: data){
            if ((todo1.getTodoTitle().equalsIgnoreCase(t.getTodoTitle()))
                    ||(todo1.getTodoDescription().equalsIgnoreCase(t.getTodoDescription()))){
                oldtodo=todo1;
            }
        }
        if (t.getTodoTitle()!=null){
            oldtodo.setTodoTitle(t.getTodoTitle());
        }
        if (t.getTodoDescription()!=null){
            oldtodo.setTodoDescription(t.getTodoDescription());
        }
        if (t.getCategory()!=null){
            oldtodo.setCategory(t.getCategory());
        }
        if (t.getDueDate()!=null){
            oldtodo.setDueDate(t.getDueDate());
        }
        if (t.getPriority()!=null){
            oldtodo.setPriority(t.getPriority());
        }
        return todoRepository.save(todolist);
    }

    @Override
    public User addTodotoArchive(String title, String email) throws UserNotFoundException {
        if (todoRepository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = todoRepository.findById(email).get();
        if(user.getArchiveTodos()==null){
            user.setArchiveTodos(new ArrayList<>());
        } else{
            List<Todo> archivedTodos=user.getArchiveTodos();
            List<Todo> todos = user.getTodoList();
            Iterator<Todo> iterator = todos.listIterator();
            while (iterator.hasNext()){
                Todo itr = iterator.next();
                if (itr.getTodoTitle().equalsIgnoreCase(title)){
                    archivedTodos.add(itr);
                    iterator.remove();
                }
            }
            user.setTodoList(new ArrayList<>());
            user.setTodoList(todos);
        }
        return todoRepository.save(user);
    }

    @Override
    public boolean unArchiveTodo(String email, String todo) throws UserNotFoundException {
        if (todoRepository.findById(email).isPresent()) {
            User user = todoRepository.findById(email).get();
            List<Todo> allTodos = user.getTodoList();
            List<Todo> allArchivedTodos = user.getArchiveTodos();
            Iterator<Todo> iterator = allArchivedTodos.listIterator();
            while (iterator.hasNext()) {
                Todo unArchive = iterator.next();
                if (unArchive.getTodoTitle().equalsIgnoreCase(todo)) {
                    allTodos.add(unArchive);
                    iterator.remove();
                    }
                }
            user.setTodoList(new ArrayList<>());
            user.setTodoList(allTodos);
            user.setArchiveTodos(new ArrayList<>());
            user.setArchiveTodos(allArchivedTodos);
            todoRepository.save(user);
            return true;
        }
        throw new UserNotFoundException();
    }

}
