public class BinTree {
	int worldSize = 0;
	private BinNode root;
	
    public BinTree(int worldSizeLocal) {
    	this.worldSize = worldSizeLocal;
    	root = BinNodeEmpty.getNode();
    }
    
    public void insert(Seminar seminar) {
    	root = root.insert(0, worldSize - 1, seminar, 0, worldSize, worldSize);
    }
    
    public void delete(int id) {
    	
    }
	
    public void search(int x, int y) {
    	
    }
    
    public int printHelper(int level, BinNode node) {
        node.print(level);
        int leftCount = 0;
        int rightCount = 0;
        if(!node.isEmpty() && !node.isLeaf()) {
            BinNodeInternal tmp = (BinNodeInternal)node;
            leftCount = printHelper(level, tmp.getLeft());
            rightCount = printHelper(level, tmp.getRight());
        } 
        return 1 + leftCount + rightCount;
    }
    
    public int print() {
        int count = printHelper(0, root);
    	return count;
    }
    
    
}
