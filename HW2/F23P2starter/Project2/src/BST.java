/**
 * BST for storing key-value pairs.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 * @param <T> java generic
 */
public class BST<T extends Comparable<? super T>> {
    private BSTNode<T> root;
    private int visitedCount = 0;

    /**
     * Get the count of nodes visited during the search.
     *
     * @return The number of nodes visited.
     */
    public int getVisitedCount() {
        return this.visitedCount;
    }

    /**
     * Get the root node of the BST.
     *
     * @return The root node.
     */
    public BSTNode<T> getRoot() {
        return this.root;
    }

    /**
     * Set the root node of the BST.
     *
     * @param node The new root node.
     */
    public void setRoot(BSTNode<T> node) {
        this.root = node;
    }

    /**
     * Create an empty BST.
     */
    public BST() {
    }

    /**
     * Create a BST with a root node containing the specified key and value.
     *
     * @param key   The key of the root node.
     * @param value The value associated with the key.
     */
    public BST(T key, Seminar value) {
        this.root = new BSTNode<T>(key, value);
    }

    /**
     * Insert a key-value pair into the BST.
     *
     * @param key The key to insert.
     * @param val The value associated with the key.
     * @return The root node of the modified BST.
     */
    private void insertHelper(BSTNode<T> curNode, T key, Seminar val,
            BSTNode<T> parent) {
        if (curNode == null) {
            BSTNode<T> insert = new BSTNode<T>(key, val);
            if (parent.compareKey(key) >= 0) {
                parent.setLeft(insert);
            }
            else {
                parent.setRight(insert);
            }
            return;
        }
        if (curNode.compareKey(key) >= 0) {
            insertHelper(curNode.getLeft(), key, val, curNode);
        }
        else {
            insertHelper(curNode.getRight(), key, val, curNode);
        }
    }

    /**
     * Inserts a node with the specified key and value into the BST.
     *
     * @param key The key to be inserted.
     * @param val The value associated with the key.
     * @return The root node of the BST after the insertion.
     */
    public BSTNode<T> insertNode(T key, Seminar val) {
        if (root == null) {
            this.root = new BSTNode<T>(key, val);
        }
        else if (root.compareKey(key) < 0) {
            insertHelper(root.getRight(), key, val, root);
        }
        else {
            insertHelper(root.getLeft(), key, val, root);
        }
        return root;
    }

    /**
     * Delete a node with a specific key and ID from the BST.
     *
     * @param key     The key to delete.
     * @param curNode current node
     * @param id      the id to be deleted
     * @return The deleted Seminar or null if not found.
     */
    public BSTNode<T> deleteNodeHelper(BSTNode<T> curNode, T key, int id) {
//        if (curNode == null) {
//            return null;
//        }

        if (curNode.compareKey(key) < 0) {
            curNode.setRight(deleteNodeHelper(curNode.getRight(), key, id));
        }
        else if (curNode.compareKey(key) > 0) {
            curNode.setLeft(deleteNodeHelper(curNode.getLeft(), key, id));
        }
        else {
            if (curNode.getValue().id() != id) {
                curNode.setLeft(deleteNodeHelper(curNode.getLeft(), key, id));
            }
            else {
                if (curNode.getLeft() == null) {
                    return curNode.getRight();
                }
                else if (curNode.getRight() == null) {
                    return curNode.getLeft();
                }

                BSTNode<T> cur = curNode.getLeft();
                while (cur.getRight() != null) {
                    cur = cur.getRight();
                }
                curNode.setKey(cur.getKey());
                curNode.setValue(cur.getValue());
                curNode.setLeft(deleteNodeHelper(curNode.getLeft(),
                        curNode.getKey(), cur.getValue().id()));
            }
        }
        return curNode;
    }

    /**
     * Deletes a node from the BST.
     *
     * @param key The key to match for deletion.
     * @param id  The ID to match for deletion.
     */
    public void deleteNode(T key, int id) {
        root = deleteNodeHelper(root, key, id);
    }

