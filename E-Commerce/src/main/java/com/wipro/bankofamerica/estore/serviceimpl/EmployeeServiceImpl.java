package com.wipro.bankofamerica.estore.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.bankofamerica.estore.model.Employee;
import com.wipro.bankofamerica.estore.repo.EmployeeRepo;
import com.wipro.bankofamerica.estore.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}

	@Override
	public Optional<Employee> getEmployee(Integer Id) {
		return employeeRepo.findById(Id);
		
	}

}
