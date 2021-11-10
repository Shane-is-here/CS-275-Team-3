/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import org.joda.time.DateTime;



/**
 *
 * @author jacob
 */
public class Time {
    
    // -- this is the time that any object will represen
    
    // -- the following are aspects of the time that will be parsed 
  
    
    private int[] _dateIndices = new int[5];
    // -- these are the indices for the above array of dates
    private int _yearInt = 0;
    private int _monthInt = 1;
    private int _dayInt = 2;
    private int _hourInt = 3;
    private int _minInt = 4;
    
 
    // -- this class currently serves the function to assign DateTime to be used
    // -- in the main
    public Time(){
       
    }
    
   
    /**
     * this method takes in an object as formatted in the GUI
     * this is in the format"yyyy/mm/dd/hh/mm"
     * it will convert it into a DateTime object so the JodaTime library
     * may work with it
     * @param timeString
     * @return 
     */
    public DateTime makeNewDateTime(String timeString){
         
        int[] dateInt; 
         
        
        // -- we extract all of the relavant information and store it in
        // -- relevant strings
        String yearString = timeString.substring(0,3);
        String monthString = timeString.substring(5,6);
        String dayString = timeString.substring(8,9);
        String hourString = timeString.substring(11,12);
        String minString = timeString.substring(14,15);
        
        // -- we then convert these strings into integers
        dateInt = setIntTime(yearString, monthString, dayString, hourString, minString);
        
        // -- and create a DateTime object of the date
         DateTime dt = new DateTime(dateInt[_yearInt], dateInt[_monthInt],
                 dateInt[_dayInt], dateInt[_hourInt], dateInt[_minInt], 0, 0);
        
         return dt;
    }
    /**
     * this creates a DateTime object 
     * @return 
     */
    public DateTime makeNewDateTime(){
        
        
        DateTime dt = new DateTime();
        return dt;
    }
    
    private int[] setIntTime(String yearString, String monthString,
            String dayString, String hourString, String minString){
        int[] dateInt = new int[5];
        
        dateInt[_yearInt] = parseTime(yearString);
        dateInt[_monthInt] = parseTime(monthString);
        dateInt[_dayInt] = parseTime(dayString);
        dateInt[_hourInt] = parseTime(hourString);
        dateInt[_minInt] = parseTime(minString);
        
        return dateInt;
    }

    private int parseTime(String time){
        int intParsed = Integer.parseInt(time);
        return intParsed;
    }
    
    
    
    
   
   
    
    public String getStringDate(){
        String toReturn = this._timeString;
        return toReturn;
    }
}
