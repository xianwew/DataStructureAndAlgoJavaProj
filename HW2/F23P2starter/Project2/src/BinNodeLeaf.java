/**
 * BinNodeLeaf class create the founction about leaf node
 *
 * @author xianwei&jiren
 * @version Oct 2023, last updated Oct 2023
 */
public class BinNodeLeaf implements BinNode {
    private SeminarList sl;

    /**
     * Create seminarlist
     *
     * @param tin input title
     */
    public SeminarList getSeminarList() {
        return sl;
    }

    public BinNodeLeaf(Seminar seminarLocal) {
        this.sl = new SeminarList();
        SeminarList tmp = new SeminarList();
        tmp.setSeminar(seminarLocal);
        sl.setNext(tmp);
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

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

        // case 2
        // create internal node
        // insert all seminars from the list to the same internal node
        // insert the seminar to the internal node
        // return the internal node
        BinNodeInternal tmp = new BinNodeInternal();
        curList = sl.getNext();
        while (curList != null) {
            tmp.insert(x, y, curList.getSeminar(), level, xWidth, yWidth);
            curList = curList.getNext();
        }
        tmp.insert(x, y, seminar, level, xWidth, yWidth);
        return tmp;
    }

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
        if (sl.getNext() != null && sl.getNext().getSeminar() != null) {
            return this;
        }
        return BinNodeEmpty.getNode();
    }

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
