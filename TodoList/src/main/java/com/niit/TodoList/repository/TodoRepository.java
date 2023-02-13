package com.niit.TodoList.repository;

import com.niit.TodoList.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<User, String> {
}
