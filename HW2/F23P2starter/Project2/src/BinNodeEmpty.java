/**
 * Implementation of the BinNode interface for representing an empty node in a
 * binary tree.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeEmpty implements BinNode {
    private static BinNodeEmpty flyweight = new BinNodeEmpty();

    /**
     * Get the fly weight instance of the empty node.
     *
     * @return The fly weight instance of the empty node.
     */
    public static BinNodeEmpty getNode() {
        return flyweight;
    }

    /**
     * Check if this node is a leaf.
     *
     * @return true since an empty node is considered a leaf.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if this node is empty.
     *
     * @return true since this is an empty node.
     */
    public boolean isEmpty() {
        return true;
    }

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
            int yWidth) {
        return new BinNodeLeaf(seminar);
    }

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
            int yWidth) {
        return this;
    }

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
            int level, int xWidth, int yWidth) {
        return 1;
    }

    /**
     * Print the empty node at the specified level.
     *
     * @param level The level at which to print the empty node.
     * @return the number of iteration.
     */
    public int print(int level) {
        int i = 0;
        for (i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("E");
        return i;
    }
}
