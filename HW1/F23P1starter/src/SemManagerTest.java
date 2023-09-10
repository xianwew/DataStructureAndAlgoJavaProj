import static org.junit.Assert.*;

import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class SemManagerTest extends TestCase {
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
        SemManager sem = new SemManager();
        assertNotNull(sem);
        SemManager.main(null);
    }
    
    public void testPrintSemManager() {
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
       
		for (int k = 1 ; k<=100; k++ ) {
			 Seminar s = new Seminar(k, title, dateTime, length, x, y, cost, keywordList, desc);
			 
			 try {
				 semManager.insert(s.serialize(), k); 
				 if(k == 1) {
					 assertEquals(false,semManager.printSemManager());
				 }
				 if (k == 3) {
					 assertEquals(true,semManager.printSemManager());
				 }
			 }
			 catch(Exception e) {}
		}	
		
    }
    
    
    public void testdoublesize(){
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(0);
		
    	assertEquals(0,semManager.doubleSize().getVal());
    	assertEquals(0,semManager.size);
    	
    	SemManager semManager2 = new SemManager();
		semManager2.initializeSemManger(1);
		
    	assertEquals(1,semManager2.doubleSize().getNext().getVal());
    	assertEquals(2,semManager2.size);
    	
    	SemManager semManager3 = new SemManager();
		semManager3.initializeSemManger(2);
		
    	assertEquals(2,semManager3.doubleSize().getNext().getVal());
    	assertEquals(4,semManager3.size);
    	assertEquals(2,semManager3.doubleSizeTest);
    	SemManager semManager4 = new SemManager();
		semManager4.initializeSemManger(2);
		semManager4.dummy = null;
    	assertEquals(null,semManager4.doubleSize());
    	
    }
    
    public void testFindSpaceAvailable() {
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(1);
		semManager.dummy = null;
		assertEquals(null,semManager.FindSpaceAvailable(0));
		
		SemManager semManager2 = new SemManager();
		semManager2.initializeSemManger(1);
		assertEquals(null,semManager2.FindSpaceAvailable(3));
		
		SemManager semManager3 = new SemManager();
		semManager3.initializeSemManger(1);
		semManager3.dummy.getNext().setVal(Integer.MAX_VALUE);
		assertEquals(null,semManager3.FindSpaceAvailable(3));
		
    }
    
    public void testgetNearestPowerOfTwo() {
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(0);
		
		assertEquals(2,semManager.getNearestPowerOfTwo(2));
		assertEquals(4,semManager.getNearestPowerOfTwo(3));
		assertEquals(1,semManager.getNearestPowerOfTwo(0));
    }
    
    public void testsplitMemoryPool() {
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(2);
		assertEquals(null,semManager.splitMemoryPool(2, semManager.dummy.getNext()).getNext().getNext());
		
//		semManager.dummy.getNext().setNext(new FreeList(1,1));
//		assertEquals(1,semManager.splitMemoryPool(2, semManager.dummy.getNext()).getVal());
		
    }
    
    public void testdelete() {
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(2);
		assertEquals(false,semManager.delete(new Handle(-1,-1,-1)));
		
		semManager.delete(new Handle(1,1,1));
		semManager.dummy.setIndex(10);
		assertEquals(true,semManager.delete(new Handle(1,1,1)));
		semManager.dummy.setPrev(new FreeList(-1,-1));
		semManager.delete(new Handle(1,1,1));
		
		semManager.insert(new byte[1], 2);
		assertEquals(true,semManager.delete(new Handle(1,1,2)));
		
		
    }
    
    public void testdetectMerge() {
    	SemManager semManager = new SemManager();
		semManager.initializeSemManger(2);
		assertEquals(2,semManager.detectMerge().getVal());
		semManager.dummy.getNext().setVal(1);
		semManager.dummy.getNext().setIndex(0);
		semManager.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(2,semManager.detectMerge().getVal());
		
		SemManager semManager2 = new SemManager();
		semManager2.initializeSemManger(2);
		assertEquals(2,semManager2.detectMerge().getVal());
		semManager2.dummy.getNext().setVal(2);
		semManager2.dummy.getNext().setIndex(0);
		semManager2.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(1,semManager2.detectMerge().getVal());
		
		SemManager semManager3 = new SemManager();
		semManager3.initializeSemManger(2);
		assertEquals(1,semManager2.detectMerge().getVal());
		semManager3.dummy.getNext().setVal(1);
		semManager3.dummy.getNext().setIndex(-1);
		semManager3.dummy.getNext().setNext(new FreeList(1,1));
		assertEquals(1,semManager3.detectMerge().getVal());
		
		SemManager semManager4 = new SemManager();
		semManager4.initializeSemManger(2);
		assertEquals(1,semManager2.detectMerge().getVal());
		semManager4.dummy.getNext().setVal(1);
		semManager4.dummy.getNext().setIndex(0);
		FreeList tmp1 = new FreeList(1,1);
		FreeList tmp2 = new FreeList(1,2);
		tmp1.setPrev(semManager4.dummy.getNext());
		tmp2.setPrev(tmp1);
		semManager4.dummy.getNext().setNext(tmp1);
		semManager4.dummy.getNext().getNext().setNext(tmp2);
		assertEquals(1,semManager4.detectMerge().getVal());
		
    }
    
   
}

