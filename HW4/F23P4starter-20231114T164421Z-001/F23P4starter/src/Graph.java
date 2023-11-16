public class Graph {
    private static class UnionFind {
        private int[] parent;
        private int[] rank;
        private int[] size;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
                size[i] = 1; 
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                    size[rootX] += size[rootY];
                }
            }
        }
        
        public int maxSize() {
            int max = 0;
            for (int i = 0; i < parent.length; i++) {
                if (i == parent[i]) { 
                    max = Math.max(max, size[i]);
                }
            }
            return max;
        }
    }
    
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

    public void insertNode(GraphList firstNode, GraphList nodeToBeInserted) {
//        if(firstNode == null) {
//            return;
//        }
//        
        GraphList next = firstNode.getNext();
        firstNode.setNext(nodeToBeInserted);
        nodeToBeInserted.setPrev(firstNode);
        nodeToBeInserted.setNext(next);
        if(next != null) {
            next.setPrev(nodeToBeInserted);
        }
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
            GraphList newInsertSongInArtistRow = new GraphList(songNode.getId());
            insertNode(artistNode, newInsertSongInArtistRow);
            GraphList newInsertArtistInSongRow = new GraphList(artistNode.getId());
            insertNode(songNode, newInsertArtistInSongRow);
            return new GraphList[] { artistNode, songNode };
        }

        if(songNodeId == -1) {
            songNode = insertNewNodeToNewRow();
            GraphList newArtist = new GraphList(artistNodeId);
            insertNode(songNode, newArtist);
            artistNode = findNode(artistNodeId);
            GraphList newInsertSong = new GraphList(songNode.getId());
            insertNode(artistNode, newInsertSong);
            
            return new GraphList[] { artistNode, songNode };
        }

        if(artistNodeId == -1) {
            artistNode = insertNewNodeToNewRow();
            GraphList newSong = new GraphList(songNodeId);
            insertNode(artistNode, newSong);
            songNode = findNode(artistNodeId);
            GraphList newInsertArtist = new GraphList(artistNode.getId());
            insertNode(songNode, newInsertArtist);
            
            return new GraphList[] { artistNode, songNode };
        }

        if(artistNodeId != -1 && songNodeId != -1) {
            GraphList songAppendToArtist = new GraphList(songNodeId);
            GraphList artistAppendToSong = new GraphList(artistNodeId);
            artistNode = findNode(artistNodeId);
            songNode = findNode(songNodeId);
            
            insertNode(artistNode, songAppendToArtist);
            insertNode(songNode, artistAppendToSong);
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
    
    public int countComponents() {
        UnionFind uf = new UnionFind(adjacencyListLoad);

        for (int i = 0; i < adjacencyListLoad; i++) {
            if(adjacencyList[i].getNext() != null) {
                GraphList curNode = adjacencyList[i].getNext();
                while(curNode != null) {
                    int j = curNode.getId();
                    uf.union(i, j);
                    curNode = curNode.getNext();
                }
            }
        }

        int count = 0;
        for (int i = 0; i < adjacencyListLoad; i++) {
            if (uf.find(i) == i) {
                count++;
            }
        }

        return count;
    }
    
    public int findLargestComponentSize() {
        UnionFind uf = new UnionFind(adjacencyListLoad);
        for (int i = 0; i < adjacencyListLoad; i++) {
            if(adjacencyList[i].getNext() != null) {
                GraphList curNode = adjacencyList[i].getNext();
                while(curNode != null) {
                    int j = curNode.getId();
                    uf.union(i, j);
                    curNode = curNode.getNext();
                }
            }
        }
        return uf.maxSize();
    }
    
    public void printGraph() {
//        for(GraphList dummy: adjacencyList) {
//            GraphList curNode = dummy.getNext();
//            if(curNode != null) {
//                System.out.println();
//                System.out.print(curNode.getId() + " ");
//                curNode = curNode.getNext();
//            }
//            while(curNode != null) {
//                System.out.print(curNode.getId() + " ");
//                curNode = curNode.getNext();
//            }
//            
//        }
//        System.out.println();
        int connected = countComponents();
        int largestConnectedComponent = findLargestComponentSize();
        System.out.println("There are " + connected + " connected components");
        System.out.println("The largest connected component has " + largestConnectedComponent + " elements");
    }

}