import java.util.*; 
import java.io.*;

public class Database {

  public static void saveFile(String firstname, String lastname, String pass, String ID, String creditCardNum, String email, 
  String timeArrived, String parkingSpot, String phoneNumber, String zipcode, String term){

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    
    try {
      
      fw = new FileWriter("database.txt", true);
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n",
      firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term);

      System.out.println("Data Successfully appended into file");
      pw.flush();

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static int datalen = 10;
  public static int idlen = 3;

  public static String retrieveData(String ID){

    String data = "";
    String toReturn = "";
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

    String[] dbvalues = data.split("\\,");
    for(int i = 0; i < dbvalues.length; i += datalen){
      if(idlen + i < dbvalues.length){
        String IDBlock = dbvalues[2 + i]; // 2 + i = 3 which is equal to the data len but i needs to increment
        //System.out.println(IDBlock +" "+ ID);

        if(IDBlock == ID){
          System.out.println(IDBlock +" "+ ID);
          for(int j = (idlen*-1); j < datalen; j++){
            toReturn += dbvalues[2 + i + j];
          }
        } else if(i < dbvalues.length && IDBlock != ID){
          toReturn = null;
        }
      }
    }
    return toReturn;
  }

  public static void main(String[] args){
    // saveFile("test", "test", "test", "4444", "test", "test", "test", "test", "test", "test", "test");
    System.out.println(retrieveData("4444"));
  }
}