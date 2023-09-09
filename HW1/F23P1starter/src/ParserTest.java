import static org.junit.Assert.*;

import java.io.File;
import java.nio.ByteBuffer;
import student.TestCase;

public class ParserTest {

	public void testInitializeComponents() {
	
		Parser test = new Parser();

        Object[] result0=test.initializeComponents(new String[] {"java","SemManager","16","0","10"});
        Object[] result1=test.initializeComponents(new String[] {"java","SemManager","0","16","10"});
        
        //assertTrue(result0);
        
//        assertNull(result0);
//        assertNull(result1);
		assertEquals(1,test.getInstruction("insert 10000"));
		assertEquals(2,test.getInstruction("search 10000"));
		assertEquals(3,test.getInstruction("print hashtable"));
		assertEquals(4,test.getInstruction("print blocks"));
		assertEquals(0,test.getInstruction("print"));
		assertEquals(5,test.getInstruction("delete 10000"));
		assertEquals(0,test.getInstruction(""));

		
		assertEquals(16,test.initializeComponents(null).length);
		assertEquals(11,test.initializeComponents(new String[] {"java","SemManager","hello","0","10"}).length);
		assertEquals(12,test.initializeComponents(new String[] {"java","SemManager","-1","0","10"}).length);
		assertEquals(12,test.initializeComponents(new String[] {"java","SemManager","3","0","10"}).length);
		assertEquals(13,test.initializeComponents(new String[] {"java","SemManager","16","hello","10"}).length);
		assertEquals(14,test.initializeComponents(new String[] {"java","SemManager","16","-1","10"}).length);
		assertEquals(14,test.initializeComponents(new String[] {"java","SemManager","16","3","10"}).length);
		assertEquals(3,test.initializeComponents(new String[] {"java","SemManager","16","16","10"}).length);
        
		Parser test1 = new Parser();
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(1024);
		String[] input = new String[]{"java","SemManager","16","16","src/testParser1.txt"};
		Object[] components = test1.initializeComponents(input);  
		semManager.memoryPool = (byte[]) components[0];
	    semManager.initializeSemManger(semManager.memoryPool.length);
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
		WorldDataBase worldDataBase = new WorldDataBase(semManager, hashTable);
		test1.processSeminars(commandFile1, worldDataBase);
		assertEquals("awdasd", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile2, worldDataBase);
		assertEquals("", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile3, worldDataBase);
		assertEquals("abcdefg", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile4, worldDataBase);
		assertEquals("awdasd", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile5, worldDataBase);
		assertEquals("insert 1", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile6, worldDataBase);
		assertEquals("sadawefgaw", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile7, worldDataBase);
		assertEquals("delete 1", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile8, worldDataBase);
		assertEquals("search 1", test1.data);
		test1.data = "";
		test1.processSeminars(commandFile9, worldDataBase);
		assertEquals("0610051600 90 10 10 awd", test1.data);
		
		
//		if(commandFile1 != null) {
//			System.out.println("abc");
//		}
//		else {
//			System.out.println("cde");
//		}
	}

	    
}