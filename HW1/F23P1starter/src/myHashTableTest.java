import static org.junit.Assert.*;
import student.TestCase;

public class myHashTableTest {

	public void testHashingCalculation() {
	
		MyHashTable test = new MyHashTable(1024);  	
		assertEquals(1, test.calculateFirstHashing(1, 1024));  	
		assertEquals(0, test.calculateFirstHashing(1, -1)); 
		assertEquals(1, test.calculateSecondHashing(1, 2));
		assertEquals(1, test.calculateSecondHashing(2, -2));
		assertEquals(1, test.calculateSecondHashing(-2, 2));
		assertEquals(1, test.calculateSecondHashing(-2, -2));
		assertEquals(1, test.calculateSecondHashing(0, -2));
		assertEquals(-3, test.calculateSecondHashing(16, -8));
	}
	
	public void testHashing() {
		MyHashTable test = new MyHashTable(8);
		test.keys[0] = 1;	
		test.hashing(1, new Handle(1, 1, 1), test.values);
//		assertEquals(1, test.lastElementIndex);
//		assertEquals(1, test.keys[0]);
		assertEquals(1, test.values[1].getSize());
		test.keys[1] = 2;
		test.hashing(2, new Handle(2, 1, 2), test.values);
//		assertEquals(2, test.lastElementIndex);
//		assertEquals(2, test.keys[1]);
		assertEquals(1, test.values[2].getSize());	
		test.keys[2] = 3;
		test.hashing(3, new Handle(3, 1, 3), test.values);
//		assertEquals(3, test.lastElementIndex);
//		assertEquals(3, test.keys[2]);
		assertEquals(1, test.values[3].getSize());
		test.keys[3] = 5;
		test.hashing(5, new Handle(4, 1, 5), test.values);
//		assertEquals(4, test.lastElementIndex);
//		assertEquals(5, test.keys[3]);
		assertEquals(1, test.values[5].getSize());
		test.keys[4] = 10;
		test.hashing(10, new Handle(5, 1, 10), test.values);
//		assertEquals(5, test.lastElementIndex);
//		assertEquals(10, test.keys[4]);
		assertEquals(1, test.values[0].getSize());
						
	}
	
	public void testinsert() {
		Handle handle = new Handle(1, 1, 1);
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(512);
		//test.hashing(1, new Handle(1, 1, 1), test.values);
		short i = 1;
		int id = 1;
        String title = "";
        String dateTime = "";
        int length = -1;
        short x = -1;
        short y = -1;
        int cost = -1;
        String desc = "";
        String[] keywordList = {};
		String[] kl = new String[] {"java","SemManager","-1","0","10"};
		Seminar s1 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 1, s1);
		assertEquals(1, test.lastElementIndex);
		assertEquals(1, test.keys[0]);
		assertEquals(32, test.values[1].getSize());
		//test.hashing(2, new Handle(2, 1, 2), test.values);
		id = 2;
		Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 2, s2);
		assertEquals(2, test.lastElementIndex);
		assertEquals(2, test.keys[1]);
		assertEquals(32, test.values[2].getSize());	
		//test.hashing(3, new Handle(3, 1, 3), test.values);
		id = 3;
		Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 3, s3);
		assertEquals(3, test.lastElementIndex);
		assertEquals(3, test.keys[2]);
		assertEquals(32, test.values[3].getSize());
		//test.hashing(5, new Handle(4, 1, 5), test.values);
		id = 5;
		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 5, s4);
		assertEquals(4, test.lastElementIndex);
		assertEquals(5, test.keys[3]);
		assertEquals(32, test.values[5].getSize());
//		test.hashing(10, new Handle(5, 1, 10), test.values);
		id = 10;
		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 10, s5);
//		assertEquals(5, test.lastElementIndex);
//		assertEquals(10, test.keys[4]);
//		assertEquals(1, test.values[10].getSize());
		
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


