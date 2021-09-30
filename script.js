let submit = document.getElementById('submit');
let spot = document.querySelector('.spot');
let close = document.querySelector('.close');
let name = document.querySelector('.name');
let start = document.querySelector('.start');
let finish = document.querySelector('.finish');

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
    
    /* Date: YYYY/mm/dd */
    var d = new Date();
    var YYYY = d.getFullYear();
    var mm = d.getMonth();
    var dd = d.getDay();
    var hh = d.getHours();
    var min = d.getMinutes();

    if(mm < 10) {
        mm = "0" + mm;
    }
    if(dd < 10) {
        dd = "0" + dd;
    }

    let date_string = YYYY + "/" + mm + "/" + dd;
    let time = hh + ":" + min;
       
 
    var id = Math.floor(Math.random()*100000);
    mod.style.display =  "block";
    let spots = ['G14', 'A12', 'C05'];
    var index = Math.floor(Math.random() * spots.length);
    const nam = "Kevin Hoffman";
    spot.innerHTML = spots[index];
    name.innerHTML = "<b>Full name: </b>" + fname.value + " " + lname.value;
    phone.innerHTML = "<b>Phone Number: </b>" + pnum.value;
    start.innerHTML = "<b>Start Time: </b>" + date_string + " " + time;
    h2.innerHTML = "<b>ID: </b>" + id;
    // finish.innerHTML = "<b>Expected Finish Time: </b>" + "Kevin Hoffman"; 
});

close.addEventListener('click', () => {
    let mod = document.querySelector('.modal');
    mod.style.display = "none";    
    $.get("http://127.0.0.1:5500/temp.html", function(data) {
        console.log(data);
    });
});