// Given an existing server route, post the body data to the header of the url
function post(url, body) {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    };
    fetch(url, options)
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.log('Error:',error);
    })
}

// Waits for middleware to post, then extracts the post using a get method. Returns and empty object if the middleware does not post.
async function wait() {
    console.log("Waiting for post");
    current_req = -1;

    let i = 0;
    while(current_req == -1 && i < 20) {
        received_data = await fetch('/api/receive?req_num='+ current_req);
        received_data = await received_data.json();
        console.log(received_data);
        if(Object.keys(received_data).length != 0) {
            current_req = received_data.current_req;
        }
        
        console.log('waiting...');
        await sleep(500);
        i++;  
    }
    return received_data;
}

// Pause the program for specified ms milliseconds
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
