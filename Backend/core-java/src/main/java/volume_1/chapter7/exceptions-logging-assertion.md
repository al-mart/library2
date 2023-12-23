# Exceptions

Exceptions that inherit from **_RuntimeException_** include such problems as
- A bad cast
- An out-of-bounds array access
- A null pointer access

Exceptions that **_do not inherit from RuntimeException_** include
- Trying to read past the end of a file
- Trying to open a file that doesn’t exist
- Trying to find a Class object for a string that does not denote an existing class

The Java Language Specification calls any exception that derives from 
the class Error or the class RuntimeException an unchecked exception.
All other exceptions are called checked exceptions.

A Java method can throw an exception if it encounters a situation 
it cannot handle. The idea is simple: A method will not only tell the
Java compiler what values it can return, it is also going to tell the 
compiler what can go wrong.

For example, code that attempts to read from a file knows that the
file might not exist or that it might be empty. The code that tries
to process the information in a file therefore will need to notify
the compiler that it can throw some sort of IOException.

In summary, a method must declare all the checked exceptions that
it might throw. Unchecked exceptions are either beyond your control (Error)
or result from conditions that you should not have allowed in the first
place (RuntimeException). If your method fails to faithfully declare all 
checked exceptions, the compiler will issue an error message.

Of course, as you have already seen in quite a few examples, 
instead of declaring the exception, you can also catch it. Then the 
exception won’t be thrown out of the method, and no throws specification 
is necessary. You will see later in this chapter how to decide whether to
catch an exception or to enable someone else to catch it.

