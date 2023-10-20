import java.io.RandomAccessFile;
import java.io.IOException;

public class BufferPool implements BufferPoolADT {
    public class BufferList {
        private BufferList next;
        private BufferList previous;
        private Buffer buffer;

        public BufferList(boolean createBuffer) {
            if(createBuffer) {
                this.buffer = new Buffer();
            }
        }

        public BufferList(Buffer bf) {
            this.buffer = bf;
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
            insertBlock.getBuffer().getData()[i + pos] = space[i];
        }
    }

    public void getbytes(byte[] space, int sz, int pos) {

    }

    public void swap(int i, int j) {

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
