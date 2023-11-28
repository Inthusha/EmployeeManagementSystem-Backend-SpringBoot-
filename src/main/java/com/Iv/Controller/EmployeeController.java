package com.Iv.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Iv.exception.ResourceNotFoundException;
import com.Iv.model.Employee;
import com.Iv.repository.EmployeeRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController 
{
	@Autowired
    private EmployeeRepository employeeRepo;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		
		return employeeRepo.findAll();
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
	{
		Employee employee= employeeRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employees not exist with id:"+id));
	    return ResponseEntity.ok(employee);
	    
	
	}
	
	
	//post 
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepo.save(employee);
	}
	
	//put
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails)
	{
		Employee updateEmployee=employeeRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employees not exist with id:"+id));
		
		updateEmployee.setFirstName(employeeDetails.getFirstName());
		updateEmployee.setLastName(employeeDetails.getLastName());
		updateEmployee.setEmailId(employeeDetails.getEmailId());
		
		employeeRepo.save(updateEmployee);
		
		return ResponseEntity.ok(updateEmployee);
		
	}
	
	//delete
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable  long id)
	{
		Employee employee=employeeRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employees not exist with id:"+id));
		
		employeeRepo.delete(employee);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
