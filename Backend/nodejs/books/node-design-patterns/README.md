#Node.js Design Patterns - Third Edition

##MODULES

- CommonJS (cjs require())
- AMD (asynchronous module definition requireJs())
- UMD (universal module definition)
- ESM (ES Modules import {} )

```js
// REVEALING MODULE PATTERN 
const myModule = (() => {
    const privateFoo = () => {}
    const privateBar = []
    const exported = {
        publicFoo: () => {},
        publicBar: () => {}
    }
    return exported
})() // once the parenthesis here are parsed, the function
     // will be invoked
console.log(myModule)
console.log(myModule.privateFoo, myModule.privateBar)
```

##CommonJS is the first module system originally built into Node.js
- `require` is a function that allows you to import a module from the local filesystem
- `exports` and `module.exports` are special variables that can be used to export public functionality from the current module.
     Exports is a reference to module exports so if we assign something to it, we will have an error 

The essential concept to remember is that everything inside a module is private unless it's assigned to the module.exports variable. The content of this variable is then cached and returned when the module is loaded using require().

The require function is synchronous

###The resolving algorithm

- File modules: If moduleName starts with `/`, it is already considered an absolute path to the module and it's returned as it is. 
If it starts with ./, then moduleName is considered a relative path, which is calculated starting from the directory of the requiring module.
- Core modules: If moduleName `is not prefixed with / or ./`, the algorithm will first try to search within the core Node.js modules.
- Package modules: If no core module is found matching moduleName, then the search continues by looking for a matching module in the first
`node_modules` directory that is found navigating up in the directory structure starting from the requiring module.
The algorithm continues to search for a match by looking into the next node_modules directory up in the directory tree, until it reaches the root of the filesystem.


File Resolution
- `<moduleName>.js`
- `<moduleName>/index.js`
- The directory/file specified in the `main` property of `<moduleName>/package.json`


###Module definition patterns

#### Named Module exports
```js
// file logger.js
exports.info = (message) => {
  console.log(`info: ${message}`)
}
exports.verbose = (message) => {
  console.log(`verbose: ${message}`)
}

// file main.js
const logger = require('./logger')
logger.info('This is an informational message')
logger.verbose('This is a verbose message')
```

####substack pattern

```js
// file logger.js
module.exports = (message) => {
  console.log(`info: ${message}`)
}

// file main.js
const logger = require('./logger');
logger();
```
####OR Modified

```js
// file logger.js
module.exports = (message) => {
     console.log(`info: ${message}`)
}

module.exports.verbose = (message) => {
  console.log(`verbose: ${message}`)
}

// file main.js
const logger = require('./logger');
logger('This is an informational message');
logger.verbose('This is a verbose message');
```

####Exporting a class
```js
// logger.js
class Logger {
  constructor (name) {
    this.name = name
  }
  log (message) {}
  info (message) {}
  verbose (message) {}
}
module.exports = Logger

// file main.js
const Logger = require('./logger')
const dbLogger = new Logger('DB')
dbLogger.info('This is an informational message')
const accessLogger = new Logger('ACCESS')
accessLogger.verbose('This is a verbose message')
```

####Exporting a class instance

```js
// file logger.js
class Logger {
  constructor (name) {
    this.count = 0
    this.name = name
  }
  log (message) {
    this.count++
    console.log('[' + this.name + '] ' + message)
  }
}
module.exports = new Logger('DEFAULT')

// main.js
const logger = require('./logger')
logger.log('This is an informational message')
```

It is kind of singleton but it is not in fact. 

We can also hack it as 
```js
const logger = require("./logger");
const otherLogger = new logger.constructor('OTHER');
```

#### Modifying other modules or the global scope
We said that a module can modify other modules or objects in the global scope; well, this is called monkey patching.

```js
// file patcher.js
// ./logger is another module
require('./logger').customMessage = function () {
  console.log('This is a new functionality')
}
// file main.js
require('./patcher')
const logger = require('./logger')
logger.customMessage()
```

##ESM: ECMAScript modules

```js
//named Export
export const myFunc = () => {};

import {myFunc} from './func'

import * as functions from './func';

//default export 

export default myFunc = () => {}

// Here we can use any name to import myFunc default as it is exported with default name 

import myFuncDefault from './myFunc';

import myFunc, {myFunc2} from './myFunc';

// async inport

import(translationModule)
        .then((strings) => {                                 
             console.log(strings.HELLO)
        });
```

