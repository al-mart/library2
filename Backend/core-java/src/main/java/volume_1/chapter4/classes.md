# Classes

- A constructor has the same name as the class.
- A class can have more than one constructor.
- A constructor can take zero, one, or more parameters.
- A constructor has no return value.
- A constructor is always called with the new operator.

Note that the var keyword can only be used with local variables inside
methods. You must always declare the types of parameters and fields.

## Null

```java
class Employee {


    public Employee(String n, double s, int year, int month, int day) {
        name = Objects.requireNonNullElse(n, "unknown");
    }
}
// The “tough love” approach is to reject a null argument:

// Click here to view code image
class Employee2 {
    public Employee2(String n, double s, int year, int month, int day) {
        name = Objects.requireNonNull(n, "The name cannot be null");
    }
}
```

The Objects class has a convenience method for making null to non-null:

### Class based privileges

```java
 class Employee {
    public boolean equals(Employee other) {
        return name.equals(other.name);
    }
}
```

Here we can access private name of other because it is the same class instance.
` A method can access the private features of any object of its class, not just of the implicit parameter.`

### Final Instance Fields

- Cannot be changed
- Must be initialized in constructor
- If it refers to mutable object it refers to the variable holding the object. The reference can't be changed.

### Static fields

You can think of static fields as belonging to the class, not to the individual objects

Static methods are methods that do not operate on objects. For example, the pow method of the Math class is a static
method

A static method of the Employee class cannot access the id instance field because it does not operate on an object.
However, a static method can access a static field. Here is an example of such a static method:

### Method Parameters

The Java programming language always uses call by value. That means that the method gets a copy of all parameter values.
In particular, the method cannot modify the contents of any parameter variables passed to it.

But when called with objects

```java
class Test {
    public static void tripleSalary(Employee x) // works
    {
        x.raiseSalary(200);
    }

    public static void main(String[] args) {
        var harry = new Employee();
        tripleSalary(harry);
    }
}
```

- x is initialized with a copy of the value of harry—that is , an object reference.

- The raiseSalary method is applied to that object reference. The Employee object to which both x and harry refer gets
  its salary raised by 200 percent.

- The method ends, and the parameter variable x is no longer in use. Of course, the object variable harry continues to
  refer to the object whose salary was tripled


- A method cannot modify a parameter of a primitive type
  (that is, numbers or boolean values).

- A method can change the state of an object parameter.

- A method cannot make an object parameter refer to a new object.

### Records

To define such classes more concisely, JDK 14 introduced “records” as a preview feature. The final version was delivered
in JDK 16.

Record is a special form of a class whose state is immutable and readable by the public

In the Java language specification, the instance fields of a record are called its components.

Note that the accessors are called x and y, not getX and getY. (It is legal in Java to have an instance field and a method with the same name.)

The automatically defined constructor that sets all instance fields is called the canonical constructor.

### Static imports

`import static java.lang.System.*;`
`out.println("Goodbye, World!"); // i.e., System.out`
`exit(0); // i.e., System.exit`
