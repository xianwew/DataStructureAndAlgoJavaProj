/**
 *Freelist class serves as a bridge between 
 *the memory manager and the hashtable, 
 *representing a list of currently
 *unoccupied memory blocks within the memory pool.
 * @author xianwei & jiren
 * @version July 2023, updated August 2023
 */

public class FreeList {
    private int val;
    private FreeList next;
    private FreeList prev;
    private int index;

    /**
     * Initialize paramaters.
     * 
     * @param val    input values
     * @param index  memory pool index
     */
    
    
    public FreeList(int val, int index) {
        this.val = val;
        this.next = null;
        this.prev = null;
        this.index = index;
    }

    /**
     * Initialize next.
     * 
     * @return initialize next    
     */
    public FreeList getNext() {
        return next;
    }
    /**
     * set the value of a field named next
     * @param next  Redefine the new next domain, receive next
     */
    public void setNext(FreeList next) {
        this.next = next;
    }
    /**
     * Initialize prev.
     * 
     * @return initialize prev    
     */
    public FreeList getPrev() {
        return prev;
    }
    /**
     * set the value of a field named prev
     * @param prev  Redefine the new next domain, receive previous 
     */
    public void setPrev(FreeList prev) {
        this.prev = prev;
    }
    /**
     * Initialize Val.
     * 
     * @return initialize Val   
     */
    public int getVal() {
        return val;
    }
    /**
     * set the value of a field named Val
     * @param Val  Redefine the new next domain, receive values
     */
    public void setVal(int val) {
        this.val = val;
    }
    /**
     * Initialize Index.
     * 
     * @return initialize Index   
     */
    public int getIndex() {
        return index;
    }
    /**
     * set the value of a field named Index
     * @param index  Redefine the new next domain, receive index 
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
}

