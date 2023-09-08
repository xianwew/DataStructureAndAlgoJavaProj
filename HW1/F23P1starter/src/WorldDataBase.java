public class WorldDataBase {
    private SemManager semManager;
    private MyHashTable hashTable;

    public WorldDataBase(SemManager semManager, MyHashTable hashTable) {
        this.semManager = semManager;
        this.hashTable = hashTable;
    }
    
    public void processCommand(int instruction, int key, Seminar seminar) {
        switch(instruction) {
        case 1:
        	System.out.println("insert was called!");
        	hashTable.insert(semManager, key, seminar);
        	break;
        case 2:
        	System.out.println("search was called!");
        	hashTable.search(semManager, key);
        	break;
        case 3:
        	System.out.println("Print Hashtable was called!");
        	hashTable.printHashtable();
        	break;
        case 4:
        	System.out.println("Print blocks was called!");
        	semManager.printSemManager();
        	break;
        case 5:
        	System.out.println("Delete was called!");
        	hashTable.delete(semManager, key);
        	break;
    	default:
    		break;
        }
    }
}
