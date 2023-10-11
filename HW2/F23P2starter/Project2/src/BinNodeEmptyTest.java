import student.TestCase;

/**
 * This class contains test cases for BinNodeEmpty.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeEmptyTest extends TestCase {

    /**
     * Test the print method.
     */
    public void testPrint() {

        BinNodeEmpty tmp = BinNodeEmpty.getNode();
        int level = 3;
        int result = 0;

        systemOut().clearHistory();
        result = tmp.print(level);
        String printOut = systemOut().getHistory();
        String refOut = "      E\n";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 3);

        systemOut().clearHistory();
        level = 0;
        result = tmp.print(level);
        refOut = "E\n";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 0);

        systemOut().clearHistory();
        level = -1;
        result = tmp.print(level);
        refOut = "E\n";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 0);

        systemOut().clearHistory();
        level = 10;
        result = tmp.print(level);
        refOut = "                    E\n";
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 10);
    }

}