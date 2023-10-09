/**
 * Implementation of the BinNode interface for representing a leaf node in a
 * binary tree. This class holds a list of seminars at the same (x, y)
 * coordinates. Each leaf node may contain multiple seminars with the same
 * coordinates.
 *
 * @author xianwei&jiren
 * @version Oct 2023, last updated Oct 2023
 */
public class BinNodeLeaf implements BinNode {
    private SeminarList sl;

    /**
     * Get the seminar list associated with this leaf node.
     *
     * @return The seminar list.
     */
    public SeminarList getSeminarList() {
        return sl;
    }

    /**
     * Create a new leaf node with an initial seminar.
     *
     * @param seminarLocal The initial seminar to be added to the leaf node.
     */
    public BinNodeLeaf(Seminar seminarLocal) {
        this.sl = new SeminarList();
        SeminarList tmp = new SeminarList();
        tmp.setSeminar(seminarLocal);
        sl.setNext(tmp);
    }

    /**
     * Check if this node is a leaf (always returns true for a leaf node).
     *
     * @return true since this is a leaf node.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if this node is empty (always returns false for a leaf node).
     *
     * @return false since this is not an empty node.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Insert a new seminar with the specified properties into the leaf node. If
     * a seminar with the same (x, y) coordinates already exists, it will be
     * added to the list.
     *
     * @param x       The x-coordinate of the seminar.
     * @param y       The y-coordinate of the seminar.
     * @param seminar The seminar to be inserted.
     * @param level   The level of the binary tree.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The updated leaf node.
     */
    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth) {
        // case 1
        // if x, y equals existing seminar
        // increase count
        // insert to the list
        SeminarList curList = sl.getNext();
        if (curList.getSeminar() != null
                && curList.getSeminar().x() == seminar.x()
                && curList.getSeminar().y() == seminar.y()) {
            while (curList.getNext() != null) {
                curList = curList.getNext();
            }
            SeminarList tmp = new SeminarList();
            tmp.setSeminar(seminar);
            curList.setNext(tmp);
            return this;
        }
        else if (curList.getSeminar() == null) {
            curList.setSeminar(seminar);
            return this;
        }
        BinNodeInternal tmp = new BinNodeInternal();
        curList = sl.getNext();
        while (curList != null) {
            tmp.insert(x, y, curList.getSeminar(), level, xWidth, yWidth);
            curList = curList.getNext();
        }
        tmp.insert(x, y, seminar, level, xWidth, yWidth);
        return tmp;
    }

    /**
     * Delete a seminar with the specified properties from the leaf node.
     *
     * @param x       The x-coordinate of the seminar to be deleted.
     * @param y       The y-coordinate of the seminar to be deleted.
     * @param seminar The seminar to be deleted.
     * @param level   The level of the binary tree.
     * @param xWidth  The width of the x-coordinate.
     * @param yWidth  The width of the y-coordinate.
     * @return The updated leaf node or an empty node if all seminars are
     *         deleted.
     */
    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth) {
        SeminarList tmp = sl.getNext();
        SeminarList prev = sl;
        while (tmp != null) {
            if (tmp.getSeminar().id() == seminar.id()) {
                tmp = tmp.getNext();
                prev.setNext(tmp);
            }
            if (tmp != null) {
                tmp = tmp.getNext();
            }
            prev = tmp;
        }
        if (sl.getNext() != null) {
            return this;
        }
        return BinNodeEmpty.getNode();
    }

    /**
     * Search for seminars within a specified circular region and print the
     * found seminars.
     *
     * @param x        The x-coordinate of the center of the circle.
     * @param y        The y-coordinate of the center of the circle.
     * @param circuleX The x-coordinate of the seminar to be searched.
     * @param circuleY The y-coordinate of the seminar to be searched.
     * @param radius   The radius of the circle.
     * @param level    The level of the binary tree.
     * @param xWidth   The width of the x-coordinate.
     * @param yWidth   The width of the y-coordinate.
     * @return The number of seminars found within the circular region.
     */
    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth) {
        SeminarList tmp = sl.getNext();
        if (tmp != null && tmp.getSeminar() != null
                && Math.pow(tmp.getSeminar().x() - circuleX, 2)
                        + Math.pow(tmp.getSeminar().y() - circuleY, 2) <= radius
                                * radius) {
            while (tmp != null) {
                System.out.println("Found a record with key value "
                        + tmp.getSeminar().id() + " at " + tmp.getSeminar().x()
                        + ", " + tmp.getSeminar().y());
                tmp = tmp.getNext();
            }
        }
        return 1;
    }

    /**
     * Print the contents of the leaf node.
     *
     * @param level The level of the binary tree.
     */
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        String s = "";
        SeminarList curList = sl.getNext();
        int count = 0;
        while (curList != null) {
            s += " " + curList.getSeminar().id();
            curList = curList.getNext();
            count++;
        }
        System.out.println("Leaf with " + count + " objects:" + s);
    }
}
