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
     * Get the singleton instance of the empty node.
     *
     * @return The singleton instance of the empty node.
     */
    public static BinNodeEmpty getNode() {
        return flyweight;
    }

    /**
     * Check if this node is a leaf (always returns true for an empty node).
     *
     * @return true since an empty node is considered a leaf.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if this node is empty (always returns true for an empty node).
     *
     * @return true since this is an empty node.
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * Insert a new node with the specified properties into the binary tree.
     *
     * @param x       The x-coordinate of the new node.
     * @param y       The y-coordinate of the new node.
     * @param seminar The seminar associated with the new node.
     * @param level   The level of the new node.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The new binary tree node.
     */
    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth) {
        return new BinNodeLeaf(seminar);
    }

    /**
     * Delete a node with the specified properties from the binary tree.
     *
     * @param x       The x-coordinate of the node to be deleted.
     * @param y       The y-coordinate of the node to be deleted.
     * @param seminar The seminar associated with the node to be deleted.
     * @param level   The level of the node to be deleted.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return This empty node since nothing is deleted.
     */
    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth) {
        return this;
    }

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
     * @return 1 since an empty node is not part of the search.
     */
    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth) {
        return 1;
    }

    /**
     * Print the empty node at the specified level.
     *
     * @param level The level at which to print the empty node.
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
