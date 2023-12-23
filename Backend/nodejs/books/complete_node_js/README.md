# Complete Node JS Developer tutorial

### CORE modules

- http
- https
- fs
- path
- os

### BASIC SERVER
```js
const http = require('http');
http.createServer((req, res) => {
    console.log(req);
}).listen(3000);
```
NODE runs as long as there is an event listening as we see the server is listening.
```js
process.exit();
```
Hard exit the event loop in node. 

```js
    res.setHeader('Content-Type', 'text/html');
    res.write("<div>Hello World!</div>")
    res.end();
```

### BLOCKING I/O

```js
// NON BLOCKING
fs.writeFile('name', data, (err) => {
    
}); 

// Blocking
fs.writeFileSync('name', data);
```

### Event Loop 

- Timers (Executes setTimeout setInterval callbacks)
- Pending Callbacks (Executes I/O related callbacks that were detected)
- Poll (Retrieve new I/O events, executes their callbacks)
- Check (Execute setImmediate() callbacks(execute after current cycle))
- Close Callbacks (Execute all 'close' event callbacks)
- process.exit (if refs === 0 (this is the reference of registered listeners ))


### Express

Express is all about middleware.
So we get request on use() and calling next() we pass it to the next use();

```js
const express = require('express');
const app = express();

app.use((req, res, next) => {
    console.log(1)
    next();
});

app.use((req, res, next) => {
    console.log(2);
    res.send("Hello World!")
});
app.listen(3000)
```
