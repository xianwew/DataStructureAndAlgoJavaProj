public class Buffer {
    private boolean isDirty = false;
    private byte[] data;
    private int ID;
    
    public Buffer() {
        data = new byte[4096];
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
    
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
}
