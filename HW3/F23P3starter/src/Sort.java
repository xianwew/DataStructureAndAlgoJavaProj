
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

    private long medianOfThree(long low, long high) {
        long mid = (low + high) / 2;

        byte[] lowSection = new byte[4];
        byte[] midSection = new byte[4];
        byte[] highSection = new byte[4];

        bufferPool.getbytes(lowSection, 4, low);
        bufferPool.getbytes(midSection, 4, mid);
        bufferPool.getbytes(highSection, 4, high);

        if (compareByteArray(lowSection, midSection) > 0) {
            bufferPool.swap(low, mid);
        }
        if (compareByteArray(lowSection, highSection) > 0) {
            bufferPool.swap(low, high);
        }
        if (compareByteArray(midSection, highSection) > 0) {
            bufferPool.swap(mid, high);
        }

        bufferPool.swap(mid, high);
        return high;
    }

    private long partition(long low, long high) {
        long pivotIndex = medianOfThree(low, high);

        byte[] highSection = new byte[4];
        byte[] pivotSection = new byte[4];

        bufferPool.getbytes(highSection, 4, pivotIndex);
        long i = (low - 1);

        for (long j = low; j < high; j++) {
            bufferPool.getbytes(pivotSection, 4, j);
            if (compareByteArray(pivotSection, highSection) == -1) {
                i++;
                bufferPool.swap(i, j);
            }
        }
        bufferPool.swap(i + 1, high);
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

//    private long findpivot(long i, long j) {
//        return (i + j) / 2;
//    } 
//    private long partition(long left, long right, long pivot) {
//        byte[] rightSection = new byte[4];   
//        byte[] pivotSection = new byte[4];   
//        byte[] leftSection = new byte[4];   
//        while (left <= right) { 
//            bufferPool.getbytes(rightSection, 4, right);
//            bufferPool.getbytes(pivotSection, 4, pivot);
//            bufferPool.getbytes(leftSection, 4, left);
//            while (compareByteArray(leftSection, pivotSection) == -1) {
//                left++;
//                if(left < 4096){
//                    bufferPool.getbytes(leftSection, 4, left);
//                }  
//            }
//            while ((right >= left) && compareByteArray(rightSection, pivotSection) != -1) {
//                right--;
//                if(right >= 0) {
//                    bufferPool.getbytes(rightSection, 4, right);
//                }   
//            }
//            if (right > left) {
//                bufferPool.swap(left, right);
//            } 
//        }
//        return left; 
//    }
//
//    public void quicksort(long i, long j) { 
//        long pivotindex = findpivot(i, j); 
//        bufferPool.swap(pivotindex, j);
//        byte[] jSection = new byte[4]; 
//        bufferPool.getbytes(jSection, 4, j);
//        //System.out.println(ByteBuffer.wrap(new byte[]{0, 0, jSection[0], jSection[1]}).getInt());
//        long k = partition(i, j - 1, ByteBuffer.wrap(new byte[]{0, 0, jSection[0], jSection[1]}).getInt());
//        bufferPool.swap(k, j);
//        if ((k - i) > 1) {
//            quicksort(i, k - 1);
//        } 
//        if ((j - k) > 1) {
//            quicksort(k + 1, j);
//        } 
//    }
//}
