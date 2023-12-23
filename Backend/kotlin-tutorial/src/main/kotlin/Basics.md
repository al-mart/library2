# Kotlin basics

We declare functions as such
```kotlin
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}
```

in Kotlin, if is an expression with a result value. It’s similar to a ternary operator in Java: (a > b) ? a : b.

In Kotlin, if is an expression, not a statement. The difference between a statement and an expression is that an expression has a value, which can be used as part of another expression, whereas a statement is always a top-level element in its enclosing block and doesn’t have its own value. In Java, all control structures are statements. In Kotlin, most control structures, except for the loops (for, do, and do/while) are expressions. The ability to combine control structures with other expressions lets you express many common patterns concisely, as you’ll see later in the book.

On the other hand, assignments are expressions in Java and become statements in Kotlin.

Mutable and immutable variables
There are two keywords to declare a variable:

- val (from value)—Immutable reference. A variable declared with val can’t be reassigned after it’s initialized. It corresponds to a final variable in Java.
- var (from variable)—Mutable reference. The value of such a variable can be changed. This declaration corresponds to a regular (non-final) Java variable.


In Kotlin, public is default visibility, so you can omit it.
this is called a simple data class in Kotlin
```kotlin
class Person(val name: String)
```
