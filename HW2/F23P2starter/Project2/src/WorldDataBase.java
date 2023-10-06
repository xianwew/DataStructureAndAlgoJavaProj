public class WorldDataBase {
    private BST<Integer> idTree;
    private BST<Integer> costTree;
    private BST<String> dateTree;
    private BST<String> keywordTree;
    private BinTree locationTree;
    
    public WorldDataBase(){
        this.idTree = new BST<Integer>();
        this.costTree = new BST<Integer>();
        this.dateTree = new BST<String>();
        this.keywordTree = new BST<String>();
        this.locationTree = new BinTree();
    }
    
    public void processCommand (int instruction, int id, Seminar seminar, String[] data) {
        int instructionType = instruction / 10;
        instruction = instruction % 10;
        switch (instructionType) {
            case 1:
                insert(seminar);
            case 2:
                search(instruction, data);
            case 3:
                delete(id);
            case 4:
                print(instruction);
        }
    }
    
    public void insert(Seminar seminar) {
        idTree.insertNode(seminar.id(), seminar);
        costTree.insertNode(seminar.cost(), seminar);
        dateTree.insertNode(seminar.date(), seminar);
        for(String s : seminar.keywords()) {
            keywordTree.insertNode(s, seminar);
        }
       
    }
    
    public void search(int instruction, String[] data) {
        switch (instruction) {
            case 1 : 
                idTree.searchNode(Integer.valueOf(data[0]), Integer.MIN_VALUE);
            case 2 :
                costTree.searchNode(Integer.valueOf(data[0]), Integer.valueOf(data[1]));
            case 3 :
                dateTree.searchNode(data[0], data[1]);
            case 4 :
                keywordTree.searchNode(data[0], "");
                
        }
    }
    
    public void delete(int id) {
        Seminar tmp = null;
        tmp = idTree.deleteNode(id, id);
        costTree.deleteNode(tmp.cost(), id);
        dateTree.deleteNode(tmp.date(), id);
        for(String s : tmp.keywords()) {
            keywordTree.deleteNode(s, id);
        }
    }
    
    public void print(int instruction) {
        switch (instruction) {
            case 1 : 
                idTree.print();
            case 2 :
                costTree.print();
            case 3 :
                dateTree.print();
            case 4 :
                keywordTree.print();
        }
    }
}
