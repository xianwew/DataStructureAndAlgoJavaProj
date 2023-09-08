/**
* Myhashtable class runs a hash function on data
* scanned by the parser and performs insertion, deletion,
* search, and printing functions.
* @author jiren&xianwei
* @version September 2023, updated September 2023
*/
public class MyHashTable {
    private Handle[] values;
    private int[] keys;
    private int size;
    private int numOfElement;
    /**
    * Constructor for the class
    *
    * @param hashSize  defines the table size
    */
    public MyHashTable(int hashSize) {
        this.values = new Handle[hashSize];
        this.keys = new int[hashSize];
        this.size = hashSize;
        this.numOfElement = 0;
    }
    /**
    * Create a new Seminar object from the field data
    *
    * @param Handle input values
    * @param keys input keys
    * @param size input hashTable size
    * @param numofElement input num of keys
    */
   
    /**
    * Retrieve the size of the hash table from the input.
    * @return initialize hashTable size
    */
	public int gethashSize() {
		return size;
	}
	
	/**
	* The first hash function
	* 
	* @param id        input key 
	* @param localSize input the size of the hashTable
	* 
	* @return the first hashing result.
	*/
	public int calculateFirstHashing(int id, int localSize) {
		return id % localSize;
	}
	
	/**
	* The second hash function
	* 
	* @param id        input key 
	* @param localSize input the size of the hashTable
	* 
	* @return the second hashing result.
	*/
	public int calculateSecondHashing(int id, int localSize) {
		return (((id / localSize) % (localSize / 2)) * 2) + 1;
	}
	
	/**
	* The main hashing function
	* 
	* @param key        input key 
	* @param handle     input the handle used for insertion
	* @param seminar    the seminar object to be stored
	*/
	public void hashing(int key, Handle handle, Seminar seminar) {	 	
		keys[numOfElement] = key;
		numOfElement++;
		Handle handleAtIndex;
		try {
			handleAtIndex = values[calculateFirstHashing(key, size)];
			if (handleAtIndex.getStartIndex() != -1) {
				//System.out.println("Inserted to value in hastTable!");
				if (values[calculateSecondHashing(key, size)] != null) {
					int prevValue = calculateFirstHashing(key, size);
					while (values[(calculateSecondHashing(key, size) +
						prevValue) % size] != null) {
						prevValue = (calculateSecondHashing(key, size)
								+ prevValue) % size;
					}
					System.out.println("(doubleHashing) Ready to "
							+ "insert at index: " +
							(calculateSecondHashing(
									key, size) + prevValue) % size);
					values[(calculateSecondHashing(key, size)
					+ prevValue) % size] = handle;
				}
				else {
					System.out.println("(SecHashing) Ready to insert at index: "
							+ calculateSecondHashing(key, size));
					values[calculateSecondHashing(key, size)] = handle;
				}
			}
		}
		catch (Exception e) {
			//System.out.println("Inserted to value in hastTable!");
			System.out.println("(FirstHashing) Ready to insert at index: "
					+ calculateFirstHashing(key, size));
			values[calculateFirstHashing(key, size)] = handle;
		}
		if (numOfElement > size / 2) {
			reHash();
		}
		printHashtable();	
	}
	
	/**
	* Implement the insertion function for the hash table.
	* @param semManager a semManager object created by main.
	* @param key provided by the user.
	* @param seminar object created by the parser.
	*/
	public void insert(SemManager semManager, int key, Seminar seminar) {
		boolean containKey = false;
		for (int i : keys) {
			if (i == key) {
				containKey = true;
				break;
			}
		}
		if (!containKey) {
		 	try {
		 		Handle handle = semManager.insert(seminar.serialize(), key);
		 		hashing(key, handle, seminar);
		 	}
			catch (Exception e) {
				System.out.println("Error in inserting to the memory manager!");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Insert FAILED - "
					+ "There is already a record with ID " + key);
		}
	}
	
