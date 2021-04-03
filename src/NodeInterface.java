import java.util.HashMap;

public interface NodeInterface {


    public boolean connectTo(Building<Integer> b);

    public void addNeighbor(Building<Integer> b, int weight);

    public int getNumber();

    public String getName();

    public boolean hasDining();

    public boolean hasLibraries();

    public boolean hasParking();

    public HashMap<Building<Integer>, Integer> getNeighbors();

}
