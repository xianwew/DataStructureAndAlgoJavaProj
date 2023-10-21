import java.io.*;
import java.util.*;

public class Sort {
    private BufferPool bufferPool;

    public Sort(BufferPool bp) {
        bufferPool = bp;
    }

    private void swap(long i, long j) {
        bufferPool.swap(i, j);
    }

    private int compareByteArray(byte[] a, byte[] b) {
        if (a[0] > b[0]) {
            return 1;
        }
        else if (a[0] < b[0]) {
            return -1;
        }
        else {
            if (a[1] > b[1]) {
                return 1;
            }
            else if (a[1] < b[1]) {
                return -1;
            }
        }
        return 0;
    }

    private long partition(long low, long high) {
        byte[] highSection = new byte[4];
        byte[] curSection = new byte[4];
        System.out.println("high: " + high);
        bufferPool.getbytes(highSection, 4, high);
        byte[] pivot = Arrays.copyOfRange(highSection, 0, 2);
        long i = (low - 4);
        for (long j = low; j <= high - 4; j = j + 4) {
            //System.out.println("j: " + j);
            bufferPool.getbytes(curSection, 4, j);
            byte[] curKey = Arrays.copyOfRange(curSection, 0, 2);
            if (compareByteArray(curKey, pivot) == -1) {
                i = i + 4;
                swap(i, j);
            }
        }
        swap(i + 4, high);
        return (i + 4);
    }

    public void quickSort(long low, long high) {
        if (low < high) {
            long pi = partition(low, high);
            quickSort(low, pi - 4);
            quickSort(pi + 4, high);
        }
    }
}
