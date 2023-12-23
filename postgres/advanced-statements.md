# Advanced Statements

### LiKE Clause

```sql
select * from categories where title like 'a%';
```

a% - starting with a 
%a - ending with a 
%ap% - contain ap

_**like searches are case-sensitive.**_

```sql
select * from categories where title ilike 'a%';
```
_**ilike is case insensitive like**_

### DISTINCT
```sql
select coalesce(NULL,'test');  /* returns test as first argument is null */
select coalesce('orange','test');  /* returns orange */
```

```sql
select distinct coalesce(description,'No description') as description
from categories
order by 1;
```

The select distinct statement is used to return only distinct (different) values. Internally, the distinct statement involves a data sort for large tables, which means that if a query uses the DISTINCT statement, the query may become slower as the number of records increases.


### LIMIT and OFFSET 

```sql
select * from categories order by pk offset 1 limit 1;
```
starts from 1st position and returns 1 item from resultset 
Another useful function of limit is that it can be used to copy the structure of the table

```sql
create table new_categories as select * from categories limit 0;
```
This statement will copy into the new_categories table only the data structure of the table categories.

The SELECT 0 clause means that no data has been copied into the new_categories table; only the data structure has been replicated

### IN/NOT IN 

```sql
select * from categories where pk=10 or pk=11;
select * from categories where pk in (10,11);
```
Those are the same.

```sql
select * from categories where not (pk=10 or pk=11);
select * from categories where pk not in (10,11);
```

Subqueries
```sql
select pk,title,content,author,category
from posts
where category in 
      (select pk from categories where title ='orange');

```

### EXISTS/NOT EXISTS


## JOINS

### CROSS JOIN 

Those two are the same.
Cartesian product of two tables
```sql
select c.id,c.title,p.id,p.category,p.title
from test_schema.categories c 
    CROSS JOIN test_schema.posts p;

select c.id,c.title,p.id,p.category,p.title
from test_schema.categories c,
     test_schema.posts p;
```

### INNER JOIN

```sql
select c.pk,c.title,p.pk,p.category,p.title 
from categories c,posts p
where c.pk=p.category;


select c.pk,c.title,p.pk,p.category,p.title
from categories c 
    inner join posts p 
        on c.pk=p.category;

```

### Left Right JOIN

The LEFT JOIN keyword returns all records from the left table (table1),
and all the records from the right table (table2). The result is NULL from
the right side if there is no match.

The RIGHT JOIN keyword returns all records from the right table (table2) and all the records from the left table (table1) that match the right table (table2). The result is NULL from the left side when there is no match.

### FULL JOIN 

Same as left and right join and one inner join between them.

## Aggregate functions 

- AVG(): This function returns the average value.
- COUNT(): This function returns the number of values.
- MAX(): This function returns the maximum value.
- MIN(): This function returns the minimum value.
- SUM(): This function returns the sum of values.

Aggregate functions are used in conjunction with the GROUP BY clause. 
A GROUP BY clause splits a resultset into groups of rows and aggregate functions
perform calculations on them.



```sql
SELECT category, count(*)
from posts
    group by p.category;
```

having is used as where in group by clauses
```sql
SELECT category, count(*)
from posts
    group by p.category
    having count(*) > 1;
```


### Union and union all  (consult if needed)

```sql
select title from categories union select tag from tags order by title;
select title from categories union all select tag from tags order by title;
```

union is distinct , union all is not.

### EXCEPT/INTERSECT

except gets all the results excepts , and intersect get all common ones.

```sql
select title from categories except select tag from tags order by 1;
select title from categories intersect select tag from tags order by 1;
```

