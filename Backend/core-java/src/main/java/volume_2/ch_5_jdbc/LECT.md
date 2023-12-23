# JDBC 

Programs written according to the API talk to the driver manager, 
which, in turn, uses a driver to talk to the actual database.
This means the JDBC API is all that most programmers will ever have to deal
with.

In summary, the ultimate goal of JDBC is to make possible the following:
- Programmers can write applications in the Java programming language to
access any database using standard SQL statements (or even specialized
extensions of SQL) while still following Java language conventions.
- Database vendors and database tool vendors can supply the low-level
drivers. Thus, they can optimize their drivers for their specific products.

