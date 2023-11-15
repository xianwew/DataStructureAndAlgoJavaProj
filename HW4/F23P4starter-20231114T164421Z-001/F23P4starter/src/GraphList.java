public class GraphList {
    private String value;
    private GraphList prev;
    private GraphList next;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String valueLocal) {
        this.value = valueLocal;
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
}
