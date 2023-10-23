/**
 * The BufferPoolADT interface defines a set of methods for managing buffered
 * storage
 * 
 * @author xianwei & jiren
 * @version Oct 22nd
 */
public interface BufferPoolADT {
    /**
     * Copies a specified number of bytes from the provided byte array
     *
     * @param space The byte array space.
     * @param sz    The number of bytes to be copied.
     * @param pos   The position in the buffered storage
     */
    public void insert(byte[] space, int sz, long pos);

    /**
     * Copies a specified number of bytes from the buffered storage
     *
     * @param space The byte array space
     * @param sz    The number of bytes to be copied.
     * @param pos   The position in the buffered storage
     */
    public void getbytes(byte[] space, int sz, long pos);
}
