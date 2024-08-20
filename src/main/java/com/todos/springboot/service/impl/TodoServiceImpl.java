package com.todos.springboot.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todos.springboot.dto.TodoDto;
import com.todos.springboot.entity.Todo;
import com.todos.springboot.exception.ResourceNotFoundException;
import com.todos.springboot.repository.TodoRepository;
import com.todos.springboot.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TodoRepository todoRepository;

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		
		// Convert TodoDto into TODO jpa entity
		Todo todo = modelMapper.map(todoDto, Todo.class);
		
		Todo savedTodo = todoRepository.save(todo);
		
		// Convert saved TODO JPA entity object to TodoDto object
		TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
		
		return savedTodoDto;
	}

	@Override
	public TodoDto getTodo(Long id) {
		
		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo is not exists with id :" + id));
		
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {
		
		List<Todo> todos = todoRepository.findAll();

		return todos.stream().map(
				(todo) -> modelMapper.map(todo, TodoDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public TodoDto updateTodo(TodoDto todoDto, Long id) {

		Todo todo = todoRepository.findById(id)
		.orElseThrow(
				() -> new ResourceNotFoundException("Todo not found with id:"+id));
		
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setCompleted(todoDto.isCompleted());
		
		Todo updatedTodo = todoRepository.save(todo);
		
		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public void deleteTodo(Long id) {
		
		Todo todo = todoRepository.findById(id)
		.orElseThrow(
				() -> new ResourceNotFoundException("Todo not found with id : "+id));
		
		todoRepository.delete(todo);
		
	}

	@Override
	public TodoDto completeTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Todo not found with id : "+id));
		
		todo.setCompleted(Boolean.TRUE);
		
		Todo updatedTodo = todoRepository.save(todo);
		
		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public TodoDto inCompleteTodo(Long id) {
		
		Todo todo = todoRepository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Todo not found with id : "+id));
		
		todo.setCompleted(Boolean.FALSE);
		
		Todo updatedTodo = todoRepository.save(todo);
		
		return modelMapper.map(updatedTodo, TodoDto.class);
	}
}

