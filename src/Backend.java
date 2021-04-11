// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu
// Team: DF blue
// Role: Backend Developer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.*;

/**
 * This class implements the backend for the campus mapper and uses dijkstra's algorithm in order to find the shortest
 * path between various buildings on campus.
 *
 */
public class Backend implements BackendInterface {
    private CS400Graph<Building<Integer>> graph = new CS400Graph<>(); // the graph for the backend
    private Hashtable<Integer, Building<Integer>> hashBuildings = new Hashtable<>(); // Hashtable of all the buildings
                                                                                    // with key = building number
                                                                                    // and value = Building
    // Hashtable of specific building types: key = Libraries, Dining, and Parking, value = ArrayList<Building>
    private Hashtable<PathfindingRequest.RequestType, ArrayList<Building<Integer>>> buildingType = new Hashtable<>();

    /**
     * Default constructor that builds a CS400Graph based on the DataReader and puts buildings in their respective
     * arraylist
     */
    public Backend() {
        buildingType.put(PathfindingRequest.RequestType.DINING, new ArrayList<>(20));
        buildingType.put(PathfindingRequest.RequestType.LIBRARY, new ArrayList<>(20));
        buildingType.put(PathfindingRequest.RequestType.PARKING, new ArrayList<>(20));
        try {
            DataReader reader = new DataReader("buildingData.txt");
            for (Building<Integer> building : reader.buildings) {
                this.hashBuildings.put(building.getNumber(), building);
                if (!this.graph.containsVertex(building)) {
                    this.graph.insertVertex(building);
                    this.addBuildingType(building);
                }

                // k = Building<Integer>, v = Integer
                building.getNeighbors().forEach((k,v) -> {
                    if (!this.graph.containsVertex(k)) {
                        this.graph.insertVertex(k);
                        this.addBuildingType(k);
                    }
                    this.graph.insertEdge(building, k, building.getDistanceBetween(k));});
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Private helper method to put a building into the correct arraylist
     * @param building that is being evaluated
     */
    private void addBuildingType(Building<Integer> building) {
        if (building.hasDining())
            this.buildingType.get(PathfindingRequest.RequestType.DINING).add(building);
        if (building.hasParking())
            this.buildingType.get(PathfindingRequest.RequestType.PARKING).add(building);
        if (building.hasLibraries())
            this.buildingType.get(PathfindingRequest.RequestType.LIBRARY).add(building);
    }

    /**
     * This method returns a list of all the buildings along the shortest path that was traversed
     *
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return a list of all the buildings that were traversed along the path
     */
    @Override
    public List<Building<Integer>> getShortestPath(PathfindingRequest<Building<Integer>> pathfindingRequest) {
        // building will always request a specific building to go to so we call shortestPath from CS400Graph
        if (pathfindingRequest.getRequestType() == PathfindingRequest.RequestType.BUILDING) {
            return this.graph.shortestPath(pathfindingRequest.getStart(), pathfindingRequest.getEnd());
        } else {
            // Check if the building itself contains what the user wants
            if (checkBuildingType(pathfindingRequest)) {
                return this.graph.shortestPath(pathfindingRequest.getStart(), pathfindingRequest.getStart());
            }
            // else try to find the shortest path to type of building
            return this.shortestPathRequest(pathfindingRequest);
        }
    }

    /**
     * Private helper method that gets the shortest path based on the request of if the user wanted one of the
     * following: Library, Dining, Parking
     *
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return a list of the buildings that were traversed on the shortest path
     */
    private List<Building<Integer>> shortestPathRequest(PathfindingRequest<Building<Integer>> pathfindingRequest) {
        // fetch the ArrayList of the type of building the user is searching for
        ArrayList<Building<Integer>> requestBuildingType = this.buildingType.get(pathfindingRequest.getRequestType());
        // Get the sorted list of paths
        LinkedList<CS400Graph<Building<Integer>>.Path> shortestPathBuilding = this.getShortestPathRequestList(requestBuildingType,
                pathfindingRequest);
        // return to the user the shortest path, in other words the first path in the list
        return this.graph.shortestPath(shortestPathBuilding.get(0).start.data, shortestPathBuilding.get(0).end.data);
    }

    /**
     * Private helper method that returns a LinkedList of the paths that were traversed in sorted order
     * @param requestBuildingType ArrayList of the type of buildings being evaluated
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return a sorted linked list of the paths to type of buildings
     */
    private LinkedList<CS400Graph<Building<Integer>>.Path> getShortestPathRequestList(ArrayList<Building<Integer>> requestBuildingType,
                                                                                      PathfindingRequest<Building<Integer>> pathfindingRequest) {
        // create the list
        LinkedList<CS400Graph<Building<Integer>>.Path> shortestPathBuilding = new LinkedList<>();

        // for each building in the arraylist, get the path from the start building and add it to LinkedList
        requestBuildingType.forEach((end) -> shortestPathBuilding.add(this.graph.dijkstrasShortestPath(pathfindingRequest.getStart(), end)));

        // sort the LinkedList based on Path's compareTo method
        Collections.sort(shortestPathBuilding);

        return shortestPathBuilding;
    }

    /**
     * This method returns the number of blocks that were traversed on the shortest path to the frontend
     *
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return the number of blocks traversed as an int
     */
    @Override
    public int getShortestPathLength(PathfindingRequest<Building<Integer>> pathfindingRequest) {
        // basically same as getShortestPath() but get the path length instead of traversed path
        if (pathfindingRequest.getRequestType() == PathfindingRequest.RequestType.BUILDING) {
            return this.graph.getPathCost(pathfindingRequest.getStart(), pathfindingRequest.getEnd());
        } else {
            if (checkBuildingType(pathfindingRequest)) {
                return 0;
            }
            return this.shortestPathRequestLength(pathfindingRequest);
        }
    }

    /**
     * Private helper method to check if the building and request type are the same
     *
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return true when the building is of the same type that it is being searched for
     */
    private boolean checkBuildingType(PathfindingRequest<Building<Integer>> pathfindingRequest) {
        if (pathfindingRequest.getStart().hasDining() && pathfindingRequest.getRequestType() == PathfindingRequest.RequestType.DINING)
            return true;
        else if (pathfindingRequest.getStart().hasParking() && pathfindingRequest.getRequestType() == PathfindingRequest.RequestType.PARKING)
            return true;
        else if (pathfindingRequest.getStart().hasLibraries() && pathfindingRequest.getRequestType() == PathfindingRequest.RequestType.LIBRARY)
            return true;
        return false;
    }

    /**
     * Private helper method that finds the shortest path length of the requested type
     *
     * @param pathfindingRequest specifies what type of request is being made to the backend
     * @return the number of blocks that were traversed on the shortest path
     */
    private int shortestPathRequestLength(PathfindingRequest<Building<Integer>> pathfindingRequest) {
        ArrayList<Building<Integer>> requestBuildingType = this.buildingType.get(pathfindingRequest.getRequestType());
        LinkedList<CS400Graph<Building<Integer>>.Path> shortestPathBuilding = this.getShortestPathRequestList(requestBuildingType,
                pathfindingRequest);

        return shortestPathBuilding.get(0).distance;
    }

    /**
     * This method returns the Building based on the number that was sent (each building has a corresponding number)
     *
     * @param number of the building
     * @return the Building corresponding to input number
     */
    @Override
    public Building<Integer> getBuildingOfNumber(int number) {
        return this.hashBuildings.get(number);
    }
}
