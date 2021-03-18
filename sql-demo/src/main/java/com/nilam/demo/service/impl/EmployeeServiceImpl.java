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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.nilam.demo.model.Employee;

@Service
public class EmployeeServiceImpl extends RouteBuilder {
	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// define the SQL Component bean which will be used as an endpoint in our route
	@Bean
	public SqlComponent sql(DataSource dataSource) {
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource);
		return sql;
	}

	@Override
	public void configure() throws Exception {
		onException(Exception.class)
		.process( p -> p.getIn().setBody("Invalid request"));
		
		// Insert Route
		from("direct:insert").log("Processing message: ").setHeader("message", body()).process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// take the Employee object from the exchange and create the parameter map
				Employee employee = xchg.getIn().getBody(Employee.class);
				Map<String, Object> employeeMap = new HashMap<String, Object>();
				employeeMap.put("EmpId", employee.getEmpId());
				employeeMap.put("EmpName", employee.getEmpName());
				xchg.getIn().setBody(employeeMap);
			}
		}).to("sql:INSERT INTO employee(EmpId, EmpName) VALUES (:#EmpId, :#EmpName)");

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

		from("direct:selectById").log("Processing message: ").setHeader("message", body()).process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// take the Employee object from the exchange and create the parameter map
				String id = (String) xchg.getIn().getHeader("id");
				Map<String, Object> employeeMap = new HashMap<String, Object>();
				employeeMap.put("EmpId", id);
				// employeeMap.put("EmpName", employee.getEmpName());
				xchg.getIn().setBody(employeeMap);
			}
		}).to("sql:select * from employee where EmpId = :#EmpId").process(new Processor() {

			@Override
			public void process(Exchange xchg) throws Exception {
				ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn().getBody();
				System.out.println(dataList);
				if (dataList.isEmpty()) {
					xchg.getIn().setBody("");
				} else {
					Employee employee = new Employee();
					Map<String, String> data = dataList.get(0);
					employee.setEmpId(data.get("empId"));
					employee.setEmpName(data.get("empName"));
					xchg.getIn().setBody(employee);
				}
			}
		});

		from("direct:deleteById").log("Processing message: ").setHeader("message", body()).process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				// take the Employee object from the exchange and create the parameter map
				String id = (String) xchg.getIn().getHeader("id");
				Map<String, Object> employeeMap = new HashMap<String, Object>();
				employeeMap.put("EmpId", id);
				// employeeMap.put("EmpName", employee.getEmpName());
				xchg.getIn().setBody(employeeMap);
			}
		}).to("sql:delete from employee where EmpId = :#EmpId").process(new Processor() {
			@Override
			public void process(Exchange xchg) throws Exception {
				HashMap<String, String> data = (HashMap<String, String>) xchg.getIn().getBody();
				System.out.println(data);
				xchg.getIn().setBody("Done.........");
			}
		});
		
		// Insert Route
				from("direct:updateByID").log("Processing message: ").setHeader("message", body()).process(new Processor() {
					public void process(Exchange xchg) throws Exception {
						// take the Employee object from the exchange and create the parameter map
						String id = (String) xchg.getIn().getHeader("id");
						Employee employee = xchg.getIn().getBody(Employee.class);
						Map<String, Object> employeeMap = new HashMap<String, Object>();
						employeeMap.put("EmpId", id);
						employeeMap.put("EmpName", employee.getEmpName());
						xchg.getIn().setBody(employeeMap);
					}
				}).to("sql:update employee set EmpName = :#EmpName where EmpId = :#EmpId")
				.to("direct:selectById");
	
	
				from("direct:selectByName").log("Processing message: ").setHeader("message", body())
				.doTry()
				.process(new Processor() {
					public void process(Exchange xchg) throws Exception {
						throw new Exception();
						// take the Employee object from the exchange and create the parameter map
						/*String id = (String) xchg.getIn().getHeader("id");
						Map<String, Object> employeeMap = new HashMap<String, Object>();
						employeeMap.put("EmpId", id);
						// employeeMap.put("EmpName", employee.getEmpName());
						xchg.getIn().setBody(employeeMap);
						*/
					}
				}).to("sql:select * from employee where EmpId = :#EmpId").process(new Processor() {

					@Override
					public void process(Exchange xchg) throws Exception {
						ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn().getBody();
						System.out.println(dataList);
						if (dataList.isEmpty()) {
							xchg.getIn().setBody("");
						} else {
							Employee employee = new Employee();
							Map<String, String> data = dataList.get(0);
							employee.setEmpId(data.get("empId"));
							employee.setEmpName(data.get("empName"));
							xchg.getIn().setBody(employee);
						}
					}
				})
				.doCatch(Exception.class)
				.log("An exception occured..........").process(exchange -> 
				{
					exchange.getIn().setBody("Invalid Request............");
				}
				)
				.doFinally()
				.end();
				
				from("direct:exceptionTestings").log("Processing message: ").setHeader("message", body())
				.process(new Processor() {
					public void process(Exchange xchg) throws Exception {
						throw new Exception();
					}
				}).to("sql:update employee set EmpName = :#EmpName where EmpId = :#EmpId")
				.to("direct:selectById");
	}
}
