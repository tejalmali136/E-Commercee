package com.wipro.bankofamerica.estore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.bankofamerica.estore.model.Employee;
import com.wipro.bankofamerica.estore.service.EmployeeService;

import jakarta.persistence.Id;

@RestController
@RequestMapping("/empl")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		Employee saveEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.ok(saveEmployee);
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable("Id") Integer Id) {
		Optional<Employee> employee = employeeService.getEmployee(Id);
		return ResponseEntity.ok(employee);
	}

}
