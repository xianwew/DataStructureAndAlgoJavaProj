import static org.junit.Assert.*;
import student.TestCase;

public class myHashTableTest {

	public void testHashingCalculation() {
	
		MyHashTable test = new MyHashTable(1024);
		assertEquals(1, test.calculateFirstHashing(1, 1024));
		assertEquals(1, test.calculateSecondHashing(1, 2));
	}
	
	public void testHashing() {
		Handle handle = new Handle(-1, -1, -1);
		MyHashTable test = new MyHashTable(1024);
		Handle[] insertArr = new Handle[256];
		//assertTrue(test.hashing(1, handle, insertArr, false));
//		assertEquals(38，insertArr[o].getKey()); 
//		assertEquals(1, test.calculateSecondHashing(1, 2));
//		assertEquals(handle，insertArr[o]);
	}
	
	public void testInsert() {
//		Handle handle = new Handle(-1, -1, -1);
//		MyHashTable test = new MyHashTable(1024);
//		Handle[] insertArr = new Handle[256];
//		assertTrue(test.hashing(1, handle, insertArr, false));
//		assertEquals(1, test.calculateSecondHashing(1, 2));
//		assertEquals(1, test.calculateSecondHashing(1, 2));
	}

}


