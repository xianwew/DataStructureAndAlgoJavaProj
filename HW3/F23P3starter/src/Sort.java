public class Sort {
    private BufferPool bufferPool;

    public Sort(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    private int compareByteArray(byte[] a, byte[] b) {
        int IntA = ((a[0] & 0xFF) << 8) | (a[1] & 0xFF);
        int IntB = ((b[0] & 0xFF) << 8) | (b[1] & 0xFF);
        return Integer.compare(IntA, IntB);
    }

    private long partition(long low, long high) {
        byte[] pivotSection = new byte[4];
        byte[] currentSection = new byte[4];

        bufferPool.getbytes(pivotSection, 4, high);
        long i = (low - 1);

        for (long j = low; j < high; j++) {
            bufferPool.getbytes(currentSection, 4, j);
            if (compareByteArray(currentSection, pivotSection) == -1) {
                i++;
                if (i != j) {
                    bufferPool.swap(i, j);
                }
            }
        }
        if (i + 1 != high) {
            bufferPool.swap(i + 1, high);
        }
        return (i + 1);
    }

    public void quickSort(long low, long high) {
        while (low < high) {
            long pivot = partition(low, high);
            if (pivot - low < high - pivot) {
                quickSort(low, pivot - 1);
                low = pivot + 1;
            }
            else {
                quickSort(pivot + 1, high);
                high = pivot - 1;
            }
        }
    }
}
