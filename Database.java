import java.util.*; 
import java.io.*;

public class Database {

  public static void saveFile(String firstname, String lastname, String pass, String ID, String creditCardNum, String email, 
  String timeArrived, String parkingSpot, String phoneNumber, String zipcode, String term){

    // Declare file writer to save data toDB
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    
    try {
      
      fw = new FileWriter("database.txt", true); // sets which file to print and add to
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n", // declare data types
      firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term); // set data

      System.out.println("Data Successfully appended into file"); // confirmation of saving data

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    //----------------------------------------------------
    // Save the data but don't overwite. Get latest data set.

    // Declare new file writer for latest data save
    FileWriter fw2 = null;
    BufferedWriter bw2 = null;
    PrintWriter pw2 = null;
    
    try {
      
      // Target latest save with false (will overwrite instead of add to)
      fw2 = new FileWriter("latestsave.txt", false);
      bw2 = new BufferedWriter(fw2);
      pw2 = new PrintWriter(bw2);

      // Code is the same as database.txt saving code
      pw2.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n",
      firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term);

      System.out.println("Latest data saved.");

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // flush both files to end
    pw.flush();
    pw2.flush();

  }

  // Values to allow search loop to work
  public static int datalen = 10;
  public static int idlen = 4;
  public static int getstart = -3;
  public static int getend = 8;

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
    for(int i = 0; i < dbvalues.length; i += datalen){
      if(datalen + i < dbvalues.length){
        String IDBlock = dbvalues[(idlen - 1) + i]; 
        if(IDBlock.equals(ID)){ // If ID is found add string to array list
          for(int j = getstart; j < getend; j++){
            toReturn.add(dbvalues[(idlen - 1) + i + j]);
          }
        }
      }
    }
    return toReturn; // return
  }

  // Used to test functions above
  public static void main(String[] args){
    //saveFile("test", "test", "test", "4444", "test", "test", "test", "test", "test", "test", "test");
    //System.out.println(retrieveData("4444"));
  }
}