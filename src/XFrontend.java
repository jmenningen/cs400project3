import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class XFrontend {

    public static void main(String[] args) {
        DataReader reader = null;

        try {
            reader = new DataReader("buildingData.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        new XFrontend(new BackendDummy(), reader.getBuildingList());
    }

    public XFrontend(BackendInterface backendInterface, ArrayList<Building<Integer>> b){
        List<Command> commands = buildCommandList();

        Scanner scnr = new Scanner(System.in);
        AtomicBoolean appRunning = new AtomicBoolean(true);
        while (appRunning.get() && scnr.hasNext()) {
            printHeaderCard(commands);
            System.out.print("command >> ");
            String input = scnr.nextLine();
            for (Command command : commands){
                String keyword = command.getKeyword();
                if (input.length() >= keyword.length() && input.substring(0, keyword.length()).equals(keyword)){ // If input line matches a command keyword
                    command.onRunCommand(input.split(" "), backendInterface, appRunning, b); // TODO: replace 'null' with the Backend once it is properly implemented
                }
            }
        }
        scnr.close();
        System.out.println("Thank you!");
    }

    private static void printHeaderCard(List<Command> commands){
        System.out.println("=========CampusMapper=========\n| Commands:");
        for (Command command : commands)
            System.out.printf("|%1$s\n", command.getSummary());
        System.out.println("==============================");
    }

    private static List<Command> buildCommandList(){
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command() {
            @Override public String getKeyword() {
                return "x";
            }

            @Override public String getSummary() {
                return "x                                                : Exits program";
            }

            @Override public void onRunCommand(String[] args, BackendInterface backendInterface,
                    AtomicBoolean appRunning, ArrayList<Building<Integer>> buildingList) {
                appRunning.set(false);
            }
        });
        commands.add(new Command() {
            @Override public String getKeyword() {
                return "p";
            }

            @Override public String getSummary() {
                return "p <start #> <end #|\"dining\"|\"library\"|\"parking\"> : Gets path to desired building";
            }

            @Override public void onRunCommand(String[] args, BackendInterface backendInterface,
                    AtomicBoolean appRunning, ArrayList<Building<Integer>> buildingList) {
                if (args.length < 2) {
                    System.out.println("ERROR: Too few arguments.");
                    return;
                }
                try {
                    int startNumber = Integer.valueOf(args[1]);
                    Building start = backendInterface.getBuildingOfNumber(startNumber);
                    switch (args[2]) {
                        case "dining":
                            displayShortestPath(backendInterface.getShortestPath(new PathfindingRequest(start, null, PathfindingRequest.RequestType.DINING)));
                            return;
                        case "library":
                            displayShortestPath(backendInterface.getShortestPath(new PathfindingRequest(start, null, PathfindingRequest.RequestType.DINING)));                            return;
                        case "parking":
                            displayShortestPath(backendInterface.getShortestPath(new PathfindingRequest(start, null, PathfindingRequest.RequestType.DINING)));                            return;
                        default:
                            int endNumber = Integer.valueOf(args[2]);
                            Building end = backendInterface.getBuildingOfNumber(endNumber);
                            displayShortestPath(backendInterface.getShortestPath(new PathfindingRequest(start, end, PathfindingRequest.RequestType.BUILDING)));
                    }
                } catch (NumberFormatException e){
                    System.out.println("ERROR: Input building numbers are invalid");
                }
            }
        });
        commands.add(new Command() {
            @Override public String getKeyword() {
                return "list";
            }

            @Override public String getSummary() {
                return "list                                             : Lists the buildings on campus and their associated number";
            }

            @Override public void onRunCommand(String[] args, BackendInterface backendInterface,
                    AtomicBoolean appRunning, ArrayList<Building<Integer>> buildingList) {
                listBuilding(buildingList, 2, 45);
            }
        });
        return commands;
    }

    public static void displayShortestPath(List<Building<Integer>> path) {
        boolean head = true;
        for (Building<Integer> b : path) {
            String marker = (head) ? " * " : "-> ";

            System.out.println(marker + b.getNumber() + " " + b.getName());
            head = false;
        }
    }

    public static void listBuilding(ArrayList<Building<Integer>> b, int buildingsPerRow, int columnWidth) {
        int listHeight = b.size() / buildingsPerRow + 1;
        for (int row = 0; row < listHeight; ++row) {
            for (int col = 0; col < buildingsPerRow; col++){
                int listAddr = row + (col * listHeight);
                if (listAddr < b.size()) {
                    System.out.printf("%1$2d ", listAddr);
                    String buildingInfo = b.get(listAddr).getName();
                    System.out.print(buildingInfo);
                    if (buildingInfo.length() < columnWidth)
                        System.out.print(" ".repeat(columnWidth - buildingInfo.length()));
                }
            }
            System.out.print('\n');
        }
    }


    public static void campusMap() throws FileNotFoundException {
    /*
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
    */
        File campusmap = new File("./campusMap.txt");
        if (campusmap.isFile()) {
            FileInputStream inputStream = new FileInputStream(campusmap);
            Scanner sc = new Scanner(inputStream);
            while (sc.hasNext())
                System.out.println(sc.nextLine());
        }
    }

}
