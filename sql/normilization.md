
- database design step
- reduce redundancy or duplicacy in data
    - data structured 
    - split data into multiple tables
- *if a db has to be in n th normal form, the db must be in all the previous normal forms*
- data integrity is
    - correct 
    - complete 
    - consistent
- normilization is done to ensure that insertion, deletion and updation anomalies do not occur

- there is a primary coulun that other columns are dependent on.

# 1st normal form
- columns that have multiple values must be broken down such that each column has one and only one value
- Ensures each attribute (column) contains only atomic (indivisible) values and eliminates repeating groups of data

# 2nd normal form
- all non primary attribute should be fully dependent on the prime attribute
- in case of composite key present, the non primary attributes must be fully dependent on all the composite key columns

# 3rd normal form
- no transitive dependencies
- ie non prime attribute is dependant on another non prime attribute which is inturn dependant on the prime attribute


# transaction processing
- commit;
- rollback;  - goes back to the last commit
- savePoint;
    - rollback to s3; (where s3 is a savePoint)
    - release savePoint s3; (removes the savePoint as it is not needed)
        - all savePoints after the released savePoint are also released
- atomicity - either success or failure (nothing in between)
- constancy - transaction must be consistent and if i pay rs 10, 10 should be removed from my acc and 10 must be added to another acc
- isolation - if multiple users perform requests/transactions, it must be isolated and should feel that they have done it one by one
- durability - backup and recovery mechanisms allow us to restore the data in case of server crashing or natural disaster


START TRANSACTION;
....
....
COMMIT;

The COMMIT statement in SQL ends the current transaction. When COMMIT is executed, all changes made within that transaction become permanent and visible to other users and sessions. It effectively finalizes the transaction, making its modifications a part of the database's consistent state.


# locks
generally used when I want to make some update and lock it
- Table lock
    - entire table is locked for reading or writing
- row lock
- column lock
 
 ## read lock
 start transaction;
 lock table cust1 read;
 unlock tables;

    SHARED LOCK
    - if one user accquires this, then he can read but not write to the table
    - select querirs allowed but DML cmds not allowed
    - other users can also get a read lock and read 
    - other users can also read without a lock
    - no one is allowed to write to the table and all the wirte lock requests are in waiting 

 ## write lock
start transaction;
lock table cust1 write;
unlock tables;

    exclusive lock
    - user with this lock can read and write
    - others are not allowed to read or write as long I don't release the lock

# isolation 
used for concurrency

first isolation level - read uncommitted
set session transaction isolation level read uncommitted;
- dirty read
    - user makes changes without committing and then rollbacks. 2nd user reads before rollback and gets dirty value

2nd isolation level - read committed
set session transaction isolation level read committed;
- non repeatable read
    - user makes changes and commits it. 2nd user reads before commit and after commit and gets different values
    - this is only for a particular transaction

3rd isolation level - repeatable read
set session transaction isolation level repeatable read;
- phantom error (read)
    - for repeateble read, newly entered or deleted records must be hidden from user and is displayed only some time
    - happens when you go for a range query (when a range query is made, then more rows are present than normal query)


4th isolation level - serializable (no anomalies)
set session transaction isolation level serializable;
every time I start a read, a read lock is accquired and every time i start a write a write lock is accquired


# view
- temporary table or virtual table
- views are not updatable when its 
    - taken from multiple tables 
    - aggrigate
    - distinct
    - having
    - group by
     functions are used
- create update delete are similar to table
    - create view v1 as (select * from ...); -- the select query output is stored in the view (//TODO: important)
    - drop view v1, v2, v3;
    - alter view v1 as (select .... );
- used for dashbording
    - since the views are run in runtime and not stored, in runtime the dashbord can be shown with updated data

    inline view is a subquery from the from clause