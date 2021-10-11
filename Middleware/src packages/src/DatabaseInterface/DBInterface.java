/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseInterface;
import java.util.HashMap;
import java.util.Map;
import users.*;


/**
 *
 * @author jacob
 */
public class DBInterface {
    /**
     * We use the get method for all relevant information on the long term user
     * when we can, it will be saved into the database
     * 
     * @param p
     * @return 
     */
    public boolean saveUser(LongTerm p){
        String emailToSave =  p.getEmail();
        int idToSave = p.getID();
        String phoneToSave = p.getPhone();
        String spotToSave = p.getSpot();
        String timeInToSave = p.getTimeIn();
        String userType = "longTerm";
        
        
        return true;
    }
    
    /**
     * The saveUser method for the daily user gets the relevant information
     * that they may have
     * 
     * when it is information not relevant to the daily user, it is saved as a 
     * null string, ""
     * 
     * @param p
     * @return the return is whether or not the method went through
     */
    public boolean saveUser(Daily p){
        int idToSave = p.getID();
        String emailToSave =  "";
        String phoneToSave = "";
        String spotToSave = "";
        String timeInToSave = p.getTimeIn();
        String userType = "daily";
        return true;
        
        // -- the next thing is the method that that will be used to get the 
        // -- information from the database
    }
    
    /**
     * we save the garage HashMap into the database
     * @param _garageMap
     * @return 
     */
    public boolean saveGarageSpots(Map<String, Integer> _garageMap){
        
        // -- we save the map into the Database
        
        return true;
    }
    
    /**
     * we get the hashmap from the database
     * @return 
     */
    public Map<String, Integer> getGarageSpots(){
        // -- the get method that will return the garagespots
        Map<String, Integer> _garageMap = new HashMap<>();
        return _garageMap;
        
    }
    
    
    public LongTerm getLongTerm(int id){
        LongTerm p = new LongTerm(id,"Jacob");
        return p;
    }
   
    public Daily getDaily(int id){
        Daily p = new Daily(id,"Jacob");
        return p;
    }
   

    
    
}
