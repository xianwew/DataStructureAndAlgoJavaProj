public class GraphList {
    private String value;
    private boolean isArtist;
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

    public boolean isArtist() {
        return isArtist;
    }

    public void setIsArtist(boolean isArtistLocal) {
        this.isArtist = isArtistLocal;
    }
    
    public GraphList() {}
    
    public GraphList(String valueLocal, boolean isArtistLocal) {
        setValue(valueLocal);
        setIsArtist(isArtistLocal);
    }
}
