public class Buffer {
    private boolean isDirty = false;
    private byte[] data;
    private long id;
    
    public Buffer() {
        data = new byte[4096];
        isDirty = false;
        id = -1;
    }
    
    public Buffer(byte[] newData) {
        data = newData;
        isDirty = false;
        id = -1;
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
