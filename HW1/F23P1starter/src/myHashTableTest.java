/**
 * This class contains a set of unit tests for the `MyHashTable` class.
 * @author jiren&xianwei
 * @version September 2023, updated September 2023
 */

import student.TestCase;
public class myHashTableTest extends TestCase {
    /**
     * Test the `calculateFirstHashing` and `calculateSecondHashing` methods for
     * correct hashing calculations.
     */
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

    /**
     * Test the `hashing` method for inserting elements into the hash table.
     */
    public void testHashing() {
        MyHashTable test = new MyHashTable(16);
        test.setKeysElement(0, 32);
        test.hashing(32, new Handle(1, 1, 32), test.getValues());
        assertEquals(1, test.getValues()[0].getSize());
        test.setKeysElement(1, 32);
        test.hashing(96, new Handle(2, 1, 96), test.getValues());
        assertEquals(1, test.getValues()[13].getSize());
        test.setKeysElement(2, 160);
        test.hashing(160, new Handle(3, 1, 160), test.getValues());
        assertEquals(1, test.getValues()[5].getSize());
        test.setKeysElement(3, 224);
        test.hashing(224, new Handle(4, 1, 224), test.getValues());
        assertEquals(1, test.getValues()[10].getSize());
        test.setKeysElement(4, 72);
        test.hashing(72, new Handle(5, 1, 72), test.getValues());
        assertEquals(1, test.getValues()[8].getSize());
        test.setKeysElement(3, 0);
        test.getValues()[10].setStartIndex(-1);
        test.hashing(224, new Handle(4, 1, 224), test.getValues());
        assertEquals(1, test.getValues()[10].getSize());

    }

    /**
     * Test the `insert` method for inserting elements into the hash table.
     */
    public void testInsert() {
        MyHashTable test = new MyHashTable(16);
        MemManager memManager = new MemManager();
        memManager.initializeMemManger(32);
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
        int[] array = new int[] { 32, 96, 160, 224, 72, 83, 99, 115, 131, 147, 163, 179, 195, 211, 227, 243 };
        for (int k = 0; k < array.length; k++) {
            Seminar s = new Seminar(array[k], title, dateTime, length, x, y, cost, keywordList, desc);
            test.insert(memManager, array[k], s);
            assertEquals(k + 1, test.getLastElementIndex());
//    		assertEquals(, test.keys[array[k]-1]);
            int t = 0;
            for (Handle h : test.getValues()) {
                if (h != null && h.getKey() == array[k]) {
                    t = array[k];
                    break;
                }
            }
//    		assertEquals(1, test.values[t].getSize());	
        }
        assertEquals(32, test.getSize());
        assertEquals(32, test.getKeys().length);
        assertEquals(32, test.getValues().length);
        for (int k = 0; k < array.length; k++) {
            Seminar s = new Seminar(array[k], title, dateTime, length, x, y, cost, keywordList, desc);
            assertEquals(false, test.insert(memManager, array[k], s));
        }
        Seminar s = new Seminar(147, title, dateTime, length, x, y, cost, keywordList, desc);
        test.delete(memManager, 147);
        test.insert(memManager, 147, s);
        test.reHash();
        assertEquals(64, test.getSize());
        assertEquals(64, test.getKeys().length);
        assertEquals(64, test.getValues().length);
    }

    /**
     * Test the `delete` method for removing elements from the hash table.
     */
    public void testDelete() {
        MyHashTable test = new MyHashTable(16);
        MemManager memManager = new MemManager();
        memManager.initializeMemManger(32);
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
        test.insert(memManager, 1, s1);
        test.delete(memManager, 1);
        test.insert(memManager, 1, s1);
        id = 32;
        Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, id, s2);
        id = 96;
        Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, id, s3);
        id = 160;
        Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, id, s4);
        id = 224;
        Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, id, s5);
        id = 72;
        Seminar s6 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, id, s6);

        test.delete(memManager, 72);
        test.delete(memManager, 224);
        test.delete(memManager, 160);
        test.delete(memManager, 96);
        test.delete(memManager, 32);

        for (int k = 1; k <= 50; k++) {
            Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
            test.insert(memManager, k, s);
        }
        for (int k = 200; k > 50; k--) {
            assertEquals(false, test.delete(memManager, k));
        }
        for (int k = 1; k <= 50; k++) {
            assertEquals(true, test.delete(memManager, k));
        }
        for (int k = 1; k <= 50; k++) {
            Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
            test.insert(memManager, k, s);
        }
        for (int k = 200; k > 50; k--) {
            assertEquals(false, test.delete(memManager, k));
        }
        for (int k = 1; k <= 50; k++) {
            assertEquals(true, test.delete(memManager, k));
        }
    }

    /**
     * Test the `search` method for searching elements in the hash table.
     */
    public void testSearch() {
        MyHashTable test = new MyHashTable(16);
        MemManager memManager = new MemManager();
        memManager.initializeMemManger(32);
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
        int[] array = new int[] { 32, 96, 160, 224, 72, 83, 99, 115, 131, 147, 163, 179, 195, 211, 227, 243 };
        for (int k = 0; k < array.length; k++) {
            Seminar s = new Seminar(array[k], title, dateTime, length, x, y, cost, keywordList, desc);
            test.insert(memManager, array[k], s);

        }
        assertEquals(false, test.search(memManager, -1));
        assertEquals(false, test.search(memManager, 2));
        assertEquals(false, test.search(memManager, 3));
        assertEquals(false, test.search(memManager, 5));
        assertEquals(false, test.search(memManager, 10));
        test.delete(memManager, 10);
        assertEquals(false, test.search(memManager, 10));
        test.delete(memManager, 5);
        assertEquals(false, test.search(memManager, 5));
        test.delete(memManager, 3);
        assertEquals(false, test.search(memManager, 3));
        test.delete(memManager, 2);
        assertEquals(false, test.search(memManager, 2));
        for (int k = 0; k < array.length; k++) {
            if (array[k] != 10 && array[k] != 2 && array[k] != 3 && array[k] != 5) {
                assertEquals(true, test.search(memManager, array[k]));
            } else {
                assertEquals(false, test.search(memManager, array[k]));
            }
        }
    }

    /**
     * Test the `printHashtable` method for printing the contents of the hash table.
     */
    public void testPrint() {
        MyHashTable test = new MyHashTable(8);
        MemManager memManager = new MemManager();
        memManager.initializeMemManger(512);
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
        test.insert(memManager, 1, s1);
        id = 2;
        Seminar s2 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, 2, s2);
        id = 3;
        Seminar s3 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, 3, s3);
        id = 5;
        Seminar s4 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        test.insert(memManager, 5, s4);
        id = 10;
        Seminar s5 = new Seminar(id, title, dateTime, length, x, y, cost, keywordList, desc);
        int[] testArr = test.printHashtable();
        test.delete(memManager, 10);
        assertEquals(1, testArr[0]);
        assertEquals(2, testArr[1]);
        assertEquals(3, testArr[2]);
        assertEquals(5, testArr[3]);
        assertEquals(0, testArr[4]);
        assertEquals(false, test.search(memManager, 10));
    }

}
