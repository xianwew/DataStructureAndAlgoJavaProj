// On my honor:
// - I have not used source code obtained from another current or
//   former student, or any other unauthorized source, either
//   modified or unmodified.
//
// - All source code and documentation used in my program is
//   either my original work, or was derived by me from the
//   source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
//   anyone other than my partner (in the case of a joint
//   submission), instructor, ACM/UPE tutors or the TAs assigned
//   to this course. I understand that I may discuss the concepts
//   of this program with other students, and that another student
//   may help me debug my program so long as neither of us writes
//   anything during the discussion or modifies any computer file
//   during the discussion. I have violated neither the spirit nor
//   letter of this restriction.

import java.io.*;

/**
 * The semSanager is used to store the seminar objects, 
 * or retrieve seminar objects from the memory pool.
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 * 
 * @FreeList dummy: the dummy node, 
 * or it is called the header of the list
 * @size: the size of the memory pool.
 * @memory pool: the place to store seminar objects.
 */

public class SemManager {
	private FreeList dummy;
	private int size;
	private byte[] memoryPool;
	public SemManager() {}
	
	/**
     * The semSanager is used to store the seminar objects, 
     * or retrieve seminar objects from the memory pool.
     * @param args
     *     Command line parameters
     * @FreeList dummy: the dummy node, or it is 
     * called the header of the list
     * @size: the size of the memory pool.
     * @memory pool: the place to store seminar objects.
     */

	/**
     * The initializeSemManger is the method that is called to 
     * initialize the memory manager.
     */	
	
	public void initializeSemManger (int size) {
			
		dummy = new FreeList(-1, -1);
		FreeList head = new FreeList(size, 0);
		dummy.setNext(head);
		head.setPrev(dummy);
		this.size = size;
		this.memoryPool = new byte[size];
	}

	public void printSemManager () {	
		/**
	     * The printSemManager is used to print out the free blocks
	     */	
		FreeList curPosition = dummy.getNext();
		if (curPosition == null) {
			System.out.println("There are no Freeblocks in the memory pool!");
		}
		else {
			System.out.println("Freeblock List:");
			while(curPosition != null) {
				System.out.println(curPosition.getVal() + ": " + curPosition.getIndex());
				curPosition = curPosition.getNext();
			}	
		}
	}
	
	public void search (Handle handle) {
		/**
	     * The search function search the result by a given handle,
	     * and print the result out.
	     */	
		Seminar searchedRecord = new Seminar();
		byte[] searchedRecordByte = new byte[handle.getSize()];
		int curSearchedRecordByteIndex = 0;
		System.out.println("search handle start index: " + handle.getStartIndex());
		for (int i = handle.getStartIndex(); i < handle.getStartIndex() + handle.getSize(); i++) {
			searchedRecordByte[curSearchedRecordByteIndex] = memoryPool[i];
			curSearchedRecordByteIndex++;
		}
		try {
			searchedRecord = Seminar.deserialize(searchedRecordByte);
			System.out.println(searchedRecord.toString());
		}
		catch (Exception e){
            System.out.println("Error in reading seminar record in memory manager!");
            e.printStackTrace();
		}
	}
	
	public void doubleSize () {
		/**
	     * The function doubles the size of the memory manager
	     */	
		System.out.println("doubling memory pool size!");
		byte[] tmp = new byte[size*2];
		for (int i = 0; i < memoryPool.length; i++) {
			tmp[i] = memoryPool[i];
		}
		FreeList newInsert = new FreeList(size, 0);
		FreeList curPosition = dummy;	
		while (curPosition != null && curPosition.getNext() != null) {
			curPosition = curPosition.getNext();
		}
		curPosition.setNext(newInsert);
		newInsert.setPrev(curPosition);	
		newInsert.setIndex(size);
		memoryPool = tmp;
		size *= 2;
	}
	
	public FreeList FindSpaceAvailable (int requestedSize) {
		/**
	     * The function tries to find if there is a position available
	     * by a given requested size.
	     */	
		FreeList curPosition = dummy.getNext();
		FreeList ReturnPtr = null;
		int smallestGreaterThanReq = Integer.MAX_VALUE;
		while (curPosition != null) {
			if (curPosition.getVal() >= requestedSize && 
				curPosition.getVal() <= smallestGreaterThanReq) {
				smallestGreaterThanReq = curPosition.getVal();
				ReturnPtr = curPosition;
			}
			System.out.println("Current ptr size: " + curPosition.getVal());	
			curPosition = curPosition.getNext();
		}
		return ReturnPtr;
	}
	
	private int getNearestPowerOfTwo (int requestedSize) {
		/**
	     * The function return a power of two number that is greater
	     * than a given number.
	     */
        if (Math.log(requestedSize) / Math.log(2) == 0) {
            return requestedSize;
        }
        int nearestPowerOfTwo = 1;
        while (nearestPowerOfTwo < requestedSize) {
        	nearestPowerOfTwo *= 2; 
        }
        return nearestPowerOfTwo;
    }
	
