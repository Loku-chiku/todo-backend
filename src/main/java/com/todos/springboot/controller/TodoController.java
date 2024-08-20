package com.todos.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todos.springboot.dto.TodoDto;
import com.todos.springboot.service.TodoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	//Build Add TODO REST API
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
		
		TodoDto savedTodo = todoService.addTodo(todoDto);
		
		return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
	}
	
	//Build Get TODO REST API
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
		
		TodoDto todoDto = todoService.getTodo(id);
		
		return ResponseEntity.ok(todoDto);
	}
	
	//Build Get All TODOS REST API
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<TodoDto>> getAllTodos(){
		
		List<TodoDto> todos = todoService.getAllTodos();
		
		return ResponseEntity.ok(todos);
	}
	
	//Build Update TODO REST API
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId){
		
		TodoDto updatedTodo = todoService.updateTodo(todoDto, todoId);
		
		return ResponseEntity.ok(updatedTodo);
	}
	
	//Build Delete TODO REST API
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
		
		todoService.deleteTodo(todoId);
		
		return ResponseEntity.ok("Todo deleted successfully!!");
	}
	
	//Build Complete TODO REST API
	@PatchMapping("{id}/complete")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
		
		TodoDto todoDto = todoService.completeTodo(todoId);
		
		return ResponseEntity.ok(todoDto);
	}
	
	//Build In Complete TODO REST API
		@PatchMapping("{id}/in-complete")
		@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
		public ResponseEntity<TodoDto> IncompleteTodo(@PathVariable("id") Long todoId){
			
			TodoDto todoDto = todoService.inCompleteTodo(todoId);
			
			return ResponseEntity.ok(todoDto);
		}


}
