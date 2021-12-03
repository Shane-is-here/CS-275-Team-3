import java.util.*; 
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {

  public static void lastestSave(String firstname, String lastname, String pass, String ID, String creditCardNum, String email, 
  String timeArrived, String parkingSpot, String phoneNumber, String zipcode, String term){

    // Save the data but don't overwite. Get latest data set.

    // Declare new file writer for latest data save
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    
    try {
      
      // Target latest save with false (will overwrite instead of add to)
      fw = new FileWriter("latestsave.txt", false);
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      // Code is the same as database.txt saving code
      pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n",
      firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term);

      System.out.println("Latest data saved.");

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    pw.flush();
  }

  public static void saveFile(String firstname, String lastname, String pass, String ID, String creditCardNum, String email, 
  String timeArrived, String parkingSpot, String phoneNumber, String zipcode, String term){

    // Declare file writer to save data toDB
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;

    String data = "";

    try {
      File db = new File("database.txt");
      Scanner myReader = new Scanner(db);
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    boolean isDataSaved = false;

    Pattern pattern = Pattern.compile(ID, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(data);
    boolean matchFound = matcher.find();

    try {

        fw = new FileWriter("database.txt", true); // sets which file to print and add to
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);

        pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n", // declare data types
          firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term); // set data

        lastestSave(firstname, lastname, pass, ID, creditCardNum, email, timeArrived, parkingSpot, phoneNumber, zipcode, term);

        System.out.println("Data Successfully appended into file"); // confirmation of saving data

    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

    pw.flush();
    isDataSaved = true;

    }


  // Values to allow search loop to work
  public static int datalen = 11;
  public static int idlen = 3;
  public static int getstart = -5;
  public static int getend = 6;

  /**
   * 
   * @param ID
   * @return Array List with the data associated with the ID
   */
  public static ArrayList<String> retrieveData(String ID){

    String data = ""; // text file data
    ArrayList<String> toReturn = new ArrayList<String>(); // return value

    // Must try for exception using scanner
    try {

      // Retrieve database data
      File db = new File("database.txt");
      Scanner myReader = new Scanner(db);
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // Turn string into array
    String[] dbvalues = data.split("\\,");

    // Search through array for only the IDs
    for(int i = idlen; i < dbvalues.length; i += datalen){
      String IDBlock = dbvalues[i];
      if(IDBlock.equals(ID)){ // If ID is found add string to array list
        for(int j = getstart; j < getend; j++){
          toReturn.add(dbvalues[(idlen - 1) + i + j]);
        }
      } 
    }
    return toReturn; // return
  }

/**
   * 
   * @return Array List that was last saved
   */
  public static ArrayList<String> retrieveLatestSave(){

    String data = ""; // text file data
    ArrayList<String> toReturn = new ArrayList<String>(); // return value

    // Must try for exception using scanner
    try {

      // Retrieve database data
      File db = new File("latestsave.txt");
      Scanner myReader = new Scanner(db);
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // Turn string into array
    String[] dbvalues = data.split("\\,");
    for(int i = 0; i < dbvalues.length; i++){
      toReturn.add(dbvalues[i]);
    }
    
    return toReturn; // return
  }

  public static void garageSaveState(HashMap garage){

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    
    try {
      
      fw = new FileWriter("garageSaveState.txt", false);
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      StringBuilder mapAsString = new StringBuilder("{");
      for (Object key : garage.keySet()) {
          mapAsString.append(key + "=" + garage.get(key) + ", ");
      }
      mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");

      pw.printf("%s%n", mapAsString);

      System.out.println("Garage data saved.");

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    pw.flush();

  }

  public static HashMap retrieveGarageData(){

    String data = ""; // text file data

    // Must try for exception using scanner
    try {

    // Retrieve database data
    File db = new File("garageSaveState.txt");
    Scanner myReader = new Scanner(db);
    while (myReader.hasNextLine()) {
      data += myReader.nextLine();
    }
    myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    HashMap<String, Integer> toReturn = new HashMap<String, Integer>();
    String[] pairs = data.split(", ");
    for (int i = 0; i < pairs.length; i++) {
        String pair = pairs[i];
        String[] keyValue = pair.split("=");
        toReturn.put(keyValue[0], Integer.valueOf(keyValue[1])); // possible bug
    }
    
    return toReturn; // return
  }

  // Used to test functions above
  public static void main(String[] args){

    //System.out.println(retrieveLatestSave()); // latest save

    //saveFile("test", "test", "test", "2", "test", "test", "test", "test", "test", "test", "test"); // DUMMY SAVE
    //ArrayList two = retrieveData("2");
    //ArrayList one = retrieveData("1");
    //ArrayList three = retrieveData("3");
    //System.out.println(three);
    //System.out.println(two);
    //System.out.println(retrieveData("1")); // ID = 1
    /*
      HashMap<String, Integer> hMapNumbers = new HashMap<String, Integer> ();

      //key-value pairs
      hMapNumbers.put("One", 1);
      hMapNumbers.put("Two", 2);
      hMapNumbers.put("Three", 3);

      garageSaveState(hMapNumbers); // DUMMY GARAGE DATA*/

      retrieveGarageData(); // The retrieve function is still being tested. However, it does work under certain scenarios. 
      // I hope to resolve this bug tomorrow.
  }
}