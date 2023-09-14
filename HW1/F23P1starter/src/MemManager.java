import java.io.*;

/**
 * The semSanager is used to store the seminar objects,
 * or retrieve seminar
 * objects from the memory pool.
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */

public class MemManager {
    private int doubleSizeTest = 0;
    private FreeList dummy;
    private int size;
    private byte[] memoryPool;

    /**
     * Dummy MemManager constructor
     */
    public MemManager() {
        //Nothing here
    }

    /**
     * The initializeSemManger is the method
     * that is called to initialize the memory
     * manager.
     * @param sizeInput the size to be initialized
     */
    public void initializeMemManger(int sizeInput) {
        dummy = new FreeList(-1, -1);
        FreeList head = new FreeList(sizeInput, 0);
        dummy.setNext(head);
        head.setPrev(dummy);
        this.size = sizeInput;
        this.memoryPool = new byte[sizeInput];
    }
    
    /**
     * The function return the dummy node
     * @Return dummy node
     */
    public FreeList getDummyNode() {
        return dummy;
    }
    
    /**
     * Set dummy node
     * @param node FreeList node input
     */
    public void setDummy(FreeList node) {
        this.dummy = node;
    }
    
    /**
     * Get the memory pool size
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Set the memory pool
     * @param memoryPoolInput input memory pool
     */
    public void setMemoryPool(
            byte[] memoryPoolInput) {
        this.memoryPool = memoryPoolInput;
    }
    
    /**
     * Get the doubleSizeTestVal
     * @return the doubleSizeTest valuable
     */
    public int getDoubleSizeTest() {
        return doubleSizeTest;
    }
    
    /**
     * Print the free list out
     */
    public void printOut(FreeList[] tmp, int length) {
        for (int k = 0; k < length; k++) {
            if (tmp[k].getVal() != -1) {
                FreeList curPosition = tmp[k];
                System.out.print(curPosition.getVal() + ": ");
                while (curPosition != null) {
                    if (curPosition.getNext() != null) {
                        System.out.print(curPosition.getIndex() + " ");
                    } else {
                        System.out.print(curPosition.getIndex());
                    }
                    curPosition = curPosition.getNext();
                }
                System.out.println("");
            }
        }
    }
    
    /**
     * Process the free list print
     * @param curPosition the current pointer
     * @param tmp the FreeList array
     * @param length the length of the tmp array
     */
    public void processFreeList(
            FreeList curPosition, FreeList[] tmp, int length) {
        boolean set = false;
        for (int k = 0; k < length; k++) {
            if (tmp[k].getVal() == curPosition.getVal()) {
                FreeList tmpList = tmp[k];
                while (tmpList.getNext() != null) {
                    tmpList = tmpList.getNext();
                }
                tmpList.setNext(new FreeList(
                        curPosition.getVal(), curPosition.getIndex()));
                set = true;
                break;
            }
        }
        if (!set) {
            for (int k = 0; k < length; k++) {
                if (tmp[k].getVal() == -1) {
                    tmp[k].setVal(curPosition.getVal());
                    tmp[k].setIndex(curPosition.getIndex());
                    set = true;
                    break;
                }
            }
        }
    }
    
    /**
     * The printMemManager is used
     * to print out the free blocks
     * @return if the print is successful
     */
    public boolean printMemManager() {
        System.out.println("Freeblock List:");
        FreeList curPosition = dummy.getNext();
        int i = 1;
        if (dummy.getNext() == null) {
            System.out.println("There are "
                    + "no freeblocks in the memory pool");
            return false;
        } 
        else {
            while (curPosition != null) {
                curPosition = curPosition.getNext();
                i++;
            }
            FreeList[] tmp = new FreeList[i];
            for (int k = 0; k < tmp.length; k++) {
                tmp[k] = new FreeList(-1, -1);
            }

            curPosition = dummy.getNext();
            while (curPosition != null) {
                processFreeList(curPosition, tmp, tmp.length);
                curPosition = curPosition.getNext();
            }
            printOut(tmp, tmp.length);
            return true;
        }
    }
    
    /**
     * The search function search the result
     * by a given handle, and print the result
     * out.
     * 
     */
    public void search(Handle handle) {
        Seminar searchedRecord = new Seminar();
        byte[] searchedRecordByte =
                new byte[handle.getSize()];
        int curSearchedRecordByteIndex = 0;
        for (int i = handle.getStartIndex(); 
                i < handle.getStartIndex() + handle.getSize(); i++) {
            searchedRecordByte[curSearchedRecordByteIndex]
                    = memoryPool[i];
            curSearchedRecordByteIndex++;
        }
        try {
            searchedRecord = Seminar.deserialize(
                    searchedRecordByte);
            System.out.println(searchedRecord.toString());
        } catch (Exception e) {
            System.out.println("Error in"
                    + " reading seminar record in memory manager!");
        }
    }

