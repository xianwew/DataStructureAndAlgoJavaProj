public class Buffer {
    private boolean isDirty = false;
    private byte[] data;
    private long id = -1;
    
    public Buffer() {
        data = new byte[4096];
        isDirty = false;
    }
    
    public Buffer(byte[] newData, long idLoc) {
        data = newData;
        isDirty = false;
        id = idLoc;
    }
    
    public Buffer(long idLoc) {
        data = new byte[4096];
        isDirty = false;
        id = idLoc;
    }
    
    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    public long getID() {
        return id;
    }
    
    public void setID(long idLoc) {
        id = idLoc;
    }
}