    /**
     * Adds a BSTNode to the end of a linked list.
     *
     * @param nodeToBeAdded The BSTNode to add to the linked list.
     * @param head          The head of the linked list.
     */
    public void addToLinkedList(BSTNode<T> nodeToBeAdded, LinkedList<T> head) {
        LinkedList<T> curList = head;
        while (curList.getNext() != null) {
            curList = curList.getNext();
        }
        curList.setNext(new LinkedList<T>(nodeToBeAdded));
    }

    /**
     * Search for nodes within a specified range or matching a key.
     *
     * @param key1    The first key for range search or exact match.
     * @param key2    The second key for range search (ignored if it is not
     *                range search).
     * @param curNode current node
     * @param range   If true, perform a range search; if false, perform an
     *                exact match search.
     * @param head    the head of the linked list
     * @return the number of visited nodes.
     */
    public int searchNodeHelper(BSTNode<T> curNode, T key1, T key2,
            boolean range, LinkedList<T> head) {
        if (curNode == null) {
            return 1;
        }

        int leftVisited = 0;
        int rightVisited = 0;
        if (!range) {
            if (curNode.compareKey(key1) >= 0) {
                leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2,
                        range, head);
            }

            if (curNode.compareKey(key1) == 0) {
                addToLinkedList(curNode, head);
                return 1 + leftVisited + rightVisited;
            }

            if (curNode.compareKey(key1) < 0) {
                rightVisited = searchNodeHelper(curNode.getRight(), key1, key2,
                        range, head);
            }
        }
        else {
            if (curNode.compareKey(key2) > 0) {
                leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2,
                        range, head);
                return 1 + leftVisited;
            }
            else if (curNode.compareKey(key1) < 0) {
                rightVisited = searchNodeHelper(curNode.getRight(), key1, key2,
                        range, head);
                return 1 + rightVisited;
            }
            else {
                leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2,
                        range, head);
                addToLinkedList(curNode, head);
                if (curNode.compareKey(key2) != 0) {
                    rightVisited = searchNodeHelper(curNode.getRight(), key1,
                            key2, range, head);
                }
            }
        }
        return 1 + leftVisited + rightVisited;
    }

    /**
     * Searches for nodes within the BST based on a specified key range.
     *
     * @param key1  The lower bound of the key range.
     * @param key2  The upper bound of the key range.
     * @param range True if a range search should be performed, false for an
     *              exact match search.
     * @return A linked list containing the nodes matching the search criteria.
     */
    public LinkedList<T> searchNode(T key1, T key2, boolean range) {
        LinkedList<T> result = new LinkedList<T>();
        int count = searchNodeHelper(root, key1, key2, range, result);
        this.visitedCount = count;
        return result.getNext();
    }

    /**
     * Prints the contents of the BST.
     */
    public void print() {
        if (root == null) {
            System.out.println("This tree is empty");
            return;
        }
        int record = printHelper(root, 0);
        System.out.println("Number of records: " + record);
    }

    /**
     * Recursively prints the contents of the BST.
     *
     * @param node  The current node to print.
     * @param level The level of the current node in the tree.
     * @return The total number of nodes printed, including the current node.
     */
    private int printHelper(BSTNode<T> node, int level) {
        if (node == null) {
            printSpaces(level);
            System.out.println("null");
            return 0;
        }
        int left = 0;
        int right = 0;
        right = printHelper(node.getRight(), level + 1);
        printSpaces(level);
        System.out.println(node.getKey());
//      System.out.println(node.getKey() + ": " + node.getValue().id());
        left = printHelper(node.getLeft(), level + 1);
        return 1 + left + right;
    }

    /**
     * Prints a specified number of spaces to provide indentation in the output.
     *
     * @param count The number of spaces to print.
     */
    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("  ");
        }
    }
}
