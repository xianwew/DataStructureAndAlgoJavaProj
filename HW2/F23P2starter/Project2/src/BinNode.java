/**
 * This is the BinNode interface that represents a binary tree node.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public interface BinNode {
    /**
     * Check if this node is a leaf node.
     *
     * @return true if this node is a leaf, false otherwise.
     */
    public boolean isLeaf();

    /**
     * Check if this node is empty.
     *
     * @return true if this node is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Insert a new node with the specified properties into the binary tree.
     *
     * @param x       The x-coordinate of the new node.
     * @param y       The y-coordinate of the new node.
     * @param seminar The seminar associated with the new node.
     * @param level   The level of the new node.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The new binary tree with the inserted node.
     */
    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    /**
     * Delete a node with the specified properties from the binary tree.
     *
     * @param x       The x-coordinate of the node to be deleted.
     * @param y       The y-coordinate of the node to be deleted.
     * @param seminar The seminar associated with the node to be deleted.
     * @param level   The level of the node to be deleted.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The binary tree with the specified node deleted.
     */
    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    /**
     * Search for a node within a specified circular region.
     *
     * @param x        The x-coordinate of the center of the circle.
     * @param y        The y-coordinate of the center of the circle.
     * @param circuleX The x-coordinate of the node to be searched.
     * @param circuleY The y-coordinate of the node to be searched.
     * @param radius   The radius of the circle.
     * @param level    The level of the node to be searched.
     * @param xWidth   The width of the x-coordinate.
     * @param yWidth   The width of the y-coordinate.
     * @return The result of the search operation.
     */
    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth);

    /**
     * Print the node at the specified level.
     *
     * @param level The level at which to print the node.
     * @return the number of iteration.
     */
    public int print(int level);
}
