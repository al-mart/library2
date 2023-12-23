# Inheritatance

The call using super must be the first statement
in the constructor for the subclass.

Recall that the this keyword has two meanings: to denote a reference to
the implicit parameter and to call another constructor of the same class.
Likewise, the super keyword has two meanings: to invoke a superclass method
and to invoke a superclass constructor. When used to invoke constructors, 
the this and super keywords are closely related. The constructor calls can
only occur as the first statement in another constructor. The constructor 
parameters are either passed to another constructor of the same
class (this) or a constructor of the superclass (super).

### Polymorphism

```java
Employee e;
e = new Employee(. . .); // Employee object expected
e = new Manager(. . .); // OK, Manager can be used as well
```
In the Java programming language, object variables are polymorphic.
A variable of type Employee can refer to an object of type Employee 
or to an object of any subclass of the Employee class 
(such as Manager, Executive, Secretary, and so on).


When you override a method, the subclass method must be at least as 
visible as the superclass method. In particular, if the superclass method
is public, the subclass method must also be declared public. 
It is a common error to accidentally omit the public specifier for the subclass
method. The compiler then complains that you try to supply a more restrictive 
access privilege.

If wwe declare class final, it cannot be extended.
If we declare method final, it cannot be overriden.
Final class's all methods are final.


Recall that fields can also be declared as final. A final field cannot 
be changed after the object has been constructed. However, if a 
class is declared final,
only the methods, not the fields, are automatically final.

### CASTING

```java
class Test {
    {
        var staff = new Employee[3];
        staff[0] = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        Manager boss = (Manager) staff[0];
        Manager boss2 = (Manager) staff[1]; // ERROR ClassCastException use instanceof

        if (staff[2] instanceof Manager)
        {
            boss = (Manager) staff[i];
        }
    }
}
```
If we want to use a Manager method that is not present in Employee we must cast it
What happens if you try to cast down an inheritance chain and are 
“lying” about what an object contains?



`The test x instanceof C does not generate an exception if x is null.
It simply returns false. That makes sense: null refers to no object, so
it certainly doesn’t refer to an object of type C.`

### Pattern Matching for instanceof

```java
class Test{
    {
        if (staff[i] instanceof Manager) {
            Manager boss = (Manager) staff[i];
            boss.setBonus(5000);
        }
    }
}
```

IS the same as (pattern matching feature introduced in java 16)

```java
class Test{
    {
        if (staff[i] instanceof Manager boss)
        {
            boss.setBonus(5000);
        }
    }
}

```

Here is a summary of the four access control modifiers in Java:

1. Accessible in the class only (private).
2. Accessible by the world (public).
3. Accessible in the package and all subclasses (protected).
4. Accessible in the package—the (unfortunate) default. No modifiers are needed.

### Object superclass 

The equals method in the Object class tests whether one object
is considered equal to another. The equals method, as implemented
in the Object class, determines whether two object references are identical.
Should be overridden 

```java
public class Employee
{
   public boolean equals(Object otherObject)
   {
      // a quick test to see if the objects are identical
      if (this == otherObject) return true;

      // must return false if the explicit parameter is null
      if (otherObject == null) return false;

      // if the classes don't match, they can't be equal
      if (getClass() != otherObject.getClass())
         return false;


      // now we know otherObject is a non-null Employee
      Employee other = (Employee) otherObject;

      // test whether the fields have identical values
      return name.equals(other.name)
         && salary == other.salary
         && hireDay.equals(other.hireDay);
   }
}
```

To guard against the possibility that name or hireDay are null, 
use the Objects.equals method. The call Objects.equals(a, b) returns true
if both arguments are null, false if only one is null, and calls a.equals(b) 
otherwise. With that method, the last statement of the Employee.equals method 
becomes


```
return Objects.equals(name, other.name)
&& salary == other.salary
&& Objects.equals(hireDay, other.hireDay);
```

When you define the equals method for a subclass, first call equals on the
super-class. If that test doesn’t pass, then the objects can’t be equal. If
the superclass fields are equal,
you are ready to compare the instance fields of the subclass

#### Equals specification

1. It is reflexive: For any non-null reference x, x.equals(x) should return true.
2. It is symmetric: For any references x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
3. It is transitive: For any references x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
4. It is consistent: If the objects to which x and y refer haven’t changed, then repeated calls to x.equals(y) return the same value.
5. For any non-null reference x, x.equals(null) should return false.

If you have fields of array type, you can use the static Arrays.equals
method to check that the corresponding array elements are equal.
Use the Arrays.deepEquals method for multidimensional arrays.

### Methods with a Variable Number of Parameters

`System.out.printf("%d %s", n, "widgets");`

```java
public class PrintStream
{
   public PrintStream printf(String fmt, Object... args)
   {
      return format(fmt, args);
   }
}
```

### Enumeration Classes

```java
public enum Size { SMALL, MEDIUM, LARGE, EXTRA_LARGE }
```

The type defined by this declaration is actually a class. The class has
exactly four instances—it is not possible to construct new objects.

You can, if you like, add constructors, methods, and fields to an 
enumerated type. Of course, the constructors are only invoked when the
enumerated constants are constructed. Here is an example:

```java
public enum Size
{
   SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

   private String abbreviation;

   Size(String abbreviation) { this.abbreviation = abbreviation; }
      // automatically private

   public String getAbbreviation() { return abbreviation; }
}
```

### Sealed Classes

In Java, a sealed class controls which classes may inherit from it.
Sealed classes were added as a preview feature in Java 15 and finalized in Java 17.



```java
public abstract sealed class JSONValue
   permits JSONArray, JSONNumber, JSONString, JSONBoolean, JSONObject, JSONNull
{ }
```

A sealed class can be declared without a permits clause. 
Then all of its direct subclasses must be declared in the same file.
Programmers without access to that file cannot form subclasses.

A file can have at most one public class, so this arrangement appears to be
only useful if the subclasses are not for use by the public.

However, as you will see in the next chapter, you can use inner 
classes as public subclasses.

`A subclass of a sealed class must specify whether it is sealed, final, 
or open for subclassing. 
In the latter case, it must be declared as non-sealed.`

non-sealed is the first hyphen keyword.


### Reflection 

```java
class Test{
    {
        var className = "java.util.Random"; // or any other name of a class with
        // a no-arg constructor
        Class cl = Class.forName(className);
        Object obj = cl.getConstructor().newInstance();
    }
}
```
