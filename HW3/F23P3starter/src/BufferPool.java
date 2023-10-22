import java.io.RandomAccessFile;
import java.io.IOException;

public class BufferPool implements BufferPoolADT {
    public class BufferList {
        private BufferList next;
        private BufferList prev;
        private Buffer buffer;

        public BufferList(boolean createBuffer) {
            if (createBuffer) {
                this.buffer = new Buffer();
            }
        }

        public BufferList getNext() {
            return next;
        }

        public void setNext(BufferList nxt) {
            this.next = nxt;
        }

        public Buffer getBuffer() {
            return buffer;
        }

        public void setBuffer(Buffer bf) {
            this.buffer = bf;
        }

        public BufferList getPrev() {
            return prev;
        }

        public void setPrev(BufferList previous) {
            this.prev = previous;
        }
    }

    private BufferList dummy = null;
    private BufferList tail = null;
    private int poolSize;
    private int curNumOfBuffer;
    private RandomAccessFile raf;
    private long time;
    private long writes;
    private long reads;
    private long hits;
    private long readWriteTime;
    private static final int REC_PER_BUFFER = 1024;
    private static final int REC_SIZE = 4;

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
        time = System.currentTimeMillis() - startTime;
    }

    public int getSize() {
        return poolSize;
    }

    public long getTime() {
        return time;
    }

    public long getWrites() {
        return writes;
    }

    public long getReads() {
        return reads;
    }

    public long getHits() {
        return hits;
    }

    public long getReadWriteTime() {
        return readWriteTime;
    }

    public void closeFile() {
        try {
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private long calculateBlockID(int sz, long pos) {
        return pos / REC_PER_BUFFER;
    }

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

    public void insert(byte[] space, int sz, long pos) {
        processBytes(space, sz, pos, true);
        setDirtyBit(sz, pos);
    }

    public void getbytes(byte[] space, int sz, long pos) {
        processBytes(space, sz, pos, false);
    }

    private void setDirtyBit(int sz, long pos) {
        BufferList searchBlock = getBlockByPos(sz, pos);
        if (searchBlock != tail) {
            searchBlock.getBuffer().setDirty(true);
        }
    }

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

    public void discardBlock() {
        BufferList prev = tail.getPrev();
        BufferList prevprev = tail.getPrev().getPrev();
        Buffer bf = prev.getBuffer();
        if (bf.isDirty()) {
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

    public void writeAllDirtyBlockToDisk() {
        BufferList tmp = dummy.getNext();
        while (tmp != tail) {
            if (tmp.getBuffer().isDirty()) {
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

    public void writeToDisk(long index, byte[] data) throws Exception {
        long startTime = System.currentTimeMillis();
        raf.seek(index);
        raf.write(data);
        long time = System.currentTimeMillis() - startTime;
        readWriteTime = readWriteTime + time;
        writes++;
    }

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