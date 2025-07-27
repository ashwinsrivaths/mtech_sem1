
 use hr;
 # 1. write a query to list the employees who work for sales dept. 
 
-- joins
 select e.* from employees e 
 join departments d on e.department_id = d.department_id 
 where department_name = "Sales";
 
-- subquery
select department_id from departments where department_name = "Sales";
select * from employees where department_id = (select department_id from departments where department_name = "Sales");

 
 /* Single row subquery :   A subquery that returns a single value and feeds to main query.
Multiple row subquery : Subquery returns multiple values (more rows) to the main query
Multiple column subqueries : Returns one or more columns.
Correlated subqueries : Reference one or more columns in the outer SQL statement. 
The subquery is known as a correlated subquery because the subquery is related to the outer SQL statement.
Nested subqueries : Subqueries are placed within another subquery.*/
-- --------------------------------------------------------------------
/*positions where sq can be placed 
insert,update,delete,select,from,having */
-- ------------------------------------------------------------------
 # relational operators  < ,>,<=>= ==<>- single row sq
 # multiple row sq- in,any ,all,exists
  -- ---------------------------------------------------------
 # single row sq

 # 1. what is the average salary of sales dept?
select avg(salary) from employees where department_id = (select department_id from departments where department_name='sales');

select avg(salary) from employees e
join departments d on e.department_id=d.department_id
where d.department_name='sales';
  
 # 2.how many employees have salary greater than that of susan
select count(*) from employees where salary > (select salary from employees where first_name = 'susan');

select count(*) from employees e  -- count is for the table and dosent make sence to call e.count(*) 
join employees susan on e.salary>susan.salary and susan.first_name = 'susan';
      
 #3. list of employees who work other than department of Den
select department_id from employees where first_name = 'den';
select count(*) from employees where department_id != (select department_id from employees where first_name = 'den');

 #4. list the employees who earn salary less than the employee gerald
select * from employees where salary > (select salary from employees where first_name='gerald');

-- -------------------------------------------------------------------
 # multiple row sq - in , any, all ,exists
 # in - list of values 
 # any -or all -and  > < 
 # x > any(2,3,4)-- > smallest 
  #x < any(2,3,4)-- < greatest
  #x > all(2,3,4)-- > greatest
  #x < all(2,3,4)-- <smallest
#  =any  same as IN

-- --------------------------------------------------------------
# multiple row queries 
#1. list the employees who work for sales,finance department
select * from employees where department_id in (select department_id from departments where department_name in ('sales','finance'));

   #2. list all the employees  who have more salary than everybody
 #who joined in 2000
 select * from employees where salary > all(select salary from employees where year(hire_date)> 2000);

 #3.list the employees where the sales representatives are earning more
 #than any of the   #sales manager 
		select * from employees where job_id='sa_rep' and salary>any(select salary from employees where job_id='sa_man');
 
		select distinct e.* from employees e
		join employees e2 on (e.salary>e2.salary and e2.job_id = 'sa_man' and e.job_id='sa_rep');

# 4.display the names of the employees whose salary is less than the 
#lowest salary of  any sh_clerk
		select first_name, last_name from employees where salary < all(select salary from employees where job_id='sh_clerk');
		
        select concat_ws(" ", e.first_name, e.last_name) as 'Full Name' from employees 
			join employees e2 on e1.salary < min(e2.salary);
 -- ----------------------------------------------
  # nested sq 
 #1. get the details of employees who are working in city seattle
select * from employees where department_id in 
	(select department_id from departments where location_id = 
		(select location_id from locations where city='seattle'));
 
 
 select location_id from locaitons where city='seattle';
 # 2.list the locations in asia region 
                         
