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

public class MemManager {
	public int doubleSizeTest = 0;
	public FreeList dummy;
	public int size;
	public byte[] memoryPool;
	public MemManager() {}
	
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
	
	public void initializeMemManger (int size) {
			
		dummy = new FreeList(-1, -1);
		FreeList head = new FreeList(size, 0);
		dummy.setNext(head);
		head.setPrev(dummy);
		this.size = size;
		this.memoryPool = new byte[size];
	}

	public boolean printMemManager () {	
		/**
	     * The printMemManager is used to print out the free blocks
	     */	
		System.out.println("Freeblock List:");
		FreeList curPosition = dummy.getNext();
		int i = 1;
		if (curPosition == null) {
			System.out.println("There are no freeblocks in the memory pool");
			return false;
		}
		else {
			while(curPosition != null) {
				curPosition = curPosition.getNext();
				i++;
			}	
			FreeList[] tmp = new FreeList[i];	
			for(int k = 0; k < tmp.length; k++) {
				tmp[k] = new FreeList(-1, -1);
			}

			curPosition = dummy.getNext();
			while (curPosition != null) {
				boolean set = false;
				for (int k = 0; k < tmp.length; k++) {
					if (tmp[k].getVal() == curPosition.getVal()) {
						FreeList tmpList = tmp[k];
						while (tmpList.getNext() != null) {
							tmpList = tmpList.getNext();
						}
						tmpList.setNext(new FreeList(curPosition.getVal(), curPosition.getIndex()));
						set = true;
						break;
					}						
				}  
				if (!set) {
					for (int k = 0; k < tmp.length; k++) {			
						if (tmp[k].getVal() == -1) {	
							tmp[k].setVal(curPosition.getVal());
							tmp[k].setIndex(curPosition.getIndex());
							set = true;
							break;
						}					
					}
				}
				curPosition = curPosition.getNext();
			}	
			for (int k = 0; k < tmp.length; k++) {
				if (tmp[k].getVal() != -1) {
					curPosition = tmp[k];
					System.out.print(curPosition.getVal() + ": ");
					while (curPosition != null) {
						if (curPosition.getNext() != null) {
							System.out.print(curPosition.getIndex() + " ");
						}
						else {
							System.out.print(curPosition.getIndex());
						}
						curPosition = curPosition.getNext();
					}
					System.out.println("");
				}
			}
		return true;
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
		}
	}
	
	public FreeList doubleSize () {
		/**
	     * The function doubles the size of the memory manager
	     */	
		
		byte[] tmp = new byte[size * 2];
		doubleSizeTest = 0;
		while (doubleSizeTest < memoryPool.length) {
			tmp[doubleSizeTest] = memoryPool[doubleSizeTest];
			doubleSizeTest++;
		}

		FreeList newInsert = new FreeList(size, size);
		FreeList curPosition = dummy;	
		while (curPosition != null && curPosition.getNext() != null) {
			curPosition = curPosition.getNext();
		}
		if (curPosition != null) {
			curPosition.setNext(newInsert);
			newInsert.setPrev(curPosition);	
		}
		memoryPool = tmp;
		size *= 2;
		System.out.println("Memory pool expanded to " + size + " bytes");
		return curPosition;
	}
	
	public FreeList FindSpaceAvailable (int requestedSize) {
		/**
	     * The function tries to find if there is a position available
	     * by a given requested size.
	     */	
		FreeList curPosition = null;
		if(dummy != null) {
			curPosition = dummy.getNext();
		}
		
		FreeList ReturnPtr = null;
		int smallestGreaterThanReq = Integer.MAX_VALUE-1;
		while (curPosition != null) {
			if (curPosition.getVal() >= requestedSize && 
				curPosition.getVal() <= smallestGreaterThanReq) {
				smallestGreaterThanReq = curPosition.getVal();
				ReturnPtr = curPosition;
			}
			curPosition = curPosition.getNext();
		}
		return ReturnPtr;
	}
	
	public int getNearestPowerOfTwo (int requestedSize) {
		/**
	     * The function return a power of two number that is greater
	     * than a given number.
	     */
//	        if (Math.log(requestedSize) / Math.log(2) == 0) {
//	            return requestedSize;
//	        }
        int nearestPowerOfTwo = 1;
        while (nearestPowerOfTwo < requestedSize) {
        	nearestPowerOfTwo *= 2; 
        }
        return nearestPowerOfTwo;
    }
	
	public FreeList splitMemoryPool (int insertPositionSize, FreeList insertPosition) {
		/**
	     * The function split the given node.
	     */
		FreeList next = insertPosition.getNext();
		int i = insertPositionSize / 2;
		int k = insertPosition.getIndex()+i;
		FreeList newInsert = new FreeList(i, k);
		newInsert.setPrev(insertPosition);
		newInsert.setNext(next);
		insertPosition.setNext(newInsert);	
		insertPosition.setVal(i);
		if (next != null) {
			next.setPrev(newInsert);
		}
		return insertPosition;
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
		while(insertPosition.getVal() > getNearestPowerOfTwo(insertData.length)){
			splitMemoryPool(insertPosition.getVal(), insertPosition);
		}
		FreeList prev = insertPosition.getPrev(), next = insertPosition.getNext();
		int insertStartIndex = insertPosition.getIndex();
		handle.setStartIndex(insertStartIndex);
		handle.setSize(insertData.length);
		handle.setKey(key);
		for(byte i : insertData){
			memoryPool[insertStartIndex] = i;
			insertStartIndex++;
		}	
		insertPosition.setNext(null);
		insertPosition.setPrev(null);
		prev.setNext(next);
		if(next != null){
			next.setPrev(prev);
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
			if(prev != null) {
				prev.setNext(newInsert);	
			}
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
	
	public FreeList detectMerge() {
		/**
	     * The function detects if any two nodes are adjacent and
	     * need to be merge to one node 
	     */
		FreeList left = dummy.getNext();
		FreeList right = null;
		if(left != null) {
			right = left.getNext();		
		}
		
		while (right != null) {
			if ((left.getVal() == right.getVal()) && 
					(left.getIndex() + left.getVal() == right.getIndex())) {
				FreeList newInsert = new FreeList(left.getVal() * 2, left.getIndex());
				FreeList prev = left.getPrev(), next = right.getNext();
				newInsert.setPrev(prev);
				newInsert.setNext(next);
				prev.setNext(newInsert);
				if (next != null) {
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
		return left;
	}   
}
	

	


