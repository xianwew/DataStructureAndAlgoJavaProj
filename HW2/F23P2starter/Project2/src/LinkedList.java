/**
 *LinkedList class 
 * @author xianwei & jiren
 * @version September 2023, updated Oct 2023
 */

public class LinkedList {
    private Seminar val;
    private LinkedList next;
    private LinkedList prev;

    /**
     * Initialize paramaters.
     * 
     * @param val    input values
     * @param index  memory pool index
     */
    public LinkedList() {
        this.next = null;
        this.prev = null;
    }
    
    public LinkedList(Seminar val) {
        this.val = val;
        this.next = null;
        this.prev = null;
    }

    /**
     * Initialize next.
     * 
     * @return initialize next    
     */
    public LinkedList getNext() {
        return next;
    }
    /**
     * set the value of a field named next
     * @param next  Redefine the new next domain, receive next
     */
    public void setNext(LinkedList next) {
        this.next = next;
    }
    /**
     * Initialize prev.
     * 
     * @return initialize prev    
     */
    public LinkedList getPrev() {
        return prev;
    }
    /**
     * set the value of a field named prev
     * @param prev  Redefine the new next domain, receive previous 
     */
    public void setPrev(LinkedList prev) {
        this.prev = prev;
    }
    /**
     * Initialize Val.
     * 
     * @return initialize Val   
     */
    public Seminar getVal() {
        return val;
    }
    /**
     * set the value of a field named Val
     * @param val  Redefine the new next domain, receive values
     */
    public void setVal(Seminar val) {
        this.val = val;
    }
    
}

