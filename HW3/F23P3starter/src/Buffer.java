/**
 * The Buffer class represents a data buffer with associated metadata.
 * 
 * @author xianwei & jiren
 * @version Oct 22nd
 */
public class Buffer {
    private boolean isDirty = false;
    private byte[] data;
    private long id = -1;
    private static final int REC_PER_BUFFER = 1024;
    private static final int REC_SIZE = 4;
    
    /**
     * Default constructor for Buffer. 
     */
    public Buffer() {
        data = new byte[REC_PER_BUFFER * REC_SIZE];
        isDirty = false;
    }

    /**
     * Constructor for Buffer that initializes the data 
     * 
     * @param newData The binary data to be stored in the buffer.
     * @param idLoc   The identifier for the buffer.
     */
    public Buffer(byte[] newData, long idLoc) {
        data = newData;
        isDirty = false;
        id = idLoc;
    }

    /**
     * Constructor for Buffer that initializes the data
     * 
     * @param idLoc The identifier for the buffer.
     */
    public Buffer(long idLoc) {
        data = new byte[REC_PER_BUFFER * REC_SIZE];
        isDirty = false;
        id = idLoc;
    }

    /**
     * Checks if the buffer is marked as dirty (modified).
     * 
     * @return True if the buffer is marked as dirty, false otherwise.
     */
    public boolean dirtyFlag() {
        return isDirty;
    }

    /**
     * Sets the dirty flag of the buffer to indicate if it has been modified.
     * 
     * @param dirty True to mark the buffer as dirty, false to mark it as
     *                clean.
     */
    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    /**
     * Retrieves the binary data stored in the buffer.
     * 
     * @return The binary data in the buffer.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the binary data in the buffer.
     * 
     * @param data The binary data to be stored in the buffer.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Retrieves the identifier (ID) associated with the buffer.
     * 
     * @return The identifier of the buffer.
     */
    public long getID() {
        return id;
    }

    /**
     * Sets the identifier (ID) for the buffer.
     * 
     * @param idLoc The identifier to be associated with the buffer.
     */
    public void setID(long idLoc) {
        id = idLoc;
    }
}
