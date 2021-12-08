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
 * There are four main variables for the garage class -- how many floors, how
 * many spots per floor how to keep track of which spots are open and the
 * interface object that allows us to communicate with the DB
 *
 * @author jacob
 */
public class Garage {

    private int _spots;
    private int _floors;
    private int[] _openSpots;
    private Map<String, Integer> _garageMap = new HashMap<>();
    private DBInterface dbInterface = new DBInterface();
    private static int car_count;
    private int _mostRecentID = dbInterface.getMostRecentID();
    private int _idToSave;
    private static final double HOURLYRATE = 2.00;
    private static final double DAILYRATE = 30;
    private final int SPOTEMPTY = 0;
    private final int SPOTTAKEN = 1;

    public Garage(int floors, int spots) {

        // -- these are how many spots and floors we have
        _floors = floors;
        _spots = spots;

        _idToSave = _mostRecentID + 1;
        // -- this is the map keeping track of spots from the garage
        Map<String, Integer> _garageMapFromDB = dbInterface.getGarageSpots();

        // -- in case we cannot get information from the database
        // -- or we return an empty map
        // -- we set all of the spots to empty
        if (_garageMapFromDB.isEmpty()) {

            for (int i = 0; i < _floors; i++) {
                for (int j = 0; j < _floors; j++) {

                    // -- we turn the spot to the key in the hashmap, and enter 
                    // -- the spot as empty
                    _garageMap.put(turnSpotToKey(i, j), SPOTEMPTY);

                }

            }
        } else {

            _garageMap.putAll(_garageMapFromDB);
        }

        _openSpots = new int[_floors];

    }

    public int getID() {
        return this._idToSave;
    }

    private void setSpotsOpen() {
        int spotOpenPerFloor = 0;
        String floorSpace;
        for (int i = 0; i < _floors; i++) {
            for (int j = 0; j < _spots - 1; j++) {
                floorSpace = turnSpotToKey(i, j);
                if (_garageMap.get(floorSpace) == 0) {
                    spotOpenPerFloor++;
                }
            }
            _openSpots[i] = spotOpenPerFloor;
            spotOpenPerFloor = 0;
        }

    }

    private void adjustSpotsOpen(boolean isCheckingIn, String floorSpot) {

        int firstDash = floorSpot.indexOf("-");
        String floor = floorSpot.substring(0, firstDash);
        int intFloor = Integer.parseInt(floor);
        if (isCheckingIn) {
            _openSpots[intFloor]++;
        } else {
            _openSpots[intFloor]--;
        }
    }

    public Map<String, Integer> getGarageMap() {
        return this._garageMap;
    }

    /**
     * -- we have a specific key to any floor/spot combination in the entire
     * garage and it is a string in the format of floor-spot
     *
     * for example 4-11 is the 11th spot on floor 4
     *
     * @param floor -- this is the floor the spot is on
     * @param spot -- and this is the specific spot the user is parking in
     * @return -- we return the spot as a key to the spot in the hashmap
     */
    public String turnSpotToKey(int floor, int spot) {

        String spotAsString = String.valueOf(floor) + "-" + String.valueOf(spot);
        return spotAsString;

    }

    /**
     *
     *
     * @return
     */
    public boolean assignSpotInMap(String spotKey) {
        boolean _spotMayBeTaken = false;

        if (_garageMap.get(spotKey) == SPOTEMPTY) {
            _spotMayBeTaken = true;
            _garageMap.put(spotKey, SPOTTAKEN);

        }

        return _spotMayBeTaken;

    }

    /**
     * we retrieve the spot from the user -- it is a key for a hashmap spot we
     * then determine if they should have been able to choose it and if they
     * are, set the value related to the key as the spot filled value
     *
     * @param p
     * @return
     */
    public boolean checkIn(Daily p) {
        boolean canCheckIn;
        boolean isCheckingIn = true;
        p.setID(_idToSave);
        _idToSave++;

        // -- we set the spot through the interface 
        // -- so we get it here from the user class
        String userSpot = p.getSpot();
        // -- this method confirms check in, 
        // -- and also sets the spot chosen as taken
        canCheckIn = assignSpotInMap(userSpot);
        // -- we try to get a spot using create spot

        // -- we next adjust the number of spots that are open on the floor
        adjustSpotsOpen(isCheckingIn, p.getSpot());

        // if check in was successful we let the caller know
        // -- so we return true or false
        this.dbInterface.saveUser(p);

        car_count++;
        return canCheckIn;
    }