	public void splitMemoryPool (int insertPositionSize, FreeList insertPosition) {
		/**
	     * The function split the given node.
	     */
		FreeList next = insertPosition.getNext();
		FreeList newInsert = new FreeList(insertPositionSize / 2, 
				insertPosition.getIndex() + insertPositionSize / 2);
		newInsert.setPrev(insertPosition);
		newInsert.setNext(next);
		insertPosition.setNext(newInsert);
		System.out.println("InsertPosition next val: " + insertPosition.getNext().getVal());	
		insertPosition.setVal(insertPositionSize / 2);
		if (next != null) {
			next.setPrev(newInsert);
		}
		System.out.println("cur ptr length: " + insertPosition.getVal());	
		System.out.println("splited ptr length: " + insertPosition.getNext().getVal());	
	}
	
	public Handle insert (byte[] insertData, int key) {
		/**
	     * The function inserts the data, when the memory pool deos not
	     * have enough space, it calls doubleSize().
	     * @return once the record is stored, a handle is returned for 
	     * recovery purposes.
	     */
		Handle handle = new Handle(-1, -1, -1);
		FreeList insertPosition = FindSpaceAvailable(insertData.length);
		while (insertPosition == null) {
			doubleSize();	
			insertPosition = FindSpaceAvailable(insertData.length);
		}	
		System.out.println("insertData length: " + insertData.length);	
		System.out.println("Memory Pool length: " + memoryPool.length);	
		//System.out.println("insertPosition Prev Val: " + insertPosition.getPrev().getVal());
		while(insertPosition.getVal() > getNearestPowerOfTwo(insertData.length)){
			splitMemoryPool(insertPosition.getVal(), insertPosition);
		}
		FreeList prev = insertPosition.getPrev(), next = insertPosition.getNext();
		//System.out.println("InsertPosition next val: " + next.getVal());	
		int insertStartIndex = insertPosition.getIndex();
		handle.setStartIndex(insertStartIndex);
		handle.setSize(insertData.length);
		handle.setKey(key);
		for(byte i : insertData){
			memoryPool[insertStartIndex] = i;
			insertStartIndex++;
		}
		System.out.println("Insert position " + insertPosition.getIndex());	
		insertPosition.setNext(null);
		insertPosition.setPrev(null);
		prev.setNext(next);
		if(next != null){
			next.setPrev(prev);
		}
		System.out.println("Successfully inserted record with ID " + key);			
		FreeList testPtr = dummy;
		while (testPtr != null) {
			System.out.println("ptr val: " + testPtr.getVal());	
			testPtr = testPtr.getNext();
		}
		return handle;
	}	
	
	public boolean delete(Handle handle) {
		/**
	     * The function deletes a record by a given handle.
	     * @return true if the record was deleted
	     */
		if (handle.getStartIndex() == -1) {
			return false;
		}
		for (int i = handle.getStartIndex(); i < handle.getStartIndex() + handle.getSize(); i++) {
			memoryPool[i] = 0;
		}
		FreeList newInsert = new FreeList(getNearestPowerOfTwo(handle.getSize()), handle.getStartIndex());
		FreeList searchPtr = dummy;
		while (searchPtr != null) {
			if (searchPtr.getIndex() > newInsert.getIndex()) {
				break;
			}
			searchPtr = searchPtr.getNext();
		}	
		if(searchPtr != null) {
			FreeList prev = searchPtr.getPrev();
			prev.setNext(newInsert);
			newInsert.setPrev(prev);
			searchPtr.setPrev(newInsert);
			newInsert.setNext(searchPtr);
		}
		else{
			newInsert.setPrev(dummy);
			dummy.setNext(newInsert);
		}
		detectMerge();
		return true;
	}
	
	public void detectMerge() {
		/**
	     * The function detects if any two nodes are adjacent and
	     * need to be merge to one node 
	     */
		FreeList left = dummy.getNext();
		FreeList right = left.getNext();	
		while (right != null) {
			if ((left.getVal() == right.getVal())) {
				FreeList newInsert = new FreeList(left.getVal() * 2, left.getIndex());
				FreeList prev = left.getPrev(), next = right.getNext();
				newInsert.setPrev(prev);
				newInsert.setNext(next);
				prev.setNext(newInsert);
				if(next != null){
					next.setPrev(newInsert);
				}
				left = newInsert;
				right = next;	
			}
			else {
				left = right;
				right = right.getNext();	
			}
		}	
	}

	public static void main (String[] args) {
		/**
	     * The main function of the program. It firstly initializes all
	     * the components and then calls function. 
	     */
	    Parser parser = new Parser();
	    Object[] components = parser.initializeComponents(args);   
	    SemManager semManager = new SemManager();
	    try {
		    semManager.memoryPool = (byte[]) components[0];
		    semManager.initializeSemManger(semManager.memoryPool.length);
		    MyHashTable hashTable = (MyHashTable) components[1];
		    File commandFile = (File) components[2];        
		    WorldDataBase worldDataBase = new WorldDataBase(semManager, hashTable);
		    parser.processSeminars(commandFile, worldDataBase);
	    }
	    catch (Exception e) {
            System.out.println("Error in initializing instances!");
            e.printStackTrace();
		}
    }
}
