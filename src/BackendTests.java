// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu
// Team: DF blue
// Role: Backend Developer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is test class that tests the different scenarios of the requests that will be made to backend
 */
public class BackendTests {

    /**
     * This method tests if the backend returns the correct path length when the buildings are neighbors.
     */
    @Test
    public void testShortestPathBuildingNeighbor() {
        Backend backend = new Backend();
        // Steenbock to Microbial Sciences
        PathfindingRequest<Building<Integer>> request = new PathfindingRequest<>(backend.getBuildingOfNumber(44),
                backend.getBuildingOfNumber(29), PathfindingRequest.RequestType.BUILDING);
        Assertions.assertEquals(2,backend.getShortestPathLength(request));
    }

    /**
     * This method tests if the backend returns the correct path length when the buildings are NOT neighbors.
     */
    @Test
    public void testShortestPathBuildingNotNeighbor() {
        Backend backend = new Backend();
        // Engineering Hall to Pyle Center
        PathfindingRequest<Building<Integer>> request = new PathfindingRequest<>(backend.getBuildingOfNumber(15),
                backend.getBuildingOfNumber(37), PathfindingRequest.RequestType.BUILDING);
        Assertions.assertEquals(15, backend.getShortestPathLength(request));
    }

    /**
     * This method tests if the backend returns the correct path length when the request type and initial building type
     * do NOT match each other.
     */
    @Test
    public void testShortestPathBuildingTypeDifferent() {
        Backend backend = new Backend();
        // Camp Randall find nearest Library
        PathfindingRequest<Building<Integer>> request = new PathfindingRequest<>(backend.getBuildingOfNumber(5),
                null, PathfindingRequest.RequestType.LIBRARY);
        Assertions.assertEquals(8, backend.getShortestPathLength(request));
    }

    /**
     * This method tests if the backend returns the correct path length when the request type and initial building type
     * match each other.
     */
    @Test
    public void testShortestPathBuildingTypeSame() {
        Backend backend = new Backend();
        // Camp Randall find nearest Parking
        PathfindingRequest<Building<Integer>> request = new PathfindingRequest<>(backend.getBuildingOfNumber(5),
                null, PathfindingRequest.RequestType.PARKING);
        Assertions.assertEquals(0, backend.getShortestPathLength(request));
    }
}
