/**
 * Implementation of the BinNode interface for representing an internal node in
 * a binary tree.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeInternal implements BinNode {
    private BinNode left;
    private BinNode right;

    /**
     * Check if this node is a leaf (always returns false for an internal node).
     *
     * @return false since this is an internal node.
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * Check if this node is empty (always returns false for an internal node).
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
     * Delete a node with the specified properties from the binary tree.
     *
     * @param x       The x-coordinate of the node to be deleted.
     * @param y       The y-coordinate of the node to be deleted.
     * @param seminar The seminar associated with the node to be deleted.
     * @param level   The level of the node to be deleted.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The updated binary tree node.
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
     * @return The result of the search operation.
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
            if (bottom > xWidth / 2 + x) {
                rightVisited = getRight().search(x + xWidth / 2, y, circuleX,
                        circuleY, radius, level + 1, xWidth / 2, yWidth);
            }
            else if (top <= xWidth / 2 + x) {
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
            if (bottom > yWidth / 2 + y) {
                rightVisited = getRight().search(x, y + yWidth / 2, circuleX,
                        circuleY, radius, level + 1, xWidth, yWidth / 2);
            }
            else if (top <= yWidth / 2 + y) {
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
     */
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("I");
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
