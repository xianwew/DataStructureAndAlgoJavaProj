import java.io.File;
import student.TestCase;
/**
 * This class contains unit tests for the `Parser` class, specifically testing
 * its initialization and command processing methods.
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */
public class ParserTest extends TestCase {
    /**
     * Test the `initializeComponents` method for correct component 
     * initialization.
     */
    public void testInitializeComponents() {

        Parser test = new Parser();

        Object[] result0 = test.initializeComponents(new String[] 
            { "16", "0", "10" });
        Object[] result1 = test.initializeComponents(new String[] 
            { "0", "16", "10" });

        assertEquals(1, test.getInstruction("insert 10000"));
        assertEquals(2, test.getInstruction("search 10000"));
        assertEquals(3, test.getInstruction("print hashtable"));
        assertEquals(4, test.getInstruction("print blocks"));
        assertEquals(0, test.getInstruction("print"));
        assertEquals(5, test.getInstruction("delete 10000"));
        assertEquals(0, test.getInstruction(""));

        assertEquals(16, test.initializeComponents(null).length);
        assertEquals(11, test.initializeComponents(new String[] 
            { "hello", "0", "10" }).length);
        assertEquals(12, test.initializeComponents(new String[] 
            { "0", "0", "10" }).length);
        assertEquals(12, test.initializeComponents(new String[] 
            { "3", "0", "10" }).length);
        assertEquals(13, test.initializeComponents(new String[] 
            { "16", "hello", "10" }).length);
        assertEquals(14, test.initializeComponents(new String[] 
            { "16", "0", "10" }).length);
        assertEquals(14, test.initializeComponents(new String[] 
            { "16", "3", "10" }).length);
        assertEquals(3, test.initializeComponents(new String[] 
            { "16", "16", "10" }).length);

        Parser test1 = new Parser();
        MemManager memManager = new MemManager();
        memManager.initializeMemManger(1024);
        String[] input = new String[] { "16", "16", "src/testParser1.txt" };
        Object[] components = test1.initializeComponents(input);
        memManager.setMemoryPool((byte[]) components[0]);
        memManager.initializeMemManger(16);
        MyHashTable hashTable = (MyHashTable) components[1];
        File commandFile1 = (File) components[2];
        File commandFile2 = new File("src/testParser0.txt");
        File commandFile3 = new File("src/testParser2.txt");
        File commandFile4 = new File("src/testParser3.txt");
        File commandFile5 = new File("src/testParser4.txt");
        File commandFile6 = new File("src/testParser5.txt");
        File commandFile7 = new File("src/testParser6.txt");
        File commandFile8 = new File("src/testParser7.txt");
        File commandFile9 = new File("src/testParser8.txt");
        WorldDataBase worldDataBase = new WorldDataBase(memManager, 
                hashTable);
        test1.processSeminars(commandFile1, worldDataBase);
        assertEquals("awdasd", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile2, worldDataBase);
        assertEquals("", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile3, worldDataBase);
        assertEquals("abcdefg", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile4, worldDataBase);
        assertEquals("awdasd", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile5, worldDataBase);
        assertEquals("insert 1", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile6, worldDataBase);
        assertEquals("print blocks", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile7, worldDataBase);
        assertEquals("delete 1", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile8, worldDataBase);
        assertEquals("search 1", test1.getData());
        test1.setData("");
        test1.processSeminars(commandFile9, worldDataBase);
        assertEquals("search 1", test1.getData());

    }

}
