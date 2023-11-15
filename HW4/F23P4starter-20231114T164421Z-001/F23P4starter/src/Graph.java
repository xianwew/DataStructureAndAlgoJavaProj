
public class Graph {
    private int size;
    private int adjacencyListLoad;
    private GraphList[] adjacencyList;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GraphList[] getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(GraphList[] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public int getAdjacencyListLoad() {
        return adjacencyListLoad;
    }

    public void setAdjacencyListLoad(int adjacencyListLoadLocal) {
        this.adjacencyListLoad = adjacencyListLoadLocal;
    }

    public Graph(int sizeLocal) {
        size = sizeLocal;
        setAdjacencyListLoad(0);
        adjacencyList = new GraphList[size];
        for (int i = 0; i < size; i++) {
            adjacencyList[i] = new GraphList();
        }
    }

    public GraphList findArtist(String artist) {
        GraphList artistNode = null;
        for (GraphList l : adjacencyList) {
            if(l.getNext() != null && l.getNext().isArtist() && l.getNext().getValue().equals(artist)) {
                artistNode = l.getNext();
                break;
            }
        }
        return artistNode;
    }

    public GraphList findSong(String song) {
        GraphList songNode = null;
        for (GraphList l : adjacencyList) {
            if(l.getNext() != null && !l.getNext().isArtist() && l.getNext().getValue().equals(song)) {
                songNode = l.getNext();
                break;
            }
        }
        return songNode;
    }

    public GraphList getUnoccupiedListRow() {
        GraphList dummyNode = null;
        for (GraphList l : adjacencyList) {
            if(l.getNext() == null) {
                dummyNode = l;
                break;
            }
        }
        adjacencyListLoad++;
        return dummyNode;
    }

    private void expandAdjacencyList() {
        GraphList[] newList = new GraphList[2*size];
        for(int i = 0; i < size; i++) {
            newList[i] = adjacencyList[i];
        }
        adjacencyList = newList;
        size *= 2;
    }

    public void insertNewRow(String artist, String song, boolean insertArtist) {
        GraphList newInsert = null;
        GraphList dummy = getUnoccupiedListRow();
        if(insertArtist) {
            newInsert = new GraphList(artist, true);
            dummy.setNext(newInsert);
            newInsert.setPrev(dummy);
            newInsert = new GraphList(song, false);
            dummy.getNext().setNext(newInsert);
            newInsert.setPrev(dummy.getNext());
        }
        else {
            newInsert = new GraphList(song, false);
            dummy.setNext(newInsert);
            newInsert.setPrev(dummy);
            newInsert = new GraphList(artist, true);
            dummy.getNext().setNext(newInsert);
            newInsert.setPrev(dummy.getNext());
        }
    }

    public boolean insert(String artist, String song) {
        if(adjacencyListLoad == size) {
            expandAdjacencyList();
        }

        GraphList newInsert = null;
        GraphList artistNode = findArtist(artist);
        if(artistNode != null) {
            newInsert = new GraphList(song, false);
            while(artistNode.getNext() != null) {
                if(artistNode.getNext().getValue().equals(song)) {
                    return false;
                }
                artistNode = artistNode.getNext();
            }
            artistNode.setNext(newInsert);
            newInsert.setPrev(artistNode);
        }
        else {
            insertNewRow(artist, song, true);
        }

        GraphList songNode = findArtist(song);
        if(songNode != null) {
            newInsert = new GraphList(artist, false);
            while(artistNode.getNext() != null) {
                artistNode = artistNode.getNext();
            }
            artistNode.setNext(newInsert);
            newInsert.setPrev(artistNode);
        }
        else {
            insertNewRow(artist, song, false);
        }

        return true;
    }

    public void remove(GraphList node) {
        if(node != null) {
            GraphList preNode = node.getPrev();
            GraphList nxtNode = node.getNext();
            preNode.setNext(nxtNode);
            if(nxtNode != null) {
                nxtNode.setPrev(preNode);
            }
            node.setNext(null);
            node.setPrev(null);
            if(preNode.getValue() == null && nxtNode == null) {
                adjacencyListLoad--;
            }
        }
    }
    
    public void printGraph() {
        System.out.println("Printing graph!");
    }
    
}
