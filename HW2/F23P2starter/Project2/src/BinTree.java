public class BinTree {
    int worldSize = 0;
    private BinNode root;

    public BinNode getRoot() {
        return this.root;
    }

    public BinTree(int worldSizeLocal) {
        this.worldSize = worldSizeLocal;
        root = BinNodeEmpty.getNode();
    }

    public void insert(Seminar seminar) {
        root = root.insert(0, 0, seminar, 0, worldSize, worldSize);
    }

    public void delete(Seminar seminar) {
        root = root.delete(0, 0, seminar, 0, worldSize, worldSize);
    }

    public int search(int circuleX, int circuleY, int radius) {
        return root.search(0, 0, circuleX, circuleY, radius, 0, worldSize,
                worldSize);
    }

    public int print(int level, BinNode node) {
        node.print(level);
        int leftCount = 0;
        int rightCount = 0;
        if (!node.isEmpty() && !node.isLeaf()) {
            BinNodeInternal tmp = (BinNodeInternal) node;
            leftCount = print(level + 1, tmp.getLeft());
            rightCount = print(level + 1, tmp.getRight());
        }
        return 1 + leftCount + rightCount;
    }

}
