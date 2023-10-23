import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * The BufferPool class represents a buffer pool for managing buffers with
 * associated metadata.
 * 
 * @author xianwei & jiren
 * @version Oct 22nd
 */
public class BufferPool implements BufferPoolADT {
    /**
     * The BufferList class represents a linked list node used to manage buffers
     * in the pool.
     */
    public class BufferList {
        private BufferList next;
        private BufferList prev;
        private Buffer buffer;

        /**
         * Constructor for BufferList.
         * 
         * @param createBuffer If true, creates a new buffer for the node.
         */
        public BufferList(boolean createBuffer) {
            if (createBuffer) {
                this.buffer = new Buffer();
            }
        }

        /**
         * Gets the next node in the linked list.
         * 
         * @return The next node in the linked list.
         */
        public BufferList getNext() {
            return next;
        }

        /**
         * Sets the next node in the linked list.
         * 
         * @param nxt The next node to be set.
         */
        public void setNext(BufferList nxt) {
            this.next = nxt;
        }

        /**
         * Gets the buffer associated with this node.
         * 
         * @return The buffer associated with this node.
         */
        public Buffer getBuffer() {
            return buffer;
        }

        /**
         * Sets the buffer associated with this node.
         * 
         * @param bf The buffer to be associated with this node.
         */
        public void setBuffer(Buffer bf) {
            this.buffer = bf;
        }

        /**
         * Gets the previous node in the linked list.
         * 
         * @return The previous node in the linked list.
         */
        public BufferList getPrev() {
            return prev;
        }

        /**
         * Sets the previous node in the linked list.
         * 
         * @param previous The previous node to be set.
         */
        public void setPrev(BufferList previous) {
            this.prev = previous;
        }
    }

    private BufferList dummy = null;
    private BufferList tail = null;
    private int poolSize;
    private int curNumOfBuffer;
    private RandomAccessFile raf;
    private long timeFlag;
    private long writes;
    private long reads;
    private long hits;
    private long readWriteTime;
    private static final int REC_PER_BUFFER = 1024;
    private static final int REC_SIZE = 4;

    /**
     * Constructs a BufferPool with a specified pool size and a
     * RandomAccessFile.
     *
     * @param size     The size of the buffer pool.
     * @param rafInput The RandomAccessFile used for reading and writing data.
     */
    public BufferPool(int size, RandomAccessFile rafInput) {
        dummy = new BufferList(false);
        tail = new BufferList(false);
        dummy.setNext(tail);
        tail.setPrev(dummy);
        poolSize = size;
        curNumOfBuffer = 0;
        readWriteTime = 0;
        raf = rafInput;
        Sort sort = new Sort(this);
        long startTime = System.currentTimeMillis();
        sort.quickSort(0, getFileLength() / REC_SIZE - 1);
        writeAllDirtyBlockToDisk();
        timeFlag = System.currentTimeMillis() - startTime;
    }

    /**
     * Gets the size of the buffer pool.
     *
     * @return The size of the buffer pool.
     */
    public int getSize() {
        return poolSize;
    }

    /**
     * Gets the time taken for various operations.
     *
     * @return The time taken for various operations in milliseconds.
     */
    public long getTime() {
        return timeFlag;
    }

    /**
     * Gets the number of write operations performed.
     *
     * @return The number of write operations.
     */
    public long getWrites() {
        return writes;
    }

    /**
     * Gets the number of read operations performed.
     *
     * @return The number of read operations.
     */
    public long getReads() {
        return reads;
    }

    /**
     * Gets the number of cache hits.
     *
     * @return The number of cache hits.
     */
    public long getHits() {
        return hits;
    }

    /**
     * Gets the total time spent on read and write operations.
     *
     * @return The total time spent on read and write operations in
     *         milliseconds.
     */
    public long getReadWriteTime() {
        return readWriteTime;
    }

