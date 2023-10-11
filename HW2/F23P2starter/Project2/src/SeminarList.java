/**
 * The SeminarList class represents a node in a linked list used to store
 * seminar objects
 *
 * @author xianwei & jiren
 * @version Oct 2023
 */
public class SeminarList {

    private Seminar seminar = null;
    private SeminarList next = null;

    /**
     * Get the Seminar object stored in this node.
     *
     * @return The Seminar object stored in this node.
     */
    public Seminar getSeminar() {
        return seminar;
    }

    /**
     * Set the Seminar object to be stored in this node.
     *
     * @param seminar The Seminar object to be stored in this node.
     */
    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    /**
     * Get the reference to the next node in the linked list.
     *
     * @return The reference to the next node in the linked list.
     */
    public SeminarList getNext() {
        return next;
    }

    /**
     * Set the reference to the next node in the linked list.
     *
     * @param next The reference to the next node in the linked list.
     */
    public void setNext(SeminarList next) {
        this.next = next;
    }

}
