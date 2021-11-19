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
    let current_req = await fetch('/api/current_req');
    current_req = await current_req.json();
    console.log(current_req.current_req);
    let i = 0;
    let isFound = false;
    while(!isFound && i < 5) {
        let current_req = await fetch('/api/current_req');
        current_req = await current_req.json();

        received_data = await fetch('/api/receive?req_num='+ current_req.current_req);
        received_data = await received_data.json();
        if(Object.keys(received_data).length > 0) {
            try{
                current_req = received_data.current_req;
                isFound = true;
            } catch {

            }
            
        }
        
        console.log('waiting...');
        await sleep(1000);
        i++;  
    }
    return received_data;
}

// Pause the program for specified ms milliseconds
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
