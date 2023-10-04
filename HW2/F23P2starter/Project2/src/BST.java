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

	public BSTNode<T> deleteNode(BSTNode<T> curNode, T key) {
		if (curNode == null) {
			return null;
		}

		if (curNode.compareKey(key) < 0) {
			curNode.setRight(deleteNode(curNode.getRight(), key));
		} 
		else if (curNode.compareKey(key) >= 0) {
			curNode.setLeft(deleteNode(curNode.getLeft(), key));
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
			curNode.setLeft(deleteNode(curNode.getLeft(), curNode.getKey()));
		}
		return curNode;
	}

	public BSTNode<T> searchNode(BSTNode<T> curNode, T key, BSTNode<T>[] searchRes) {
		if (curNode == null) {
			return curNode;
		}
		
		BSTNode<T> leftNode = null;
		BSTNode<T> rightNode = null;
		
		if(curNode.compareKey(key) >= 0) {
			leftNode = searchNode(curNode.getLeft(), key, searchRes);
		}
		else {
			rightNode = searchNode(curNode.getRight(), key, searchRes);
		}

		if (curNode.compareKey(key) == 0) {
			return curNode;
		} 
		else if (leftNode != null && leftNode.compareKey(key) == 0) {
			return leftNode;
		} 
		else if (rightNode != null && rightNode.compareKey(key) == 0) {
			return rightNode;
		}
		return null;
	}

}
