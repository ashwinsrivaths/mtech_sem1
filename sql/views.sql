select @@transaction_isolation;

create database new1;
use new1;

create table student (sid int, snamme varchar(20), marks int);

insert into student values(1,"sam",45),(2,"jon",90), (3,"jack",56);
select * from student;


-- create view
-- verticle view ?
create view v1 as (select snamme from student);

-- horizantle view? 
create view v2 as (select * from student where marks>50);

-- update view/table (cant do one without the other)
update v1 set snamme = 'johnew' where sid=2;

-- only simple views are updateble
-- update in one place reflects in another place 
select * from v1;
select * from student;

-- alter view
alter view v1 as select marks from student;
select * from v1;

-- drop view
drop view v1, v2; 




 