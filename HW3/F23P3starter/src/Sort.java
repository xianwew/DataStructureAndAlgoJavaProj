public class Sort {
    private BufferPool bufferPool;
    private final int INSERTION_SORT_THRESHOLD = 48;
    private final int REC_SIZE = 4;

    public Sort(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    private int compareByteArray(byte[] a, byte[] b) {
        int IntA = ((a[0] & 0xFF) << 8) | (a[1] & 0xFF);
        int IntB = ((b[0] & 0xFF) << 8) | (b[1] & 0xFF);
        return Integer.compare(IntA, IntB);
    }

    private long partition(long low, long high) {
        byte[] pivotSection = new byte[REC_SIZE];
        byte[] currentSection = new byte[REC_SIZE];

        bufferPool.getbytes(pivotSection, REC_SIZE, high);
        long i = (low - 1);

        for (long j = low; j < high; j++) {
            bufferPool.getbytes(currentSection, REC_SIZE, j);
            if (compareByteArray(currentSection, pivotSection) == -1) {
                i++;
                bufferPool.swap(i, j);
            }
        }
        bufferPool.swap(i + 1, high);
        return (i + 1);
    }

    private void insertionSort(long low, long high) {
        for (long i = low + 1; i <= high; i++) {
            byte[] current = new byte[REC_SIZE];
            bufferPool.getbytes(current, REC_SIZE, i);
            long j = i - 1;
            while (j >= low) {
                byte[] prev = new byte[REC_SIZE];
                bufferPool.getbytes(prev, REC_SIZE, j);
                if (compareByteArray(current, prev) < 0) {
                    bufferPool.swap(j + 1, j);
                    j--;
                }
                else {
                    break;
                }
            }
        }
    }

    public void quickSort(long low, long high) {
        while (low < high) {
            if (high - low < INSERTION_SORT_THRESHOLD) {
                insertionSort(low, high);
                break;
            }
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
