public class BSTNode<T extends Comparable<? super T>> {
	private T key;
	private Seminar value;
	private BSTNode<T> left;
	private BSTNode<T> right;

	public BSTNode(T key, Seminar value) {
		this.key = key;
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public Seminar getValue() {
		return value;
	}

	public void setValue(Seminar value) {
		this.value = value;
	}

	public BSTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BSTNode<T> left) {
		this.left = left;
	}

	public BSTNode<T> getRight() {
		return right;
	}

	public void setRight(BSTNode<T> right) {
		this.right = right;
	}

	public int compareNode(BSTNode<T> node) {
		return this.key.compareTo(node.getKey());
	}
	
	public int compareKey(T key) {
		return this.key.compareTo(key);
	}
}
