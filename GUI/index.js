const express = require('express');
const app = express();

app.use(express.static('public'));
app.use(express.json({limit:'5mb'}));

app.post('/api/request', function (request, response) {
    let data = request.body;
    console.log(data);
    response.status(200).send(JSON.stringify(data));
})
app.get('/temp.html', function (request, response) {
    let data = request.body;
    console.log(request.body);
    response.json(data);
});

app.listen(3000, () => console.log('listening at 3000'));
    