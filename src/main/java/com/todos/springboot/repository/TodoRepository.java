package com.todos.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todos.springboot.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
