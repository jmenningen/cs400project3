// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF blue
// Role: Data Wrangler
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class DataReader {
    //stores building info
    ArrayList<Building<Integer>> buildings = new ArrayList<>();
    // temp list stores neigbor data from file for updating each Building object
    LinkedList<LinkedList<Integer>> neigborData = new LinkedList<>();

    /**
     * constructor: read file, loads and processes building data
     *
     * @param file path
     * @throws IOException
     */
    public DataReader(String filePath) throws IOException {
        FileInputStream inStream = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(inStream);
        BufferedReader bfReader = new BufferedReader(inReader);

        // read file
        while (bfReader.ready())
            readNodeData(bfReader.readLine());

        // close
        if (inStream != null)
            inStream.close();
        if (inReader != null)
            inReader.close();
        if (bfReader != null)
            bfReader.close();

        loadEdgeData(); // update neigbor data for each Building object
    }

    /**
     * extra data from each line
     */
    private void readNodeData(String line) {
        String[] tokens = line.split("\\s+");

        int i = 0;
        int number = Integer.parseInt(tokens[i++]);

        StringBuilder name = new StringBuilder();
        while (!tokens[i].equals("$")) {
            if (name.length() > 0)
                name.append(" ");
            name.append(tokens[i]);
            i++;
        }
        i++; // skip the "$" token

        Building<Integer> b =
            new Building<Integer>(number, name.toString(), tokens[i++].equals("1"),  // read dining
                tokens[i++].equals("1"),  // read library
                tokens[i++].equals("1")); // read parking
        buildings.add(b);

        // read neigbor information
        LinkedList<Integer> temp = new LinkedList<>();
        while (i < tokens.length) {
            temp.add(Integer.parseInt(tokens[i++]));
        }
        neigborData.add(temp);
    }

    /**
     * load edge data for each Building object
     */
    private void loadEdgeData() {
        int i = 0;
        for (LinkedList<Integer> data : neigborData) {
            Building<Integer> curr = buildings.get(i++);
            Iterator<Integer> list = data.iterator();
            while (list.hasNext())
                curr.addNeighbor(buildings.get(list.next()), list.next());
            // put building             put distance
        }
    }

    /**
     * @return an ArrayList of Building objects
     */
    public ArrayList<Building<Integer>> getBuildingList() {
        return isValidData() ? buildings : null;
    }

    /**
     * check whether the weight from building A to building B is equal
     * to the weight from building B to building A, which guarantees
     * the given data is an valid undirected graph.
     *
     * @return true - if it all edges maintain properties of an undirected graph
     * false - if the weights between two nodes are different
     */
    private boolean isValidData() {
        // traverse each node
        for (int i = 0; i < buildings.size(); ++i) {
            Building<Integer> curr = buildings.get(i);

            // for each node, traverse its neigbors
            for (Map.Entry<Building<Integer>, Integer> entry : curr.getNeighbors().entrySet()) {

                int d1 = entry.getValue();                         // distance from this to neigbor
                int d2 = entry.getKey().getNeighbors().get(curr);  // distance from neigbor to this

                // if distances unequal, return false
                if (d1 != d2) {
                    System.out.println(
                        "error: data mismatch " + i + " and " + entry.getKey().getNumber());
                    return false;
                }
            }
        }
        return true;
    }
}


