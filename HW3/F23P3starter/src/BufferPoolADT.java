public interface BufferPoolADT {
    // Copy "sz" bytes from "space" to position "pos" in the buffered storage
    public void insert(byte[] space, int sz, long pos);

    // Copy "sz" bytes from position "pos" of the buffered storage to "space"
    public void getbytes(byte[] space, int sz, long pos);
}
