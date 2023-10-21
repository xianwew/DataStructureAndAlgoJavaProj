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
    
    public int getCurNumOfBuffer() {
        return curNumOfBuffer;
    }

    public void setCurNumOfBuffer(int numOfBuffer) {
        this.curNumOfBuffer = numOfBuffer;
    }
    
    public BufferList insertBufferToTop(long id) {
        BufferList insertBlock = new BufferList(true);
        insertBlock.getBuffer().setID(id);
        BufferList oldTop = dummy.getNext();
        dummy.setNext(insertBlock);
        insertBlock.setPrev(dummy);
        insertBlock.setNext(oldTop);
        oldTop.setPrev(insertBlock);
        setCurNumOfBuffer(getCurNumOfBuffer() + 1);
        return insertBlock;
    }
    
    public void insert(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList insertBlock = dummy.getNext();
        while(insertBlock != tail) { 
            if(insertBlock.getBuffer().getID() == blockID) {
                break;
            }
            insertBlock = insertBlock.getNext();
        }
        
        if(insertBlock == tail) {
            //System.out.println("insertBlock is null!");
            if(getCurNumOfBuffer() > poolSize - 1) {
                discardBlock();
            }
            insertBlock = insertBufferToTop(blockID);
        }
        else{
            moveToTheTop(pos);
        }
        
        for(int i = 0; i < sz; i++) {
            insertBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))] = space[i];
        } 
    }

    public void getbytes(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != tail) {  
            if(searchBlock.getBuffer().getID() == blockID) {
                //System.out.println("abab");
                break;
            }
            searchBlock = searchBlock.getNext();
        }
        
        if(searchBlock == tail) {
            if(getCurNumOfBuffer() > poolSize - 1) {
                discardBlock();
            }
            byte[] dataRead = readFromDisk(pos - pos % 4096);
            System.out.println("new insert index: " + (pos - pos % 4096));
            System.out.println("new insert block ID: " + blockID);
            searchBlock = insertBufferToTop(blockID);
//          System.out.println("cur number of block: " + poolSize);
            searchBlock.setBuffer(new Buffer(dataRead));
        }   
        else{
            moveToTheTop(pos);
        }
        
        for(int i = 0; i < sz; i++) {
            space[i] = searchBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))];
        }  
    }
    
    private void setDirtyBit(long pos) {
        long blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != tail) {  
            if(searchBlock.getBuffer().getID() == blockID) {
                break;
            }
            searchBlock = searchBlock.getNext();
        }
        if(searchBlock != null){
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
        setDirtyBit(i);
        setDirtyBit(j);
    }

    public void moveToTheTop(long pos) {
        if(dummy.getNext() == null || dummy.getNext().getNext() == null) {
            return;
        }

        long blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != tail) {    
            if(searchBlock.getBuffer().getID() == blockID) {
                break;
            }
            searchBlock = searchBlock.getNext();
        }
        
        if(dummy.getNext() != searchBlock) {
            System.out.println("Block with ID: " + blockID + " moved to the top!");
            BufferList searchPrev = searchBlock.getPrev();
            BufferList searchNext = searchBlock.getNext();          
            searchPrev.setNext(searchNext);
            searchNext.setPrev(searchPrev);
            BufferList oldTop = dummy.getNext();
            dummy.setNext(searchBlock);
            searchBlock.setPrev(dummy);
            searchBlock.setNext(oldTop);
            oldTop.setPrev(searchBlock);
        }
    }
    
    public void discardBlock() {
        BufferList prev = tail.getPrev();
        BufferList prevprev = tail.getPrev().getPrev();
        if(getCurNumOfBuffer() <= poolSize) { 
            return;
        }
        else {
            Buffer bf = prev.getBuffer();
            if(bf != null) {
                long blockID = bf.getID();
                if(bf.isDirty()) {
                    writeToDisk(4096 * blockID, bf.getData());
                }
            }
            prevprev.setNext(tail);
            tail.setPrev(prevprev);
            setCurNumOfBuffer(getCurNumOfBuffer() - 1);
//          System.out.println("curNumOfBuffer: " + getCurNumOfBuffer());
//          System.out.println("block discarded!");
        }
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
