import java.io.RandomAccessFile;
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

    public BufferPool() {
        dummy = new BufferList(false);
    }

    public void insert(byte[] space, int sz, int pos) {
        int blockID = pos / 4096;
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
        
        for(int i = 0; i < 4; i++) {
            insertBlock.getBuffer().getData()[i + pos - blockID * 4096] = space[i];
        }
    }

    public void getbytes(byte[] space, int sz, int pos) {
        int blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != null && searchBlock.getBuffer().getID() != blockID) {    
            searchBlock = searchBlock.getNext();
        }
        
        if(searchBlock != null) {
            for(int i = 0; i < 4; i++) {
                space[i] = searchBlock.getBuffer().getData()[i + pos - blockID * 4096];
            }
        }   
        else {
            
        }
        
    }
    
    private void setDirtyBit(int pos) {
        int blockID = pos / 4096;
        BufferList searchBlock = dummy.getNext();
        while(searchBlock != null && searchBlock.getBuffer().getID() != blockID) {    
            searchBlock = searchBlock.getNext();
        }
        if(searchBlock != null){
            searchBlock.getBuffer().setDirty(true);
        }
    }

    public void swap(int i, int j) {
        byte[] tmpJ = new byte[4];
        byte[] tmpI = new byte[4];
        getbytes(tmpJ, 4, j);
        getbytes(tmpI, 4, i);
        insert(tmpJ, 4, i);
        insert(tmpI, 4, j);
        setDirtyBit(i);
        setDirtyBit(j);
    }

    public byte[] readFromDisk(int index, String fileName) {
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

    public void writeToDisk(int index, byte[] data, String fileName) {
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
}
