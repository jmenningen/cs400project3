import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class XBackend {
  
  ArrayList<Building<Integer>> nodes = null;
  private int pathLength = 0;
  
  public XBackend(ArrayList<Building<Integer>> dataList) {
    nodes = dataList;
  }
  
  
  public LinkedList<Building<Integer>> shortestPath(int s, int t) {
    nodes.stream().forEach(b -> b.resetTempVal());
    
    int[] dist = new int[nodes.size()];
    int[] prev = new int[nodes.size()];
    
    for (int i = 0; i < nodes.size(); ++i)
      dist[i] = Integer.MAX_VALUE;
    
    dist[s] =  0;
    prev[s] = -1;
    
    HashSet<Integer> visited = new HashSet<>();
    LinkedList<Building<Integer>> list = new LinkedList<>();
    
    list.add(nodes.get(s));  // add the source node
    
    // Dijkstra's algorithm
    while (!list.isEmpty()) {
      
      Building<Integer> curr = list.pollFirst();
      int j = curr.getNumber();
      
      // update all nodes that connect with the current node
      for (Building<Integer> neighbor : curr.getNeighbors().keySet()) {
      
        int k = neighbor.getNumber();
        if (visited.contains(k)) continue;
        
        int newDist = dist[j] + curr.getDistanceBetween(neighbor);
        if (newDist < dist[k]) {
          dist[k] = newDist;
          prev[k] = j;
        }
        neighbor.setTempVal(dist[k]);
        
        if (!list.contains(neighbor))
          list.add(neighbor);
      }
      visited.add(j);
      Collections.sort(list, (b1, b2) -> b1.getTempVal() - b2.getTempVal());
    }
    
    //printBackendData(dist, prev);  // for debug
    
    // update the length of shortest path
    pathLength = nodes.get(t).getTempVal();
    
    // construct the shortest path
    int p = t;
    while (p != -1) {
      Building<Integer> curr = nodes.get(p);
      list.addFirst(curr);
      p = prev[p];
    }
    return list;
  }
  
  @SuppressWarnings("unused")
  private void printBackendData(int[] dist, int[] prev) {
    System.out.println();
    for (int i = 0; i < nodes.size(); ++i) {
      String node = i + "." + nodes.get(i).getName();
      String sps = " ".repeat(43 - node.length());
      System.out.println(node + sps + "d:" + dist[i] + "\t [" + prev[i] + "]");
    }
  }
  
  public int getDist() {
    return pathLength;
  }

}