    /**
     * The doubleSize function is used to
     * double the size of the memory pool
     * @return the curPosition pointer 
     */
    public FreeList doubleSize() {
        byte[] tmp = new byte[size * 2];
        doubleSizeTest = 0;
        while (doubleSizeTest < memoryPool.length) {
            tmp[doubleSizeTest] = memoryPool[doubleSizeTest];
            doubleSizeTest++;
        }

        FreeList newInsert = new FreeList(size, size);
        FreeList curPosition = dummy;
        while (curPosition != null &&
                curPosition.getNext() != null) {
            curPosition = curPosition.getNext();
        }
        if (curPosition != null) {
            curPosition.setNext(newInsert);
            newInsert.setPrev(curPosition);
        }
        memoryPool = tmp;
        size *= 2;
        detectMerge();
        System.out.println("Memory pool expanded to " + size + " bytes");
        return curPosition;
    }

    /**
     * The FindSpaceAvailable function is used to
     * find the available space for insertion
     * @param requestedSize the size that
     *  is used to check for space
     * @return the pointer pointing to the available space
     */
    public FreeList findSpaceAvailable(int requestedSize) {
        FreeList curPosition = null;
        if (dummy != null) {
            curPosition = dummy.getNext();
        }

        FreeList returnPtr = null;
        int smallestGreaterThanReq = Integer.MAX_VALUE - 1;
        while (curPosition != null) {
            if (curPosition.getVal() >= requestedSize &&
                    curPosition.getVal() <= smallestGreaterThanReq) {
                smallestGreaterThanReq = curPosition.getVal();
                returnPtr = curPosition;
            }
            curPosition = curPosition.getNext();
        }
        return returnPtr;
    }

    /**
     * The getNearestPowerOfTwo function is used to
     * return a power of two number 
     * that is greater than a given number.
     * @param requestedSize the requested size
     * @return the nearestPowerOfTwo number
     */
    public int getNearestPowerOfTwo(int requestedSize) {     
        int nearestPowerOfTwo = 1;
        while (nearestPowerOfTwo < requestedSize) {
            nearestPowerOfTwo *= 2;
        }
        return nearestPowerOfTwo;
    }

    /**
     * Split the memory pool
     * @param insertPositionSize the original space size
     * @param insertPosition the pointer pointing to the space
     *        needed to be split
     * @return the pointer pointing to the split space         
     */
    public FreeList splitMemoryPool(
            int insertPositionSize, FreeList insertPosition) {
        FreeList next = insertPosition.getNext();
        int i = insertPositionSize / 2;
        int k = insertPosition.getIndex() + i;
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
    
    /**
     * The function inserts the data, 
     * when the memory pool does not have enough
     * space, it calls doubleSize().
     * 
     * @param insertData the seminar serialized object
     * @param key the key associated with the seminar object
     * @return once the record is stored, a handle 
     *         is returned for recovery purposes.
     */
    public Handle insert(byte[] insertData, int key) {
        Handle handle = new Handle(-1, -1, -1);
        FreeList insertPosition = 
                findSpaceAvailable(insertData.length);
        while (insertPosition == null) {
            doubleSize();
            insertPosition = 
                    findSpaceAvailable(insertData.length);
        }
        while (insertPosition.getVal()
                > getNearestPowerOfTwo(insertData.length)) {
            splitMemoryPool(insertPosition.getVal(), insertPosition);
        }
        FreeList prev = insertPosition.getPrev();
        FreeList next = insertPosition.getNext();
        int insertStartIndex = insertPosition.getIndex();
        handle.setStartIndex(insertStartIndex);
        handle.setSize(insertData.length);
        handle.setKey(key);
        for (byte i : insertData) {
            memoryPool[insertStartIndex] = i;
            insertStartIndex++;
        }
        insertPosition.setNext(null);
        insertPosition.setPrev(null);
        prev.setNext(next);
        if (next != null) {
            next.setPrev(prev);
        }

        return handle;
    }

    /**
     * The function deletes a record by a given handle.
     * @param handle handle to be deleted
     * @return true if the record was deleted
     */
    public boolean delete(Handle handle) {
        if (handle.getStartIndex() == -1) {
            return false;
        }
        for (int i = handle.getStartIndex(); i 
                < handle.getStartIndex() + handle.getSize(); i++) {
            memoryPool[i] = 0;
        }
        FreeList newInsert = 
                new FreeList(getNearestPowerOfTwo(
                handle.getSize()), handle.getStartIndex());
        FreeList searchPtr = dummy;
        while (searchPtr != null) {
            if (searchPtr.getIndex() > newInsert.getIndex()) {
                break;
            }
            searchPtr = searchPtr.getNext();
        }
        if (searchPtr != null) {
            FreeList prev = searchPtr.getPrev();
            if (prev != null) {
                prev.setNext(newInsert);
            }
            newInsert.setPrev(prev);
            searchPtr.setPrev(newInsert);
            newInsert.setNext(searchPtr);
        } else {
            newInsert.setPrev(dummy);
            dummy.setNext(newInsert);
        }
        detectMerge();
        return true;
    }
    
    /**
     * The function detects if any two 
     * nodes are adjacent and need to be merge to
     * one node
     */
    public FreeList detectMerge() {
        if (dummy == null) {
            return null;
        }

        FreeList left = dummy.getNext();
        FreeList right = null;
        if (left != null) {
            right = left.getNext();
        }

        while (right != null) {
            if ((left.getVal() == right.getVal()) && (left.getIndex()
                    + left.getVal() == right.getIndex())) {
                FreeList newInsert = 
                        new FreeList(left.getVal() * 2, left.getIndex());
                FreeList prev = left.getPrev();
                FreeList next = right.getNext();
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