    public boolean checkIn(LongTerm p) {
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

    public double checkOut(Daily dailyUser) {
        String userSpot = dailyUser.getSpot();
        boolean isCheckingIn = false;
        double amountOwed;
        double toReturn;
        _garageMap.put(userSpot, 0);
        // -- and generate their payment
        amountOwed = generatePayment(dailyUser);

        adjustSpotsOpen(isCheckingIn, dailyUser.getSpot());

        toReturn = amountOwed;
        return toReturn;
    }

    public double checkOut(LongTerm longUser) {
        String userSpot = longUser.getSpot();
        double amountOwed;
        double toReturn;
        _garageMap.put(userSpot, 0);
        // -- and generate their payment
        amountOwed = generatePayment(longUser);

        toReturn = amountOwed;
        return toReturn;
    }

    /**
     * In this method, we get which type of user we have, in order to tell how
     * to check out
     *
     * we also need to tell the
     *
     * @param id
     * @param userType
     * @return
     */
    /**
     * here we generate the payment based on the length of stay we calculate the
     * number of hours and the number of days
     *
     * if a user stays for less than a 12 hours, we charge them an hourly rate
     * If they stay for 12 hours or more, they are charged for a day after 24
     * hours, they are charge the daily rate per day
     *
     * longterm users get a 75% discount
     *
     * @param p
     * @return
     */
    private double generatePayment(LongTerm p) {
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
        if (numHours < 12) {
            owedAmount = numHours * HOURLYRATE * longTermDiscount;
        } else {
            // -- otherwise we charge them a daily rate
            // -- we add a day to account for the initial 12 hours counting as a 
            // -- full day
            numDays = numDays + 1;

            owedAmount = numDays * DAILYRATE * longTermDiscount;

        }

        return owedAmount;
    }

    /**
     * here we generate the payment based on the length of stay we calculate the
     * number of hours and the number of days
     *
     * if a user stays for less than a 12 hours, we charge them an hourly rate
     * If they stay for 12 hours or more, they are charged for a day after 24
     * hours, they are charge the daily rate per day
     *
     * @param p
     * @return
     */
    private static double generatePayment(Daily p) {
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
        if (numHours < 24) {
            owedAmount = numHours * HOURLYRATE * dailyDiscount;
        } else {
            // -- otherwise we charge them a daily rate
            double numHoursOver = numHours % 24;

            owedAmount = ((numDays * DAILYRATE) + (numHoursOver * HOURLYRATE)) * dailyDiscount;
            System.out.println(owedAmount);
        }

        return owedAmount;
    }

    /**
     * We send the information for a LongTerm user into the database interface
     * where it will be saved
     *
     * @param p
     * @return
     */
    public boolean sendToDB(LongTerm p) {

        dbInterface.saveUser(p);
        return true;
    }

    /**
     * We send the information for a LongTerm user into the database interface
     * where it will be saved
     *
     * @param p
     * @return
     */
    public boolean sendToDB(Daily p) {

        dbInterface.saveUser(p);
        return true;
    }

    public boolean checkForEmptySpots() {
        boolean areEmptySpots;
        areEmptySpots = _garageMap.containsValue(SPOTEMPTY);
        return areEmptySpots;
    }

    public int getNumEmpty() {
        int numSpotsEmpty = 0;
        String key;
        String floor;
        String spot;
        for (int i = 0; i < _floors; i++) {
            floor = Integer.toString(i);

            for (int j = 0; i < _floors; j++) {
                spot = Integer.toString(j);
                key = spot + "-" + floor;
                if (_garageMap.get(key).equals(0)) {
                    numSpotsEmpty++;
                }

            }
        }

        return numSpotsEmpty;
    }

    /**
     * returns the number of spots open per floor
     *
     * @return
     */
    public int[] getOpenSpots() {
        setSpotsOpen();
        return _openSpots;
    }

    public void printOut() {
        String spot;
        for (int i = 0; i < _floors; i++) {
            for (int j = 0; j < _spots; j++) {
                spot = turnSpotToKey(i, j);
                System.out.print(_garageMap.get(spot));
            }
            System.out.println("");

        }
    }

    public static void main(String[] args) {
        Daily me = new Daily(1, "hi", "lo");
        me.setTimeIn("2020/11/18/06/01");
        double payment = generatePayment(me);
        System.out.println(payment);
    }

}
