public class BST<T extends Comparable<? super T>> {
	BSTNode<T> root;

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
		else if (curNode.compareKey(key) >= 0) {
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
			curNode.setLeft(deleteNodeHelper(curNode.getLeft(), curNode.getKey()));
		}
		return curNode;
	}

	public void deleteNode (T key) {
	    LinkedList<T> searchResult = searchNode(key, null);
	    while(searchResult != null) {
	        deleteNodeHelper(searchResult.getVal(), key);
	        searchResult = searchResult.getNext();
	    }
	}
	
	public BSTNode<T> searchNodeHelper(BSTNode<T> curNode, T key1, T key2, boolean left) {
		if (curNode == null) {
			return null;
		}
		
		BSTNode<T> leftNode = null;
		BSTNode<T> rightNode = null;
		if(key2 == null) {
    		if(curNode.compareKey(key1) >= 0) {
    			leftNode = searchNodeHelper(curNode.getLeft(), key1, key2, left);
    		}
    		else {
    			rightNode = searchNodeHelper(curNode.getRight(), key1, key2, left);
    		}
    		
		    if (curNode.compareKey(key1) == 0) {
	            return curNode;
	        } 
	        else if (leftNode != null && leftNode.compareKey(key1) == 0) {
	            return leftNode;
	        } 
	        else if (rightNode != null && rightNode.compareKey(key1) == 0) {
	            return rightNode;
	        } 
		}
		else {
		    if(curNode.compareKey(key2) > 0 || curNode.compareKey(key1) < 0 ) {
		        return null;
		    }
		    if(left) {
		        leftNode = searchNodeHelper(curNode.getLeft(), key1, key2, left);
		        if(leftNode != null && leftNode.compareKey(key1) > 0 && leftNode.compareKey(key2) < 0) {
		            return leftNode;
		        }
		    }
		    else{
		        rightNode = searchNodeHelper(curNode.getRight(), key1, key2, left);
		        if(rightNode != null && rightNode.compareKey(key1) > 0 && rightNode.compareKey(key2) < 0) {
                    return rightNode;
                }
		    }
		    
		}
		return null;
	}
	
	public LinkedList<T> searchNode(T key1, T key2) {
		LinkedList<T> curList = new LinkedList<T>();
		BSTNode<T> curNode = root;
		LinkedList<T> result = curList;
		while(searchNodeHelper(curNode, key1, key2, true)!=null) {
		    LinkedList<T> tmp = new LinkedList<T>();
		    curNode = searchNodeHelper(curNode,key1, key2, true);
		    tmp.setVal(curNode);
		    curNode = curNode.getLeft();
		    curList.setNext(tmp);
		    curList = curList.getNext();
		}
		if(String.valueOf(key2) != "" && String.valueOf(key2) != String.valueOf(Integer.MIN_VALUE)) {
		    while(searchNodeHelper(curNode, key1, key2, false)!=null) {
	            LinkedList<T> tmp = new LinkedList<T>();
	            curNode = searchNodeHelper(curNode,key1, key2, false);
	            tmp.setVal(curNode);
	            curNode = curNode.getRight();
	            curList.setNext(tmp);
	            curList = curList.getNext();
	        }
		}
		return result.getNext();
		
	}

	
}
