import java.util.HashMap;
import java.util.NoSuchElementException;

public class Building<W extends Comparable<W>> implements NodeInterface {
  
  private int number;
  private String name;
  private boolean dining = false;
  private boolean library = false;
  private boolean parking = false;
  
  private HashMap<Building<Integer>, Integer> neigborhood = new HashMap<>();
  private W tempVal = null; 
  
  public Building(int number, String name, boolean d, boolean l, boolean p) {
    this.number = number;
    this.name = name;
    dining = d;
    library = l;
    parking = p;
  }
  
  public boolean connectTo(Building<Integer> b) {
    return neigborhood.containsKey(b);
  }
    
  public void addNeighbor(Building<Integer> b, int w) {
    neigborhood.put(b, w);
  }
  
  public boolean removeNeighbor(Building<Integer> b) {
    if (connectTo(b)) {
      neigborhood.remove(b);
      return true;
    }
    return false;
  }
  
  public void setDining(boolean d) {
    dining = d;
  }
  
  public void setLibrary(boolean l) {
    library = l;
  }
  
  public void setParking(boolean p) {
    parking = p;
  }
  
  public Building(String name) {
    this.name = name;
  }
  
  public int getNumber() {
    return number;
  }
  
  public String getName() {
    return name;
  }
  
  public boolean hasDining() {
    return dining;
  }
  
  public boolean hasLibraries() {
    return library;
  }
  
  public boolean hasParking() {
    return parking;
  }
  
  public HashMap<Building<Integer>, Integer> getNeighbors() {
    return neigborhood;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
  public String getDescription() {
    return number + "." + name;
  }
  
  public int getDistanceBetween(Building<Integer> b) {
    if (!neigborhood.containsKey(b))
      throw new NoSuchElementException("error: no such neigbor");
    return neigborhood.get(b);
  }
  
  public void showSelf() {
    String d = dining ? "Dining " : "";
    String l = library ? "Library " : "";
    String p = parking ? "Parking " : "";
    System.out.println(number + "." + name + " (" + neigborhood.size() + ") " +
     "{ " + d + l + p + "}");
  }
  
  public void showNeigbor() {
    for(Building<Integer> b : neigborhood.keySet()) {
      System.out.println(b.getNumber() + "." + b.getName());
    }
  }
  
  public void setTempVal(W val) {
    tempVal = val;
  }
  
  public void resetTempVal() {
    tempVal = null;
  }
  
  public W getTempVal() {
    return tempVal;
  }
  
}
