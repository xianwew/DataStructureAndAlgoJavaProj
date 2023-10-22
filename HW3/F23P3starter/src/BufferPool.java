import java.io.RandomAccessFile;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferPool implements BufferPoolADT {
    public class BufferList {
        private BufferList next;
        private BufferList prev;
        private Buffer buffer;

        public BufferList(boolean createBuffer) {
            if(createBuffer) {
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
    private String fileName;
    private int curNumOfBuffer;
    private RandomAccessFile raf;
    private long time;
    private int writes;
    private int reads;
    private int hits;
    
    public BufferPool(int size, String fName, RandomAccessFile rafInput) {
        dummy = new BufferList(false);
        tail = new BufferList(false);
        dummy.setNext(tail);
        tail.setPrev(dummy);
        poolSize = size;
        fileName = fName;
        curNumOfBuffer = 0;
        raf = rafInput; 
        Sort sort = new Sort(this);
        long startTime = System.currentTimeMillis();
        sort.quickSort(0, getFileLength() - 4);
        writeAllDirtyBlockToDisk();
        time = System.currentTimeMillis() - startTime;
    }

    public int getSize() {
        return poolSize;
    } 

    public long getTime() {
        return time;
    }

    public int getWrites() {
        return writes;
    }

    public int getReads() {
        return reads;
    }

    public int getHits() {
        return hits;
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
        long blockID = pos / (sz * 1024);
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != tail) { 
            if(searchBlock.getBuffer().getID() == blockID) {
                break;
            }
            searchBlock = searchBlock.getNext();
        }
        return searchBlock;
    }
    
    public void insert(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList insertBlock = getBlockByPos(sz, pos);
        if(insertBlock == tail) {
            if(curNumOfBuffer > poolSize - 1) {
                discardBlock();
            }
            insertBlock = insertBufferToTop(blockID);
            setDirtyBit(sz, pos);
        }
        else{
            moveToTheTop(insertBlock);
        }
        
        for(int i = 0; i < sz; i++) {
            insertBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))] = space[i];
        } 
    }

    public void getbytes(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList searchBlock = getBlockByPos(sz, pos);
        if(searchBlock == tail) {
            if(curNumOfBuffer > poolSize - 1) {
                discardBlock();
            }
            byte[] dataRead = null;
            try {
                dataRead = readFromDisk(pos - pos % 4096);
                searchBlock = insertBufferToTop(blockID);
                searchBlock.setBuffer(new Buffer(dataRead, blockID));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }   
        else{
            hits++;
            moveToTheTop(searchBlock);
        }
        
        for(int i = 0; i < sz; i++) {
            space[i] = searchBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))];
        }  
    }
    
    private void setDirtyBit(int sz, long pos) {
        BufferList searchBlock = getBlockByPos(sz, pos);
        if(searchBlock != tail){
            searchBlock.getBuffer().setDirty(true);
        }
    }

    public void swap(long i, long j) {
        byte[] tmpJ = new byte[4];
        byte[] tmpI = new byte[4];
        getbytes(tmpJ, 4, j);
        getbytes(tmpI, 4, i);
        insert(tmpJ, 4, i);
        insert(tmpI, 4, j);
        setDirtyBit(4, i);
        setDirtyBit(4, j);
    }

    public void printBuffers() {
        BufferList tmp = dummy.getNext();
        System.out.println("Buffer list: ");
        while(tmp != tail) {
            System.out.print(tmp.getBuffer().getID() + " ");
            tmp = tmp.getNext();
        }
        System.out.println();
    }
    
    public void moveToTheTop(BufferList block) {
        if(dummy.getNext() == tail || dummy.getNext().getNext() == tail || dummy.getNext() == block) {
            return;
        }
        BufferList searchPrev = block.getPrev();
        BufferList searchNext = block.getNext();          
        searchPrev.setNext(searchNext);
        searchNext.setPrev(searchPrev);
        BufferList oldTop = dummy.getNext();
        dummy.setNext(block);
        block.setPrev(dummy);
        block.setNext(oldTop);
        oldTop.setPrev(block);
//      printBuffers();
    }
    
    public void discardBlock() {
        BufferList prev = tail.getPrev();
        BufferList prevprev = tail.getPrev().getPrev();
        Buffer bf = prev.getBuffer();
        if(bf != null && bf.isDirty()) {
            try {
                writeToDisk(4096 * bf.getID(), bf.getData());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        prevprev.setNext(tail);
        tail.setPrev(prevprev);
        curNumOfBuffer--;
//        printBuffers();
//        System.out.println("block discarded!");
    }
    
    public byte[] readFromDisk(long index) throws Exception{
        byte[] buffer = new byte[4096];
        raf.seek(index);
        raf.read(buffer);
        reads++;
        return buffer;
    }

    public void writeAllDirtyBlockToDisk() {
        BufferList tmp = dummy.getNext();
        while(tmp != tail) {
            if(tmp.getBuffer().isDirty()) {
                try {
                    writeToDisk(tmp.getBuffer().getID() * 4096, tmp.getBuffer().getData());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tmp = tmp.getNext();
        }
        try {
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeToDisk(long index, byte[] data) throws Exception {
        raf.seek(index);
        raf.write(data);
        writes++;
    }
    
    public long getFileLength() {
        long length = 0;
        try {
            length = raf.length();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }
}
