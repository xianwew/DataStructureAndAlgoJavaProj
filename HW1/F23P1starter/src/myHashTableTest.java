import static org.junit.Assert.*;
import student.TestCase;

public class myHashTableTest {

	public void testHashingCalculation() {
	
		MyHashTable test = new MyHashTable(1024);  	
		assertEquals(1, test.calculateFirstHashing(1, 1024));  	
		assertEquals(0, test.calculateFirstHashing(1, -1)); 
//		assertEquals(-1, test.calculateFirstHashing(1, -2)); 
		assertEquals(1, test.calculateSecondHashing(1, 2));
		assertEquals(1, test.calculateSecondHashing(2, -2));
		assertEquals(1, test.calculateSecondHashing(-2, 2));
		assertEquals(1, test.calculateSecondHashing(-2, -2));
		assertEquals(1, test.calculateSecondHashing(0, -2));
		assertEquals(-3, test.calculateSecondHashing(16, -8));
	}
	
	public void testHashing() {
		Handle handle = new Handle(1, 1, 1);
		MyHashTable test = new MyHashTable(4);
		test.hashing(1, new Handle(1, 1, 1), test.values, false);
		assertEquals(1, test.lastElementIndex);
		assertEquals(1, test.keys[0]);
		assertEquals(1, test.values[1].getSize());
		test.hashing(2, new Handle(2, 1, 2), test.values, false);
		assertEquals(2, test.lastElementIndex);
		assertEquals(2, test.keys[1]);
		assertEquals(1, test.values[2].getSize());		
//		test.reHash();
//		test.hashing(10, new Handle(10, 1, 10), test.values, false);
//		assertEquals(3, test.lastElementIndex);
//		assertEquals(10, test.keys[2]);
//		assertEquals(1, test.values[5].getSize());
//		test.hashing(5, new Handle(5, 1, 5), test.values, false);
//		assertEquals(4, test.lastElementIndex);
//		assertEquals(5, test.keys[3]);
//		assertEquals(1, test.values[6].getSize());
		
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


