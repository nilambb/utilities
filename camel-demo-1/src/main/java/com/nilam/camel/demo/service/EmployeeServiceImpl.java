package com.nilam.camel.demo.service;

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

import com.nilam.camel.demo.model.Employee;

@Service
public class EmployeeServiceImpl extends RouteBuilder{
	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    	//define the SQL Component bean which will be used as an endpoint in our route
	@Bean
	public SqlComponent sql(DataSource dataSource) {
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource);
		return sql;
	}

	@Override
	public void configure() throws Exception {
        
       		 //Insert Route
		from("direct:insert").log("Processing message: ").setHeader("message", body()).process(new Processor() {
			public void process(Exchange xchg) throws Exception {
			    //take the Employee object from the exchange and create the parameter map
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
			//the camel sql select query has been executed. We get the list of employees.
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
