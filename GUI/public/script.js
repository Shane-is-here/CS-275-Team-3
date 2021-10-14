let submit = document.getElementById('submit');
let spot = document.querySelector('.spot');
let close = document.querySelector('.close');
let name = document.querySelector('.name');
let start = document.querySelector('.start');
let finish = document.querySelector('.finish');

var reqNum = 0

submit.addEventListener('click', async () => {
    let fname = document.getElementById('fname');
    let lname = document.getElementById('lname');
    let pnum = document.getElementById('pnum');
    let block = document.querySelector('.block');
    let phone = document.querySelector('.phone');
    let mod = document.querySelector('.modal');
    let h2 = document.querySelector('.h2');
    block.style.display = "block";
    block.style.display = "none";

    reqNum = reqNum + 1;
    
    /* Date: YYYY/mm/dd */
    let d = new Date();
    let YYYY = d.getFullYear();
    let mm = d.getMonth();
    let dd = d.getDay();
    let hh = d.getHours();
    let min = d.getMinutes();

    let date_string = YYYY + "/" + mm + "/" + dd;
    let time = hh + "/" + min;

    const data = {
        request: 0,
        first: fname.value,
        last: lname.value,
        phone: pnum.value,
        start_time: date_string + '/' + time,
        ID: id,
        req_num: reqNum
    };

    post('/api/request', data);

    var id = Math.floor(Math.random()*100000);
    mod.style.display =  "block";
    let spots = ['G14', 'A12', 'C05'];
    var index = Math.floor(Math.random() * spots.length);
    spot.innerHTML = spots[index];
    name.innerHTML = "<b>Full name: </b>" + fname.value + " " + lname.value;
    phone.innerHTML = "<b>Phone Number: </b>" + pnum.value;
    start.innerHTML = "<b>Start Time: </b>" + date_string + " " + time;
    h2.innerHTML = "<b>ID: </b>" + id;

    
    // finish.innerHTML = "<b>Expected Finish Time: </b>" + "Kevin Hoffman"; 
});

close.addEventListener('click', async () => {
    let mod = document.querySelector('.modal');
    mod.style.display = "none";    

    fetch('/api/request?req_num=1')
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
});
