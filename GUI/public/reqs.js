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
    let i = 0;
    let isFound = false;
    let received_data = {}
    let current_req = {}
    let result = {}
    while(!isFound && i < 20) {
        current_req = await fetch('/api/current_req');
        current_req = await current_req.json();

        received_data = await fetch('/api/receive?req_num='+ current_req.current_req);
        received_data = await received_data.json();

        if(Object.keys(received_data).length > 0 && received_data.req_num == current_req.current_req) {
            try{
                current_req = received_data.current_req;
                isFound = true;
                result = received_data
            } catch {

            }
            
        }
        console.log('waiting...');
        await sleep(1000);
        i++;  
    }
    return result;
}

// Pause the program for specified ms milliseconds
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
