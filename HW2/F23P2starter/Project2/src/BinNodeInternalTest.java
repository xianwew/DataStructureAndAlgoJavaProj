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
    public void searchTest() {
        BinNodeInternal tmp = new BinNodeInternal();
        tmp.search(0, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Test the insert function.
     */
    public void insertTest() {
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
    }
}
