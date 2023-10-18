import student.TestCase;

/**
 * This class contains test cases for BinNodeLeaf.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeLeafTest extends TestCase {
    /**
     * Test the print function.
     */
    public void testPrint() {
        int id = 12;
        String title = "";
        String date = "";
        int length = 1;
        short x = 16;
        short y = 16;
        int cost = 2;
        String[] keywords = new String[3];
        String desc = "";
        Seminar seminar = new Seminar(id, title, date, length, x, y, cost,
                keywords, desc);

        BinNodeLeaf tmp = new BinNodeLeaf(seminar);
        int level = 3;
        int result = 0;

        systemOut().clearHistory();
        result = tmp.print(level);
        String printOut = systemOut().getHistory();
        String refOut = "leaf with 1 objects 12";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 3);

        systemOut().clearHistory();
        result = tmp.print(0);
        printOut = systemOut().getHistory();
        refOut = "leaf with 1 objects 12";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 0);
    }
}
