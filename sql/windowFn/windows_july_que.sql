use hr;

# total salary of all the employees in the company

 select sum(salary) from employees;                                         -- one row
 select department_id, sum(salary) from employees group by department_id;   -- 12 department rows
 select department_id, sum(salary) over() as sum from employees;            -- 107 rows displays all rows irrespective of department and displays it for all rows
 
 
 -- second select using window
 

 /*
  window /analytic function
 - performs calculations on set of rows  , do not collapase the result of the rows
 into a single rows. 
 - used for analysis - next ,previous ,n th value,running total , cumaltive 
 syntax - window function
 window_function_name(expression) over(
 [partition by]--data grouping
 [order by ] determine the order asc or desc
 [frame definition] -- limit the rows /range/window size
 )
 */
 
-- -------------------------------------------------------------
# row_number() --serial no, rowid 
-- ------------------------------------------------------------
#1. order the rows based on salary 
select *, row_number() over(order by salary) as rowId from employees;

# 2. rowid based on salary dept wise 
select *, row_number() over(partition by department_id order by salary) as rowId from employees;

-- --------------------------------------------------------------------
# rank()
-- the rank will skip if there is same value unlike dense rank
-- ---------------------------------------------------------------------  
# 1.ranking based on  salary
select *, rank() over(order by salary desc) as ranking from employees;
 
#2. ranking in order of salary  dept wise
select *, rank() over(partition by department_id order by salary) as ranking from employees;
   -- --------------------------------------------------
# dense rank 

-- --------------------------------------------------------------------
#1. dense rank dept wise 
select *, dense_rank() over(partition by department_id order by salary) as denseRanking from employees;

#2.display row number, rank and dense rank in the same query dept 60 and 90
select department_id, salary, 
row_number() over(partition by department_id order by salary) as rowId,
rank() over(partition by department_id order by salary) as normal_rank,
dense_rank() over(partition by department_id order by salary) as denseRank
from employees where department_id in (60,90);

-- --------------------------------------------------------------------
# ntile -  create n bins based on formula --  total no of rows /no of   bins
-- --------------------------------------------------------------------
#1. create ten bins 
select *, ntile(10) over() as bins from employees;

#2. same query dept wise ,no of bins 10
select *, ntile(10) over(partition by department_id) as bins from employees;

   
-----------------------------------
-- LEAD and LAG
-- LEAD computes an expression based on the next rows
-- i.e. rows coming after the current row) and return value to current row
-- LEAD (expr, offset, default)
-- expr = expression to compute from leading row
-- offset = index of the leading row relative to the current row
-- default = value to return if the <offset> points to a row beyond partition range
-----------------------------------

#1. display previous salary 
select salary, lag(salary) over(order by salary) as previousSalary from employees;
 
#2. display previous salary with offset 2 and default value 200
select salary, lag(salary, 2, 200) over(order by salary) as previousSalary from employees;

#3. lag - display the previous salary and difference in salary dept wise ,order by salary
select salary, lag(salary, 1, 0) over(partition by department_id order by salary) as previousSalary,
salary - lag(salary, 1, 0) over(partition by department_id order by salary) as salaryDifference   -- normal subtraction bw a val and a window fn
from employees;


#4. lead- next value
select salary, lead(salary, 2, 200) over(order by salary) as previousSalary from employees;


#5.find the next salary milestone dept wise order by salary
select department_id, salary, lead(salary, 2, 200) over(partition by department_id order by salary) as previousSalary from employees;

-- --------------------------------------------------------------------
# first_value last_value  range should be unbouded following and unbounded preceding
   -- --------------------------------------------------------------------
 
#1.display the first value of salary deptwise 
select *, first_value(first_name)
over(partition by department_id order by salary) as first_sal
from employees;

#2. find the name of all employees with their  start day of the first job.use job_history table 
select *,
first_value(start_date) over( partition by j.employee_id order by start_date) as firstJob
from employees e join job_history j on e.employee_id=j.employee_id;

#3.last_value-- find recent job of the employee- frame partition - from where to where range of rows  
select distinct first_name,
last_value(start_date) over( partition by j.employee_id order by start_date
range between unbounded preceding and unbounded following -- default for no order by but not for query with order by
-- for query with order by, the default is range between unbounded preceding and current
) as firstJob
from employees e join job_history j on e.employee_id=j.employee_id;

# 4. display the employee first name who gets highest pay dept wise - last_value
select *,
last_value(first_name) over(
partition by department_id order by salary
range between unbounded preceding and unbounded following) as highestpay
from employees;



-- --------------------------------------------------------------------
-- nth_value 

-- --------------------------------------------------------------------
 #1.- 2nd highest paid employee dept wise 
select *, nth_value(first_name, 2) over(partition by department_id order by salary desc) as highest from employees;

 -- -------------------------------------------------------------------- 
  -- percent_rank - (rank-1)/(totalrows-1)--70th percentile is the value below which 70% of the values fall.
  -- cume_dist- cumaltive distribution of a record occupied in the total set 
  -- no of rows whose values  are less than or equal to rows value/total rows  0 to 1
  -- --------------------------------------------------------------------
 # 1. percent rank and cume dist for dept id =50

 -- --------------------------------------------------------------------
# Window function with aggregate functions      
-- --------------------------------------------------------------------     
# 1. display the count of employees in each dept 
select *, count(*) over(partition by department_id) as countTemp from employees;

#2.print the sum of salary dept wise 
select *, sum(salary) over(partition by department_id) as totalSalary from employees;

select *, sum(salary) over(partition by department_id) as totalSalary from employees;



#3 print the sum of salary dept 30 and 40
select *, sum(salary) over(partition by department_id) as totalSal from employees where department_id in (30,40);

# variations with rows and range 

select salary, department_id, sum(salary) over(partition by  department_id order by salary
rows between 2 preceding and 1 following) as sum2p1f
from employees where department_id=30;


select salary, department_id, sum(salary) over(partition by  department_id order by salary
rows between unbounded preceding and 1 following) as sum2p1f
from employees where department_id=30;

select salary, department_id, sum(salary) over(partition by  department_id order by salary
rows between 1 preceding and unbounded following) as sum2p1f
from employees where department_id=30;

select salary, department_id, sum(salary) over(partition by  department_id order by salary
rows between 1 preceding and unbounded following) as sum2p1f
from employees where department_id=30;

-- range uses values 
select salary, department_id, sum(salary) over(partition by  department_id order by salary
range between 10 preceding and unbounded following) as sum2p1f
from employees where department_id=30;
#4.-- running total

       
#5. moving average 
  
  
  -- CTE 
   # Find the minimum and maximum of the avg salary among all the depts
   with ex1 as (select department_id, avg(salary) as avgSal from employees group by department_id)
   select min(avgSal), max(avgSal) from ex1;
   
   # Find the biggest and smallest departments wrt to the count of employees 
   with ex2 as (select department_id, count(*) as counttemp from employees group by department_id)
   select min(counttemp), max(counttemp) from ex2;
 
    -- Find percentile rank  of every dept by total salary.
	with ex3 as (select department_id, sum(salary) as totalsalary from employees group by department_id)
    select *, department_id, percent_rank() over (order by totalsalary) from ex3;
    
-- find employee details who earns 2nd highest salary dept wise 
with ex4 as (select *, dense_rank() over(partition by department_id order by salary desc) as rnk from employees)
select * from ex4 where rnk=2;
 
     
    
    
    
    
   
   
   
   
   
   
   
   
   
   
   
   
   
                  
                  
                  
                  
  
           
          
          
          
          
          
          
          




