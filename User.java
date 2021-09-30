

package users;
/**
 *
 * @author brend
 */
public class User {
    // Identification number of the user
    protected int userID;   
    // String containing the coordinates of the parking spot
    protected String parkingSpot;   
    // String (or int?) containing the exact time of entry of the user
    // Format is YYYY/MM/DD 
    protected String timeIn;
    // String (or int?) containing the exact time the user exits the garage
    protected String timeOut;
    // String containing the User's first name
    protected String firstName;
    // String containing the User's last name
    protected String lastName;
    
    /**
     * Constructor for the User class
     * @param userID Integer containing the identification number of the user
     * @param name String containing the first and last name of the user
     */
    public User(int userID, String name){
        this.userID = userID;
        
    }
    
    /**
     * Method that returns the ID of the user as an int
     * @return The user's ID number
     */
    public int getID(){
        return this.userID;
    }
    
    /**
     * Method to set/change the user's ID number
     * @param userID New ID number to be used
     */
    public void setID(int userID){
        this.userID = userID;
        System.out.println("ID set!");
    }
    
    /**
     * Method to get the time that the user entered the garage
     * @return A string containing the exact time of entry
     */
    public String getTimeIn(){
        return this.timeIn;
    }
    
    /**
     * Method to set/change the time the user entered
     * @param timeIn String containing the time at which the user entered
     */
    public void setTimeIn(String timeIn){
        this.timeIn = timeIn;
        System.out.println("Time In set!");
    }
    
    /**
     * Method to get the user's parking spot
     * @return A String containing the coordinates of the user's spot
     */
    public String getSpot(){
        return this.parkingSpot;
    }
    
    /**
     * Method to set/change the user's parking spot
     * @param parkingSpot A String containing the coordinates of the parking spot
     */
    public void setSpot(String parkingSpot){
        this.parkingSpot = parkingSpot;
        System.out.println("Parking spot set!");
    }
    
    /**
     * Method to get the time that the user exited the garage
     * @return A string containing the exact time of exit
     */
    public String getTimeOut(){
        return this.timeOut;
    }
    
    /**
     * Method to set/change the time the user exited the garage
     * @param timeOut String containing the time at which the user exited
     */
    public void setTimeOut(String timeOut){
        this.timeOut = timeOut;
        System.out.println("Time Out set!");
    }
    
    public void saveToDB(){
        // Insert way to save to the database
    }
 }    