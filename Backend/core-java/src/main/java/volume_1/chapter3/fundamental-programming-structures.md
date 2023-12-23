# FUNDAMENTAL PROGRAMMING STRUCTURES

8 primitives

| Type    | Storage Requirement | Range (Inclusive)                                                                                 |
|---------|---------------------|---------------------------------------------------------------------------------------------------|
| int     | 4 bytes             | –2,147,483,648 to 2,147,483,647 (just over 2 billion)                                             |   
| short   | 2 bytes             | –32,768 to 32,767                                                                                 |   
| long    | 8 bytes             | –9,223,372,036,854,775,808 to 9,223,372,036,854,775,807                                           |   
| byte    | bytes               | –128 to 127                                                                                       |   
| float   | 4 bytes             | Approximately ±3.40282347×10<sup>38</sup> (6–7 significant decimal digits)                        |   
| double  | 8 bytes             | Approximately ±1.79769313486231570×10<sup>308</sup> (15 significant decimal digits)               |   
| char    | 2 bytes             | can store one character 'A' (not string "A") but nowadays some unicode characters require 2 chars |
| boolean | 1 bit               | true false                                                                                        |   

Long integer numbers have a suffix L or l (for example, 4000000000L). Hexadecimal numbers have a prefix 0x or 0X (for
example, 0xCAFE). Octal numbers have a prefix 0 (for example, 010 is 8)—naturally, this can be confusing, and few
programmers use octal constants.

You can write numbers in binary, with a prefix 0b or 0B. For example, 0b1001 is 9. You can add underscores to number
literals, such as 1_000_000 (or 0b1111_0100_0010_0100_0000) to denote one million. The underscores are for human eyes
only. The Java compiler simply removes them.

Numbers of type float have a suffix F or f (for example, 3.14F). Floating-point numbers without an F suffix (such as
3.14) are always considered to be of type double. You can optionally supply the D or d suffix (for example, 3.14D).

An E or e denotes a decimal exponent. For example, 1.729E3 is the same as 1729.

All floating-point computations follow the IEEE 754 specification. In particular, there are three special floating-point
values to denote overflows and errors:

- Positive infinity
- Negative infinity
- NaN (not a number)

For example, the result of dividing a positive number by 0 is positive infinity. Computing 0/0 or the square root of a
negative number yields NaN.

```java
class Test {
    static {
        if (x == Double.NaN) ; // is never true
        if (Double.isNaN(x)) ;
    }
}
```

### Variables and Constants

Starting with Java 10, you do not need to declare the types of local variables if they can be inferred from the initial
value. Simply use the keyword var instead of the type:

```java
class Test {
    static {
        var vacationDays = 12; // vacationDays is an int
        var greeting = "Hello"; // greeting is a String
    }
}
```

In Java, you use the keyword final to denote a constant. For example:

```java
public class Constants
{
   public static final int CM_PER_METER = 100; 
   public static void main(String[] args)
   {
      final double CM_PER_INCH = 2.54;
   }
}
```

Enums
```java
class Test {
    static {
        enum Size {SMALL, MEDIUM, LARGE, EXTRA_LARGE};
        Size s = Size.MEDIUM;
    }
}
```

### Mathematical Functions and Constants
The Math class contains an assortment of mathematical functions that you may occasionally need, depending on the kind of programming that you do.

### Conversions between Numeric Types
 
![](/Users/al.martirosyan/IdeaProjects/library/core-java/src/chapter3/diagram.jpeg)

The six solid arrows in Figure denote conversions without information loss. The three dotted arrows denote conversions that may lose precision. For example, a large integer such as 123456789 has more digits than the float type can represent. When the integer is converted to a float, the resulting value has the correct magnitude but loses some precision.

### Casts

```java
class Test {
    static {
        double x = 9.997;
        int nx = (int) Math.round(x);
    }
}
```

### SWITCH 

```java
class Test{
    static{
        String seasonName = switch (seasonCode)
                {
                    case 0 -> "Spring";
                    case 1 -> "Summer";
                    case 2 -> "Fall";
                    case 3 -> "Winter";
                    default -> "???";
                };
    }
}
```

### Bitwise Operators

`& (“and”) | (“or”) ^ (“xor”) ~ (“not”)`
`There are also >> and << operators which shift a bit pattern right or left.`
`a >>> operator fills the top bits with zero, unlike >> which extends the sign bit into the top bits. There is no <<< operator.`

When applied to boolean values, the & and | operators yield a boolean value.
These operators are similar to the && and || operators, except that the & and | operators are not evaluated in “short circuit” fashion—that is,
both arguments are evaluated before the result is computed.

### Operator precedence 




| Operators                                   | Associativity |
|---------------------------------------------|---------------|
| [] . () (method call)                       | Left to right |
| ! ~ ++ -- + (unary) - (unary) () (cast) new | Right to left |
| * / %                                       | Left to right |
| + -                                         | Left to right |
| << >> >>>                                   | Left to right |
| < <= > >= instanceof                        | Left to right |
| == !=                                       | Left to right |
| &                                           | Left to right |
| ^                                           | Left to right |
| bitwise or                                  | Left to right |                
| &&                                          | Left to right |
| Or                                          | Left to right |
| ?:                                          | Right to left |
| = += -= *= /= %= &=  ^= <<= >>= >>>=        | Right to left |

