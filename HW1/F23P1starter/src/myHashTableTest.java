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
		semManager.initializeSemManger(32);
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
        for(int k= 1; k <= 100; k++) {
    		Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
    		test.insert(semManager, k, s);
    		assertEquals(k, test.lastElementIndex);
    		assertEquals(k, test.keys[k-1]);
    		int t = 0;
    		for(Handle h:test.values) {
    			if(h != null && h.getKey()==k) {
    				t = k;
    				break;
    			}
    		}
    		assertEquals(32, test.values[t].getSize());	
    		}
		assertEquals(256,test.size);
		assertEquals(256, test.keys.length);
		assertEquals(256, test.values.length);	
		 for(int k= 1; k <= 100; k++) {
	    		Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
	    		
	    		assertEquals(false,test.insert(semManager, k, s));
	    		}
	}

	public void testDelete() {
		MyHashTable test = new MyHashTable(8);
		SemManager semManager = new SemManager();
		semManager.initializeSemManger(32);
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
   		id = 17;
   		Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
   		test.insert(semManager, 17, s4);
   		id = 10;
   		Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
   		test.insert(semManager, 10, s5);
   		id = 11;
   		Seminar s6 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
   		test.insert(semManager, 11, s6);
   			
   		test.delete(semManager, 17);
           test.insert(semManager, 10, s5);
           test.delete(semManager, 2);
           test.delete(semManager, 3);  	
           test.insert(semManager, 3, s3); 	
           test.delete(semManager, -1); 	
           test.delete(semManager, 10);
           test.insert(semManager, 17, s4);
   		test.delete(semManager, 17);
        for(int k= 1; k <= 100; k++) {
    		Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
    		test.insert(semManager, k, s);	
    		}
        for(int k = 200; k>50; k--) {
        	if(k <= 100) {
        		assertEquals(true,test.delete(semManager, k));
        	}
        	else {
        		assertEquals(false,test.delete(semManager, k));
        	}
        	
        }
        for(int k = 1; k <= 50; k++) {
        	if(k <= 100) {
        		assertEquals(true,test.delete(semManager, k));
        	}
        	else {
        		assertEquals(false,test.delete(semManager, k));
        	}
        	
        }
        for(int k= 1; k <= 100; k++) {
    		Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
    		test.insert(semManager, k, s);	
    		}
        for(int k = 200; k>50; k--) {
        	if(k <= 100) {
        		assertEquals(true,test.delete(semManager, k));
        	}
        	else {
        		assertEquals(false,test.delete(semManager, k));
        	}
        	
        }
        for(int k = 1; k <= 50; k++) {
        	if(k <= 100) {
        		assertEquals(true,test.delete(semManager, k));
        	}
        	else {
        		assertEquals(false,test.delete(semManager, k));
        	}
        	
        }


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
		test.delete(semManager, 10);
		assertEquals(false, test.search(semManager, 10));
		test.delete(semManager, 5);
		assertEquals(false, test.search(semManager, 5));
		test.delete(semManager, 3);
		assertEquals(false, test.search(semManager, 3));
		test.delete(semManager, 2);
		assertEquals(false, test.search(semManager, 2));
		test.delete(semManager, 2);
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


