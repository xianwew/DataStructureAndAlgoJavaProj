import student.TestCase;

/**
 * This class contains test cases for the BinNodeInternal class.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeInternalTest extends TestCase {

    /**
     * Test the search function.
     */
    public void testSearch() {
        BinNodeInternal tmp = new BinNodeInternal();
        tmp.search(0, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Test the insert function.
     */
    public void testInsert() {
        int id = 12;
        String title = "";
        String date = "";
        int length = 1;
        short x = 16;
        short y = 16;
        int cost = 2;
        String[] keywords = new String[3];
        String desc = "";
        BinTree tree = new BinTree(32);
        Seminar seminar = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);
        tree.insert(seminar);
        assertEquals(tree.getRoot().isLeaf(), true);
        assertEquals(tree.getRoot().isEmpty(), false);

        id = 12;
        title = "";
        date = "";
        length = 1;
        x = 15;
        y = 15;
        cost = 2;
        desc = "";
        tree = new BinTree(32);
        Seminar seminar2 = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);
        assertEquals(tree.getRoot().isLeaf(), true);
        assertEquals(tree.getRoot().isEmpty(), true);
        tree.insert(seminar2);
        assertEquals(tree.getRoot().isLeaf(), true);
        assertEquals(tree.getRoot().isEmpty(), false);
        BinNodeLeaf tmpLeaf = (BinNodeLeaf) tree.getRoot();
        assertEquals(tmpLeaf.getSeminarList().getNext().getSeminar().id(), 12);

        id = 12;
        title = "";
        date = "";
        length = 1;
        x = 3;
        y = 3;
        cost = 2;
        desc = "";
        tree = new BinTree(8);
        Seminar seminar3 = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);
        tree.insert(seminar3);
        x = 5;
        y = 6;
        id = 11;
        Seminar seminar4 = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);
        tree.insert(seminar4);
        BinNodeInternal tmpInternal1 = (BinNodeInternal) tree.getRoot();
        BinNodeLeaf left1 = (BinNodeLeaf) tmpInternal1.getLeft();
        BinNodeLeaf right1 = (BinNodeLeaf) tmpInternal1.getRight();
        assertEquals(left1.getSeminarList().getNext().getSeminar().id(), 12);
        assertEquals(right1.getSeminarList().getNext().getSeminar().id(), 11);
    }
}
