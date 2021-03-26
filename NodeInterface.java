/*
 * Used to store information about a particular node, such as it's name and whether it is a building or an intersection.
 */
public interface NodeInterface {
	
	/*
	 * Returns the name of the building or intersection node.
	 */
	public String getName();
	
	/*
	 * Returns true if the node represents a building and false if it
	 * represents an intersection.
	 */
	public boolean isBuilding();
}
