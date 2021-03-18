package com.nilam.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.nilam.demo.model.Employee;

//@Service
public class EmployeeServiceTxImpl extends RouteBuilder {
	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public SqlComponent sql(DataSource dataSource) {
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource);
		return sql;
	}

	// Create Transaction Manager
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// Specify Spring Transaction Management Policy
	@Bean(name = "PROPAGATION_REQUIRED")
	public SpringTransactionPolicy propogationRequired(PlatformTransactionManager transactionManager) {
		SpringTransactionPolicy propagationRequired = new SpringTransactionPolicy();
		propagationRequired.setTransactionManager(transactionManager);
		propagationRequired.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		return propagationRequired;
	}

	@Override
	public void configure() throws Exception {

		// Insert Route
		from("direct:insert").transacted("PROPAGATION_REQUIRED").log("Processing message: ").process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// take the Employee object from the exchange and create the parameter map
				Employee employee = xchg.getIn().getBody(Employee.class);
				Map<String, Object> employeeMap = new HashMap<String, Object>();
				employeeMap.put("EmpId", employee.getEmpId());
				employeeMap.put("EmpName", employee.getEmpName());
				xchg.getIn().setBody(employeeMap);
			}
		}).to("sql:INSERT INTO employee(EmpId, EmpName) VALUES (:#EmpId, :#EmpName)").
		process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				xchg.getIn().setBody("Done");
			}
		});
		/*.process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// throw an exception after insert statement.
				System.out.println("Exception Occurred");
				throw new Exception();
			}
		});*/

		// Select Route
		from("direct:select").to("sql:select * from employee").process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// the camel sql select query has been executed. We get the list of employees.
				ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn().getBody();
				List<Employee> employees = new ArrayList<Employee>();
				System.out.println(dataList);
				for (Map<String, String> data : dataList) {
					Employee employee = new Employee();
					employee.setEmpId(data.get("empId"));
					employee.setEmpName(data.get("empName"));
					employees.add(employee);
				}
				xchg.getIn().setBody(employees);
			}
		});

	}
}