select * from employees where department_id in 
	(select department_id from departments where location_id in
		(select location_id from locations where country_id in (
        select country_id from countries where region_id in (select region_id from regions where region_name = 'europe')
        ) ) );


 -- --------------------------------------------------------------
 # multiple column queries 
 #1. list all the employees who earn salary equal to that of employee 
 #gerald and # work in the same department as gerald. 
 
 select * from employees where (salary,department_id) in (select salary, department_id from employees where first_name='gerald');

	 -- ------------- 
    # sq in from clause  - derived tables - alias 
    #1. find out the  5th highest salary of the employee
 select min(salary) from (select * from employees order by salary desc limit 5)b;
  
    #2.display the count of employees whose name starts with 'a'
    select count(*) from (select * from employees where first_name like 'a%')b;
  -- ----------------------------------------------------------------  
    # sq in having clause 
    #1. find the departments  with average salary greater than the 
    #salary of lex.
    
select * from (select e.department_id, d.department_name, avg(e.salary) as avgSal from employees e join departments d on e.department_id=d.department_id
 group by department_id) b 
having avgSal > (select salary from employees where first_name='lex' );
 
 
 
 
select e.department_id, d.department_name, avg(e.salary) as avgSal from employees e join departments d on e.department_id=d.department_id
 group by department_id
having avgSal > (select salary from employees where first_name='lex' );
  
  -- -----------------
      # sq in update, delete and insert statements
    -- dont execute
    # 1.update the comm_pct of the employees as 0.05 for those who belong
    #to accounts
   update employees set commission_pct = 0.05 where department_id = (select department_id from departments where department_name = 'accounts');

    #2.give 5 % hike to all the employees of sales dept.
    
    # sq in delete 
    #1. delete the records of the employees who belongs to accounts dept

    # sq in insert 
    -- create a new table - insert - using sq
    
create table example (name varchar(20),sal int);
 insert into example(select concat_ws(" ", first_name, last_name) as 'name', salary from employees where department_id=80);
 select * from example;
  
    -- ----------------------------------------------------
    /*Correlated subquery -inner query will depend on outer query
    inner query gets executed , once for every row that is selected by the outer query
    SELECT j FROM t2 WHERE j IN (SELECT i FROM t1); - uncorrelated 
    SELECT j FROM t2 WHERE (SELECT i FROM t1 WHERE i = j); - corrlated query
     time consuming
   
    */
      # exists and not exists
  # exists -- true if your sq returns atleast one row 
  
  # Finding Employees with a Salary Above the Average Salary in Their Department      
select * from employees o where salary ;

   # 1 find all the dept which have atleast one employee with salary >4000
select * from departments d where exists(
select * from employees e where salary>4000 and e.department_id=d.department_id);

    #2 list the employees who changed their jobs atleast once. 
select * from employees outerTable where exists(
select * from job_history innerTable where outerTable.employee_id=innerTable.employee_id 
);
    
  #3  Display only the department which has employees

   select * from departments outerTable where exists(
	select * from employees innerTable where outerTable.department_id=innerTable.department_id
   );
         
         
         
  -- Extra Practice questions
 -- 1.Find all the employees who have the  highest salary
-- 2.find employees with second maximum salary 
-- 3.list the employees whose salary is in the range of 10000 and 20000 and working for dept id 10 0r 20 
 -- 4.Write a query to display the employee name (first name and last name), employee id and salary of all employees who report to Payam. 
-- 5.Write a query to display all the information of the employees whose salary is within the range of smallest salary and 2500. 
-- 6.Write a query to display the employee number, name (first name and last name), and salary for all employees who earn more than the average salary and who work in a department with any employee with a J in their name. 
-- 7.Display the employee name (first name and last name), employee id, and job title for all employees whose department location is Toronto. 
-- 8.Write a query to display the employee id, name (first name and last name), salary and the SalaryStatus column with a title HIGH and LOW respectively for those employees whose salary is more than and less than the average salary of all employees. 
-- 9.Write a query in SQL to display all the information of those employees who did not have any job in the past. 
-- 10. Write a query in SQL to display the full name (first and last name) of manager who is supervising 4 or more employees. 

  
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
        
        
    
    
    
    
    
    
    
    
    
    
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 