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
		assertEquals(1, test.values[1].getSize());
		test.keys[1] = 2;
		test.hashing(2, new Handle(2, 1, 2), test.values);
		assertEquals(1, test.values[2].getSize());	
		test.keys[2] = 3;
		test.hashing(3, new Handle(3, 1, 3), test.values);
		assertEquals(1, test.values[3].getSize());
		test.keys[3] = 5;
		test.hashing(5, new Handle(4, 1, 5), test.values);
		assertEquals(1, test.values[5].getSize());
		test.keys[4] = 10;
		test.hashing(10, new Handle(5, 1, 10), test.values);
		assertEquals(1, test.values[0].getSize());
						
	}
	
	public void testInsert() {
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(512);
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
		Seminar s1 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 1, s1);
		assertEquals(1, test.lastElementIndex);
		assertEquals(1, test.keys[0]);
		assertEquals(32, test.values[1].getSize());
		id = 2;
		Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 2, s2);
		assertEquals(2, test.lastElementIndex);
		assertEquals(2, test.keys[1]);
		assertEquals(32, test.values[2].getSize());	
		id = 3;
		Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 3, s3);
		assertEquals(3, test.lastElementIndex);
		assertEquals(3, test.keys[2]);
		assertEquals(32, test.values[3].getSize());
		id = 5;
		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 5, s4);
		assertEquals(4, test.lastElementIndex);
		assertEquals(5, test.keys[3]);
		assertEquals(32, test.values[5].getSize());
		id = 10;
		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 10, s5);
		assertEquals(16, test.size);
		test.insert(semManager, 10, s5);
	}

	public void testDelete() {
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(512);
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
		Seminar s1 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 1, s1);
		test.delete(semManager, 1);
		test.insert(semManager, 1, s1);
		id = 2;
		Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 2, s2);
		id = 3;
		Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 3, s3);
		id = 5;
		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 5, s4);
		id = 10;
		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 10, s5);
		test.delete(semManager, 2);
		test.delete(semManager, 3);
		test.insert(semManager, 3, s3);
		test.delete(semManager, 4);
	}

	public void testSearch() {
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(512);
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
		Seminar s1 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 1, s1);
		id = 2;
		Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 2, s2);
		id = 3;
		Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 3, s3);
		id = 5;
		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 5, s4);
		id = 10;
		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 10, s5);
		assertEquals(false, test.search(semManager, -1));
		assertEquals(true, test.search(semManager, 2));
		assertEquals(true, test.search(semManager, 3));
		assertEquals(true, test.search(semManager, 5));
		assertEquals(true, test.search(semManager, 10));
	}
	
	public void testPrint() {
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(512);
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
		Seminar s1 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 1, s1);
		id = 2;
		Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 2, s2);
		id = 3;
		Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 3, s3);
		id = 5;
		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		test.insert(semManager, 5, s4);
		id = 10;
		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
		int[] testArr = test.printHashtable();
		test.delete(semManager, 10);
		assertEquals(1, testArr[0]);
		assertEquals(2, testArr[1]);
		assertEquals(3, testArr[2]);
		assertEquals(5, testArr[3]);
		assertEquals(0, testArr[4]);
		assertEquals(false, test.search(semManager, 10));
	}

}


