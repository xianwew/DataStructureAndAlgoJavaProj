public class BinTree {
	int worldSize = 0;
	private BinNode root;
	
    public BinTree(int worldSizeLocal) {
    	this.worldSize = worldSizeLocal;
    	root = BinNodeEmpty.getNode();
    }
    
    public void insert(Seminar seminar) {
    	root = root.insert(worldSize, worldSize, seminar, worldSize, worldSize, worldSize)
    }
    
    public void delete(int id) {
    	
    }
	
    public void search(int x, int y) {
    	
    }
    
    public int print() {
    	
    	return 0;
    }
    
    
}
