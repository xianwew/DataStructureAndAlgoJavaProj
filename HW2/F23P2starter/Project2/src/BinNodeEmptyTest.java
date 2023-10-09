import java.io.*;
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
    public void printTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        BinNodeEmpty tmp = BinNodeEmpty.getNode();
        int level = 3;

        tmp.print(level);
        System.setOut(System.out);

        String expectedOutput = "      E\n";
        assertEquals(expectedOutput, outputStream.toString());

        level = 0;
        tmp.print(level);
        System.setOut(System.out);

        expectedOutput = "E\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

}