import java.io.RandomAccessFile;
import java.util.Arrays;
import java.io.IOException;

public class BufferPool implements BufferPoolADT {
    public class BufferList {
        private BufferList next;
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
        }
        
        if(insertBlock == null) {
            insertBlock = new BufferList(true);
            insertBlock.getBuffer().setID(blockID);
            prev.setNext(insertBlock);
        }
        
        for(int i = 0; i < sz; i++) {
            //System.out.println("BlockID: " + blockID);
            System.out.println("pos: " + (pos + i));
            insertBlock.getBuffer().getData()[(int) (i + pos % (sz * 1024))] = space[i];
        }
        
        moveToTheTop(pos);
        discardBlock();
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
            searchBlock = new BufferList(true);
            searchBlock.getBuffer().setID(blockID);
            searchBlock.setBuffer(new Buffer(dataRead));
            prev.setNext(searchBlock);
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
        
        if(searchBlock != null) {
            byte[] dataToMove = searchBlock.getBuffer().getData();
            long idToMove = searchBlock.getBuffer().getID();
            byte[] dataToSwap = dummy.getNext().getBuffer().getData();
            long idToSwap = dummy.getNext().getBuffer().getID();
            searchBlock.getBuffer().setData(dataToSwap);
            searchBlock.getBuffer().setID(idToSwap);
            dummy.getNext().getBuffer().setData(dataToMove);
            dummy.getNext().getBuffer().setID(idToMove);
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
            return;
        }
        else {
            Buffer bf = prevprev.getBuffer();
            if(bf != null) {
                long blockID = prevprev.getBuffer().getID();
                if(bf.isDirty()) {
                    for(int i = 0; i < bf.getData().length; i = i + 4) {
                        writeToDisk(i + 4096 * blockID, Arrays.copyOfRange(bf.getData(), i, i + 3));
                    }
                }
            }
            prevprev.setNext(null);
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
