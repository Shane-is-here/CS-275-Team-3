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
    private Map<String, Integer> _garageMap = new HashMap<>();
    private DBInterface dbInterface = new DBInterface();
    private static int car_count;
    
    private final double HOURLYRATE = 2.00;
    private final double DAILYRATE = 30;
    private final int SPOTEMPTY = 0;
    private final int SPOTTAKEN = 1;
    
    
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
                _garageMap.put(turnSpotToKey(i,j), SPOTEMPTY);
              
            }
            
        _garageMap.putAll(_garageMapFromDB);
         
        }
        
    }
    
    public Map<String, Integer> getGarageMap(){
        return this._garageMap;
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
     * 
     * 
     * @return 
     */
    public boolean assignSpotInMap(String spotKey){
        boolean  _spotMayBeTaken = false;
        
        if(_garageMap.get(spotKey) == SPOTEMPTY){
            _spotMayBeTaken = true;
            _garageMap.put(spotKey, SPOTTAKEN);
            
        }
        
        
        
   
        return _spotMayBeTaken;
        
    }
    
    /**
     * we retrieve the spot from the user -- it is a key for a hashmap spot
     * we then determine if they should have been able to choose it
     * and if they are, set the value related to the key as the spot filled
     * value
     * 
     * @param p
     * @return 
     */
    public boolean checkIn(Daily p){
        boolean canCheckIn;
        p.setSpot("0-0");
       // -- we set the spot through the interface 
       // -- so we get it here from the user class
        String userSpot = p.getSpot();
        // -- this method confirms check in, 
        // -- and also sets the spot chosen as taken
        canCheckIn = assignSpotInMap(userSpot);
        // -- we try to get a spot using create spot
       
        // if check in was successful we let the caller know
        // -- so we return true or false
        this.dbInterface.saveUser(p);
        car_count++;
        return canCheckIn;
    }
    
    public boolean checkIn(LongTerm p){
        boolean canCheckIn;
       // -- we set the spot through the interface 
       // -- so we get it here from the user class
        String userSpot = p.getSpot();
        // -- this method confirms check in, 
        // -- and also sets the spot chosen as taken
        canCheckIn = assignSpotInMap(userSpot);
        // -- we try to get a spot using create spot
       
        // if check in was successful we let the caller know
        // -- so we return true or false
        
        car_count++;
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
    public double checkOut(int id, String userType){
        // -- this will be determined by getting information from the db
       
        // -- if the user is a long term we will get their information from the 
        // -- database and call functions
        
        double toReturn;
        double amountOwed;
        
        if (userType.equals("longTerm")){
            // -- we get the user from the database based on their id info
            LongTerm p = dbInterface.getLongTerm(id);
            
            // -- then we get their spot information and empty it out
            String userSpot = p.getSpot();
            _garageMap.put(userSpot, 0);
            
            // -- next we determine how much they owe by generating their payment
            amountOwed = generatePayment(p);
            
            toReturn = amountOwed;
            
        }
        
        // -- if they arent a long term user, they are a daily user
        else{
        // -- we still generate the user based on their id number
        
             Daily p = dbInterface.getDaily(id);
             
             // -- likewise we get their spot and open it in the garage structure
             String userSpot = p.getSpot();
             
            _garageMap.put(userSpot, 0);
             // -- and generate their payment
            amountOwed = generatePayment(p);
            
            
            toReturn = amountOwed;
        }
        
       
        return toReturn;
        
        
        
        
      
    }
    
    
    /**
     * here we generate the payment based on the length of stay
     * we calculate the number of hours and the number of days
     * 
     * if a user stays for less than a 12 hours, we charge them an hourly rate
     * If they stay for 12 hours or more, they are charged for a day
     * after 24 hours, they are charge the daily rate per day
     * 
     * longterm users get a 75% discount
     * @param p
     * @return 
     */
    private double generatePayment(LongTerm p){
        // -- we get the check in time of the user 
        String stringInTime = p.getTimeIn();
        // -- this is an object that will help us convert times to DateTime objects
        // -- which has a very versatile API
        Time toConvert = new Time();
        // -- long term users get a 25% discount
        double longTermDiscount = 0.75;
        double owedAmount;
       
        // -- we turn the string inTime into a DateTime object
        DateTime inTime = toConvert.makeNewDateTime(stringInTime);
        // -- we also turn the checkOut time into a DateTime Object
        // -- this is the current date
        DateTime outTime = toConvert.makeNewDateTime();
        // -- the period object has the information about the length of time
        // -- between the two points in time
        Period period = new Period(inTime, outTime);
        
        // -- this is the number of days between the two points in time
        double numDays = period.getDays();
        
        // -- and this is the number of hours, likely a big number
        double numHours = period.getHours();
        
        // -- if the user stays for less than 12 hours, we charge them an hourly 
        // -- rate
        if(numHours < 12){
            owedAmount = numHours*HOURLYRATE*longTermDiscount;
        }
        else {
            // -- otherwise we charge them a daily rate
            // -- we add a day to account for the initial 12 hours counting as a 
            // -- full day
            numDays = numDays + 1;
            
            owedAmount = numDays*DAILYRATE*longTermDiscount;
            
        }
        
        return owedAmount;
    }
    
    /**
     * here we generate the payment based on the length of stay
     * we calculate the number of hours and the number of days
     * 
     * if a user stays for less than a 12 hours, we charge them an hourly rate
     * If they stay for 12 hours or more, they are charged for a day
     * after 24 hours, they are charge the daily rate per day
     * @param p
     * @return 
     */
    private double generatePayment(Daily p){
        // -- we get the check in time of the user 
        String stringInTime = p.getTimeIn();
        // -- this is an object that will help us convert times to DateTime objects
        // -- which has a very versatile API
        Time toConvert = new Time();
        // -- daily users have the ability to have a discount, but don't currently
        double dailyDiscount = 1;
        
        double owedAmount = 0;
       
        // -- we turn the string inTime into a DateTime object
        DateTime inTime = toConvert.makeNewDateTime(stringInTime);
        // -- we also turn the checkOut time into a DateTime Object
        // -- this is the current date
        DateTime outTime = toConvert.makeNewDateTime();
         // -- the period object has the information about the length of time
        // -- between the two points in time
        Period period = new Period(inTime, outTime);
        
        // -- this is the number of days between the two points in time
        double numDays = period.getDays();
        
        // -- and this is the number of hours, likely a big number
        double numHours = period.getHours();
        
        // -- if the user stays for less than 12 hours, we charge them an hourly 
        // -- rate
        if(numHours < 12){
            owedAmount = numHours*HOURLYRATE*dailyDiscount;
        }
        else {
            // -- otherwise we charge them a daily rate
            // -- we add a day to account for the initial 12 hours counting as a 
            // -- full day
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