    /**
     * Closes the associated RandomAccessFile.
     */
    public void closeFile() {
        try {
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a buffer with the specified ID at the top of the buffer pool.
     *
     * @param id The ID of the buffer to be inserted.
     * @return The newly inserted BufferList.
     */
    public BufferList insertBufferToTop(long id) {
        Buffer bf = new Buffer(id);
        BufferList insertBlock = new BufferList(false);
        insertBlock.setBuffer(bf);
        BufferList oldTop = dummy.getNext();
        dummy.setNext(insertBlock);
        insertBlock.setPrev(dummy);
        insertBlock.setNext(oldTop);
        oldTop.setPrev(insertBlock);
        curNumOfBuffer++;
        return insertBlock;
    }

    /**
     * Retrieves a buffer block from the pool
     *
     * @param sz  The size of records.
     * @param pos The position to retrieve the buffer block.
     * @return The BufferList containing the buffer block.
     */
    public BufferList getBlockByPos(int sz, long pos) {
        long blockID = calculateBlockID(sz, pos);
        BufferList searchBlock = dummy.getNext();
        while (searchBlock != tail) {
            if (searchBlock.getBuffer().getID() == blockID) {
                break;
            }
            searchBlock = searchBlock.getNext();
        }
        return searchBlock;
    }

    /**
     * Calculates the block ID for a given position and record size.
     *
     * @param sz  The size of records.
     * @param pos The position.
     * @return The calculated block ID.
     */
    private long calculateBlockID(int sz, long pos) {
        return pos / REC_PER_BUFFER;
    }

    /**
     * Processes bytes, either inserting or retrieving data from the buffer
     * pool.
     *
     * @param space    The byte array to insert or store data.
     * @param sz       The size of records.
     * @param pos      The position.
     * @param isInsert A flag indicating whether to insert or retrieve data.
     */
    private void processBytes(byte[] space, int sz, long pos,
            boolean isInsert) {
        long blockID = calculateBlockID(sz, pos);
        BufferList targetBlock = getBlockByPos(sz, pos);

        if (targetBlock == tail) {
            if (curNumOfBuffer >= poolSize)
                discardBlock();

            if (isInsert) {
                targetBlock = insertBufferToTop(blockID);
            }
            else {
                byte[] dataRead;
                try {
                    dataRead = readFromDisk(blockID * sz * REC_PER_BUFFER);
                    targetBlock = insertBufferToTop(blockID);
                    targetBlock.setBuffer(new Buffer(dataRead, blockID));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            hits++;
        }

        byte[] bufferData = targetBlock.getBuffer().getData();
        int offset = (int) (pos * sz % (REC_PER_BUFFER * REC_SIZE));
        for (int i = 0; i < sz; i++) {
            if (isInsert) {
                bufferData[offset + i] = space[i];
            }
            else {
                space[i] = bufferData[offset + i];
            }
        }

        moveToTheTop(targetBlock);
    }

    /**
     * Inserts data into the buffer pool.
     *
     * @param space The byte array to insert.
     * @param sz    The size of records.
     * @param pos   The position to insert the data.
     */
    public void insert(byte[] space, int sz, long pos) {
        processBytes(space, sz, pos, true);
        setDirtyBit(sz, pos);
    }

    /**
     * Retrieves data from the buffer pool.
     *
     * @param space The byte array to store the retrieved data.
     * @param sz    The size of records.
     * @param pos   The position to retrieve the data.
     */
    public void getbytes(byte[] space, int sz, long pos) {
        processBytes(space, sz, pos, false);
    }

    /**
     * Sets the dirty bit for the buffer block at the specified position and
     * record size.
     *
     * @param sz  The size of records.
     * @param pos The position to set the dirty bit.
     */
    private void setDirtyBit(int sz, long pos) {
        BufferList searchBlock = getBlockByPos(sz, pos);
        if (searchBlock != tail) {
            searchBlock.getBuffer().setDirty(true);
        }
    }

    /**
     * Swaps the content of two buffer blocks at the given positions.
     *
     * @param i The position of the first buffer block.
     * @param j The position of the second buffer block.
     */
    public void swap(long i, long j) {
        if (i == j) {
            return;
        }
        byte[] tmpJ = new byte[REC_SIZE];
        byte[] tmpI = new byte[REC_SIZE];
        getbytes(tmpJ, REC_SIZE, j);
        getbytes(tmpI, REC_SIZE, i);
        insert(tmpJ, REC_SIZE, i);
        if (poolSize == 1) {
            getbytes(tmpJ, REC_SIZE, j);
        }
        insert(tmpI, REC_SIZE, j);
    }

    /**
     * Moves a buffer block to the top of the buffer pool.
     *
     * @param block The BufferList containing the buffer block to be moved.
     */
    public void moveToTheTop(BufferList block) {
        if (block == dummy.getNext() || dummy.getNext() == tail) {
            return;
        }
        block.getPrev().setNext(block.getNext());
        block.getNext().setPrev(block.getPrev());
        BufferList oldTop = dummy.getNext();
        dummy.setNext(block);
        block.setPrev(dummy);
        block.setNext(oldTop);
        oldTop.setPrev(block);
    }

    /**
     * Discards the least recently used buffer block from the buffer pool.
     */
    public void discardBlock() {
        BufferList prev = tail.getPrev();
        BufferList prevprev = tail.getPrev().getPrev();
        Buffer bf = prev.getBuffer();
        if (bf.dirtyFlag()) {
            try {
                writeToDisk(REC_SIZE * REC_PER_BUFFER * bf.getID(),
                        bf.getData());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        prevprev.setNext(tail);
        tail.setPrev(prevprev);
        curNumOfBuffer--;
    }

    /**
     * Reads a block of data from the disk at the specified index and returns it
     * as a byte array.
     *
     * @param index The index from which to read the data on the disk.
     * @return The data read from the disk.
     * @throws Exception If an error occurs during reading from the disk.
     */
    public byte[] readFromDisk(long index) throws Exception {
        byte[] buffer = new byte[REC_SIZE * REC_PER_BUFFER];
        long startTime = System.currentTimeMillis();
        raf.seek(index);
        raf.read(buffer);
        reads++;
        long time = System.currentTimeMillis() - startTime;
        readWriteTime = readWriteTime + time;
        return buffer;
    }

    /**
     * Writes all the dirty buffer blocks to the disk.
     */
    public void writeAllDirtyBlockToDisk() {
        BufferList tmp = dummy.getNext();
        while (tmp != tail) {
            if (tmp.getBuffer().dirtyFlag()) {
                try {
                    writeToDisk(
                            tmp.getBuffer().getID() * REC_SIZE * REC_PER_BUFFER,
                            tmp.getBuffer().getData());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tmp = tmp.getNext();
        }
        closeFile();
    }

    /**
     * Writes the specified data to the disk at the given index.
     *
     * @param index The index where the data will be written on the disk.
     * @param data  The data to be written to the disk.
     * @throws Exception If an error occurs during writing to the disk.
     */
    public void writeToDisk(long index, byte[] data) throws Exception {
        long startTime = System.currentTimeMillis();
        raf.seek(index);
        raf.write(data);
        long time = System.currentTimeMillis() - startTime;
        readWriteTime = readWriteTime + time;
        writes++;
    }

    /**
     * Retrieves the length of the file associated with the RandomAccessFile.
     *
     * @return The length of the file.
     */
    public long getFileLength() {
        try {
            return raf.length();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}