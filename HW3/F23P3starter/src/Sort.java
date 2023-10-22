public class Sort {
    private BufferPool bufferPool;

    public Sort(BufferPool bp) {
        bufferPool = bp;
    }

    private int compareByteArray(byte[] a, byte[] b) {
        for (int i = 0; i < 2; i++) {
            if (a[i] > b[i]) {
                return 1;
            } 
            else if (a[i] < b[i]) {
                return -1;
            }
        }
        return 0;  
    }

    private long partition(long low, long high) {
        byte[] highSection = new byte[4];
        byte[] curSection = new byte[4];
        bufferPool.getbytes(highSection, 4, high);
        long i = (low - 1);
        for (long j = low; j <= high - 1; j++) {
            bufferPool.getbytes(curSection, 4, j);
            if (compareByteArray(curSection, highSection) == -1) { 
                i++;
                bufferPool.swap(i, j);
            }
        }
        bufferPool.swap(i + 1, high);
        return (i + 1);
    }

    public void quickSort(long low, long high) {
        if (low < high) {
            long pivot = partition(low, high);
            quickSort(low, pivot - 1);
            quickSort(pivot + 1, high);  
        }     
    }
}
