##JAVASCRIPT CONCEPTS

A programming language is said to have <span style="color: yellow">first-class functions</span> if a function can be treated the same way as any other variable in the language.

Functions that take functions as arguments are called <span style="color: yellow">higher-order functions.</span>

LESSON ONE: `prototype` saves memory

LESSON TWO: `prototype` is not good for properties you plan on writing to.

###Path Module

`path.normalize(str)`

`path.join([str1], [str2], …)`

`dirname basename extname`

###FS Module
FileSystem utilities
`fs.unlink()  fs.writefile()`


###OS Module
Operating system utilities 
`os.totalmem() os.freemem()`

###Util Module
Utilitys `util.format() util.isDate()`



##MODULE DEFINITIONS

###Commonjs 
Used by node. Loads modules synchronously.
```js
module.exports.foo = function () {}

let bas = require('foo');
```


##AMD
Async module definition 
Used in browser

used with requirejs

```html
<script
    src="./require.js"
    data-main="./client/app">
</script>
```

```js
define(['./foo', './bar'], function (foo, bar) {
        // use foo and bar here
});
```


##Event and streams

###Event emitter

```js
//event-emitter.js

let EventEmitter = require('events').EventEmitter;
let emitter = new EventEmitter();

// Listener addition / removal notifications
emitter.on('removeListener', function (eventName, listenerFunction) {
    console.log(eventName, 'listener removed', listenerFunction.name);
});
emitter.on('newListener', function (eventName, listenerFunction) {
    console.log(eventName, 'listener added', listenerFunction.name);
});

function a() { }
function b() { }

// Add
emitter.on('foo', a);
emitter.on('foo', b);

// Remove
emitter.removeListener('foo', a);
emitter.removeListener('foo', b);

```


##HTTP



<span style="color: yellow"></span>
