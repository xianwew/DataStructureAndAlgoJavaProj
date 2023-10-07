public class BST<T extends Comparable<? super T>> {
	BSTNode<T> root;
	private int visitedCount = 0;

	public int getVisitedCount(){
		return this.visitedCount;
	}
	
	public BSTNode<T> getRoot(){
		return this.root;
	}
	
	public void setRoot(BSTNode<T> node){
		this.root = node;
	}
	
	public BST(){}
	
	public BST (T key, Seminar value) {
		this.root = new BSTNode<T>(key, value);
	}

	private void insertHelper(BSTNode<T> curNode, T key, Seminar val, BSTNode<T> parent) {
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

	public BSTNode<T> insertNode(T key, Seminar val) {
		if (root != null && (root.compareKey(key) < 0)) {
			insertHelper(root.getRight(), key, val, root);
		} 
		else if (root != null && (root.compareKey(key) >= 0)) {
			insertHelper(root.getLeft(), key, val, root);
		} 
		else {
			this.root = new BSTNode<T>(key, val);
		}
		return root;
	}

	public BSTNode<T> deleteNodeHelper(BSTNode<T> curNode, T key) {
		if (curNode == null) {
			return null;
		}
		
		if (curNode.compareKey(key) < 0) {
			curNode.setRight(deleteNodeHelper(curNode.getRight(), key));
		} 
		else if (curNode.compareKey(key) > 0) {
			curNode.setLeft(deleteNodeHelper(curNode.getLeft(), key));
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
			curNode.setLeft(deleteNodeHelper(curNode.getLeft(), cur.getKey()));
		}
		return curNode;
	}

	public Seminar deleteNode (T key, int id) {
	    LinkedList<T> searchResult = searchNode(key, null, false);
	    Seminar result = null;
	    while(searchResult != null) {
	        BSTNode<T> tmp = searchResult.getVal();
	        if(tmp.getValue().id() == id) {
	            if(String.valueOf(id).equals(String.valueOf(key))) {
	                result = tmp.getValue();
	            }
	            root = deleteNodeHelper(tmp, key); 
	        }
	        searchResult = searchResult.getNext();
	    }
	    return result;
	}
	
	public void addToLinkedList (BSTNode<T> nodeToBeAdded, LinkedList<T> head) {
		LinkedList<T> curList = head;
		while(curList.getNext() != null) {
			curList = curList.getNext();
		}
		curList.setNext(new LinkedList<T>(nodeToBeAdded));
	}
	
	public int searchNodeHelper(BSTNode<T> curNode, T key1, T key2, boolean range, LinkedList<T> head) {
		if (curNode == null) {
			return 1;
		}
		
		int leftVisited = 0;
		int rightVisited = 0;
		if(!range) {
    		if(curNode.compareKey(key1) >= 0) {
    			leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2, range, head);
    		}
    		
		    if (curNode.compareKey(key1) == 0) {
		    	addToLinkedList(curNode, head);
		    	return 1 + leftVisited + rightVisited;
	        }     
		    
		    if (curNode.compareKey(key1) < 0) {
		    	rightVisited = searchNodeHelper(curNode.getRight(), key1, key2, range, head);
		    }
		}
		else {
			if (curNode.compareKey(key2) > 0) {
				leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2, range, head);
				return 1 + leftVisited;
			}    
			else if (curNode.compareKey(key1) < 0) {
				rightVisited = searchNodeHelper(curNode.getRight(), key1, key2, range, head);
				return 1 + rightVisited;
	        }    
			else {
				leftVisited = searchNodeHelper(curNode.getLeft(), key1, key2, range, head);
				addToLinkedList(curNode, head);
				if(curNode.compareKey(key2) != 0) {
					rightVisited = searchNodeHelper(curNode.getRight(), key1, key2, range, head);
				}
			}
		}
		return 1 + leftVisited + rightVisited;
	}
	
	public LinkedList<T> searchNode(T key1, T key2, boolean range) {
		LinkedList<T> result = new LinkedList<T>();
		int count = searchNodeHelper(root, key1, key2, range, result);
		this.visitedCount = count;
		return result.getNext();
	}

	public void print() {
		if(root == null) {
			System.out.println("This tree is empty");
			return;
		}
	    int record = printHelper(root, 0);
	    System.out.println("Number of records: " + record);
	}
	
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
        left = printHelper(node.getLeft(), level + 1);
        return 1 + left + right;
    }

    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("  ");
        }
    }
}
