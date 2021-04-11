/*
 * Authored by Jared Menningen on 04/02/2021.
 *
 * Upon further review, it makes more sense to make a BackendInterface that the Frontend calls rather than vice versa.
 * This interface abstracts away the implementation of the backend.
 *
 * It is thus
 */

import java.util.List;

public interface BackendInterface {

    List<Building<Integer>> getShortestPath(PathfindingRequest<Building<Integer>> pathfindingRequest);

    int getShortestPathLength(PathfindingRequest<Building<Integer>> pathfindingRequest);

    Building<Integer> getBuildingOfNumber(int number);

}
