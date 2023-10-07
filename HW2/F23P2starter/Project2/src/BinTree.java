public class BinTree {
	int worldSize = 0;
	private BinNode root;
	
    public BinTree(int worldSizeLocal) {
    	this.worldSize = worldSizeLocal;
    	this.root = new BinNodeEmpty();
    }
    
    public void insertHelper(Seminar seminar) {
        
    }
    
    public void insert(Seminar seminar) {
    	if(root.isEmpty() == true) {
    	    root = new BinNodeLeaf(seminar);
//    	   insert sth
    	}
    	
    }
    
    public void delete(int id) {
    	
    }
	
    public void search(int x, int y) {
    	
    }
    
    
}
