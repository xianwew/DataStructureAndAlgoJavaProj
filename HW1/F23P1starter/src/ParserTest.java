import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test() {
	
		Parser test = new Parser();
		String[] args = new String[5];
		assertEquals(null,test.initializeComponents(null));
		
	}

}
