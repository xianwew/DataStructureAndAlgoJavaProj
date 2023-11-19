/**
 * GraphList class
 *
 * @author <Xianwei && Jiren>
 * @version <Nov, 2023>
 */

public class GraphList {
    private int id = -1;
    private GraphList prev;
    private GraphList next;

    /**
     * Sets the ID of the node.
     *
     * @param idLocal The ID to set for the node.
     */
    public void setId(int idLocal) {
        id = idLocal;
    }

    /**
     * Gets the ID of the node.
     *
     * @return The ID of the node.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the previous node in the linked list.
     *
     * @return The previous node.
     */
    public GraphList getPrev() {
        return prev;
    }

    /**
     * Sets the previous node in the linked list.
     *
     * @param prevLocal The previous node to set.
     */
    public void setPrev(GraphList prevLocal) {
        this.prev = prevLocal;
    }

    /**
     * Gets the next node in the linked list.
     *
     * @return The next node.
     */
    public GraphList getNext() {
        return next;
    }

    /**
     * Sets the next node in the linked list.
     *
     * @param nextLocal The next node to set.
     */
    public void setNext(GraphList nextLocal) {
        this.next = nextLocal;
    }

    /**
     * Constructs an empty GraphList node.
     */
    public GraphList() {
    }

    /**
     * Constructs a GraphList node with the specified ID.
     *
     * @param idlocal The ID to set for the node.
     */
    public GraphList(int idlocal) {
        setId(idlocal);
    }
}
