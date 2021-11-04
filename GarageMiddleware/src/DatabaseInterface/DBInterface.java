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
        String paymentInfo = p.getPaymentInfo();
        String password = p.getPassword();
        
        // -- our return value will be whether or not the save is successful
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
        String emailToSave = "";
        int idToSave = p.getID();
        String phoneToSave = "";
        String spotToSave =p.getSpot();
        String timeInToSave = p.getTimeIn();
        String userType = "daily";
        String paymentInfo = "";
        String password = "";
        
        // -- our return value will be whether or not the save is successful
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
        
        // -- we save the map into the Database, likely by deconstructing it
        
        
        // -- our return value will be whether or not the save is successful
        return true;
    }
    
    /**
     * we get the hashmap from the database, and then we will return it 
     * likely in here will will also have to rebuild it 
     * @return 
     */
    public Map<String, Integer> getGarageSpots(){
        // -- the get method that will return the garagespots
        Map<String, Integer> _garageMap = new HashMap<>();
        return _garageMap;
        
    }
    
    /**
     * we get all of the information relevant to the long term user
     * and we then create a "new" user with all of the information of the user 
     * being called
     * 
     * 
     * @param id
     * @return 
     */
    public LongTerm getLongTerm(int id){
        LongTerm p = new LongTerm(id,"Jacob", "Dunst");
        return p;
    }
   
    /**
     *  we get all of the information relevant to the long term user
     * and we then create a "new" user with all of the information of the user 
     * being called
     * 
     * @param id
     * @return 
     */
    public Daily getDaily(int id){
        Daily p = new Daily(id,"Jacob", "Dunst");
        return p;
    }
   

    
    
}
