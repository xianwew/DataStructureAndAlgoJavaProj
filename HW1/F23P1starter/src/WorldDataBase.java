/**
 * The `WorldDataBase` class represents a 
 * database that processes commands and interacts
 * with a hashtable and a seminar manager.
 * 
 * @author Jiren&xianwei
 * @version September 2023, updated September 2023
 */
public class WorldDataBase {
    private SemManager semManager;
    private MyHashTable hashTable;
    /**
     * Constructs a new `WorldDataBase` object with
     * the specified seminar manager and hashTable.
     *
     * @param semManager a memory manager reference
     * @param hashTable a hashTable class reference
     */
    public WorldDataBase(SemManager semManager, MyHashTable hashTable) {
        this.semManager = semManager;
        this.hashTable = hashTable;
    }
    /**
     * Processes a command based on the given instruction, key, and seminar.
     *
     * @param instruction The instruction code for the command.
     * @param key The key associated with the command.
     * @param seminar The seminar object associated with the command.
     */
    public void processCommand(int instruction, int key, Seminar seminar) {
        switch (instruction) {
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