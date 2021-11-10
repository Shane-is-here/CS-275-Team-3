/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import users.*;
import java.util.HashMap;
import java.util.Map;
import DatabaseInterface.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
/**
 *
 * @author jacob
 */

/**
 *  There are four main variables for the garage class -- 
 *  how many floors, how many spots per floor
 *  how to keep track of which spots are open
 *  and the interface object that allows us to communicate with the DB
 * @author jacob
 */
public class Garage {
    
    private int _spots;
    private int _floors;
    private  Map<String, Integer> _garageMap = new HashMap<>();
    private DBInterface dbInterface = new DBInterface();
    private static int car_count;
    
    private final double HOURLYRATE = 2.00;
    private final double DAILYRATE = 30;
    
    
    public Garage (int floors, int spots){
        
        // -- these are how many spots and floors we have
        _floors = floors;
        _spots = spots;
        
        // -- this is the map keeping track of spots from the garage
        Map<String, Integer> _garageMapFromDB = dbInterface.getGarageSpots();
        
        
       
       // -- in case we cannot get information from the database
       // -- we set all of the spots to empty
        for (int i = 0; i <  _floors; i++){
            for (int j = 0; j <  _floors; j++){
               
                // -- we turn the spot to the key in the hashmap, and enter 
                // -- the spot as empty
                _garageMap.put(turnSpotToKey(i,j), 0);
              
            }
            
        _garageMap.putAll(_garageMapFromDB);
         
        }
        
    }
    
    
   /**
    * -- we have a specific key to any floor/spot combination in the entire garage
    * and it is a string in the format of floor-spot 
    * 
    * for example 4-11 is the 11th spot on floor 4
    * 
    * @param floor -- this is the floor the spot is on
    * @param spot -- and this is the specific spot the user is parking in
    * @return   -- we return the spot as a key to the spot in the hashmap
    */
    
    public String turnSpotToKey(int floor, int spot){
        
        String spotAsString = String.valueOf(floor) + "-" + String.valueOf(spot);
        return spotAsString;
        
    }
    
    /**
     * in this method, we loop through all of the spots in the garage until we 
     * find an empty on
     * 
     * @return 
     */
    public String assignSpot(){
        boolean  _spotEmpty = false;
        
        String spotKey;
        
        int floor = -1;  // -- these are floors and spots the vehicle will 
        int spot = -1;   // -- be assigned
                         // -- we say that it is -1, so the program doesnt crash lol
                         
        String space;   // -- this will hold the key for the hashmap for any given space
        
        int floorIncrement=0;   // -- we use these to increment the counter 
        int spotIncrement=0;
        
        
        do{
 
            do{
                // -- we get the key for the current space in the loop
                 space = turnSpotToKey(floorIncrement,spotIncrement);
                 // -- and we check if it is empty
                _spotEmpty = _garageMap.get(space) == 0;
                
                // -- if the current spot is empty, save the assigned spot
                // -- into the variables that hold the spot and floor to be saved
                if (_spotEmpty){
                    spot = spotIncrement;
                    floor = floorIncrement;
                }
              
                // -- we the increment the spot to check the next spot
            spotIncrement++;
            
                // -- we finish the loop when we've found an empty spot
                // -- or all the spots have been checked on this floor
            } while(!_spotEmpty && spotIncrement < _spots);
            
            // -- we then reset the spot to 0 in order to check and move onto the next floor
            
            spotIncrement = 0;
            floorIncrement++;
            // -- we end when we find an empty spot or we move through all spots
        }while(!_spotEmpty && floorIncrement < _floors); 
        
        
        // -- if we find an empty spot 
        if(_spotEmpty){
            // -- we set that spot to full in the array, and hashmap
            spotKey = turnSpotToKey(floor, spot);
            _garageMap.put(spotKey, 1);
        }
        
        String toReturn = turnSpotToKey(spot,floor);
        return toReturn;
        
    }
    
    /**
     * When we have a daily user try to check in it goes through this method
     * we try to assign them a spot, and if there is a spot for them
     * we allow them to check in
     * 
     * @param p
     * @return 
     */
    public boolean checkIn(Daily p){
        boolean canCheckIn = false;
        
        // -- if there are no empty spots,  createSpot() will return the string "-1--1"
        String noneEmpty = "-1--1";
        
        // -- we try to get a spot using create spot
        String userSpot = assignSpot();
        
        // -- if we have a spot that is empty 
        if (!userSpot.equals(noneEmpty)){
            //we allow the user to check in
            canCheckIn = true;
            // -- we set the spot in the user class
            p.setSpot(userSpot);
            // -- and send the user info to the database
            car_count++;
            sendToDB(p);
            
        }
        
        return canCheckIn;
    }
    
