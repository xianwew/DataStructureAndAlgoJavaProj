import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import student.TestCase;

/**
 * This class contains test cases for the BinNodeEmpty class.
 * 
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinNodeEmptyTest extends TestCase {

    /**
     * Test the print method of BinNodeEmpty.
     */
    public void testPrint() {

        BinNodeEmpty tmp = BinNodeEmpty.getNode();
        int level = 3;

        systemOut().clearHistory();
        tmp.print(level);
        String printOut = systemOut().getHistory();
        String refOut = "      E\n";
        assertFuzzyEquals(printOut, refOut);
 
        
        systemOut().clearHistory();
        level = 0;
        tmp.print(level);

        refOut = "E\n";
        assertFuzzyEquals(printOut, refOut);

        
        systemOut().clearHistory();
        level = -1;
        tmp.print(level);

        refOut = "E\n";
        assertFuzzyEquals(printOut, refOut);
        
        
        systemOut().clearHistory();
        level = 10;
        tmp.print(level);

        refOut = "                    E\n";
        assertFuzzyEquals(printOut, refOut);
    }

}