package com.nilam.demo.service.repository;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.nilam.demo.model.Employee;

@Component(value="employeeRepo")
public class EmployeeRepo {

	@Autowired
	ProducerTemplate producerTemplate;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = producerTemplate.requestBody("direct:select", null, List.class);
		return employees;

	}

	public Employee insertEmployee(Employee employee) {
		//producerTemplate.requestBody("direct:insert", employee, List.class);
		return employee;
	}
}
