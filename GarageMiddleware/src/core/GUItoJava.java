package core;

import users.*;
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

    static Garage garage = new Garage(5, 10);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        getRequests();

    }

    /**
     * Obtains a request from the GUI
     */
    public static void getRequests() {
        // Holds the previous request number from the actual request to prevent
        // duplicate requests from coming through
        String prevReqNum = "";
        // Holds the number of times the program couldn't connect to the site
        int webErrorCount = 0;
        do {
            String req_num = "";
            try {

                req_num = findReqNum();
                // Will need to change the request number each time
                // TODO -> Create a loop to keep checking for a new request, making
                // sure that a request is not repeated
                String urlString = "http://localhost:3000/api/request?req_num=" + req_num;
                URL url = new URL(urlString);

                // Creates an HttpURLConnection object which will handle the communication
                // between this program and the GUI. Currently, this object is built
                // to GET data from the GUI
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                // Create a RequestHandler object to be used for POSTing data to the GUI
                String uri = "http://localhost:3000/api/receive";
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
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());

                    // Convert the data obtained from the GUI into a List<String> 
                    String response = sb.toString();
                    System.out.println(response);
                    if (!response.equals("{}\n")) {
                        response = response.substring(1);
                        response = response.substring(0, response.length() - 2);
                        List<String> headers = Arrays.asList(response.split(","));
                        System.out.println(headers);

                        // Pull out the value from "req_num" in the data from the request
                        int i = headers.size() - 1;
                        System.out.println(headers.get(i));
                        String[] items = headers.get(i).split(":");
                        String curr = items[1];

                        // If the request has a different req_num than the previous one,
                        // process the data and fulfull the request
                        if (!prevReqNum.equals(curr)) {
                            // Use the data from the GUI to interact with the garage and Users
                            fulfillRequest(headers);
                            // Update the prevReqNum to compare with the next request
                            prevReqNum = curr;
                        }
                    }


                    /* TODO make this section actually use info from the middleware
                     (For testing purposes) Put data into a HashMap to POST to the GUI
                    HashMap<String, String> postMap = new HashMap<>();
                    postMap.put("first", "Kevin");
                    postMap.put("last", "Hoffman");

                    // Convert the HashMap into a String so that the GUI can understand it
                    var objectMapper = new ObjectMapper();
                    String requestBody = objectMapper
                            .writeValueAsString(postMap);

                    // Send the POST request
                    r.postRequest(requestBody);
                    String garageBody = objectMapper
                            .writeValueAsString(garage.getGarageMap());

                    r.postRequest(garageBody);*/
                }

            } catch (IOException e) {
                System.out.println("We ran into an issue with connecting to "
                        + "the website. Could be the wrong URL or the site is down.");
                webErrorCount++;
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // Will end the loop and stop the program when it cannot connect
            // to the GUI
        } while (webErrorCount < 1);
    }

    /**
     * Use the current_req api from the GUI to determine the current request
     * number
     *
     * @return req_num String containing the current request number
     */
    public static String findReqNum() {
        String req_num = "";
        try {
            URL req = new URL("http://localhost:3000/api/current_req");
            HttpURLConnection req_conn = (HttpURLConnection) req.openConnection();
            req_conn.setRequestMethod("GET");
            req_conn.connect();

            int responsecode = req_conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                // Reads in data from the GUI
                BufferedReader br = new BufferedReader(new InputStreamReader(req_conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Write the data from the GUI to a string
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                String[] item = sb.toString().split(":");
                // Separate the identifier from the data value
                req_num = item[1].substring(0, item[1].length() - 2);
                System.out.println(req_num);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return req_num;
    }

    /**
     * Method which takes in a List<String> full of data from the GUI. This
     * method splits the List elements into two strings: one determining the
     * user method to be called, and one containing the value to be sent to that
     * user method. Will later have functionality to call methods in the parking
     * garage class itself
     *
     * @param data List<String> containing data and instructions from the GUI
     */
    public static void fulfillRequest(List<String> data) {
        // Variables to be used later on for passing data
        String first = "";
        String last = "";
        String phone = "";
        String startTime = "";
        String ID = "0001";
        String endTime = "";
        String username = "";
        String password = "";
        String email = "";
        String cardNum = "";
        String expiration = "";
        String securityCode = "";
        String zipCode = "";

        // Loop through the List in order to use the data within
        for (int i = 1; i < data.size() - 1; i++) {
            // Split the current entry based on where the ':' is
            String[] item = data.get(i).split(":");
            // Separate the identifier from the data value
            String identifier = item[0].substring(1, item[0].length() - 1);
            String value = item[1].substring(1, item[1].length() - 1);
            // Sanity check
            System.out.println(identifier);
            System.out.println(value);

            // Determine what data is being passed 
            switch (identifier) {
                case "ID":
                    ID = value;
                    break;

                case "first":
                    first = value;
                    break;

                case "last":
                    last = value;
                    break;

                case "phone":
                    phone = value;
                    break;

                case "start_time":
                    startTime = value;
                    break;

                case "end_time":
                    endTime = value;
                    break;

                case "uname":
                    username = value;
                    break;

                case "pass":
                    password = value;
                    break;

                case "email":
                    email = value;
                    break;

                case "cnum":
                    cardNum = value;
                    break;

                case "exp":
                    expiration = value;
                    break;

                case "sec":
                    securityCode = value;
                    break;

                case "zip":
                    zipCode = value;
                    break;

                default:
                    break;

            }
        }

        // Grab the first entry, which is the name of the functionality to be used
        // Split the current entry based on where the ':' is
        String[] item = data.get(0).split(":");
        // Separate the identifier from the data value
        String function = item[1].substring(1, item[1].length() - 1);
        // Check to see what function is requested
        switch (function) {
            case "checkin":
                Daily dailyUser = new Daily(Integer.parseInt(ID), first, last);
                dailyUser.setTimeIn(startTime);
                garage.checkIn(dailyUser);
                break;
            case "checkout":
                // Use the ID to pull a User class from the DB
                // Set the timeOut
                // Call checkOut method
                break;
            case "register":
                LongTerm longUser = new LongTerm(Integer.parseInt(ID), first, last);
                longUser.setTimeIn(startTime);
                longUser.setPassword(password);
                String paymentInfo = cardNum + "/" + expiration + "/" + securityCode
                        + "/" + zipCode;
                longUser.setPaymentInfo(paymentInfo);
                longUser.setEmail(email);
                garage.checkIn(longUser);
                break;

            // These 3 are dependent on the database being completed
            case "view":
                // Call method to view user's info
                
                break;
            case "edit":
                break;
            case "close":
                break;
            default:
                break;

        }

    }
}
