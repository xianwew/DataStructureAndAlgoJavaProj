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
//    		System.out.println(seminar.toString());
    	    System.out.println("Insert FAILED - There is already a record with ID " + seminar.id());
    	    return;
    	}
    }
    
    public void search(int instruction, String[] data) {
    	switch (instruction) {
    		case 1:
    			LinkedList<Integer> resultId = idTree.searchNode(Integer.valueOf(data[0]), Integer.MIN_VALUE, false);
            	if(resultId == null) {
            		System.out.println("Search FAILED -- There is no record with ID " + data[0]);
            	}
            	else {
            	    System.out.println("Found record with ID " + data[0] + ":");
            		System.out.println(resultId.getVal().getValue().toString());
            	}
            	break;
    		case 2:
    		    System.out.println("Seminars with costs in range "+ data[0] + " to " + data[1] + ":"); 
    			LinkedList<Integer> resultCost = costTree.searchNode(Integer.valueOf(data[0]), Integer.valueOf(data[1]), true);
    			while(resultCost != null) {
    				System.out.println(resultCost.getVal().getValue().toString());
    				resultCost = resultCost.getNext();
    			}
    			break;
    		case 3:
    			System.out.println("Seminars with dates in range " + data[0] + " to " + data[1] + ":");
    			LinkedList<String> resultDate = dateTree.searchNode(data[0], data[1], true);
    			while(resultDate != null) {
    				System.out.println(resultDate.getVal().getValue().toString());
    				resultDate = resultDate.getNext();
    			}
    			break;
    		case 4:
    			System.out.println("Seminars matching keyword " + data[0] + ":");
    			LinkedList<String> resultKeyword = keywordTree.searchNode(data[0], "", false);
    			while(resultKeyword != null) {
    				System.out.println(resultKeyword.getVal().getValue().toString());
    				resultKeyword = resultKeyword.getNext();
    			}
            	break;
    		case 5:
    			System.out.println("search location " + data[0] + " " + data[1] + " " + data[2]);
    			break;
    	}
    }
    
    public void delete(int id) {
//    	System.out.println("delete " + id);
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
    	switch (instruction) {
    		case 1:
            	System.out.println("ID Tree:");
            	idTree.print();
            	break;
    		case 2:
            	System.out.println("Date Tree:");
            	dateTree.print();
            	break;
    		case 3:
            	System.out.println("Keyword Tree:");
            	keywordTree.print();
            	break;
    		case 4:
            	System.out.println("Location Tree:");
//            	locationTree.print();
            	break;
    		case 5:
                System.out.println("Cost Tree:");
                costTree.print();
                break;
    	}
    }
}
