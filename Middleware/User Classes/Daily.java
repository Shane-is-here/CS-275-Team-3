/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

/**
 *
 * @author brend
 */
public class Daily extends User implements Chargable{
    
    
    /**
     * Constructor for the Daily class; utilizes the User superclass
     * @param userID Int containing the ID number of the user
     */
    public Daily(int userID, String name){
        super(userID, name);
    }
    
    /**
     * Method to be used when the user pays
     */
    @Override
    public void chargeFee(){
        
        
    }
}
