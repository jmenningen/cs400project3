/*
 * Used to store information about the nodes at both ends of the edge and the edge’s weight.
 */
public interface GraphEdgeInterface {

    /*
     * Returns an int representing the number of blocks between connected nodes.
     */
    public int getEdgeWeight();

    /*
     * Returns the node stored at one end of the edge.
     */
    public NodeInterface getNodeOne();

    /*
     * Returns the node stored at the other end of the edge.
     */
    public NodeInterface getNodeTwo();


    /**
     * Convenience method for getting the node opposite of the input node.
     * If the input node is not one of the two described by this GraphEdgeInterface, it should return null.
     *
     * @param node The NodeInterface to get the opposite node from
     * @return The node opposite of the input NodeInterface
     */
    public NodeInterface getOtherNode(NodeInterface node);

}
