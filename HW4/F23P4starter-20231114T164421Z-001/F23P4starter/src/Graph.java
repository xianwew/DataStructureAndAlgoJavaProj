/**
 * Graph class
 *
 * @author <Xianwei && Jiren>
 * @version <Nov, 2023>
 */

public class Graph {

    /**
     * UnionFind class, it keeps track of parent nodes, ranks, and sizes of each
     * set.
     */
    private class UnionFind {
        private int[] parent;
        private int[] rank;
        private int[] size;

        /**
         * Constructs a new UnionFind object with a given number of elements.
         *
         * @param n The number of elements to initialize the data structure
         *          with.
         */
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

        /**
         * Find operation to determine the root of the set to which an element
         * belongs.
         *
         * @param i The element whose root is to be found.
         * @return The root element of the set to which the input element
         *         belongs.
         */
        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        /**
         * Union operation to merge two sets represented by their root elements.
         *
         * @param x The root element of the first set.
         * @param y The root element of the second set.
         */
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
                else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                    size[rootX] += size[rootY];
                }
            }
        }

        /**
         * Calculate the number of largest connected component's elements
         *
         * @return The number of largest connected component's elements.
         */
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
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Constructor: sets the load for the adjacency list.
     *
     * @param adjacencyListLoadLocal The load to set.
     */
    public void setAdjacencyListLoad(int adjacencyListLoadLocal) {
        this.adjacencyListLoad = adjacencyListLoadLocal;
    }

    /**
     * Constructs a new graph with the given size.
     *
     * @param sizeLocal The size of the graph.
     */
    public Graph(int sizeLocal) {
        size = sizeLocal;
        setAdjacencyListLoad(0);
        adjacencyList = new GraphList[size];
        for (int i = 0; i < size; i++) {
            adjacencyList[i] = new GraphList();
        }
    }

    /**
     * Finds a node with the specified ID in the graph.
     *
     * @param id The ID of the node to find.
     * @return The found GraphList node or null if not found.
     */
    public GraphList findNode(int id) {
        GraphList curNode = null;
        for (GraphList l : adjacencyList) {
            if (l.getNext() != null && l.getNext().getId() == id) {
                curNode = l.getNext();
                break;
            }
        }
        return curNode;
    }

    /**
     * Gets an unoccupied list row from the adjacency list.
     *
     * @return An unoccupied GraphList node.
     */
    public GraphList getUnoccupiedListRow() {
        GraphList dummyNode = null;
        int index = 0;
        for (index = 0; index < size; index++) {
            if (adjacencyList[index].getNext() == null) {
                dummyNode = adjacencyList[index];
                dummyNode.setId(index);
                break;
            }
        }

        adjacencyListLoad++;
        return dummyNode;
    }

    /**
     * Expands the adjacency list of the graph.
     */
    private void expandAdjacencyList() {
        GraphList[] newList = new GraphList[2 * size];
        for (int i = 0; i < size; i++) {
            newList[i] = adjacencyList[i];
        }
        adjacencyList = newList;
        size *= 2;
        for (int i = 0; i < size; i++) {
            if (adjacencyList[i] == null) {
                adjacencyList[i] = new GraphList();
            }
        }
    }

    /**
     * Inserts a new GraphList node into a new row of the adjacency list.
     *
     * @return The newly inserted GraphList node.
     */
    public GraphList insertNewNodeToNewRow() {
        if (adjacencyListLoad == size) {
            expandAdjacencyList();
        }
        GraphList dummy = getUnoccupiedListRow();
        int index = dummy.getId();
        dummy.setId(-1);
        GraphList newNode = new GraphList(index);
        dummy.setNext(newNode);
        newNode.setPrev(dummy);
        return newNode;
    }

    /**
     * Inserts a GraphList node after a specified node in the adjacency list.
     *
     * @param firstNode        The node before which the new node is inserted.
     * @param nodeToBeInserted The new node to insert.
     */
    public void insertNode(GraphList firstNode, GraphList nodeToBeInserted) {
        GraphList next = firstNode.getNext();
        firstNode.setNext(nodeToBeInserted);
        nodeToBeInserted.setPrev(firstNode);
        nodeToBeInserted.setNext(next);
        if (next != null) {
            next.setPrev(nodeToBeInserted);
        }
    }

    /**
     * Inserts nodes into the graph based on the given artist and song IDs.
     *
     * @param artistNodeId The ID of the artist node.
     * @param songNodeId   The ID of the song node.
     * @return An array containing the inserted artist and song nodes.
     */
    public GraphList[] insert(int artistNodeId, int songNodeId) {
        GraphList artistNode = null;
        GraphList songNode = null;
        if (artistNodeId == -1 && songNodeId == -1) {
            artistNode = insertNewNodeToNewRow();
            songNode = insertNewNodeToNewRow();
            GraphList newInsertSongInArtistRow = new GraphList(
                    songNode.getId());
            insertNode(artistNode, newInsertSongInArtistRow);
            GraphList newInsertArtistInSongRow = new GraphList(
                    artistNode.getId());
            insertNode(songNode, newInsertArtistInSongRow);
            return new GraphList[] { artistNode, songNode };
        }

        if (artistNodeId != -1 && songNodeId != -1) {
            GraphList songAppendToArtist = new GraphList(songNodeId);
            GraphList artistAppendToSong = new GraphList(artistNodeId);
            artistNode = findNode(artistNodeId);
            songNode = findNode(songNodeId);

            insertNode(artistNode, songAppendToArtist);
            insertNode(songNode, artistAppendToSong);
            return new GraphList[] { artistNode, songNode };
        }

        if (songNodeId == -1) {
            songNode = insertNewNodeToNewRow();
            GraphList newArtist = new GraphList(artistNodeId);
            insertNode(songNode, newArtist);
            artistNode = findNode(artistNodeId);
            GraphList newInsertSong = new GraphList(songNode.getId());
            insertNode(artistNode, newInsertSong);
            return new GraphList[] { artistNode, songNode };
        }

        if (artistNodeId == -1) {
            artistNode = insertNewNodeToNewRow();
            GraphList newSong = new GraphList(songNodeId);
            insertNode(artistNode, newSong);
            songNode = findNode(songNodeId);
            GraphList newInsertArtist = new GraphList(artistNode.getId());
            insertNode(songNode, newInsertArtist);
        }

        return new GraphList[] { artistNode, songNode };
    }

    /**
     * Removes a node from the entire graph, including all its occurrences.
     *
     * @param node The node to be removed from the graph.
     */
    public void removeNodeInTheWholeGraph(GraphList node) {
        GraphList rowElement = node.getNext();
        while (rowElement != null) {
            GraphList rowNodeAsKey = adjacencyList[rowElement.getId()];
            GraphList rowNodeAsKeyRowElement = rowNodeAsKey.getNext();
            while (true) {
                if (rowNodeAsKeyRowElement.getId() == node.getId()) {
                    removeSingleNode(rowNodeAsKeyRowElement);
                    break;
                }
                rowNodeAsKeyRowElement = rowNodeAsKeyRowElement.getNext();
            }
            rowElement = rowElement.getNext();
        }
        removeWholeRow(node);
    }

    /**
     * Removes an entire row of nodes from the adjacency list.
     *
     * @param firstNode The first node of the row to be removed.
     */
    public void removeWholeRow(GraphList firstNode) {
        GraphList dummy = firstNode.getPrev();
        dummy.setNext(null);
        firstNode.setPrev(null);
        adjacencyListLoad--;
    }

    /**
     * Removes a single node from the adjacency list.
     *
     * @param node The node to be removed.
     */
    public void removeSingleNode(GraphList node) {
        GraphList preNode = node.getPrev();
        GraphList nxtNode = node.getNext();
        preNode.setNext(nxtNode);
        if (nxtNode != null) {
            nxtNode.setPrev(preNode);
        }
        node.setNext(null);
        node.setPrev(null);
    }

    /**
     * Creates a unionFind object and union the graph.
     *
     * @return A UnionFind object.
     */
    private UnionFind unionGraph() {
        UnionFind uf = new UnionFind(size);
        for (int i = 0; i < size; i++) {
            GraphList curNode = adjacencyList[i].getNext();
            while (curNode != null) {
                uf.union(i, curNode.getId());
                curNode = curNode.getNext();
            }
        }
        return uf;
    }

    /**
     * Counts the number of connected components in the graph.
     *
     * @return The number of connected components.
     */
    public int countComponents() {
        UnionFind uf = unionGraph();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (findNode(i) != null) {
                if (uf.find(i) == i) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Finds the size of the largest connected component in the graph.
     *
     * @return The size of the largest connected component.
     */
    public int findLargestComponentSize() {
        if (adjacencyListLoad == 0) {
            return 0;
        }

        UnionFind uf = unionGraph();
        return uf.maxSize();
    }

    /**
     * Trim the given adjacency matrix to a smaller matrix, only keep '1's
     *
     * @param matrix The input adjacency matrix.
     * @return The the trimmed matrix.
     */
    public int[][] trimZeros(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[] emptyRows = new boolean[rows];
        boolean[] emptyCols = new boolean[cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    emptyRows[i] = true;
                    emptyCols[j] = true;
                }
            }
        }

        int nonEmptyRows = 0;
        int nonEmptyCols = 0;
        for (int i = 0; i < rows; i++) {
            if (emptyRows[i])
                nonEmptyRows++;
        }
        for (int j = 0; j < cols; j++) {
            if (emptyCols[j])
                nonEmptyCols++;
        }

        int[][] trimmed = new int[nonEmptyRows][nonEmptyCols];
        int rowIndex = 0;
        for (int i = 0; i < rows; i++) {
            if (emptyRows[i]) {
                int colIndex = 0;
                for (int j = 0; j < cols; j++) {
                    if (emptyCols[j]) {
                        trimmed[rowIndex][colIndex] = matrix[i][j];
                        colIndex++;
                    }
                }
                rowIndex++;
            }
        }

        return trimmed;
    }

    /**
     * Computes the diameter of the largest connected component in the graph
     * using Floyd algorithm.
     *
     * @param sizeLargestConnected  The size of the largest connected component.
     * @param adjacencyMatrix The trimmed adjacency matrix for the largest
     *                              connected components.
     * @return The diameter of the largest connected component.
     */
    public int floyd(int sizeLargestConnected, int[][] adjacencyMatrix) {
        if (countComponents() == adjacencyListLoad) {
            return 0;
        }

        int[][] dist = new int[sizeLargestConnected][sizeLargestConnected];

        for (int i = 0; i < sizeLargestConnected; i++) {
            for (int j = 0; j < sizeLargestConnected; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                }
                else {
                    dist[i][j] = adjacencyMatrix[i][j] != 0
                            ? adjacencyMatrix[i][j]
                            : INF;
                }
            }
        }

        for (int k = 0; k < sizeLargestConnected; k++) {
            for (int i = 0; i < sizeLargestConnected; i++) {
                for (int j = 0; j < sizeLargestConnected; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF
                            && dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        int diameter = 0;
        for (int i = 0; i < sizeLargestConnected; i++) {
            for (int j = 0; j < sizeLargestConnected; j++) {
                if (i != j && dist[i][j] != INF && dist[i][j] > diameter) {
                    diameter = dist[i][j];
                }
            }
        }

        return diameter;
    }

    /**
     * Check if 2 vertices have edge
     * 
     * @param i vertex i
     * @param j vertex j
     * 
     * @return whether these 2 vertices have edge
     */
    public boolean isEdge(int i, int j) {
        GraphList node = findNode(i);
        if (node == null) {
            return false;
        }

        node = node.getNext();
        while (node != null) {
            if (node.getId() == j) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    /**
     * Calculate all the largest connected components by UnionFind
     * 
     * @return all the adjacency matrices of the largest connected components by
     *         UnionFind
     */
    public int[][][] getLargestConnectedMatrices() {
        UnionFind uf = unionGraph();
        int maxComponentSize = uf.maxSize();

        int[] componentIndex = new int[uf.parent.length];
        for (int i = 0; i < componentIndex.length; i++) {
            componentIndex[i] = -1;
        }
        int index = 0;
        for (int i = 0; i < uf.parent.length; i++) {
            int root = uf.find(i);
            if (root == i && uf.size[i] == maxComponentSize) {
                componentIndex[root] = index++;
            }
        }

        int[][][] largestMatrices = 
                new int[index][uf.parent.length][uf.parent.length];
        for (int i = 0; i < uf.parent.length; i++) {
            int rootI = uf.find(i);
            if (uf.size[rootI] == maxComponentSize) {
                int componentIdx = componentIndex[rootI];
                for (int j = 0; j < uf.parent.length; j++) {
                    int rootJ = uf.find(j);
                    if (rootI == rootJ && isEdge(i, j)) {
                        largestMatrices[componentIdx][i][j] = 1;
                    }
                }
            }
        }

        return largestMatrices;
    }

    /**
     * Prints information about the graph.
     */
    public void printGraph() {
        int connected = countComponents();
        int maxSize = findLargestComponentSize();
        int diameter = 0;
        int[][][] largestMatrices = getLargestConnectedMatrices();
        for (int i = 0; i < largestMatrices.length; i++) {
            diameter = Math.max(diameter,
                    floyd(maxSize, trimZeros(largestMatrices[i])));
        }
        System.out.println("There are " + connected + " connected components");
        System.out.println(
                "The largest connected component has " + maxSize + " elements");
        System.out.println(
                "The diameter of the largest component is " + diameter);
    }
}
