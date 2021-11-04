import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class Database {

  public static void saveFile(String name, String pass, double ID, String creditCardNum, String email, 
  String timeArrived, String parkingSpot, String phoneNumber, String billingAddress,
  String term){

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    
    try {
      
      fw = new FileWriter("database.txt", true);
      bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
      name,pass,ID,creditCardNum,email,timeArrived,parkingSpot,phoneNumber,billingAddress,term);

      System.out.println("Data Successfully appended into file");
      pw.flush();

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    saveFile("test", "test", "4444", "test", "test", "test", "test", "test", "test", "test");
  }

  //saveFile("test", "test", 4444, "test", "test", "test", "test", "test", "test", "test");
  
}

/*
        Database db = new Database();
        db.saveFile("Jacob", password, idToSave, emailToSave, 
        timeInToSave, spotToSave, phoneToSave, "2394 Dart Lane",
        userType);
*/