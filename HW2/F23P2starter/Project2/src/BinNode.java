/**
 * BinNode interface that represents a binTree node.
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
     * Insert a new node into the BinTree.
     *
     * @param x       The x-coordinate of the block.
     * @param y       The y-coordinate of the block.
     * @param seminar The seminar to be inserted.
     * @param level   The level of the binary tree.
     * @param xWidth  The x width of the block.
     * @param yWidth  The y width of the block.
     * @return The new binTree node.
     */
    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    /**
     * Delete a node from the binTree.
     *
     * @param x       The x-coordinate of the block.
     * @param y       The y-coordinate of the block.
     * @param seminar The seminar associated with the node to be deleted.
     * @param level   The level of the node to be deleted.
     * @param xWidth  The x width of the block.
     * @param yWidth  The y width of the block.
     * @return This empty node since nothing is deleted.
     */
    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    /**
     * Search for a node within a specified circular region.
     *
     * @param x        The x-coordinate of the block.
     * @param y        The y-coordinate of the block.
     * @param circuleX The x-coordinate of the center of the circle.
     * @param circuleY The y-coordinate of the center of the circle.
     * @param radius   The radius of the circle.
     * @param level    The level of the node to be searched.
     * @param xWidth  The x width of the block.
     * @param yWidth  The y width of the block.
     * @return 1 since an empty node is visited.
     */
    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth);

    /**
     * Print the node at the specified level.
     *
     * @param level The level to be printed.
     * @return the number of iteration.
     */
    public int print(int level);
}
