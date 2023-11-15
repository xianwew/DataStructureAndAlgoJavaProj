
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
        for(int i = 0; i < size; i++) {
            adjacencyList[i] = new GraphList();
        }
    }
    
    public GraphList searchForNodeOrReturnNewNode(String artist, String song, boolean returnNewNode) {
        
        
        
    }
    
}
