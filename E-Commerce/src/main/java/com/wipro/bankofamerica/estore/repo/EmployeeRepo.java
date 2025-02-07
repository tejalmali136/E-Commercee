package com.wipro.bankofamerica.estore.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.bankofamerica.estore.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	public Optional<Employee> findById(Integer Id);

}
