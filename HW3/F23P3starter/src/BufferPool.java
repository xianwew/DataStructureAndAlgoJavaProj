import java.io.RandomAccessFile;
import java.util.Arrays;
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
    private int poolSize;
    private String fileName;
    
    public BufferPool(int size, String fName) {
        dummy = new BufferList(false);
        poolSize = size;
        fileName = fName;
    }

    public int getSize() {
        return poolSize;
    }
    
    public void insert(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList insertBlock = dummy.getNext();
        BufferList prev = dummy;
        while(insertBlock != null && insertBlock.getBuffer().getID() != blockID) {    
            prev = insertBlock;
            insertBlock = insertBlock.getNext();
            //System.out.println("abb");
        }
        
        if(insertBlock == null) {
            System.out.println("adwad");
            insertBlock = new BufferList(true);
            insertBlock.getBuffer().setID(blockID);
            prev.setNext(insertBlock);
            insertBlock.setPrev(prev);
        }
        
        for(int i = 0; i < sz; i++) {
            //System.out.println("BlockID: " + blockID);
            //System.out.println("pos: " + (pos + i));
            insertBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))] = space[i];
        }
    }

    public void getbytes(byte[] space, int sz, long pos) {
        long blockID = pos / (sz * 1024);
        BufferList searchBlock = dummy.getNext();
        BufferList prev = dummy;
        while(searchBlock != null && searchBlock.getBuffer().getID() != blockID) {   
            prev = searchBlock;
            searchBlock = searchBlock.getNext();
        }
        
        if(searchBlock == null) {
            byte[] dataRead = readFromDisk(pos - pos % 4096);
            System.out.println("new insert index: " + (pos - pos % 4096));
            System.out.println("new insert block ID: " + blockID);
            searchBlock = new BufferList(true);
            searchBlock.getBuffer().setID(blockID);
            searchBlock.setBuffer(new Buffer(dataRead));
            prev.setNext(searchBlock);
            searchBlock.setPrev(prev);
            discardBlock();
        }   
        
        for(int i = 0; i < sz; i++) {
            space[i] = searchBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))];
        }
        
        moveToTheTop(pos);
    }
    
    private void setDirtyBit(long pos) {
        long blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != null && searchBlock.getBuffer().getID() != blockID) {    
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
        moveToTheTop(i);
        moveToTheTop(j);
    }

    public void moveToTheTop(long pos) {
        if(dummy.getNext() == null || dummy.getNext().getNext() == null) {
            return;
        }

        long blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != null && searchBlock.getBuffer().getID() != blockID) {    
            searchBlock = searchBlock.getNext();
        }
        
        if(searchBlock != null && dummy.getNext() != searchBlock) {
            System.out.println("moved to the top!");
            BufferList topBlock = dummy.getNext();
            BufferList searchPrev = searchBlock.getPrev();
            BufferList searchNext = searchBlock.getNext();
            BufferList topPrev = topBlock.getPrev();
            BufferList topNext = topBlock.getNext();
            searchBlock.setPrev(topPrev);
            searchBlock.setNext(topNext);
            searchPrev.setNext(topBlock);
            if(searchNext != null) {
                searchNext.setPrev(topBlock);
            }
            topBlock.setPrev(searchPrev);
            topBlock.setNext(searchNext);
            topPrev.setNext(searchBlock);
            topNext.setPrev(searchBlock);
        }
    }
    
    public void discardBlock() {
        int curSize = 0;
        BufferList curList = dummy.getNext();
        BufferList prev = dummy;
        BufferList prevprev = null;
        while(curList != null) {
            curSize++;
            prevprev = prev;
            prev = curList;
            curList = curList.getNext();
        }
        
        if(curSize <= poolSize) {
            System.out.println(curSize);
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
            prevprev.setNext(null);
            prev.setPrev(null);
            System.out.println("block discarded!");
        }
    }
    
    public byte[] readFromDisk(long index) {
        byte[] returnVal = new byte[4096];
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            raf.seek(index);
            raf.read(returnVal);
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return returnVal;
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
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            raf.seek(0);
            length = raf.length();
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }
}
