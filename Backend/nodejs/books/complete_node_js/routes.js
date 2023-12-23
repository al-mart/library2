const fs = require('fs');
const requestHandler = (req, res) => {
    if (req.url === 'message' && req.method === 'POST')
        console.log(req.url, req.method, req.headers);
    const dataChunks = [];
    req.on("data", (chunk) => {
        dataChunks.push(chunk);
    });
    req.on("end", () => {
        const parsedBody = Buffer.concat(dataChunks).toString();
        fs.writeFile('message.txt', parsedBody, (err) => {
            res.setHeader('Location', '/');
            res.statusCode = 302;
            res.end();
        })
    });
}

exports.requestHandler = requestHandler;
