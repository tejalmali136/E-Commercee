package com.wipro.bankofamerica.estore.service;

import java.util.Optional;

import com.wipro.bankofamerica.estore.model.Employee;

public interface EmployeeService {
	
	public Employee saveEmployee(Employee employee);
	
	public Optional<Employee> getEmployee(Integer Id);

}
