# Generics

Generics are very helpful to work

We can have generic classes and generic methods
Generic boundaries can help to ensure that class implements an interface or is
a subclass of a class that has a method than we use extends and list boundaries by &

SEE Default Generic

### VM

VM knows nothing about generics so, when we use a generic type it compiles
and replaces with raw type

In summary, you need to remember these facts about translation of Java generics:

- There are no generics in the virtual machine, only ordinary classes and methods.
- All type parameters are replaced by their bounds.
- Bridge methods are synthesized to preserve polymorphism.
- Casts are inserted as necessary to preserve type safety.

Objects in the virtual machine always have a specific nongeneric type.
Therefore, all type inquiries yield only the raw type.

```java
if(a instanceof Pair<String>) {} // ERROR
if(a instanceof Pair<T>){} // ERROR
Pair<String> p = (Pair<String>) a; // warning--can only test that a is a Pair
```

In the same spirit, the getClass method always returns the raw type.
```java
Pair<String> stringPair = . . .;
Pair<Employee> employeePair = . . .;
if (stringPair.getClass() == employeePair.getClass()) // they are equal
// The comparison yields true because both calls to getClass return Pair.class.

```

