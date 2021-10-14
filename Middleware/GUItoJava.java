
package guitojava;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author brenden
 */
public class GUItoJava {
    
    /**
     * Code to be put into the parking garage class; contains code to GET and POST
     * data to/from the GUI. Still a work in progress, but has functionality.
     * @param args 
     */
    public static void main(String[] args) {
        try {

            // Will need to change the request number each time
            // TODO -> Create a loop to keep checking for a new request, making
                    // sure that a request is not repeated
            URL url = new URL("http://localhost:3000/api/request?req_num=1");
            
            // Creates an HttpURLConnection object which will handle the communication
            // between this program and the GUI. Currently, this object is built
            // to GET data from the GUI
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            // Create a RequestHandler object to be used for POSTing data to the GUI
            String uri = "http://localhost:3000/api/request";
            RequestHandler r = new RequestHandler(uri);
            
            //Getting the response code as verification that the request worked
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                // Reads in data from the GUI
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Write the data from the GUI to a string
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                System.out.println(sb.toString());
                
                // (For testing purposes) Put data into a HashMap to POST to the GUI
                HashMap<String, String> postMap = new HashMap<>();
                postMap.put("first", "Kevin");
                postMap.put("last", "Hoffman");
                
                // Convert the HashMap into a String so that the GUI can understand it
                var objectMapper = new ObjectMapper();
                String requestBody = objectMapper
                .writeValueAsString(postMap);
                
                // Send the POST request
                r.postRequest(requestBody);
                
                // Convert the data obtained from the GUI into a List<String> 
                String response = sb.toString();
                response = response.substring(1);
                response = response.substring(0, response.length());
                List<String> headers = Arrays.asList(response.split(","));
                System.out.println(headers);
                
                // Use the data from the GUI to interact with the garage and Users
                fulfillRequest(headers);
                
            }
            
            
        }
        catch(IOException | InterruptedException e){
            System.out.println("You probably have the wrong URL bud.");
        }
        
           
    }
    
    /**
     * Method which takes in a List<String> full of data from the GUI.
     * This method splits the List elements into two strings: one determining
     * the user method to be called, and one containing the value to be sent
     * to that user method. Will later have functionality to call methods in 
     * the parking garage class itself
     * @param data List<String> containing data and instructions from the GUI
     */
    public static void fulfillRequest(List<String> data){
        // Loop through the List in order to use the data within
        for(int i = 1; i < data.size()-1; i++){
            // Split the current entry based on where the ':' is
            String[] item = data.get(i).split(":");
            // Separate the identifier from the data value
            String identifier = item[0].substring(1, item[0].length()-1);
            String value = item[1].substring(1, item[1].length()-1);
            System.out.println(identifier);
            System.out.println(value);
            
            
            switch(identifier){
                case "first":
                    // Save first name to user as String
                    break;
                case "last":
                    // Save last name to user as String
                    break;
                case "phone":
                    // Save phone number to user as String
                    break;
                case "start_time":
                    // Save checkIn time to user as String
                    break;
                default:
                    break;
                
            }
        }
        
        
    }
}
