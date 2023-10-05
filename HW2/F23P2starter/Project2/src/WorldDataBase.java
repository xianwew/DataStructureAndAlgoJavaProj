public class WorldDataBase {
    private BST<Integer> idTree;
    private BST<Integer> costTree;
    private BST<String> dateTree;
    private BST<String> keywordTree;
    private BinTree locationTree;
    public void processCommand (int instruction, int id, Seminar seminar, String[] data) {
        int instructionType = instruction / 10;
        instruction = instruction % 10;
        switch (instructionType) {
            case 1:
                insert(seminar);
            case 2:
                search(instruction, seminar);
            case 3:
                delete(id);
            case 4:
                print(instruction, seminar);
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
    
    public void search(int instruction, Seminar seminar) {
        switch (instruction) {
            case 1 : 
                idTree.searchNode(seminar.id());
            case 2 :
                costTree.searchNode(seminar.cost());
            case 3 :
                dateTree.searchNode(seminar.date());
            case 4 :
                keywordTree.searchNode(seminar.keywords());
                
        }
    }
    
    public void delete(int id) {
        
    }
    
    public void print(int instruction, Seminar seminar) {
        
    }
    
   
    
    
}
