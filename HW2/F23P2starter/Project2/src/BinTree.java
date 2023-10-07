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
    
    public void delete(Seminar seminar, int id) {
    	root = root.delete(0, worldSize - 1, seminar, 0, worldSize, worldSize);
    }
	
    public int searchHelper(int x, int y, int radius, BinNode node, int level) {
        if(!node.isEmpty() && node.isLeaf()) {
            BinNodeLeaf tmp = (BinNodeLeaf) node;
            SeminarList sl = tmp.getSeminarList();
            if(sl != null && sl.getSeminar() != null && Math.pow(sl.getSeminar().x(), 2) + Math.pow(sl.getSeminar().y(), 2) <= radius * radius) {
                while(sl != null) {
                    System.out.println("Found a record with key value " + sl.getSeminar().id() + " at " + sl.getSeminar().x() + ", " + sl.getSeminar().y());
                    sl = sl.getNext();
                }
            }
            return 1;
        }
        else if(node.isEmpty() && node.isLeaf()) {
            return 1;
        }
        else {
            int mod = level / 2;
            BinNodeInternal tmp = (BinNodeInternal) node;
            int leftVisited = searchHelper(x, y, radius, tmp.getLeft(), level);
            int rightVisited = searchHelper(x, y, radius, tmp.getRight(), level);
            return 1 + leftVisited + rightVisited;
        }
    }
    
    public int search(int x, int y, int radius) {
        return searchHelper(x, y, radius, root, 0);
    }
    
    public int printHelper(int level, BinNode node) {
        node.print(level);
        int leftCount = 0;
        int rightCount = 0;
        if(!node.isEmpty() && !node.isLeaf()) {
            BinNodeInternal tmp =  (BinNodeInternal) node;
            leftCount = printHelper(level + 1, tmp.getLeft());
            rightCount = printHelper(level + 1, tmp.getRight());
        } 
        return 1 + leftCount + rightCount;
    }
    
    public int print() {
        if(!(root.isEmpty() && root.isLeaf())) {
            int count = printHelper(0, root);
            return count;
        }
        return 0;
    }
    
    
}
