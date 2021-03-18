DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  empId VARCHAR(10) NOT NULL,
  empName VARCHAR(100) NOT NULL
);

insert into employee values('1', 'nilam'),('2', 'Priti');
commit;