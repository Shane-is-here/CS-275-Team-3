let sub = document.querySelector('#subtract');
let input = document.querySelector('input');
let add = document.querySelector('#add');
let submit = document.querySelector('.submission');
var hours = -1;

add.addEventListener('click', () => {
    val = parseInt(input.value);
    if (val == 0) {
        sub.style.opacity = "1";
        sub.style.cursor = "pointer";
    } 
    input.value = parseInt(input.value) + 1;
    
});

sub.addEventListener('click', () => {
    val = parseInt(input.value);

    if (val > 0) {
        if (val == 1) {
            sub.style.opacity = "0";
            sub.style.cursor = "auto";
        }
        input.value = val - 1;
        
    } 
    
});

submit.addEventListener('click', () => {
    hours = parseInt(input.value);
    var d = new Date();
    var t = d.getTime();
    console.log(hours, t)
});
