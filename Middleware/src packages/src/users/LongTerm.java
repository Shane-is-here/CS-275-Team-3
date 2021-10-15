 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author brend
 */
public class LongTerm extends User implements Chargable{
    // These variables will be used for the long-term user's account
    
    // String containing the user's email address
    private String email;
    // String containing the user's password
    private String password;
    // Double containing the user's credit card number
    private String paymentInfo;
    // String containing the user's phone number
    private String phoneNumber;
    
    
    /**
     * Constructor for the LongTerm class; utilizes the User superclass
     * @param userID Int containing the user's ID
     * @param name
     */
    public LongTerm(int userID, String name){
        super(userID, name);
    }
    
    @Override
    public void chargeFee(){
        
    }
    
    /**
     * Method to determine if the entered email is equal to the user's email
     * that is currently stored
     * @param email The email address entered when attempting to log in
     * @return A boolean which is true if the emails are equal, false if not
     */
    public boolean checkEmail(String email){
        boolean isEqual =  this.email.equals(email);
        return isEqual;
    }
    
    
    /**
     * Method to set the email address of the long term user. First checks if
     * a valid email address was entered. If an invalid address was entered, 
     * the user is notified and the email is not set. If a valid email is entered,
     * the new email is set
     * @param email String containing a new email address
     */
    public void setEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if(email == null || !pat.matcher(email).matches()){
            System.out.println("You have entered an invalid email. Email has not"
                    + " been set");
        } 
        else {
            this.email = email;
            System.out.println("Email set!");
        }
        
    }
    
    /**
     * Method to change the user's password. Checks to see if the user entered 
     * their old password (as a verification) and then also checks if the new
     * password is strong enough by calling isStrong
     * @param oldPass The user's current password
     * @param newPass The user's new password
     */
    public void changePassword(String oldPass, String newPass){
        if(checkPassword(oldPass)){
            this.password = newPass;
            System.out.println("Your password has been changed");
        }
        else{
            System.out.println("The 'old' password you entered does not match your "
                    + "current password. Please try again.");
        }
    }
    
    /**
     * Method to set the user's password for the first time. Checks to see if 
     * the new password is strong enough by calling isStrong
     * @param newPass String containing what the user wants their password to be
     */
    public void setPassword(String newPass){
        if (this.password == null){
            this.password = newPass;
            System.out.println("Your password has been set!");
        }
    }
    
    
    
    /**
     * Method to determine if the person attempting to log in has entered the
     * correct password for this user account
     * @param password The password entered when attempting to log in
     * @return True if the passwords are equal, false if not
     */
    private boolean checkPassword(String password){
        boolean isEqual =  this.password.equals(password);
        return isEqual;
    }
    
    /**
     * This method gets the password as set by the user
     * we will have to figure out a more elegant solution as this is a 
     * security nightmare
     */
    public String getPassword(){
        
        String toReturn = this.password;
        return toReturn;
    }
    
    /**
     * Method to set the user's phone number. Must be a string in the format
     * XXX-XXX-XXXX
     * Will add functionality to check if the number's format is correct
     * @param phone String containing the phone number to be set to this account
     */
    public void setPhone(String phone){
        if(phoneIsValid(phone)){
            this.phoneNumber = phone;
            System.out.println("Phone number set!");
        }
        else{
            System.out.println("You have entered an invalid phone number.");
        }
        
    }
    
    /**
     * Method to set the user's payment info. Must be a string in the format
     * XXXX-XXXX-XXXX-XXXX
     * Will add functionality to check if the number's format is correct
     * @param paymentInfo String containing the paymentInfo to be set to this account
     */
    public void setPaymentInfo(String paymentInfo){
       this.paymentInfo = paymentInfo;
        
        System.out.println("Payment info set!");
    }
    
    /**
     * Method to get payment info of the user
     * @return A String containing the user's payment info in the format
     * XXXX-XXXX-XXXX-XXXX
     */
    public String getPaymentInfo(){
        String toReturn = this.paymentInfo;
        return toReturn;
    }
    
    /**
     * Returns the email as set by the user
     * @return 
     */
    public String getEmail(){
        String toReturn = this.email;
        return toReturn;
    }
    
    /**
     * 
     * @param phone
     * @return True if the phone number is valid, false if not
     */
    private boolean phoneIsValid(String phone){
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    /**
     * Method to get the phone number of the user
     * @return A String containing the user's phone number in the format
     * XXX-XXX-XXXX
     */
    public String getPhone(){
        return this.phoneNumber;
    }
    
}