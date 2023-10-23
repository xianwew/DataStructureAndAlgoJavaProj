import java.util.Random;

public class Sort {
    private BufferPool bufferPool;
    private static final int INSERTION_SORT_THRESHOLD = 48;
    private static final int REC_SIZE = 4;
    private static final int SAMPLE_SIZE = 3;
    private Random rand = new Random();

    public Sort(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    private int compareByteArray(byte[] a, byte[] b) {
        int IntA = ((a[0] & 0xFF) << 8) | (a[1] & 0xFF);
        int IntB = ((b[0] & 0xFF) << 8) | (b[1] & 0xFF);
        return Integer.compare(IntA, IntB);
    }

    private void threeWayPartition(long low, long high, long[] result) {
        byte[] pivotSection = new byte[REC_SIZE];
        bufferPool.getbytes(pivotSection, REC_SIZE, low);

        long lessThan = low;
        long greaterThan = high;
        long i = low;

        while (i <= greaterThan) {
            byte[] currentSection = new byte[REC_SIZE];
            bufferPool.getbytes(currentSection, REC_SIZE, i);
            int compareRes = compareByteArray(currentSection, pivotSection);

            if (compareRes < 0) {
                bufferPool.swap(lessThan, i);
                lessThan++;
                i++;
            }
            else if (compareRes > 0) {
                bufferPool.swap(i, greaterThan);
                greaterThan--;
            }
            else {
                i++;
            }
        }

        result[0] = lessThan;
        result[1] = greaterThan;
    }

    private long twoWayPartition(long low, long high) {
        byte[] pivotSection = new byte[REC_SIZE];
        byte[] currentSection = new byte[REC_SIZE];

        bufferPool.getbytes(pivotSection, REC_SIZE, high);
        long i = low - 1;

        for (long j = low; j < high; j++) {
            bufferPool.getbytes(currentSection, REC_SIZE, j);
            if (compareByteArray(currentSection, pivotSection) == -1) {
                i++;
                bufferPool.swap(i, j);
            }
        }
        bufferPool.swap(i + 1, high);
        return i + 1;
    }

    private boolean shouldUseThreeWay(long low, long high) {
        int duplicates = 0;
        byte[][] samples = new byte[SAMPLE_SIZE][REC_SIZE];

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            long pos = low + rand.nextInt((int) (high - low + 1));
            bufferPool.getbytes(samples[i], REC_SIZE, pos);
        }

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            for (int j = i + 1; j < SAMPLE_SIZE; j++) {
                if (compareByteArray(samples[i], samples[j]) == 0) {
                    duplicates++;
                }
            }
        }

        return duplicates >= (SAMPLE_SIZE / 2);
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

            if (shouldUseThreeWay(low, high)) {
                long[] partitionResults = new long[2];
                threeWayPartition(low, high, partitionResults);
                long lessThan = partitionResults[0];
                long greaterThan = partitionResults[1];

                quickSort(low, lessThan - 1);
                quickSort(greaterThan + 1, high);
                return;
            }
            else {
                long pivot = twoWayPartition(low, high);
                quickSort(low, pivot - 1);
                low = pivot + 1;
            }
        }
    }
}
