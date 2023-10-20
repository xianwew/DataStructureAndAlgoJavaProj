import java.io.RandomAccessFile;
import java.io.IOException;

public class BufferPool implements BufferPoolADT {
    public class BufferList{
        
        
    }
    
    public void insert(byte[] space, int sz, int pos) {

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
