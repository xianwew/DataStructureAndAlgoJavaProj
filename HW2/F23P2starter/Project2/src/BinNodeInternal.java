/**
 * Implementation of BinNodeInternal.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeInternal implements BinNode {
    private BinNode left;
    private BinNode right;

    /**
     * Check if this node is a leaf.
     *
     * @return false since this is an internal node.
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * Check if this node is empty.
     *
     * @return false since this is an internal node.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Create an internal node with empty left and right children.
     */
    public BinNodeInternal() {
        left = BinNodeEmpty.getNode();
        right = BinNodeEmpty.getNode();
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
        // modify left and right node
        // x, y <= mid point, go left, > go right. left = left.insert
        int mod = level % 2;
        if (mod == 0) {
            xWidth /= 2;
            if (seminar.x() < xWidth + x) {
                left = left.insert(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.insert(x + xWidth, y, seminar, level + 1, xWidth,
                        yWidth);
            }
        }
        else {
            yWidth /= 2;
            if (seminar.y() < yWidth + y) {
                left = left.insert(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.insert(x, y + yWidth, seminar, level + 1, xWidth,
                        yWidth);
            }
        }
        return this;
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
        int mod = level % 2;
        if (mod == 0) {
            xWidth /= 2;
            if (seminar.x() < xWidth + x) {
                left = left.delete(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.delete(x + xWidth, y, seminar, level + 1, xWidth,
                        yWidth);
            }
        }
        else {
            yWidth /= 2;
            if (seminar.y() < yWidth + y) {
                left = left.delete(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.delete(x, y + yWidth, seminar, level + 1, xWidth,
                        yWidth);
            }
        }
        if (left.isLeaf() && right.isLeaf()) {
            if (left.isEmpty()) {
                return getRight();
            }
            else if (right.isEmpty()) {
                return getLeft();
            }
        }
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
        int mod = level % 2;
        int leftVisited = 0;
        int rightVisited = 0;
        int top = 0;
        int bottom = 0;
        if (mod == 0) {
            bottom = circuleX - radius;
            top = circuleX + radius;
            if (bottom > xWidth / 2 + x - 1) {
                rightVisited = getRight().search(x + xWidth / 2, y, circuleX,
                        circuleY, radius, level + 1, xWidth / 2, yWidth);
            }
            else if (top <= xWidth / 2 + x - 1) {
                leftVisited = getLeft().search(x, y, circuleX, circuleY, radius,
                        level + 1, xWidth / 2, yWidth);
            }
            else {
                leftVisited = getLeft().search(x, y, circuleX, circuleY, radius,
                        level + 1, xWidth / 2, yWidth);
                rightVisited = getRight().search(x + xWidth / 2, y, circuleX,
                        circuleY, radius, level + 1, xWidth / 2, yWidth);
            }
        }
        else {
            bottom = circuleY - radius;
            top = circuleY + radius;
            if (bottom > yWidth / 2 + y - 1) {
                rightVisited = getRight().search(x, y + yWidth / 2, circuleX,
                        circuleY, radius, level + 1, xWidth, yWidth / 2);
            }
            else if (top <= yWidth / 2 + y - 1) {
                leftVisited = getLeft().search(x, y, circuleX, circuleY, radius,
                        level + 1, xWidth, yWidth / 2);
            }
            else {
                leftVisited = getLeft().search(x, y, circuleX, circuleY, radius,
                        level + 1, xWidth, yWidth / 2);
                rightVisited = getRight().search(x, y + yWidth / 2, circuleX,
                        circuleY, radius, level + 1, xWidth, yWidth / 2);
            }
        }
        return 1 + leftVisited + rightVisited;
    }

    /**
     * Print the internal node at the specified level.
     *
     * @param level The level at which to print the internal node.
     * @return the number of iteration.
     */
    public int print(int level) {
        int i = 0;
        for (i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("I");
        return i;
    }

    /**
     * Get the left child of this internal node.
     *
     * @return The left child of this internal node.
     */
    public BinNode getLeft() {
        return left;
    }

    /**
     * Set the left child of this internal node.
     *
     * @param left The left child to be set for this internal node.
     */
    public void setLeft(BinNode left) {
        this.left = left;
    }

    /**
     * Get the right child of this internal node.
     *
     * @return The right child of this internal node.
     */
    public BinNode getRight() {
        return right;
    }

    /**
     * Set the right child of this internal node.
     *
     * @param right The right child to be set for this internal node.
     */
    public void setRight(BinNode right) {
        this.right = right;
    }

}
