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

      pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
      firstname,lastname,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,zipcode,term);

      System.out.println("Data Successfully appended into file");
      pw.flush();

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static String retrieveData(String ID){

    var data = "";
    try {
      File db = new File("database.txt");
      Scanner myReader = new Scanner(db);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    String[] dbvalues = data.split("\\,");
    for(int i)

    return data;
  }

  public static void main(String[] args){
    saveFile("test", "test", "test", "4444", "test", "test", "test", "test", "test", "test", "test");
    System.out.print(retrieveData("4444"));
  }

  //saveFile("test", "test", 4444, "test", "test", "test", "test", "test", "test", "test");
  
}

/*
        Database db = new Database();
        db.saveFile("Jacob", password, idToSave, emailToSave, 
        timeInToSave, spotToSave, phoneToSave, "2394 Dart Lane",
        userType);
*/