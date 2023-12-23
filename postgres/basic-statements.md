```shell
postgres=# \l
postgres=# \c {{DBNAME}} 
```

\l list databases
\l+ dbname more info on a db
\c dbname connect to db
\d list tables
\x expanded mode
\du describe users

```sql
CREATE
DATABASE {{dbname}};
CREATE TABLE {{TBNAME}}
(
    dummyfield integer not null primary key
);
CREATE
DATABASE forumdb2 TEMPLATE forumdb;

drop
database {{{{dbname}}}} ;
drop table {{TBNAME}};

```

_**It is important to remember that any changes made to the template1 database will be present in all databases created
after this change.**_

Create forumdb2 using as template forumdb;

```sql
select pg_database_size('forumdb');
select pg_size_pretty(pg_database_size('forumdb'));
select *
from pg_database
where datname = 'forumdb';
```

1 - dbsize
2 - pretty size kb, mb, gb
3 - pg_database metadata

_** Every db hase OID and there is a file with name of OID in base folder**_

There is 3 types of tables

- Temporary tables: Very fast tables, visible only to the user who created them
- Unlogged tables: Very fast tables to be used as support tables common to all users
- Logged tables: Regular tables

CREATE TABLE

```sql
    CREATE TABLE users
    (
        pk       int GENERATED ALWAYS AS IDENTITY,
        username text NOT NULL,
        gecos    text,
        email    text NOT NULL,
        PRIMARY KEY (pk),
        UNIQUE (username)
    );
```

```sql
CREATE TABLE if not exists users
(
    pk
    int
    GENERATED
    ALWAYS AS
    IDENTITY,
    username
    text
    NOT
    NULL,
    gecos
    text,
    email
    text
    NOT
    NULL,
    PRIMARY
    KEY
(
    pk
),
    UNIQUE
(
    username
)
    );
/*  NOTICE: relation "users" already exists, skipping */

drop table if exists users;
/* DROP TABLE */
drop table if exists users;
/* NOTICE: table "users" does not exist, skipping */
```

IN POSTGRES there are sessions, transactions and concurrency
Session is a set of transactions. Each session is isolated, and that a transaction is isolated from everything else

Temp tables are visible inside a session which will only be visible within the session where the table was created

```sql
create
temp table if not exists temp_users  (
    pk int GENERATED ALWAYS AS IDENTITY
   ,username text NOT NULL
   ,gecos text
   ,email text NOT NULL
   ,PRIMARY KEY( pk )
   ,UNIQUE ( username )
);
```

To make the temp table visible inside just one transaction we add on commit;

```sql
BEGIN
WORK;
create
temp table if not exists temp_users (
 pk int GENERATED ALWAYS AS IDENTITY,
    username text NOT NULL,
    gecos text 
 email text NOT NULL,
 PRIMARY KEY( pk ),
 UNIQUE ( username ),  
) on commit drop;

COMMIT WORK;

```

```sql
create unlogged table if not exists unlogged_users (
    pk int GENERATED ALWAYS AS IDENTITY
   ,username text NOT NULL
   ,gecos text
   ,email text NOT NULL
   ,PRIMARY KEY( pk )
   ,UNIQUE ( username ) 
);

```
Unlogged tables are a fast alternative to permanent and temporary tables. This performance increase comes at the expense of losing data in the event of a server crash, however. This is something you may be able to afford under certain circumstances

In PostgreSQL, each table or index is stored in one or more files. When a table or index exceeds 1 GB, it is divided into gigabyte-sized segments.
If the users table has a size greater than 2 GB, then the table will be stored in three files, called 16633, 16633.1, 16633.2
16633 is the OID of table 

## Understanding basic table manipulation statements

```sql
INSERT INTO users (username, gecos, email)
VALUES ('myusername', 'mygecos', 'myemail');
SELECT pk, username, gecos, email
FROM users;
SELECT *
FROM users;
SELECT *
FROM users
ORDER BY username;
SELECT *
FROM users
ORDER BY 2;
SELECT *
FROM users
WHERE username = 'myuser';
SELECT *
FROM users
WHERE username = 'myuser'
ORDER BY email DESC;
SELECT *
FROM users
WHERE gecos IS NULL;
SELECT *
FROM users
WHERE gecos IS NOT NULL;
SELECT *
FROM user
ORDER BY gecos NULLS LAST;
SELECT *
FROM user
ORDER BY gecos NULLS FIRST;
CREATE
TEMP TABLE temp_categories AS
SElECT *
FROM categories;
UPDATE temp_categories
SET title='peach'
WHERE pk = 14;
DELETE
FROM temp_categories
WHERE pk = 10;
DELETE
FROM temp_categories
WHERE description IS NULL;
DELETE
FROM temp_categories; /* delete all table data */
INSERT INTO temp_categories
SELECT *
FROM categories; /* add all data from one table to other*/
TRUNCATE TABLE temp_categories; /* delete all table data*/
```

- TRUNCATE deletes all the records in a table similar to the DELETE command.
- In the TRUNCATE command, it is not possible to use WHERE conditions.
- The TRUNCATE command deletes records much faster than the DELETE command.