    public boolean checkIn(LongTerm p){
        boolean canCheckIn = false;
        
        // -- if there are no empty spots,  createSpot() will return the string "-1--1"
        String noneEmpty = "-1--1";
        
        // -- we try to get a spot using create spot
        String userSpot = assignSpot();
        
        // -- if we have a spot that is empty 
        if (!userSpot.equals(noneEmpty)){
            //we allow the user to check in
            canCheckIn = true;
            // -- we set the spot in the user class
            p.setSpot(userSpot);
            // -- and send the user info to the database
            car_count++;
            sendToDB(p);
            
        }
        
        return canCheckIn;
    }
    
   
    
    /**
     * In this method, we get which type of user we have, in order to 
     * tell how to check out
     * 
     * we also need to tell the 
     * @param id
     * @param userType
     * @return 
     */
    public boolean checkOut(int id, String userType){
        // -- this will be determined by getting information from the db
       
        // -- if the user is a long term we will get their information from the 
        // -- database and call functions
        
        boolean toReturn = false;
        
        if (userType.equals("longTerm")){
            LongTerm p = dbInterface.getLongTerm(id);
            
            // -- this is opening their spot up in the hashmap
            String userSpot = p.getSpot() ;
            _garageMap.put(userSpot, 0);
            
            generatePayment(p);
            
            toReturn = true;
            
        }
        
        // -- if they arent a long term user, they are a daily user
        else{
        // -- we still get the information from the DB and 
             Daily p = dbInterface.getDaily(id);
             String userSpot = p.getSpot() ;
            _garageMap.put(userSpot, 0);
            
            p.getTimeIn();
            
            toReturn = true;
        }
        
       
        return toReturn;
        
        
        
        
      
    }
    
    
    
    private double generatePayment(LongTerm p){
        
        String stringInTime = p.getTimeIn();
        Time toConvert = new Time();
        double longTermDiscount = 0.75;
        double owedAmount = 0;
       
        DateTime inTime = toConvert.makeNewDateTime(stringInTime);
        DateTime outTime = toConvert.makeNewDateTime();
        Period period = new Period(inTime, outTime);
        double numDays = period.getDays();
        double numHours = period.getHours();
        
        if(numHours < 12){
            owedAmount = numHours*HOURLYRATE*longTermDiscount;
        }
        else {
            numDays = numDays + 1;
            
            owedAmount = numDays*DAILYRATE*longTermDiscount;
            
        }
        
        return owedAmount;
    }
    
    private double generatePayment(Daily p){
        
        String stringInTime = p.getTimeIn();
        Time toConvert = new Time();
        double dailyDiscount = 1;
        double owedAmount = 0;
       
        DateTime inTime = toConvert.makeNewDateTime(stringInTime);
        DateTime outTime = toConvert.makeNewDateTime();
        Period period = new Period(inTime, outTime);
        double numDays = period.getDays();
        double numHours = period.getHours();
        
        if(numHours < 12){
            owedAmount = numHours*HOURLYRATE*dailyDiscount;
        }
        else {
            numDays = numDays + 1;
            
            owedAmount = numDays*DAILYRATE*dailyDiscount;
            
        }
        
        return owedAmount;
    }
    
    
    
    /**
     * We send the information for a LongTerm user into the database interface
     * where it will be saved
     * @param p
     * @return 
     */
    public boolean sendToDB(LongTerm p){
        
        dbInterface.saveUser(p);
        return true;
    }
    
    /**
     * We send the information for a LongTerm user into the database interface
     * where it will be saved
     * @param p
     * @return 
     */
    public boolean sendToDB(Daily p){
        
        dbInterface.saveUser(p);
        return true;
    }
    
    
    
    
    
    
    public void printOut(){
        String spot;
         for (int i = 0; i < _floors; i++){
            for (int j = 0; j < _spots; j++){
                spot = turnSpotToKey(i,j);
                System.out.print(_garageMap.get(spot));
            }
            System.out.println("");
            
            
        }
    }
    
}
