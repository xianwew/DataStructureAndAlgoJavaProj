import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import student.TestCase;

public class ParserTest {

	public void testInitializeComponents() {
	
		Parser test = new Parser();
		String[] args = {"Java", "SemManager", "-1", "0", "0"};
//		assertEquals(null, test.initializeComponents(args));
		
		assertEquals(1,test.getInstruction("insert 10000"));
		assertEquals(2,test.getInstruction("search 10000"));
		assertEquals(3,test.getInstruction("print  hashtable"));
		assertEquals(4,test.getInstruction("print  blocks"));
		assertEquals(5,test.getInstruction("delete  10000"));
	}

}
