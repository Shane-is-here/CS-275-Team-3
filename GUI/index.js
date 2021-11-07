const express = require('express');
const bodyParser = require('body-parser');
const url = require('url');
const querystring = require('querystring');
const app = express();

app.use(express.static('public'));
app.use(express.json({limit:'5mb'}));

var user_request = {}
var middle_post = {}
var current_req = 1

// Communication from the GUI to the Middleware
app.post('/api/request', async function (request, response) {
    let data = request.body;
    console.log(data);
    user_request = data
    user_request.req_num = current_req
    response.status(200).send(data);
})
app.get('/api/request', async function (request, response) {
    let data = {}
    console.log(current_req);
    if (request.query.req_num == current_req) {
        data = user_request
        current_req++
        console.log(data);
    }
    else {
        console.log('Not expected request')
    }
    response.send(JSON.stringify(data));
});

// Communication from the Middleware to the GUI
app.post('/api/receive', async function (request, response) {
    let data = request.body;
    console.log(data);
    middle_post = data
    response.status(200).send(data);
})
app.get('/api/receive', async function (request, response) {
    let data = {}
    console.log(current_req);
    if (request.query.req_num == current_req) {
        data = middle_post
        current_req++
        console.log(data);
    }
    else {
        console.log('Not expected request')
    }
    response.send(JSON.stringify(data));
});
app.get('/api/current_req', async function (request, response) {
    let data = {
        current_req: current_req
    }
    response.send(JSON.stringify(data));
});


app.listen(3000, () => console.log('listening at 3000'));
