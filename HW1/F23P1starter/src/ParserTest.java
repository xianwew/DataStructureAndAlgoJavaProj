import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import student.TestCase;

public class ParserTest {

	public void testInitializeComponents() {
	
		Parser test = new Parser();
		String[] args = {"Java", "SemManager", "-1", "0", "0"};
		assertEquals(null, test.initializeComponents(args));
	}

}
