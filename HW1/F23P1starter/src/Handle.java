/**
 * Handle class represents a handle 
 * used to manage memory allocations. It
 * contains information about the 
 * starting index, size, and a key associated
 * with the handle.
 * 
 * @author xianwei & jiren
 * @version September 2023, updated September 2023
 */
public class Handle {
    private int startIndex = -1;
    private int size = -1;
    private int key = -1;

    /**
     * start a construction includes new 
     * object with the specified start index,
     * size, and key.
     *
     * @param start the start of the memory allocation
     * @param size  The size of the memory allocation.
     * @param key   The key associated with the handle.
     */
    public Handle(int start, int size, int key) {
        this.startIndex = start;
        this.size = size;
        this.key = -1;
    }

    /**
     * Gets the starting index of the memory allocation.
     *
     * @return The starting index.
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    /**
     * Gets the size of the memory allocation.
     *
     * @return The size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Sets the starting index of the memory allocation.
     *
     * @param startIndex The new starting index to set.
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * Sets the size of the memory allocation.
     *
     * @param endIndex the end of the memory index
     */
    public void setSize(int endIndex) {
        this.size = endIndex;
    }

    /**
     * Gets the key associated with the handle.
     *
     * @return The key.
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Sets the key associated with the handle.
     *
     * @param key The new key to set.
     */
    public void setKey(int key) {
        this.key = key;
    }

}
