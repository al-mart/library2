let http = require('Backend/nodejs/books/beggining_node_js/http');

let server = http.createServer(function (request, response) {
    console.log('request starting...');
    console.log(request.headers);

    // respond
    response.write('hello client!');
    response.end();

});

server.listen(3000);
console.log('Server running at http://127.0.0.1:3000/');
