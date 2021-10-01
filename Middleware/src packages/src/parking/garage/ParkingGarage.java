/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking.garage;
import parkingGarage.*;
import users.*;

/**
 *
 * @author jacob
 */
public class ParkingGarage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Garage a = new Garage(10,10);
        
        LongTerm user1 = new LongTerm(123,"Jacob");
        
        a.checkIn(user1);
        System.out.println(user1.getSpot());
        user1.setEmail("joebid?en@thewhitehouse.gov");
        
       
    }
    
    
}
