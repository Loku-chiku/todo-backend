package com.todos.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todos.springboot.entity.Role;
import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);

}
