public class BinTree {
	int worldSize = 0;
	private BinNode root;
	
    public BinTree(int worldSizeLocal) {
    	this.worldSize = worldSizeLocal;
    	this.root = new BinNodeEmpty();
    }
    
    public void insertHelper(Seminar seminar, int level, int xWidth, int yWidth, int x, int y) {
        if(level % 2 == 0) {
            xWidth = xWidth / 2;
            
        }
    }
    
    public void insert(Seminar seminar) {
    	if(root.isEmpty() == true) {
    	    root = new BinNodeLeaf(seminar);
//    	   insert sth
    	}
    	else {
    	   insertHelper(seminar, 0, worldSize-1, worldSize - 1, 0, worldSize-1);
    	}
    }
    
    public void delete(int id) {
    	
    }
	
    public void search(int x, int y) {
    	
    }
    
    
}
