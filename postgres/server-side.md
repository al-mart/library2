# Server side programming

## Data types

- Boolean type
- Numeric types
- Character types
- Date/time
- NoSQL data types : hstore, xml, json, and jsonb

### Boolean

Truthy true, yes, on, 1
Falsy false, no, off, 0

```sql
alter table users
    add user_on_line boolean; -- add row boolean user_on_line
update users
set user_on_line = true
where pk = 1; -- update data 
select *
from users
where user_on_line = true; -- read data
select *
from users
where user_on_line is NULL; -- read where is null
```

### Numeric

- **_integer_** or **_int4_** (4-byte integer number).
- _**bigint**_ or **_int8_** (8-byte integer number).
- _**real**_ (4-byte variable-precision, inexact with 6 decimal digit precision).
- **_double precision_** (8-byte variable precision, inexact with 15 decimal digits precision).
- **_numeric_** (precision, scale), where the precision of a numeric is the total count of significant digits in the
  whole number, and the scale of a numeric is the count of decimal digits in the fractional part. For example, 5.827 has
  a precision of 4 and a scale of 3.

```sql
select 1.123456789::integer as my_field; -- result 1 
select 1.123456789::int4 as my_field; -- result 1 
select 1.123456789::bigint as my_field; -- result 1 
select 1.123456789::int8 as my_field; -- result 1 
select 1.123456789::real as my_field; -- result 1.1234568
select 1.123456789::double precision as my_field; -- 1.123456789
select 1.123456789::numeric(10,1) as my_field; -- 1.1
select 1.123456789::numeric(10,5) as my_field; -- 1.12346
select 1.123456789::numeric(10,9) as my_field; -- 1.123456789

select 1.123456789::numeric(10,11) as my_field;
-- ERROR: NUMERIC scale 11 must be between 0 and precision 10
-- ROW 1: select 1.123456789::numeric(10,11) as my_field;
```

### Character

- character(n)/char(n) (fixed-length, blank-padded) if we insert more than n we would get an Error
- character varying(n)/varchar(n) (variable-length with a limit)
- text (variable unlimited length)

First lets create a table

```sql
create table new_tags
(
    pk  integer not null primary key,
    tag char(10)
);
insert into new_tags values (1,'first tag');
insert into new_tags values (2,'tag');
```

And introduce two new functions

- **_length (p)_** : This counts the number of characters, where p is an input parameter and a string.
- **_octet_length(p)_** : This counts the number of bytes, where p is an input parameter and a string.

```sql
select pk,tag,length(tag),octet_length(tag),char_length(tag);
 pk | tag        | length | octet_length | char_length 
----+------------+--------+--------------+-------------
 1 | first tag   | 9      | 10           | 9
 2 | tag         | 3      | 10           | 3
(2 rows)
```

```sql

-- if tag is tag varchar(10)
select pk,tag,length(tag),octet_length(tag) from new_tags ;
 pk | tag       | length | octet_length 
----+-----------+--------+--------------
  1 | first tag | 9      | 9
  2 | tag       | 3      | 3
(2 rows)
```

Text is the prefered type ALWAYS

```sql
select pk,substring(tag from 0 for 20),length(tag),octet_length(tag) from new_tags ;
 pk  | substring          | length | octet_length 
----+---------------------+--------+--------------
 1  | first tag           | 9      | 9
 2  | tag                 | 3      | 3
 3  | this sentence has m | 41     | 41
(3 rows)
```

### Data

```sql
show timezone;
set timezone='GMT';
```

```sql
-- Helper function to_char() to_date()
select to_date('31/12/2020','dd/mm/yyyy') ;
select pk,title,to_char(created_on,'dd-mm-yyyy') as created_on

```

```sql
create table new_posts as
    select pk,
           title,
           created_on::timestamp with time zone as created_on_t,
        created_on::timestamp without time zone as create_on_nt from posts;
```


## NoSQL Data Types

### HSTORE

To use HStore we must run  on our database
```sql
create extension hstore;
```
insert into hstore 
```sql
insert into categories values (10, 'title', 'char', '"username"=>"alik","category"=>"alikCategory"');
```

### Json 

There is two types for json -> json and jsonb
json is stored as string 
jsonb is internally represented in a binary and indexable manner



# Functions 

```sql
CREATE FUNCTION function_name(p1 type, p2 type,p3 type, ....., pn type)
 RETURNS type AS
BEGIN
 -- function logic
END;
LANGUAGE language_name
```

Basic Function 
```sql
CREATE OR REPLACE FUNCTION my_sum(x integer, y integer) RETURNS integer AS $$
 SELECT x + y;
$$ LANGUAGE SQL;
```

return setof items

```sql
CREATE OR REPLACE FUNCTION delete_posts(p_title text) returns setof integer as $$ 
delete from posts where title=p_title returning pk; 
$$ LANGUAGE SQL;
```

$$ is for BEGIN and END


Returning Table
```sql
create or replace function delete_posts (p_title text) returns table (ret_key integer,ret_title text) AS $$
delete from posts where title=p_title returning pk,title;
$$
language SQL;
```

Polymorphic SQL functions

```sql
create or replace function nvl ( anyelement,anyelement) returns anyelement as $$
select coalesce($1,$2);
$$
language SQL;
    
select nvl(NULL::int,1);
select nvl('a'::text,'n'::text);

```
