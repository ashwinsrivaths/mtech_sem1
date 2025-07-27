use hr;
#Q1. WAQ to display the details of employees like id, names , salary ,
# and the names of the departments they work in.  

select e.employee_id, e.first_name, e.salary, d.department_name
from employees e join departments d 
	on e.department_id = d.department_id;
    
    
select e.employee_id, e.first_name, e.salary, d.department_name
from employees e join departments d; 

  
#Q2. WAQ to display the details of employees like id, names , salary ,
# and the names of the departments they work in. Also include such
#employees who are not assigned to any departments yet.  

-- this is a left join as i want all entries in my left table
-- leftTable join rightTable 
 select e.employee_id, e.first_name, e.salary, d.department_name
from employees e left join departments d 
	on e.department_id = d.department_id;


# Q3. WAQ to display the details of employees like id, names , salary ,
# and the names of the departments they work in. Include the list of departments
#where no employees are working. 

 select e.employee_id, e.first_name, e.salary, d.department_name
from employees e right join departments d 
	on e.department_id = d.department_id;



#Q4. WAQ to display the details of employees like id, names , salary ,
# and the names of the departments they work in. Also include such
#employees who are not assigned to any departments yet and the list of 
#departments where no employees are working.

 select e.employee_id, e.first_name, e.salary, d.department_name
from employees e left join departments d 
	on e.department_id = d.department_id
union
 select e.employee_id, e.first_name, e.salary, d.department_name
from employees e right join departments d 
	on e.department_id = d.department_id;





#Q5. WAQ to display the details of employees along with the departments, cities and 
#the country #they work in.

select e.employee_id, e.first_name, e.salary, d.department_name,city,country_name
from employees e 
	join departments d on e.department_id = d.department_id
    join locations l on d.location_id=l.location_id
    join countries c on l.country_id=c.country_id;


#Q6. WAQ to get the count of employees working in different cities.
#display such cities which has more than 20 employees working in them.

select count(*) as cnt,city
from employees e 
	join departments d on e.department_id = d.department_id
    join locations l on d.location_id=l.location_id group by city having cnt>20;
       
     
#Q7. Display the list of employees who are based out of America Region.
select *  from employees e
join departments d on e.department_id=d.department_id
join locations l on d.location_id=l.location_id
join countries c on l.country_id=c.country_id
join regions r on c.region_id=r.region_id 
where r.region_name like "%america%";


#Q8. WAQ to list the employees working in 'Seattle'.
select *  from employees e
join departments d on e.department_id=d.department_id
join locations l on d.location_id=l.location_id 
where l.city = "seattle";

#Q9. WAQ to list the details of employees, their department names and the job titles.

select * from employees e
join departments d on e.department_id=d.department_id
join jobs j on e.job_id=j.job_id
;

-- --------------------------------------
#Natural join - primitive join - uses any columns with same name and datatype to perform the 
#join.

#Write a query to find the addresses (location_id, street_address, city, state_province, 
#country_name) of all the departments.

select location_id, street_address, city, state_province, country_name 
from departments natural join locations natural join countries;


#write a query to display job title, firstname , difference between max salary and 
#salary of all employees using natural join 

select max_salary - salary, first_name
from jobs natural join employees;




-- ----------------------------------------------------------------
 -- ---------------------------------------------------------------------------------------

 # Self join -- same table has different copy
-- this is used when I am operating on the same table 
-- 

-- Write a query to find the name (first_name, last_name) and hire date of the employees 
#who was hired after 'Jones'.

select * from employees;

select e1.first_name, e1.last_name, e1.hire_date
from employees e1 join employees e2
on e2.last_name = "Jones" and e1.hire_date>e2.hire_date;

    
# Write a query to display first and last name ,salary of employees who earn less than the 
#employee whose number is 182 using self join  

select e1.first_name, e1.last_name 
from employees e1 join employees e2
on e2.employee_id = 182 and e2.salary > e1.salary;

 
-------------------------------------------------------------------

/*
#Cross Join - cartersian product between tables
			- every row of 1 table mapped to every row of other table.
            - m*n rows
            - cross join   ,   join 


this is exclusive used when there are no common columns
every row in one table will be mapped to every row in the joining table
table 1 - n rows | table 2 m rows -> cross join => m*n rows
*/

select * from employees cross join departments;
select * from employees, departments;

-- extra practices 
 
  
 -- 1.Display the first name, last name, department id and department name, for all employees for departments 80 or 40.
select e.first_name, e.last_name, d.department_name from employees e
join departments d on e.department_id=d.department_id
where d.department_id in (80,40);
 
-- 2. Write a query in SQL to display the full name (first and last name), and salary of those employees who working in
--  any department located in London. */
select concat_ws(" ", e.first_name, e.last_name), e.salary
from employees e
join departments d on e.department_id=d.department_id
join locations l on d.location_id=l.location_id
where l.city = "london";

-- why is this not working-- 
select concat_ws(" ", first_name, last_name) as name, salary, city
from employees natural join departments natural join locations
where city = "london";

 -- 3.	Write a query in SQL to display those employees who contain a letter z to their first name and also display their last name,
 -- department, city, and state province. (3 rows)
 select e.first_name, e.last_name, d.department_name, l.city, l.state_province 
 from employees e
 join departments d on e.department_id=d.department_id
 join locations l on d.location_id=d.location_id
 where first_name like "%z%";


-- 4.	Write a query in SQL to display the job title, department id, full name (first and last name) of employee, starting date 
-- and end date for all the jobs which started on or after 1st January, 1993 and ending with on or before 31 August, 2000. (use employee,job_history)

select * 
from job_history jh
join employees e on jh.employee_id=e.employee_id
join departments d on e.department_id=d.department_id

where jh.start_date >= "01-01-1993" and jh.end_date <= "31-08-2000";
   
select compa
   select Date(01-01-1993) > Date(02-01-1993) ;
select *  
from job_history;
where start_date >= Date(01-01-1993) ;
   
-- 5.	.Display employee name if the employee joined before his manager.




-- 6 •Write a query in SQL to display the name of the department, average salary and number of employees working in that department who 
-- got commission. */


  

-- 7. Write a query in SQL to display the details of jobs which was done by any of the employees who is  earning a salary on and above 12000( use job_history,employee) */



-- 8. Write a query in SQL to display the employee ID, job name, number of days worked in for all those jobs in department 80.(use job, job_history)

  

  


