import static org.junit.Assert.*;

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
        
	}

	    
}