###Module loading in depth
####Loading phases

The goal of the interpreter is to build a graph of all the necessary modules (a dependency graph).

When the node interpreter is launched, it gets passed some code to execute, generally in the form of a JavaScript file. This file is the starting point for the dependency resolution, and it is called the entry point. From the entry point, the interpreter will find and follow all the import statements recursively in a depth-first fashion, until all the necessary code is explored and then evaluated.
- Phase 1 - Construction (or parsing): Find all the imports and recursively load the content of every module from the respective file.
- Phase 2 - Instantiation: For every exported entity, keep a named reference in memory, but don't assign any value just yet. Also, references are created for all the import and export statements tracking the dependency relationship between them (linking). No JavaScript code has been executed at this stage.
- Phase 3 - Evaluation: Node.js finally executes the code so that all the previously instantiated entities can get an actual value. Now running the code from the entry point is possible because all the blanks have been filled


Due to its dynamic nature, CommonJS will execute all the files while the dependency graph is explored. We have seen that every time a new require statement is found, all the previous code has already been executed. This is why you can use require even within if statements or loops, and construct module identifiers from variables.
In ESM, these three phases are totally separate from each other, no code can be executed until the dependency graph has been fully built, and therefore module imports and exports have to be static.

####Read-only live bindings
This module exports two values: a simple integer counter called count and an increment function that increases the counter by one.

Let's now write some code that uses this module:

```js
// counter.js
export let count = 0
export function increment () {
  count++
}


// main.js
import { count, increment } from './counter.js'
console.log(count) // prints 0
increment()
console.log(count) // prints 1
count++ // TypeError: Assignment to constant variable!
```
This approach is fundamentally different from CommonJS. In fact, in CommonJS, the entire exports object is copied (shallow copy) when required from a module. This means that, if the value of primitive variables like numbers or string is changed at a later time, the requiring module won't be able to see those changes.

But we can still monkey patch in ESM
```js
// mock-read-file.js
import fs from 'fs'
const originalReadFile = fs.readFile                     
let mockedResponse = null
function mockedReadFile (path, cb) {                     
  setImmediate(() => {
    cb(null, mockedResponse)
  })
}
export function mockEnable (respondWith) {              
  mockedResponse = respondWith
  fs.readFile = mockedReadFile
}
export function mockDisable () {                         
  fs.readFile = originalReadFile
}

// main.js
import fs from 'fs'                                      
import { mockEnable, mockDisable } from './mock-read-file.js'
mockEnable(Buffer.from('Hello World'))                      
fs.readFile('fake-path', (err, data) => {                    
     if (err) {
          console.error(err)
          process.exit(1)
     }
     console.log(data.toString()) // 'Hello World'
})
mockDisable()
```
But it will not work if we try 
```js
import * as fs from 'fs' // 

import { readFile } from 'fs'
```

But we can use 
```js
import fs, { readFileSync } from 'fs'
import { syncBuiltinESMExports } from 'module'
fs.readFileSync = () => Buffer.from('Hello, ESM')
syncBuiltinESMExports()
console.log(fs.readFileSync === readFileSync) // true
```

###ESM and CommonJS differences and interoperability

- ESM runs in strict mode
- Missing references in ESM
```js
console.log(exports) // ReferenceError: exports is not defined
console.log(module) // ReferenceError: module is not defined
console.log(__filename) // ReferenceError: __filename is not defined
console.log(__dirname) // ReferenceError: __dirname is not defined

import { fileURLToPath } from 'url'
import { dirname } from 'path'
const __filename = fileURLToPath(import.meta.url)
const __dirname = dirname(__filename)
import { createRequire } from 'module'
const require = createRequire(import.meta.url)


// this.js - ESM
console.log(this) // undefined
// this.cjs – CommonJS
console.log(this === exports) // true

import packageMain from 'commonjs-package' // Works
import { method } from 'commonjs-package'  // Errors

import data from './data.json'             // Errors

import { createRequire } from 'module'
const require = createRequire(import.meta.url)
const data = require('./data.json')
console.log(data)
```
