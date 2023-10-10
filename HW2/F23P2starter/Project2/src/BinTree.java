/**
 * Represents a binary tree for storing seminars in a two-dimensional space. It
 * provides methods for inserting, deleting, searching, and printing seminars.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinTree {
    private int worldSize = 0;
    private BinNode root;

    /**
     * Get the root node of the binary tree.
     *
     * @return The root node.
     */
    public BinNode getRoot() {
        return this.root;
    }

    /**
     * Creates a new binary tree with the specified world size.
     *
     * @param worldSizeLocal The size of the world (both width and height).
     */
    public BinTree(int worldSizeLocal) {
        this.worldSize = worldSizeLocal;
        root = BinNodeEmpty.getNode();
    }

    /**
     * Insert a seminar into the binary tree.
     *
     * @param seminar The seminar to be inserted.
     */
    public void insert(Seminar seminar) {
        root = root.insert(0, 0, seminar, 0, worldSize, worldSize);
    }

    /**
     * Delete a seminar from the binary tree.
     *
     * @param seminar The seminar to be deleted.
     */
    public void delete(Seminar seminar) {
        root = root.delete(0, 0, seminar, 0, worldSize, worldSize);
    }

    /**
     * Search for seminars within a circular region defined by a center and
     * radius.
     *
     * @param circuleX The x-coordinate of the center of the circle.
     * @param circuleY The y-coordinate of the center of the circle.
     * @param radius   The radius of the circular region.
     * @return The number of seminars found within the circular region.
     */
    public int search(int circuleX, int circuleY, int radius) {
        return root.search(0, 0, circuleX, circuleY, radius, 0, worldSize,
                worldSize);
    }

    /**
     * Print the binary tree starting from the specified node.
     *
     * @param level The level of the current node in the tree.
     * @param node  The current node to be printed.
     * @return The total number of nodes printed in the subtree.
     */
    public int print(int level, BinNode node) {
        node.print(level);
        if (!node.isLeaf()) {
            BinNodeInternal tmp = (BinNodeInternal) node;
            return print(level + 1, tmp.getLeft()) + print(level + 1, tmp.getRight()) + 1;
        }
        return 1;
    }

}
