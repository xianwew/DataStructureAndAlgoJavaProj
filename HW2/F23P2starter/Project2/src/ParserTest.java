import student.TestCase;

/**
 * This class contains unit tests for Parser
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */
public class ParserTest extends TestCase {
    /**
     * Test the getInstruction function
     */
    public void testGetInstruction() {

        Parser test = new Parser();

        assertEquals(11, test.getInstruction("insert 10000"));
        assertEquals(21, test.getInstruction("search ID 10000"));
        assertEquals(22, test.getInstruction("search cost 10000"));
        assertEquals(23, test.getInstruction("search date 0 1"));
        assertEquals(24, test.getInstruction("search keyword awr"));
        assertEquals(25, test.getInstruction("search location 1 2 1"));
        assertEquals(31, test.getInstruction("delete 1"));
        assertEquals(41, test.getInstruction("print ID 1"));
        assertEquals(42, test.getInstruction("print date 1"));
        assertEquals(43, test.getInstruction("print keyword awd"));
        assertEquals(44, test.getInstruction("print location"));
        assertEquals(45, test.getInstruction("print cost 123"));

    }
    
}