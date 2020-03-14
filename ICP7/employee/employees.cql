USE test;
CREATE TABLE employees (
	employee_id int,
	department text,
	lastname text,
	years_with_company int,
	date text,
	title text,
	salary int,
	num int,
	PRIMARY KEY(department, lastname, years_with_company, date, title, salary,num)
);

COPY employees (employee_id, department, lastname, years_with_company, date, title, salary,num) FROM '/Users/louis_lyu/Desktop/ICP7/employee/employee_entries.csv' WITH HEADER=True AND DELIMITER='|';