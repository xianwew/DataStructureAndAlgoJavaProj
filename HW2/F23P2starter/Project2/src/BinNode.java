public interface BinNode {
    public boolean isLeaf();

    public boolean isEmpty();

    public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth,
            int yWidth);

    public int search(int x, int y, int circuleX, int circuleY, int radius,
            int level, int xWidth, int yWidth);

    public void print(int level);
}
