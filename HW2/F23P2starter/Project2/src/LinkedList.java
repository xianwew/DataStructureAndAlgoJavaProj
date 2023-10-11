/**
 * LinkedList class
 * 
 * @author xianwei & jiren
 * @version September 2023, updated Oct 2023
 * @param <T> java generic
 */

public class LinkedList<T extends Comparable<? super T>> {
    private BSTNode<T> val;
    private LinkedList<T> next;

    /**
     * Initialize.
     */
    public LinkedList() {
        this.next = null;
    }

    /**
     * Initialize .
     * 
     * @param val value
     */
    public LinkedList(BSTNode<T> val) {
        this.val = val;
        this.next = null;
    }

    /**
     * Initialize next.
     * 
     * @return initialize next
     */
    public LinkedList<T> getNext() {
        return next;
    }

    /**
     * set the value of a field named next
     * 
     * @param next Redefine the new next domain, receive next
     */
    public void setNext(LinkedList<T> next) {
        this.next = next;
    }

    /**
     * Initialize Val.
     * 
     * @return initialize Val
     */
    public BSTNode<T> getVal() {
        return val;
    }

}
