/*
* This class is designed to handle HTTP requests from the GUI and send the 
* information to the rest of the middleware
*/
package core;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.URI;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Map;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author brenden
 */
public class RequestHandler {
 

private String _uri;
private final HttpClient _client;



    public RequestHandler(String uri){
        _uri = uri;
        _client = HttpClient.newHttpClient();
        
    }
    
    public void updateUri(String uri){
        _uri = uri;
    }
    
    
    public int getStatus() throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(_uri))
                .GET()
                .build();
        
        HttpResponse<Void> response = _client.send(request, 
                HttpResponse.BodyHandlers.discarding());
        
        int status = response.statusCode();
        return status;
    }
    
    
    public Map<String, List<String>> getRequest() throws IOException, InterruptedException{
        var request = HttpRequest.newBuilder(URI.create(_uri))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        Path directory = Paths.get("/home/brenden/NetBeansProjects");
        
        HttpResponse<String> response = _client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        HttpHeaders headers = response.headers();
        System.out.println(response.body());
        System.out.println(response.statusCode());
        return headers.map();
        
    }
    
    // This chunk of code to be used to convert user data into the proper format
    // to send to the GUI
    /* var values = new HashMap<String, String>() {{
            put("name", "John Doe");
            put ("occupation", "gardener");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);*/
    
    
    public void postRequest(String data) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
            .header("Content-Type", "application/json")
            .uri(URI.create(_uri))
            .POST(HttpRequest.BodyPublishers.ofString(data))
            .build();

        HttpResponse<?> response = _client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
        
    }
    
    
    
}
