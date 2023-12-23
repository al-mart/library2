# Interfaces Lambda expressions and Inner classes

## Interfaces

In the Java programming language, an interface is not a class but a set of requirements for the classes that want to
conform to the interface.

Typically, the supplier of some service states: “If your class conforms to a particular interface, then I’ll perform the
service.” Let’s look at a concrete example. The sort method of the Arrays class promises to sort an array of objects,
but under one condition: The objects must belong to classes that implement the Comparable interface.

```java
public interface Comparable<T> {
    int compareTo(T other); // parameter has type T
}
```

In the interface, the compareTo method is abstract—it has no implementation. A class that implements the Comparable
interface needs to have a compareTo method, and the method must take an Object parameter and return an integer.
Otherwise, the class is also abstract—that is, you cannot construct any objects.

All methods of an interface are automatically public. For that reason, it is not necessary to supply the keyword public
when declaring a method in an interface.

The documentation of the Comparable interface suggests that the compareTo method should be compatible with the equals
method. That is, x.compareTo(y) should be zero exactly when x.equals(y)
Most classes in the Java API that implement Comparable follow this advice. A notable exception is BigDecimal. Consider x
= new BigDecimal("1.0") and y = new BigDecimal("1.00"). Then x.equals(y) is false because the numbers differ in
precision. But x.compareTo(y) is zero. Ideally, it shouldn’t be, but there was no obvious way of deciding which one
should come first.

According to the language standard: “The implementor must ensure sgn(x.compareTo(y)) = -sgn(y.compareTo(x)) for all x
and y.
(This implies that x.compareTo(y) must throw an exception if y.compareTo(x)
throws an exception.)” Here, sgn is the sign of a number: sgn(n) is –1 if n is negative, 0 if n equals 0, and 1 if n is
positive. In plain English, if you flip the parameters of compareTo, the sign (but not necessarily the actual value) of
the result must also flip.

If there is a common algorithm for comparing subclass objects, simply provide a single compareTo method in the
superclass and declare it as final.

### Interface fields

Just as methods in an interface are automatically public, fields are always public static final.

```java
public interface Powered extends Moveable {
    double milesPerGallon();

    double SPEED_LIMIT = 95; // a public static final constant
}
```

Interfaces can be sealed. As with sealed classes, the direct subtypes
(which can be classes or interfaces) must be declared in a permits clause or be located in the same source file.

Interfaces can have private and static methods and default methods.

```java
public interface Iterator<E> {
    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
```

#### Resolving Default Method Conflicts

What happens if the exact same method is defined as a default method in one interface and then again as a method of a
superclass or another interface? The rules in Java are much simpler. Here they are:

1. Superclasses win. If a superclass provides a concrete method, default methods with the same name and parameter types
   are simply ignored.

2. Interfaces clash. If an interface provides a default method, and another interface contains a method with the same
   name and parameter types
   (default or not), then you must resolve the conflict by overriding that method.

### CALLBACK

java.awt.event package interface:

```java
public interface ActionListener {
    void actionPerformed(ActionEvent event);
}
```

instance is passed as callback object and the function get invoked.

### Comparator

To deal with this situation, there is a second version of the Arrays.sort method whose parameters are an array and a
comparator—an instance of a class that implements the Comparator interface.

BiFunction<T, U, R>, describes functions with parameter types T and U and return type R

### Predicate

A particularly useful interface in the java.util.function package is Predicate:

```java
public interface Predicate<T> {
    boolean test(T t);
// additional default and static methods
}
```

### Supplier

Another useful functional interface is Supplier<T>:

```java
public interface Supplier<T> {
    T get();
}
```

A supplier has no arguments and yields a value of type T when it is called. Suppliers are used for lazy evaluation.

```java
class TEST {
    {
        LocalDate hireDay = Objects.requireNonNullElse(day,
                LocalDate.of(1970, 1, 1));
    }
}
```

This is not optimal. We expect that day is rarely null, so we only want to construct the default LocalDate when
necessary. By using the supplier, we can defer the computation:

```java
class TEST {
    {
        LocalDate hireDay = Objects.requireNonNullElseGet(day,
                () -> LocalDate.of(1970, 1, 1));
    }
}
```

The requireNonNullElseGet method only calls the supplier when the value is needed.

### Method References

```java
class TEST {
    {
        var timer = new Timer(1000, event -> System.out.println(event));
    }
}
```

It would be nicer if you could just pass the println method to the Timer constructor.

```java
class TEST {
    {
        var timer = new Timer(1000, System.out::println);
    }
}
```

There are ten overloaded println methods in the PrintStream class
(of which System.out is an instance). The compiler needs to figure out which one to use, depending on context. In our
example, the method reference System.out::println must be turned into an ActionListener instance with a method

`void actionPerformed(ActionEvent e)`

The println(Object x) method is selected from the ten overloaded println methods since Object is the best match for
ActionEvent. When the actionPerformed method is called, the event object is printed.

Now suppose we assign the same method reference to a different functional interface:

`Runnable task = System.out::println;`
The Runnable functional interface has a single abstract method with no parameters

`void run()`
In this case, the println() method with no parameters is chosen. Calling task.run() prints a blank line to System.out.

As you can see from these examples, the :: operator separates the method name from the name of an object or class. There
are three variants.

1. object::instanceMethod
2. Class::instanceMethod
3. Class::staticMethod

### Constructor References

```java
class Test {
    {
        ArrayList<String> names = {"A", "B", "C"};
        Stream<Person> stream = names.stream().map(Person::new);
        List<Person> people = stream.toList();
    }
}
```

### CLOSURE

```java
class Test {

    public static void repeatMessage(String text, int delay) {
        ActionListener listener = event ->
        {
            System.out.println(text);
            Toolkit.getDefaultToolkit().beep();
        };
        new Timer(delay, listener).start();
    }
   
}
```

#JAVA HAS CLOSURES
In a lambda expression, you can only reference variables whose
value doesn’t change

```java
class Test {
    
   public static void repeat(String text, int count) {
      for (int i = 1; i <= count; i++) {
         ActionListener listener = event ->
         {
            System.out.println(i + ": " + text);
            // ERROR: Cannot refer to changing i
         };
         new Timer(1000, listener).start();
      }
   }
}
```

When you use the this keyword in a lambda expression, you refer to 
the this parameter of the method that creates the lambda. 

### Inner Classes

An inner class is a class that is defined inside another class.
Why would you want to do that?

- Inner classes can be hidden from other classes in the same package.
- Inner class methods can access the data from the scope in which they
are defined—including the data that would otherwise be private.

Any static fields declared in an inner class must be final and initialized
with a compile-time constant. If the field was not a constant, it might not be
unique.

An inner class cannot have static methods. The Java Language Specification
gives no reason for this limitation. It would have been possible to allow
static methods that only access static fields and methods from the enclosing 
class. Apparently, the language designers decided that the complexities
outweighed the benefits.

