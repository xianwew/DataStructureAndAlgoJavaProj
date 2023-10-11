/**
 * The node class for the BST.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 * @param <T> java generic
 */

public class BSTNode<T extends Comparable<? super T>> {
    private T key;
    private Seminar value;
    private BSTNode<T> left;
    private BSTNode<T> right;

    /**
     * Creates a new BSTNode with the specified key and value.
     *
     * @param key   The key associated with this node.
     * @param value The value associated with this node.
     */
    public BSTNode(T key, Seminar value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Gets the key associated with this node.
     *
     * @return The key of this node.
     */
    public T getKey() {
        return key;
    }

    /**
     * Sets the key associated with this node.
     *
     * @param key The new key for this node.
     */
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * Gets the value associated with this node.
     *
     * @return The value of this node.
     */
    public Seminar getValue() {
        return value;
    }

    /**
     * Sets the value associated with this node.
     *
     * @param value The new value for this node.
     */
    public void setValue(Seminar value) {
        this.value = value;
    }

    /**
     * Gets the left child node.
     *
     * @return The left child node.
     */
    public BSTNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the left child node.
     *
     * @param left The new left child node.
     */
    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    /**
     * Gets the right child node.
     *
     * @return The right child node.
     */
    public BSTNode<T> getRight() {
        return right;
    }

    /**
     * Sets the right child node.
     *
     * @param right The new right child node.
     */
    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    /**
     * Compares this node with another node based on their keys.
     *
     * @param node The other node to compare.
     * @return A negative integer if this node's key is less than the other
     *         node's key, zero if they are equal, or a positive integer if this
     *         node's key is greater.
     */
    public int compareNode(BSTNode<T> node) {
        return this.getKey().compareTo(node.getKey());
    }

    /**
     * Compares this node's key with a specified key.
     *
     * @param keyLocal The key to compare with.
     * @return A negative integer if this node's key is less than the specified
     *         key, zero if they are equal, or a positive integer if this node's
     *         key is greater.
     */
    public int compareKey(T keyLocal) {
        return this.getKey().compareTo(keyLocal);
    }
}
