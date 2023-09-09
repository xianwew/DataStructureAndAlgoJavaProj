/**
* Myhashtable class runs a hash function on data
* scanned by the parser and performs insertion, deletion,
* search, and printing functions.
* @author jiren&xianwei
* @version September 2023, updated September 2023
*/
public class MyHashTable {
    public Handle[] values;
    public int[] keys;
    public int size;
    public int lastElementIndex;
    /**
    * Constructor for the class
    *
    * @param hashSize  defines the table size
    */
    public MyHashTable(int hashSize) {
        this.values = new Handle[hashSize];
        this.keys = new int[hashSize];
        this.size = hashSize;
        this.lastElementIndex = 0;
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
	public int calculateFirstHashing  (int id, int localSize) {
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
	* @param insertArr    a array to store the handle
	* @param ignoreLastElementIndex    a boolean to specify 
	* if we need to change the lastElementIndex
	*/
	public void hashing(int key, Handle handle, 
			Handle[] insertArr) {	 	
		Handle handleAtIndex;
		int arrSize = insertArr.length;
		handleAtIndex = insertArr[calculateFirstHashing(key, arrSize)];
		if (handleAtIndex == null) {
			insertArr[calculateFirstHashing(key, arrSize)] = handle;
		}
		else if (handleAtIndex != null && handleAtIndex.getStartIndex() == -1) {
			insertArr[calculateFirstHashing(key, arrSize)] = handle;
		}
		else if (insertArr[calculateSecondHashing(key, arrSize)] != null) {
			if (insertArr[calculateSecondHashing(
					key, arrSize)].getStartIndex() == -1) {
				insertArr[calculateSecondHashing(key, arrSize)] = handle;
			}	
			else {
				int prevValue = calculateFirstHashing(key, arrSize);
				while (true) {
					if (insertArr[(calculateSecondHashing(key, arrSize) +
							prevValue) % arrSize] != null) {
						if (insertArr[(calculateSecondHashing(key, arrSize) +
								prevValue) % arrSize].getStartIndex() == -1) {
							insertArr[(calculateSecondHashing(key, arrSize)
							+ prevValue) % arrSize] = handle;
						}
						else {
							prevValue = (calculateSecondHashing(key, arrSize)
									+ prevValue) % arrSize;
						}
					}
					else {
						insertArr[(calculateSecondHashing(key, arrSize)
								+ prevValue) % arrSize] = handle;
						break;
					}
				}
			}
		}
		else if (insertArr[calculateSecondHashing(key, arrSize)] == null) {
			insertArr[calculateSecondHashing(key, arrSize)] = handle;	
		}
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
		 		Handle handle = semManager.insert(
		 				seminar.serialize(), key);	
		 		keys[lastElementIndex] = key;	
		 		lastElementIndex++;	
		 		hashing(key, handle, values);
				if (lastElementIndex > size / 2) {
					reHash();
				}
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
				System.out.println(i + ": " + (
						values[i].getKey() == -1 ? 
								"TOMBSTONE" : values[i].getKey()));
			}	
		}
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
			lastElementIndex--;
			if (values[calculateFirstHashing(key, size)].getKey() == key) {
				success = semManager.delete(
						values[calculateFirstHashing(key, size)]);
				values[calculateFirstHashing(key, size)].setKey(-1); 
				values[calculateFirstHashing(key, size)].setSize(-1); 
				values[calculateFirstHashing(key, size)].setStartIndex(-1); 
			}
			else if (values[calculateSecondHashing(
					key, size)].getKey() == key) {
				success = semManager.delete(
						values[calculateSecondHashing(key, size)]);
				values[calculateFirstHashing(key, size)].setKey(-1); 
				values[calculateFirstHashing(key, size)].setSize(-1); 
				values[calculateFirstHashing(key, size)].setStartIndex(-1); 
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
						+ prevValue) % size].setKey(-1);
				values[(calculateSecondHashing(key, size)
						+ prevValue) % size].setSize(-1);
				values[(calculateSecondHashing(key, size)
						+ prevValue) % size].setStartIndex(-1);
			}
		}
		else {
			System.out.println("Delete FAILED -- "
					+ "There is no record with ID " + key);
		}
		if (success) {
			System.out.println("Record with ID " + key + 
					" successfully deleted from the database");
		}
		return success;
	}
	
	/**
	* Resizing the hashtable's size.
	*/
	public void reHash() {
		Handle[] tmpHandle = new Handle[size * 2];
		int[] tmpKey = new int[size * 2];
		
		for (Handle i: values) {
			if (i != null) {
				hashing(i.getKey(), i, tmpHandle);
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
	}
}


