import java.io.*;
import java.util.*;

public class Sort {
    private BufferPool bufferPool;

    public Sort(BufferPool bp) {
        bufferPool = bp;
    }

    private void swap(int i, int j) {
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

    private int partition(int low, int high) {
        byte[] highSection = null;
        byte[] curSection = null;
        bufferPool.getbytes(highSection, 4, high);
        byte[] pivot = Arrays.copyOfRange(highSection, 0, 1);
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            bufferPool.getbytes(curSection, 4, j);
            byte[] curKey = Arrays.copyOfRange(curSection, 0, 1);
            if (compareByteArray(curKey, pivot) == -1) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }

    public void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }
    

//    private void printArr(int[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
//    }
//        public static void main(String[] args)
//        {
//            int[] arr = { 10, 7, 8, 9, 1, 5 };
//            int N = arr.length;
//
//            // Function call
//            quickSort(arr, 0, N - 1);
//            System.out.println("Sorted array:");
//            printArr(arr);
//        }

}
