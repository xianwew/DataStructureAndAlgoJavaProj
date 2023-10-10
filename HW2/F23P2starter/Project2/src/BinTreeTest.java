import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import student.TestCase;

/**
 * This class contains test cases for the BinTree class. It tests various
 * methods of the BinTree class to ensure their correctness. You can add more
 * test methods to cover additional scenarios.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class BinTreeTest extends TestCase {

    public void testPrint() {
        int result = 0;
        
        BinTree tmp = new BinTree(32);
        result = tmp.print(0, tmp.getRoot());
        
        String refOut = "E";
        String printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        assertEquals(result, 1);
        
        systemOut().clearHistory();
        int id = 12;
        String title = "";
        String date = "";
        int length = 1;
        short x = 15;
        short y = 15;
        int cost = 2;
        String[] keywords = new String[3];
        String desc = "";
        Seminar seminar = new Seminar(id, title, date, length, x, y, cost, keywords, desc);
        
        id = 10;
        x = 13;
        y = 18;
        Seminar seminar2 = new Seminar(id, title, date, length, x, y, cost, keywords, desc);
        
        tmp.insert(seminar);
        BinNode node = tmp.getRoot();
        result = tmp.print(0, node);
        assertEquals(result, 1);
        refOut = "leaf with 1 objects 12";
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);     
        
        tmp.insert(seminar2);
        result = tmp.print(0, tmp.getRoot());
        assertEquals(result, 5);
        refOut = "i/n  i/n    leaf with 1 objects 12/n  leaf with 1 objects 10/nE";
        printOut = systemOut().getHistory();
        //assertFuzzyEquals(printOut, refOut);     
    }
    

}
