/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingGarage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import users.*;
/**
 *
 * @author jacob
 */

// we use a two dimensional array to keep track 
// of how many spots we have and which spots are open
// the first index is floor, the second is spot, and the third is if it is open
public class Garage {
    private int[][] _garageSpots; 
    private int _spots;
    private int _floors;
    private  Map<String, String> _garageMap = new HashMap<>();
    
    public Garage (int floors, int spots){
        
        _floors = floors;
        _spots = spots;
        
        _garageSpots = new int[_floors][_spots];
        
        
        // -- we initialize all empty spots to -1
        // -- what we will do is get the array from the database
        // -- and set this array to the one in the database
       
        
        for (int i = 0; i < _garageSpots.length; i++){
            for (int j = 0; j < _garageSpots[0].length; j++){
                
                // -- i am also going to try to keep track of information with a hashmap
                // -- because i think it might be easier to access information
                _garageMap.put(turnSpotToString(i,j), "-1");
                
                
                _garageSpots[i][j] = -1;
                
            }
         
        }
        
    }
    
    /**
     * 
     * @param id
     * @param inTime
     * @return 
     * 
     * Takes the information about the user, and assigns them a spot
     * 
     * 
     */
    
    /**
     * 
     * @param max
     * @return 
     */
    public int createRandom(int max){
        int min = 0;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        
        return randomNum;
    }
    
    public String turnSpotToString(int floor, int spot){
        
        String spotAsString = String.valueOf(floor) + "-" + String.valueOf(spot);
        return spotAsString;
        
    }
    
    public String createSpot(){
        boolean  _spotEmpty = false;
        
        String spotKey;
        
        int floor = -1;  // -- these are floors and spots the vehicle will 
        int spot = -1;   // -- be assigned
        
        int floorIncrement=0;   // -- we use these to increment the counter
        int spotIncrement=0;
        
        
        do{
 
            do{
                _spotEmpty = _garageSpots[floorIncrement][spotIncrement] == -1;
                
                // -- if the current spot is empty, save the assigned spot
                if (_spotEmpty){
                    spot = spotIncrement;
                    floor = floorIncrement;
                }
              
                // -- we the increment the spot to check the next one
            spotIncrement++;
            
                // -- we finish the loop when we've found an empty spot
                // -- or all the spots have been checked on this floor
            } while(!_spotEmpty && spotIncrement < _spots);
            
            // -- we then reset the spot to check and move onto the next floor
            spotIncrement = 0;
            floorIncrement++;
        }while(!_spotEmpty && floorIncrement < _floors);
        
        if(_spotEmpty){
            _garageSpots[floor][spot] = 1;
            spotKey = turnSpotToString(floor, spot);
            _garageMap.put(spotKey, "1");
        }
        
        String toReturn = turnSpotToString(spot,floor);
        return toReturn;
    }
    
    // -- we have a couple different check in methods as per the different type of users
    public boolean checkIn(LongTerm p){
        boolean canCheckIn = false;
        
        // -- if there are no empty 
        String noneEmpty = "-1--1";
        
        String userSpot = createSpot();
        
        if (!userSpot.equals(noneEmpty)){
            canCheckIn = true;
            p.setSpot(userSpot);
            sendToDatabase(p);
        }
        
        return canCheckIn;
    }
    
    // -- see above
    public boolean checkIn(Daily p){
        boolean canCheckIn = false;
        String noneEmpty = "-1--1";
        String userSpot = createSpot();
        
        if (!userSpot.equals(noneEmpty)){
            canCheckIn = true;
            p.setSpot(userSpot);
            sendToDatabase(p);
        }
        
        return canCheckIn;
    }
    
    
    public boolean checkOut(){
        // -- this will be determined by getting information from the db
        
        generatePayment();
        /**
        
        
        _garageSpots[floor][spot] = -1;
        
        **/
        
        return  false;
    }
    
    private boolean sendToDatabase(LongTerm p){
        return false;
    }
    
    private boolean sendToDatabase(Daily p){
        return false;
    }
    
    
    private double generatePayment(){
        // -- we will use shane's code in here at some poitn
        return 0;
    }
    
    public void printOut(){
         for (int i = 0; i < _garageSpots.length; i++){
            for (int j = 0; j < _garageSpots[0].length; j++){
                
                System.out.print(_garageSpots[i][j]);
            }
            System.out.println("");
        }
    }
    
}
