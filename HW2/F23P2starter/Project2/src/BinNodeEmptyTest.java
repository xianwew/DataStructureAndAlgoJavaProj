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
        BinNodeEmpty tmp = BinNodeEmpty.getNode();
        tmp.print(0);
    }
    
}

