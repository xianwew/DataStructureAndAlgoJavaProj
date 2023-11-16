
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

    public GraphList findNode(int id) {
        GraphList curNode = null;
        for (GraphList l : adjacencyList) {
            if(l.getNext() != null && l.getNext().getId() == id) {
                curNode = l.getNext();
                break;
            }
        }
        return curNode;
    }

    public GraphList getUnoccupiedListRow() {
        GraphList dummyNode = null;
        int index = 0;
        for (index = 0; index < size; index++) {
            if(adjacencyList[index].getNext() == null) {
                dummyNode = adjacencyList[index];
                dummyNode.setId(index);
                break;
            }
        }

        adjacencyListLoad++;
        return dummyNode;
    }

    private void expandAdjacencyList() {
        GraphList[] newList = new GraphList[2 * size];
        for (int i = 0; i < size; i++) {
            newList[i] = adjacencyList[i];
        }
        adjacencyList = newList;
        size *= 2;
        for (int i = 0; i < size; i++) {
            if(adjacencyList[i] == null) {
                adjacencyList[i] = new GraphList();
            }
        }
    }

    public GraphList insertNewNodeToNewRow() {
        GraphList dummy = getUnoccupiedListRow();
        int index = dummy.getId();
        dummy.setId(-1);
        GraphList newNode = new GraphList(index);
        dummy.setNext(newNode);
        newNode.setPrev(dummy);
        return newNode;
    }

    public GraphList[] insert(int artistNodeId, int songNodeId) {
        if(adjacencyListLoad == size) {
            expandAdjacencyList();
        }

        GraphList artistNode = null;
        GraphList songNode = null;
        if(artistNodeId == -1 && songNodeId == -1) {
            artistNode = insertNewNodeToNewRow();
            songNode = insertNewNodeToNewRow();
//            System.out.println("artistNodeId: " + artistNode.getId());
//            System.out.println("songNodeId: " + songNode.getId());
            GraphList newInsertSongInArtistRow = new GraphList(songNode.getId());
            newInsertSongInArtistRow.setPrev(artistNode);
            artistNode.setNext(newInsertSongInArtistRow);
            GraphList newInsertArtistInSongRow = new GraphList(artistNode.getId());
            newInsertArtistInSongRow.setPrev(songNode);
            songNode.setNext(newInsertArtistInSongRow);
            return new GraphList[] { artistNode, songNode };
        }

        if(songNodeId == -1) {
            songNode = insertNewNodeToNewRow();
            artistNode = findNode(artistNodeId);
            GraphList next = artistNode.getNext();
            GraphList newInsertSong = new GraphList(songNode.getId());
            artistNode.setNext(newInsertSong);
            newInsertSong.setPrev(artistNode);
            newInsertSong.setNext(next);
            if(next != null) {
                next.setPrev(newInsertSong);
            }
            return new GraphList[] { artistNode, songNode };
        }

        if(artistNodeId == -1) {
            artistNode = insertNewNodeToNewRow();
            songNode = findNode(artistNodeId);
            GraphList next = songNode.getNext();
            GraphList newInsertArtist = new GraphList(artistNode.getId());
            artistNode.setNext(newInsertArtist);
            newInsertArtist.setPrev(songNode);
            newInsertArtist.setNext(next);
            if(next != null) {
                next.setPrev(newInsertArtist);
            }
            return new GraphList[] { artistNode, songNode };
        }

        return new GraphList[] { artistNode, songNode };
    }

    public void removeNode(GraphList node) {
        GraphList rowElement = node.getNext();
        while(rowElement != null) {
            GraphList rowNodeAsKey = adjacencyList[rowElement.getId()];
            if(rowNodeAsKey != null) {
                GraphList rowNodeAsKeyRowElement = rowNodeAsKey.getNext();
                while(rowNodeAsKeyRowElement != null) {
                    if(rowNodeAsKeyRowElement.getId() == node.getId()) {
                        removeSingleNode(rowNodeAsKeyRowElement);
                        break;
                    }
                    rowNodeAsKeyRowElement = rowNodeAsKeyRowElement.getNext();
                }

//                if(rowNodeAsKey.getNext() == null) {
//                    graph.remove(songWrittenByThatArtist);
//                    songs.delete(curArtistSong.getValue());
//                }
            }

            rowElement = rowElement.getNext();
        }

        removeWholeRow(node);
    }

    public void removeWholeRow(GraphList firstNode) {
        GraphList dummy = firstNode.getPrev();
        dummy.setNext(null);
        firstNode.setPrev(null);
    }

    public void removeSingleNode(GraphList node) {
        if(node != null) {
            GraphList preNode = node.getPrev();
            GraphList nxtNode = node.getNext();
            preNode.setNext(nxtNode);
            if(nxtNode != null) {
                nxtNode.setPrev(preNode);
            }
            node.setNext(null);
            node.setPrev(null);
            if(preNode.getId() == -1 && nxtNode.getNext() == null) {
                adjacencyListLoad--;
            }
        }
    }

    
    
    
    
    
    
    
    public void printGraph() {
        System.out.println("Printing graph!");
    }

}
