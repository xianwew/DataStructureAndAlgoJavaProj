public class BinNodeInternal implements BinNode {
	private BinNode left;
	private BinNode right;
	
    public boolean isLeaf() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
        return null;
    }

    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
        return null;
    }

    public int search(int circuleX, int circuleY, int level, int xWidth, int yWidth) {
        return 0;
    }

    public void print(int level) {
        
    }

	public BinNode getLeft() {
		return left;
	}

	public void setLeft(BinNode left) {
		this.left = left;
	}

	public BinNode getRight() {
		return right;
	}

	public void setRight(BinNode right) {
		this.right = right;
	}
    
    
}
