public class WorldDataBase {
    private BST<Integer> idTree;
    private BST<Integer> costTree;
    private BST<String> dateTree;
    private BST<String> keywordTree;
    private BinTree locationTree;
    private int worldSize;
    
    public WorldDataBase(int worldSizeLocal){
        this.idTree = new BST<Integer>();
        this.costTree = new BST<Integer>();
        this.dateTree = new BST<String>();
        this.keywordTree = new BST<String>();
        this.locationTree = new BinTree();
        this.worldSize = worldSizeLocal;
    }
    
    public void processCommand (int instruction, int id, Seminar seminar, String[] data) {
        int instructionType = instruction / 10;
        instruction = instruction % 10;
        switch (instructionType) {
            case 1:
                insert(seminar);
                break;
            case 2:
                search(instruction, data);
                break;
            case 3:
                delete(id);
                break;
            case 4:
                print(instruction);
                break;
        }
    }
    
    public void insert(Seminar seminar) {
    	if(seminar.x() < 0 || seminar.y() < 0 || seminar.x() >= worldSize || seminar.y() >= worldSize) {
    	    System.out.println("Insert FAILED - Bad x, y coordinates: " + seminar.x() + ", " + seminar.y());
    	    return;
    	}
    	LinkedList<Integer> result = idTree.searchNode(seminar.id(), Integer.MIN_VALUE, false);
    	if(result == null) {
    	    System.out.println("Successfully inserted record with ID " + seminar.id());
	        System.out.println(seminar.toString());
	        idTree.insertNode(seminar.id(), seminar);
	        costTree.insertNode(seminar.cost(), seminar);
	        dateTree.insertNode(seminar.date(), seminar);
	        for(String s : seminar.keywords()) {
	            keywordTree.insertNode(s, seminar);
	        }
    	}
    	else {
    	    System.out.println("Insert FAILED - There is already a record with ID " + seminar.id());
    	    return;
    	}
    }
    
    public void search(int instruction, String[] data) {
        if (instruction == 1) {
        	LinkedList<Integer> result = idTree.searchNode(Integer.valueOf(data[0]), Integer.MIN_VALUE, false);
        	if(result == null) {
        		System.out.println("Search FAILED -- There is no record with ID " + data[0]);
        	}
        	else {
        		System.out.println(result.getVal().getValue().toString());
        	}
        }
        else if (instruction == 2) {
        	costTree.searchNode(Integer.valueOf(data[0]), Integer.valueOf(data[1]), true);
        }
        else if (instruction == 3) {
        	dateTree.searchNode(data[0], data[1], true);
        }
        else if (instruction == 4) {
            System.out.println("Seminars matching keyword " + data[0] + ":");
        	keywordTree.searchNode(data[0], "", false);
        }
    }
    
    public void delete(int id) {
    	System.out.println("delete " + id);
        Seminar tmp = null;
        tmp = idTree.deleteNode(id, id);
        if(tmp != null) {
        	costTree.deleteNode(tmp.cost(), id);
	        dateTree.deleteNode(tmp.date(), id);
	        for(String s : tmp.keywords()) {
	            keywordTree.deleteNode(s, id);
	        }
	        System.out.println("Record with ID " + id + " successfully deleted from the database");
        }
        else {
        	System.out.println("Delete FAILED -- There is no record with ID " + id);
        }
    }
    
    public void print(int instruction) {
        if (instruction == 1) {
        	System.out.println("ID Tree:");
        	idTree.print();
        }
        else if (instruction == 2) {
        	System.out.println("Date Tree:");
        	dateTree.print();
        }
        else if (instruction == 3) {
        	System.out.println("Keyword Tree:");
        	keywordTree.print();
        }
        else if (instruction == 4) {
        	System.out.println("Location Tree:");
//        	locationTree.print();
        }
        else if (instruction == 5) {
            System.out.println("Cost Tree:");
            costTree.print();
        }
    }
}
