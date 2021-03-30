import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class XFrontend {
  
  public static void main(String[] args) {
    DataReader reader = null;
    
    try {
      reader = new DataReader("buildingData.txt");
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      return;
    }
        
    ArrayList<Building<Integer>> b = reader.getBuildingList();
    campusMap();
    listBuilding(b);
    //System.out.println(b.size());
    
    XBackend backend = new XBackend(b);
    
    
    Scanner scnr = new Scanner(System.in);
    while (true) {
      System.out.println("\n- Enter 'x' to exit");
      System.out.println("- Enter two integers to find shortest path");
      System.out.println("  range: 0 to 55; format example: 11-48");
      System.out.print("commend >> ");
      String input = scnr.nextLine();
      if (input.toLowerCase().trim().charAt(0) == 'x') break;
      
      String[] num = input.split("-");
      int from = Integer.parseInt(num[0]);
      int to   = Integer.parseInt(num[1]);
      
      LinkedList<Building<Integer>> path = backend.shortestPath(from, to);
      int dist = backend.getDist();
      
      System.out.println();
      displayShorestPath(path);
      System.out.println("distance: " + dist);
    }
    scnr.close();
    System.out.println("Thank you!");
  }

  public static void displayShorestPath(LinkedList<Building<Integer>> path) {
    boolean head = true;
    for (Building<Integer> b : path) {
      String marker = (head) ? " * " : "-> ";
      
      System.out.println(marker + b.getNumber() + "." + b.getName());
      head = false;
    }
   
  }
  
  public static void listBuilding(ArrayList<Building<Integer>> b) {
    
    int half = b.size() / 2 + 1;
    for (int i = 0; i < half; ++i) {
      String ones = (i < 10) ? "0" : "";
      String left = b.get(i).getNumber() + "." + b.get(i).getName();
      String offset = " ".repeat(45 - left.length() - ones.length());
      System.out.print(ones + left + offset);
      
      int j = i + half;
      if (j < b.size())
        System.out.println(b.get(j).getNumber() + "." + b.get(j).getName());
      else
        System.out.println();
    }
  }
  
  
  public static void campusMap() {
    String campus = 
      "======================================================================================\n" +
      "|~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.  LAKE MENDOTA  ~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.|\n" +
      "|~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.|\n" +
      "|          [42]  [06]              [52--]  [41]..............[21]  [28]....[37]      |\n" +
      "|                                          [--]..............[--]  [--]....[--]      |\n" +
      "|                                                            [39]                    |\n" +
      "|    [44]  [29]      [00]                  [23]              [--]  [53]....[27]      |\n" +
      "|[38][--]  [--]      [--]  [32----]  [49]  [--]  [02]............  [--]....[--]      |\n" +
      "|                                                [--]............                    |\n" +
      "|  [01--]  [22]            [35]              [50]        [25][31]  [30]              |\n" +
      "|  [----]  [--]                            [45--]  [03]  [--]      [--]  [09]        |\n" +
      "|          [12------]  [17----]  [26----]  [08--]            [07]  [--]              |\n" +
      "|          [--------]  [------]  [------]  [----]      [24]  [--]  [--]    [20]      |\n" +
      "| UNIVERSITY AVE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" +
      "|                              [13--][04--]  [10--]        [19--]  [51]          [16]|\n" +
      "|                              [----][----]  [----]        [----]  [--]              |\n" +
      "|                [15--------]                        [14--]                          |\n" +
      "|                [----------]  [48--][11--]    [34]                [40]  [18--]  [54]|\n" + 
      "|                     .......  [----][----]          [46]          [--]  [----]  [--]|\n" +
      "|                     ....... W DAYTON ST <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" +
      "|  [05---------------].......                                      [36]  [33--]      |\n" +
      "|  [-----------------].......                                      [--]  [----]      |\n" +
      "|  [-----------------].......                                                  [47--]|\n" +
      "|  [-----------------]......                                                   [----]|\n" +
      "|  [-----------------]....                                                     [----]|\n" +
      "|  [-----------------]..                                           [43]              |\n" +
      "|  [-----------------]                                             [--]              |\n" +
      "============================== CAMPUS MAP OF UW-MADISON ==============================\n";
    //" 00  02  04  06  08  10  12  14  16  18  20  22  24  26  28  30  32  34  36  38  40  \n";
    campus = campus.replace('.', ' ');
    campus = campus.replace('-', '.');
    System.out.println(campus);
  }
  
  
}
