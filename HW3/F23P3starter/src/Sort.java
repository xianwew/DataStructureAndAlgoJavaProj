import java.util.Random;

/**
 * The Sort class is responsible for performing quicksort
 * 
 * @author xianwei & jiren
 * @version Oct 22nd
 */
public class Sort {
    private BufferPool bufferPool;
    private static final int REC_SIZE = 4;
    private static final int SAMPLE_SIZE = 5;
    private Random rand = new Random();

    /**
     * Constructs a Sort object associated with the given BufferPool.
     *
     * @param bufferPool The BufferPool object to perform sorting on.
     */
    public Sort(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    /**
     * Compares two byte arrays
     *
     * @param a The first byte array to compare.
     * @param b The second byte array to compare.
     * @return A negative value if the first byte array is less, a positive
     *         value if the second byte array is less, or zero if they are
     *         equal.
     */
    private int compareByteArray(byte[] a, byte[] b) {
        int intA = ((a[0] & 0xFF) << 8) | (a[1] & 0xFF);
        int intB = ((b[0] & 0xFF) << 8) | (b[1] & 0xFF);
        return Integer.compare(intA, intB);
    }

    /**
     * Performs a three-way partitioning of elements within a given range.
     *
     * @param low    The lower bound of the range to be partitioned.
     * @param high   The upper bound of the range to be partitioned.
     * @param result An array to store the resulting indices of the partition.
     */
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

    /**
     * Performs a two-way partitioning of elements within a given range.
     *
     * @param low  The lower bound of the range to be partitioned.
     * @param high The upper bound of the range to be partitioned.
     * @return The index of the pivot element after partitioning.
     */
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

    /**
     * Determines whether the three-way partitioning algorithm should be used.
     *
     * @param low  The lower bound of the range to be partitioned.
     * @param high The upper bound of the range to be partitioned.
     * @return true if the three-way partitioning algorithm should be used,
     *         false otherwise.
     */
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

    /**
     * Sorts the elements within the given range using the quicksort algorithm.
     *
     * @param low  The lower bound of the range to be sorted.
     * @param high The upper bound of the range to be sorted.
     */
    public void quickSort(long low, long high) {
        while (low < high) {
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
