const express = require('express');

const app = express();


app.use('/hello', (req, res, next) => {
    console.log(req.url)
    req.send(`<h1>${req.url} world!</h1>`);
});

app.use('/', (req, res, next) => {
    console.log(req.url)
    req.send(`<h1>${req.url}</h1>`);
});

app.listen(3000)


