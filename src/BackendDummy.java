import java.util.ArrayList;
import java.util.List;

public class BackendDummy implements BackendInterface {

    private ArrayList<Building<Integer>> buildings;

    public BackendDummy(){
        buildings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            boolean d = (i & 0x4) != 0;
            boolean l = (i & 0x2) != 0;
            boolean p = (i & 0x1) != 0;
            buildings.add(new Building<>(i, String.format("Building %d",i), d, l, p));
        }
    }

    @Override
    public List<Building<Integer>> getShortestPath(PathfindingRequest pathfindingRequest) {
        ArrayList<Building<Integer>> list = new ArrayList<>();
        list.add(buildings.get(pathfindingRequest.getStart().getNumber()));
        for (int i = pathfindingRequest.getStart().getNumber() + 1; i < buildings.size(); i++) {
            Building<Integer> building = buildings.get(i);
            list.add(building);
            switch (pathfindingRequest.getRequestType()){
                case DINING:
                    if (building.hasDining())
                        return list;
                    break;
                case LIBRARY:
                    if (building.hasDining())
                        return list;
                    break;
                case PARKING:
                    if (building.hasParking())
                        return list;
                    break;
                default:
                    if (pathfindingRequest.getEnd().equals(building))
                        return list;
            }
        }
        return null;
    }

    @Override public int getShortestPathLength(PathfindingRequest pathfindingRequest) {
        return getShortestPath(pathfindingRequest).size();
    }

    @Override public Building getBuildingOfNumber(int number) {
        return buildings.get(number);
    }
}
