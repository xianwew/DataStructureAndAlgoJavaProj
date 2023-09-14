import static org.junit.Assert.*;

import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class MemManagerTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testPrintMemManager() {
    	MemManager memManager2 = new MemManager();
    	memManager2.initializeMemManger(512);
    	memManager2.dummy.setNext(null);
    	assertEquals(false, memManager2.printMemManager());
    	
    	MemManager memManager = new MemManager();
		MyHashTable myHashTable = new MyHashTable(4);
		memManager.initializeMemManger(512);
        String title = "";
        String dateTime = "";
        int length = -1;
        short x = 10;
        short y = 10;
        int cost = -1;
        int id = 1;
        String desc = "This seminar will present an overview of HCI research at VT";
        String[] keywordList = {};
        FreeList tmp = memManager.dummy.getNext();
        memManager.dummy.setNext(null);
        assertEquals(false, memManager.printMemManager());
        memManager.dummy.setNext(tmp);	
		Seminar s1 = new Seminar(1, "Overview of HCI Research at VT", "0610051600", 90, x, y, 45, new String[] { "HCI", "Computer_Science", "VT", "Virginia_Tech"}, desc);		
        x = 20;
        y = 10;
        desc = "Introduction to   bioinformatics and computation biology";
		Seminar s2 = new Seminar(2, "Computational Biology and Bioinformatics in CS at Virginia Tech", "0610071600", 60, x, y, 30, new String[] { "Bioinformatics", "computation_biology", "Biology", "Computer_Science", "VT", "Virginia_Tech"}, desc);
		x = 30;
		y = 10;
        desc = "Seminar about the      Computing systems research at      VT";
		Seminar s3 = new Seminar(3, "Computing Systems Research at VT", "0701250830", 30, x, y, cost, new String[] { "high_performance_computing", "grids", "VT", "computer", "science"}, desc);
		x = 0;
		y = 0;
        desc = "Learn what kind of    research is done on HPC  and CSE at VT";
		Seminar s4 = new Seminar(10, "Overview of HPC and CSE Research at VT", "0703301125", 35, x, y, 25, new String[] { "HPC", "CSE", "Computer_Science"}, desc);
		x = 20;
		y = 10;
        desc = "Introduction to bioinformatics and lots of computation biology";
		Seminar s5 = new Seminar(2, "Much More Computational Biology and Bioinformatics in CS at Virginia Tech", "0610071600", 60, x, y, 30, new String[] { "Bioinformatics", "computation_biology", "Biology", "Computer_Science", "VT", "Virginia_Tech"}, desc);
		Seminar s6 = new Seminar(4, "Much More Computational Biology and Bioinformatics in CS at Virginia Tech", "0610071600", 60, x, y, 30, new String[] { "Bioinformatics", "computation_biology", "Biology", "Computer_Science", "VT", "Virginia_Tech"}, desc);
		myHashTable.insert(memManager, 1, s1); 	
		assertEquals(true, memManager.printMemManager());
		myHashTable.insert(memManager, 2, s2); 	
		assertEquals(false, memManager.printMemManager());
		myHashTable.insert(memManager, 3, s3); 	
		assertEquals(true, memManager.printMemManager());
		myHashTable.insert(memManager, 10, s4); 	
		assertEquals(false, memManager.printMemManager());
		myHashTable.delete(memManager, 2);
		assertEquals(true, memManager.printMemManager());
		myHashTable.insert(memManager, 2, s5); 
		assertEquals(true, memManager.printMemManager());
		myHashTable.insert(memManager, 4, s6); 
		assertEquals(true, memManager.printMemManager());
		myHashTable.delete(memManager, 10);
		assertEquals(true, memManager.printMemManager());
		myHashTable.delete(memManager, 2);
		assertEquals(true, memManager.printMemManager());
    	
    	MemManager memManager3 = new MemManager();
    	memManager3.initializeMemManger(0);
    	FreeList tmp1 = new FreeList(64, 0);
    	FreeList tmp2 = new FreeList(32, 64);
    	FreeList tmp3 = new FreeList(64, 96);
    	FreeList tmp4 = new FreeList(128, 128);
    	assertEquals(true, memManager3.printMemManager());
    	memManager3.dummy.setNext(tmp1);
    	assertEquals(true, memManager3.printMemManager());
    	tmp1.setNext(tmp2);
    	assertEquals(true, memManager3.printMemManager());
    	tmp2.setNext(tmp3);
    	assertEquals(true, memManager3.printMemManager());
    	tmp3.setNext(tmp4);
    	assertEquals(true, memManager3.printMemManager());
    }
    
    
    public void testdoublesize(){
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(0);
		
    	assertEquals(0,memManager.doubleSize().getVal());
    	assertEquals(0,memManager.size);
    	
    	MemManager memManager2 = new MemManager();
		memManager2.initializeMemManger(1);
		
    	assertEquals(1,memManager2.doubleSize().getNext().getVal());
    	assertEquals(2,memManager2.size);
    	
    	MemManager memManager3 = new MemManager();
		memManager3.initializeMemManger(2);
		
    	assertEquals(2,memManager3.doubleSize().getNext().getVal());
    	assertEquals(4,memManager3.size);
    	assertEquals(2,memManager3.doubleSizeTest);
    	MemManager memManager4 = new MemManager();
		memManager4.initializeMemManger(2);
		memManager4.dummy = null;
    	assertEquals(null,memManager4.doubleSize());
    	
    }
    
    public void testFindSpaceAvailable() {
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(1);
		memManager.dummy = null;
		assertEquals(null,memManager.FindSpaceAvailable(0));
		
		MemManager memManager2 = new MemManager();
		memManager2.initializeMemManger(1);
		assertEquals(null,memManager2.FindSpaceAvailable(3));
		
		MemManager memManager3 = new MemManager();
		memManager3.initializeMemManger(1);
		memManager3.dummy.getNext().setVal(Integer.MAX_VALUE);
		assertEquals(null,memManager3.FindSpaceAvailable(3));
		
    }
    
    public void testgetNearestPowerOfTwo() {
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(0);
		
		assertEquals(2,memManager.getNearestPowerOfTwo(2));
		assertEquals(4,memManager.getNearestPowerOfTwo(3));
		assertEquals(1,memManager.getNearestPowerOfTwo(0));
    }
    
    public void testsplitMemoryPool() {
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(2);
		assertEquals(null,memManager.splitMemoryPool(2, memManager.dummy.getNext()).getNext().getNext());
		
//		memManager.dummy.getNext().setNext(new FreeList(1,1));
//		assertEquals(1,memManager.splitMemoryPool(2, memManager.dummy.getNext()).getVal());
		
    }
    
    public void testdelete() {
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(2);
		assertEquals(false,memManager.delete(new Handle(-1,-1,-1)));
		
		memManager.delete(new Handle(1,1,1));
		memManager.dummy.setIndex(10);
		assertEquals(true,memManager.delete(new Handle(1,1,1)));
		memManager.dummy.setPrev(new FreeList(-1,-1));
		memManager.delete(new Handle(1,1,1));
		
		memManager.insert(new byte[1], 2);
		assertEquals(true,memManager.delete(new Handle(1,1,2)));
		
		
    }
    
    public void testdetectMerge() {
    	MemManager memManager = new MemManager();
		memManager.initializeMemManger(2);
		assertEquals(2,memManager.detectMerge().getVal());
		memManager.dummy.getNext().setVal(1);
		memManager.dummy.getNext().setIndex(0);
		memManager.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(2,memManager.detectMerge().getVal());
		
		MemManager memManager2 = new MemManager();
		memManager2.initializeMemManger(2);
		assertEquals(2,memManager2.detectMerge().getVal());
		memManager2.dummy.getNext().setVal(2);
		memManager2.dummy.getNext().setIndex(0);
		memManager2.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(1,memManager2.detectMerge().getVal());
		
		MemManager memManager3 = new MemManager();
		memManager3.initializeMemManger(2);
		assertEquals(1,memManager2.detectMerge().getVal());
		memManager3.dummy.getNext().setVal(1);
		memManager3.dummy.getNext().setIndex(-1);
		memManager3.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(1,memManager3.detectMerge().getVal());
		
		MemManager memManager4 = new MemManager();
		memManager4.initializeMemManger(2);
		assertEquals(1,memManager2.detectMerge().getVal());
		memManager4.dummy.getNext().setVal(1);
		memManager4.dummy.getNext().setIndex(0);
		FreeList tmp1 = new FreeList(1,1);
		FreeList tmp2 = new FreeList(1,2);
		tmp1.setPrev(memManager4.dummy.getNext());
		tmp2.setPrev(tmp1);
		memManager4.dummy.getNext().setNext(tmp1);
		memManager4.dummy.getNext().getNext().setNext(tmp2);
		assertEquals(1,memManager4.detectMerge().getVal());
		
    }
    
   
}

