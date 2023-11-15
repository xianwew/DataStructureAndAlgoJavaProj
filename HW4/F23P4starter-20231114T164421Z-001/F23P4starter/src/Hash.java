/**
 * Hash table class
 *
 * @author <Put Something Here>
 * @version <Put Something Here>
 */

public class Hash {
    public class KVPair {
        private String key;
        private GraphList value;
        
        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public GraphList getValue() {
            return value;
        }
        
        public void setValue(GraphList value) {
            this.value = value;
        }
    }
    
    private int size;
    private KVPair[] keyValues;
    private int numberOfElements;
    private boolean isArtist;
    
    /**
     * Get the size valuable
     * 
     * @return the size
     */
    public int getSize() {
        return this.size;
    }

    public void setSize(int sizeLocal) {
        size = sizeLocal;
    }

    public Hash(int sizeLocal) {
        size = sizeLocal;
    }

    public boolean isArtist() {
        return isArtist;
    }

    public void setIsArtist(boolean isArtist) {
        this.isArtist = isArtist;
    }
    
    /**
     * Get the keys array
     * 
     * @return the keys array
     */
    public KVPair[] getKeyValues() {
        return this.keyValues;
    }

    /**
     * Set keys element
     * 
     * @param index input index
     * @param val   input key
     */
    public void setKeyValue(int index, String key, int value) {
        keyValues[index].setKey(key);
        keyValues[index].setKey(key);
    }

    /**
     * Get the last element index valuable
     * 
     * @return the lastElementIndex
     */
    public int getNumberOfElements() {
        return this.numberOfElements;
    }
    
    public Hash(int sizeLocal, boolean isArtistLocal) {
        setSize(sizeLocal);
        setIsArtist(isArtistLocal);
    }
    
    public int searchForValueOrSlot(String key, boolean returnAvailableSlot) {
        int homeIndex = getHomeSlot(key, getSize());
        if(keyValues[homeIndex] == null) {
            return returnAvailableSlot? homeIndex: -1;
        }
        else if(keyValues[homeIndex].getKey() == "TombStone") {
            if(returnAvailableSlot) {
                return homeIndex;
            }
        }

        int awayFromHomeSlot = 1;
        while(true) {
            int slotToBeChecked = (awayFromHomeSlot*awayFromHomeSlot + homeIndex) % size;
            if(keyValues[slotToBeChecked].getKey() ==  null) {
                return returnAvailableSlot? slotToBeChecked: -1;
            }
            else if(keyValues[slotToBeChecked].getKey() == "TombStone") {
                if(returnAvailableSlot) {
                    return slotToBeChecked;
                }
            }
            else if(keyValues[slotToBeChecked].getKey() == key) {
                return returnAvailableSlot? -1: slotToBeChecked;
            }
            awayFromHomeSlot++;
        }
    }
    
    /**
     * Implement the insertion function for the hash table.
     * 
     * @param key key to be inserted.
     * @return if insert was successful
     */
    public boolean insert(String key, GraphList value) {
        int availableSlot = searchForValueOrSlot(key, true);
        if(availableSlot != -1) {
            keyValues[availableSlot].setKey(key);
            keyValues[availableSlot].setValue(value);
            numberOfElements++;
            if(numberOfElements > size / 2) {
                reHash();
            } 
            return true;
        }
        return false;
    }

    /**
     * Implement the print function for the hash table.
     * 
     */
    public void printHashtable() {
        int totalValidValue = 0;
        for(int i = 0; i < size; i++) {
            if(keyValues[i].getKey() != "") {
                if(keyValues[i].getKey() != "TombStone") {
                    totalValidValue++;
                    System.out.println(i + " :|" + keyValues[i].getValue() + "|");
                }
                else {
                    System.out.println(i + ": " + keyValues[i].getValue());
                }
            }
        }
        if(isArtist) {
            System.out.println("total artists: " + totalValidValue);
        }
        else {
            System.out.println("total songs: " + totalValidValue);
        }
    }

    public boolean shouldSetTombStone(String key) {
        int homeIndex = getHomeSlot(key, getSize());
        if(keyValues[homeIndex].getKey() == key) {
            int slotToBeChecked = (1 + homeIndex) % size;
            if(keyValues[slotToBeChecked] == null) {
                return false;
            }
            return true;
        }
        
        int awayFromHomeSlot = 1;
        while(true) {
            int slotToBeChecked = (awayFromHomeSlot*awayFromHomeSlot + homeIndex) % size;
            if(keyValues[homeIndex].getKey() == key) {
                awayFromHomeSlot++;
                slotToBeChecked = (awayFromHomeSlot*awayFromHomeSlot + homeIndex) % size;
                if(keyValues[slotToBeChecked] == null) {
                    return false;
                }
                return true; 
            }
            awayFromHomeSlot++;
        }  
    }
    
    /**
     * Implement the delete function for the hash table.
     * 
     * @param memManager a memManager object created by main.
     * @param key        provided by the user.
     * @return if deletion was successful
     */
    public boolean delete(String key) {
        int slot = searchForValueOrSlot(key, false);
        if(slot == -1) {
            return false;
        }
        
        if(shouldSetTombStone(key)) {
            keyValues[slot].setKey("TombStone");
        }
        else {
            keyValues[slot].setKey("");
        }
        keyValues[slot].setValue(null);
        numberOfElements--;
        
        return true;
    }

    /**
     * Resizing the hashtable's size.
     */
    public void reHash() {
        KVPair[] newKeyValues = new KVPair[size * 2];

        for (Handle i : keys) {
            if(i != null && i.getKey() != -1) {
                hashing(i.getKey(), i, tmpHandle);
            }
        }

        int j = 0;
        for (int i : keys) {
            tmpKey[j] = i;
            j++;
        }

        this.size *= 2;
        this.keys = tmpHandle;
        this.keys = tmpKey;

        System.out.println("Hash table expanded to " + size + " records");
    }

    /**
     * Compute the hash function
     * 
     * @param s      The string that we are hashing
     * @param length Length of the hash table (needed because this method is static)
     * @return The hash function key (the home slot in the table for this key)
     */
    public static int getHomeSlot(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int) (Math.abs(sum) % length);
    }
}
