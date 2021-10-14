const express = require('express');
const bodyParser = require('body-parser');
const url = require('url');
const querystring = require('querystring');
const app = express();

app.use(express.static('public'));
app.use(express.json({limit:'5mb'}));

var req_info = {}
var current_req = 1

app.post('/api/request', async function (request, response) {
    let data = request.body;
    console.log(data);
    req_info = data
    response.status(200).send(data);
})
app.get('/api/request', async function (request, response) {
    let data = {}
    console.log(current_req);
    if (request.query.req_num == current_req) {
        data = req_info
        current_req++
        console.log(data);
    }
    else {
        console.log('Not expected request')
    }
    response.send(JSON.stringify(data));
});

app.listen(3000, () => console.log('listening at 3000'));
    
