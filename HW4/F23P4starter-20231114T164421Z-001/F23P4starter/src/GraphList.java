public class GraphList {
    private int id = -1;
    private GraphList prev;
    private GraphList next;
    
    public void setId(int idLocal) {
        id = idLocal;
    }
    
    public int getId() {
        return id;
    }
    
    public GraphList getPrev() {
        return prev;
    }
    
    public void setPrev(GraphList prevLocal) {
        this.prev = prevLocal;
    }
    
    public GraphList getNext() {
        return next;
    }
    
    public void setNext(GraphList nextLocal) {
        this.next = nextLocal;
    }
    
    public GraphList() {}
    
    public GraphList(int idlocal) {
        setId(idlocal);
    }
}
