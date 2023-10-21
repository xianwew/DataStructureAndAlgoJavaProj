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
        //System.out.println("high: " + high);
        bufferPool.getbytes(highSection, 4, high);
        long i = (low - 4);
        for (long j = low; j <= high - 4; j = j + 4) {
            //System.out.println("j: " + j);
            bufferPool.getbytes(curSection, 4, j);
            if (compareByteArray(curSection, highSection) == -1) { 
                i = i + 4;
                bufferPool.swap(i, j);
            }
        }
        bufferPool.swap(i + 4, high);
        return (i + 4);
    }

    public void quickSort(long low, long high) {
        if (low < high) {
            //System.out.println("low: " + low);
            //System.out.println("high: " + high);
            long pivot = partition(low, high);
            quickSort(low, pivot - 4);
            quickSort(pivot + 4, high);  
        }     
    }
}