	/**
	* Implement the search function for the hash table.
	* @param semManager a semManager object created by main.
	* @param key provided by the user.
	* @return Whether it contains the key you are looking for.
	*/
	public boolean search(SemManager semManager, int key) {
		boolean containKey = false;
		for (int i : keys) {
			if (i == key) {
				containKey = true;
				break;
			}
		}
		if (!containKey) {
			System.out.println("Search FAILED -- "
					+ "There is no record with ID " + key);
		}
		else {	
			if (values[calculateFirstHashing(key, size)].getKey() == key) {
				System.out.println("Found record with ID " + key + ":");
				semManager.search(values[calculateFirstHashing(key, size)]);
				return true;
			}
			else {
				if (values[calculateSecondHashing(key, size)].getKey() == key) {
					System.out.println("Found record with ID " + key + ":");
		 			semManager.search(values
		 					[calculateSecondHashing(key, size)]);
		 			return true;
				}
				else {
					int prevValue = calculateFirstHashing(key, size);
					while (values[(calculateSecondHashing(key, size)
							+ prevValue) % size].getKey() != key) {
						prevValue = (calculateSecondHashing(key, size)
								+ prevValue) % size;
					}
		 			semManager.search(values[(calculateSecondHashing(key, size)
		 					+ prevValue) % size]);
		 			return true;	
				} 			
			}
		}
		return false;
	}
	
	/**
	* Implement the print function for the hash table.
	*/
	public void printHashtable() {
		System.out.println("Hashtable:");
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				System.out.println(i + ": " + values[i].getKey());
			}	
			else {
				//System.out.println(i + ": " + "TOMBSTONE");
			}
		}
	// 	for(int i = 0; i < keys.length; i++) {
	// 		if(keys[i] != 0){
	// 			System.out.println("key : " + keys[i]);
	// 		}			
	// 	}
	}
	
	/**
	* Implement the delete function for the hash table.
	* @param semManager a semManager object created by main.
	* @param key provided by the user.
	* @return return true if deletion succeeded
	*/
	public boolean delete(SemManager semManager, int key) {
		boolean containKey = false;
		boolean success = false;
		int[] tmp = new int[keys.length];
		int j = 0;
		for (int i : keys) {
			if (i == key) {
				containKey = true;
			}
			else {
				tmp[j] = i;
				j++;
			}	
		}
		keys = tmp;
		if (containKey) {
			if (values[calculateFirstHashing(key, size)].getKey() == key) {
				success = semManager.delete(
						values[calculateFirstHashing(key, size)]);
				values[calculateFirstHashing(key, size)] = null;
				numOfElement--;
			}
			else if (values[calculateSecondHashing(
					key, size)].getKey() == key) {
				success = semManager.delete(
						values[calculateSecondHashing(key, size)]);
				values[calculateSecondHashing(key, size)] = null;
				numOfElement--;
			}
			else {
				int prevValue = calculateFirstHashing(key, size);
				while (values[(calculateSecondHashing(key, size)
					+ prevValue) % size].getKey() != key) {
					prevValue = (calculateSecondHashing(key, size)
							+ prevValue) % size;
				}
				success = semManager.delete(
						values[(calculateSecondHashing(key, size)
						+ prevValue) % size]);
				values[(calculateSecondHashing(key, size)
						+ prevValue) % size] = null;
				numOfElement--;
			}
		}
		else {
			System.out.println("Delete FAILED -- "
					+ "There is no record with ID " + key);
		}
		if (success) {
			System.out.println("Successfully deleted record with ID " + key);
		}
		printHashtable();
		return success;
	}
	
	/**
	* Resizing the hashtable's size.
	*/
	public void reHash() {
		Handle[] tmpHandle = new Handle[size * 2];
		int[] tmpKey = new int[size * 2];
		Handle originalHandle = new Handle(-1, -1, -1);
		for (int i : keys) {
			try {
				originalHandle = values[calculateFirstHashing(i, size)];
			}
			catch (Exception e1) {
				try {
					originalHandle = values[calculateSecondHashing(i, size)];
				}
				catch (Exception e2) {
					System.out.println("Error in locating value!");
				}
			}
			
			try {
				Handle k = tmpHandle[calculateFirstHashing(i, size * 2)];
				if (k.getStartIndex() != -1) {
					tmpHandle[calculateSecondHashing(
							i, size * 2)] = originalHandle;
				}
			}
			catch (Exception e) {
				tmpHandle[calculateFirstHashing(i, size * 2)]
						= originalHandle;
			}	 		
		}
		
		int j = 0;
		for (int i : keys) {
			tmpKey[j] = i;
			j++;
		}
		this.size *= 2;	
		this.values = tmpHandle;
		this.keys = tmpKey;
		
		System.out.println("hashTable resized to: " + size);
		//printHashtable();	
	}
}


