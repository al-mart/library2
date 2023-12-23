# Types

### Primitives

- string
- boolean
- number
- symbol
- bigint
    #### Specials
- null
- undefined

### Non-Primitives 

- Object 

*An ordinary JavaScript object is an unordered collection of named values*

The language also defines a special kind of object, known as an array, that represents an ordered collection of numbered values.

PRIMITIVES ARE IMMUTABLE. OBJECTS ARE MUTABLE.


#### Functions and Classes

Like any JavaScript value that is not a primitive value, functions and classes are a specialized kind of object.


In JavaScript, null and undefined are the only values that methods cannot be invoked on.

JavaScript constants and variables are untyped: declarations do not specify what kind of values will be assigned


### NUMBERS

JavaScript represents numbers using the 64-bit floating-point format defined by the IEEE 754 standard,1 which means it can represent numbers as large as ±1.7976931348623157 × 10308 and as small as ±5 × 10−324.
The JavaScript number format allows you to exactly represent all integers between −9,007,199,254,740,992 (−253) and 9,007,199,254,740,992 (253), inclusive

####Integer Literals
```js
3         // normal 10 based 
OxBADCAFE // hexadecimal
0b10101  // binar
0o377    // octal
```
####Floating point
```js
3.14
2345.6789
.333333333333333333
6.02e23        // 6.02 × 10²³
1.4738223E-32  // 1.4738223 × 10⁻³²
```


You can use underscores within numeric literals to break long literals up into chunks that are easier to read:
```js
let billion = 1_000_000_000;   // Underscore as a thousands separator.
let bytes = 0x89_AB_CD_EF;     // As a bytes separator.
let bits = 0b0001_1101_0111;   // As a nibble separator.
let fraction = 0.123_456_789;  // Works in the fractional part, too.
```

###NULL and Undefined

Null is a keyword that evaluates to a special value
```js
typeof null === "object"
```


The undefined value represents a deeper kind of absence.

- value of variables that have not been initialized
- value of object property that doesn't exist
- array element that does not exist.
- the return value of functions that do not explicitly return a value 
- function parameters for which no argument is passed.

undefined is a predefined global constant (not a language keyword like null, though this is not an important distinction in practice) that is initialized to the undefined value.

```js
typeof undefined === 'undefined';
```

```js
null == undefined  // true
null === undefined // false
```

### SYMBOL

Symbols were introduced in ES6 to serve as non-string property names


```js
let s = Symbol.for("shared");  // returns new symbol 
let t = Symbol.for("shared");  // returns the same symbol as it exists in global registry
s === t          // => true
s.toString()     // => "Symbol(shared)"  
Symbol.keyFor(t) // => "shared"   
```

### GLOBAL

```js
window // in browser
global // in node 
self // in web workers 
globalThis
```

ES2020 finally defines globalThis as the standard way to refer to the global object in any context.

## Type Conversion

when JavaScript expects a boolean value, you may supply a value of any type, and JavaScript will convert it as needed. Some values (“truthy” values) convert to true and others (“falsy” values) convert to false
