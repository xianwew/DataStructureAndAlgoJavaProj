/**
 * Implementation of BinNodeLeaf
 *
 * @author xianwei&jiren
 * @version Oct 2023, last updated Oct 2023
 */
public class BinNodeLeaf implements BinNode {
    /**
     * The SeminarList class represents a node in a linked list used to store
     * seminar objects
     */
    public class SeminarList {

        private Seminar seminar = null;
        private SeminarList next = null;

        /**
         * Get the Seminar object stored in this node.
         *
         * @return The Seminar object stored in this node.
         */
        public Seminar getSeminar() {
            return seminar;
        }

        /**
         * Set the Seminar object to be stored in this node.
         *
         * @param seminar The Seminar object to be stored in this node.
         */
        public void setSeminar(Seminar seminar) {
            this.seminar = seminar;
        }

        /**
         * Get the reference to the next node in the linked list.
         *
         * @return The reference to the next node in the linked list.
         */
        public SeminarList getNext() {
            return next;
        }

        /**
         * Set the reference to the next node in the linked list.
         *
         * @param next The reference to the next node in the linked list.
         */
        public void setNext(SeminarList next) {
            this.next = next;
        }

    }
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
     * Create a new leaf node with an seminar value.
     *
     * @param seminarLocal The seminar to be added to the leaf node.
     */
    public BinNodeLeaf(Seminar seminarLocal) {
        int id = Integer.MIN_VALUE;
        String title = "";
        String date = "";
        int length = 0;
        short x = 0;
        short y = 0;
        int cost = 0;
        String[] keywords = new String[1];
        String desc = "";
        Seminar dummy = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);
        this.sl = new SeminarList();
        this.sl.setSeminar(dummy);
        SeminarList tmp = new SeminarList();
        tmp.setSeminar(seminarLocal);
        sl.setNext(tmp);
    }

    /**
     * Check if this node is a leaf.
     *
     * @return true since this is a leaf node.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if this node is empty.
     *
     * @return false since this is not an empty node.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Insert a new seminar into the leaf node. If a seminar with the same (x,
     * y) coordinates already exists, it will be added to the list.
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
        // case 1
        // if x, y equal to existing seminar
        SeminarList curList = sl.getNext();
        SeminarList prev = sl;
        if (curList.getSeminar().x() == seminar.x()
                && curList.getSeminar().y() == seminar.y()) {
            SeminarList tmp = new SeminarList();
            tmp.setSeminar(seminar);
            while (true) {
                if (curList.getSeminar().id() > seminar.id()) {
                    prev.setNext(tmp);
                    tmp.setNext(curList);
                    break;
                }
                prev = curList;
                curList = curList.getNext();
                if (curList == null) {
                    prev.setNext(tmp);
                    break;
                }
            }
            return this;
        }
        // System.out.println("awd");
        // case 2
        // if x, y not equal to existing seminar
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
        SeminarList tmp = sl.getNext();
        SeminarList prev = sl;
        while (tmp != null) {
            if (tmp.getSeminar().id() == seminar.id()) {
                prev.setNext(tmp.getNext());
            }
            prev = tmp;
            tmp = tmp.getNext();
        }
        if (sl.getNext() != null) {
            return this;
        }
        return BinNodeEmpty.getNode();
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
     * @param xWidth   The x width of the block.
     * @param yWidth   The y width of the block.
     * @return 1 since an empty node is visited.
     */
    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth) {
        SeminarList tmp = sl.getNext();
        if (Math.pow(tmp.getSeminar().x() - circuleX, 2) + Math
                .pow(tmp.getSeminar().y() - circuleY, 2) <= radius * radius) {
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
     * @return the number of iteration.
     */
    public int print(int level) {
        int i = 0;
        for (i = 0; i < level; i++) {
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
        return i;
    }

}
