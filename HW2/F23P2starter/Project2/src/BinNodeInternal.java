public class BinNodeInternal implements BinNode {
	private BinNode left;
	private BinNode right;
	
    public boolean isLeaf() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public BinNodeInternal(){
    	left = BinNodeEmpty.getNode();
    	right = BinNodeEmpty.getNode();
    }
    
    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
        // modify left and right node
    	// x, y <= mid point, go left, > go right. left = left.insert
        int mod = level % 2;
        if(mod == 0) {
            xWidth /= 2;
            if(seminar.x() < xWidth + x) {
                left =  left.insert(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.insert(x + xWidth, y, seminar, level + 1, xWidth, yWidth);
            }
        }
        else {
            yWidth /= 2;
            if(seminar.y() < yWidth + y) {
                left = left.insert(x, y, seminar, level + 1, xWidth, yWidth);
            }
            else {
                right = right.insert(x, y + yWidth, seminar, level + 1, xWidth, yWidth);
            }
        }   
    	return this;
    }

    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
        int mod = level % 2;
        if(mod == 0) {
            
        }
        return null;
    }

    public int search(int circuleX, int circuleY, int radius, int level, int xWidth, int yWidth) {
        int mod = level / 2;
        int leftVisited = getLeft().search(circuleX, circuleY, radius, level, xWidth, yWidth);
        int rightVisited = getRight().search(circuleX, circuleY, radius, level, xWidth, yWidth);
        return 1 + leftVisited + rightVisited;
    }

    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("I");
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
