public class FreeList {
    private int val;
    private FreeList next;
    private FreeList prev;
    private int index;

    public FreeList(int val, int index) {
        this.val = val;
        this.next = null;
        this.prev = null;
        this.index = index;
    }

    public FreeList getNext() {
        return next;
    }
    
    public void setNext(FreeList next) {
        this.next = next;
    }

    public FreeList getPrev() {
        return prev;
    }

    public void setPrev(FreeList prev) {
        this.prev = prev;
    }
    
    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}

