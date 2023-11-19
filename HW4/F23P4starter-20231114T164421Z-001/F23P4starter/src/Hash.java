/**
 * Hash table class
 *
 * @author <Put Something Here>
 * @version <Put Something Here>
 */

public class Hash {
    private class KVPair {
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

        public KVPair(String keyLocal, GraphList graphListLocal) {
            setKey(keyLocal);
            setValue(graphListLocal);
        }
    }

    private int size;
    private KVPair[] keyValues;
    private int numberOfElements;
    private boolean isArtist;

    public Hash(int sizeLocal, boolean isArtistLocal) {
        size = sizeLocal;
        isArtist = isArtistLocal;
        keyValues = new KVPair[size];
    }

    public int searchForValueOrSlot(String key, boolean returnAvailableSlot) {
        int homeIndex = getHomeSlot(key, size);
        int awayFromHomeSlot = 0;

        while(true) {
            int slotToBeChecked = (awayFromHomeSlot * awayFromHomeSlot + homeIndex) % size;

            if(keyValues[slotToBeChecked] == null) {
                return returnAvailableSlot ? slotToBeChecked : -1;
            }
            else if(keyValues[slotToBeChecked].getValue() == null) {
                if(returnAvailableSlot) {
                    return slotToBeChecked;
                }
            }
            else if(keyValues[slotToBeChecked].getKey().equals(key)) {
                return returnAvailableSlot ? -1 : slotToBeChecked;
            }

            awayFromHomeSlot++;
        }
    }

    public GraphList getValue(String key) {
        int slot = searchForValueOrSlot(key, false);
        if(slot == -1) {
            return null;
        }
        return keyValues[slot].getValue();
    }

    /**
     * Implement the insertion function for the hash table.
     * 
     * @param key key to be inserted.
     * @return if insert was successful
     */
    public boolean insert(String key, GraphList value, boolean rehashing) {
        int availableSlot = searchForValueOrSlot(key, true);
        if(availableSlot != -1) {
            KVPair newPair = new KVPair(key, value);
            keyValues[availableSlot] = newPair;
            if(!rehashing) {
                numberOfElements++;
                if(numberOfElements > size / 2) {
                    reHash();
                }
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
        for (int i = 0; i < size; i++) {
            if(keyValues[i] != null) {
                if(keyValues[i].getValue() != null) {
                    totalValidValue++;
                    System.out.println(i + ": |" + keyValues[i].getKey() + "|");
                    //System.out.println(i + ": |" + keyValues[i].getKey() + "|" + " index: " + keyValues[i].getValue().getId());
                }
                else {
                    System.out.println(i + ": TOMBSTONE");
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

    /**
     * Implement the delete function for the hash table.
     * 
     * @param memManager a memManager object created by main.
     * @param key        provided by the user.
     * @return if deletion was successful
     */
    public boolean delete(String key) {
        int slot = searchForValueOrSlot(key, false);
        keyValues[slot].setKey("TOMBSTONE");
        keyValues[slot].setValue(null);
        numberOfElements--;
        return true;
    }

    /**
     * Resizing the hashtable's size.
     */
    public void reHash() {
        KVPair[] newKeyValues = new KVPair[size * 2];
        KVPair[] currentKeyValues = new KVPair[size];

        int indexOfCurKeyValues = 0;
        for (KVPair i : keyValues) {
            if(i != null && i.getValue() != null) {
                currentKeyValues[indexOfCurKeyValues] = i;
                indexOfCurKeyValues++;
            }
        }

        keyValues = newKeyValues;
        size *= 2;
        for (int i = 0; i < currentKeyValues.length; i++) {
            if(currentKeyValues[i] != null) {
                insert(currentKeyValues[i].getKey(), currentKeyValues[i].getValue(), true);
            }
        }

        String tableType = isArtist ? "Artist" : "Song";
        System.out.println(tableType + " hash table size doubled.");
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
