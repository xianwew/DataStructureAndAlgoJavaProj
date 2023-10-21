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
    
    public BufferPool(int size, String fName) {
        dummy = new BufferList(false);
        tail = new BufferList(false);
        dummy.setNext(tail);
        tail.setPrev(dummy);
        poolSize = size;
        fileName = fName;
        curNumOfBuffer = 0;
    }

    public int getSize() {
        return poolSize;
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
            byte[] dataRead = readFromDisk(pos - pos % 4096);
//            System.out.println("new insert index: " + (pos - pos % 4096));
//            System.out.println("new insert block ID: " + blockID);
            searchBlock = insertBufferToTop(blockID);
            searchBlock.setBuffer(new Buffer(dataRead, blockID));
        }   
        else{
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
//      System.out.println("Block with ID: " + blockID + " moved to the top!");
        BufferList searchPrev = block.getPrev();
        BufferList searchNext = block.getNext();          
        searchPrev.setNext(searchNext);
        searchNext.setPrev(searchPrev);
        BufferList oldTop = dummy.getNext();
        dummy.setNext(block);
        block.setPrev(dummy);
        block.setNext(oldTop);
        oldTop.setPrev(block);
//        printBuffers();
    }
    
    public void discardBlock() {
        BufferList prev = tail.getPrev();
        BufferList prevprev = tail.getPrev().getPrev();
        Buffer bf = prev.getBuffer();
        if(bf != null && bf.isDirty()) {
            writeToDisk(4096 * bf.getID(), bf.getData());
        }
        prevprev.setNext(tail);
        tail.setPrev(prevprev);
        curNumOfBuffer--;
//        printBuffers();
//          System.out.println("block discarded!");
    }
    
    public byte[] readFromDisk(long index) {
        byte[] buffer = new byte[4096];
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            raf.seek(index);
            raf.read(buffer);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void writeAllDirtyBlockToDisk() {
        BufferList tmp = dummy.getNext();
        while(tmp != tail) {
            if(tmp.getBuffer().isDirty()) {
                writeToDisk(tmp.getBuffer().getID() * 4096, tmp.getBuffer().getData());
            }
            tmp = tmp.getNext();
        }
    }
    
    
    public void writeToDisk(long index, byte[] data) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            raf.seek(index);
            raf.write(data);
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public long getFileLength() {
        long length = 0;
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            length = raf.length();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }
}
