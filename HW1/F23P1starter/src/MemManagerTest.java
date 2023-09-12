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
    public void testMInitx()
    {
        MemManager sem = new MemManager();
        assertNotNull(sem);
        SemManager.main(null);
    }
    
    public void testPrintMemManager() {
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
       
		for (int k = 1 ; k<=100; k++ ) {
			 Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
			 
			 try {
				 memManager.insert(s.serialize(), k); 
				 if(k == 1) {
					 assertEquals(false,memManager.printMemManager());
				 }
				 if (k == 3) {
					 assertEquals(true,memManager.printMemManager());
				 }
			 }
			 catch(Exception e) {}
		}	
		
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

