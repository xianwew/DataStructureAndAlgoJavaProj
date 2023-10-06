public class BinNodeEmpty implements BinNode{
	static private BinNodeEmpty flyweight = null;

	public static BinNodeEmpty getNode () {
		if (flyweight == null) {
			flyweight = new BinNodeEmpty();
		}
		return flyweight;
	}
	
	public boolean isLeaf() {
		return true;
	}

	public boolean isEmpty() {
		return true;
	}

	public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		return new BinNodeLeaf(seminar);
	}

	public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		return this;	
	}

	public int search(int circuleX, int circuleY, int level, int xWidth, int yWidth) {
		return 1;	
	}

	public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
		System.out.println("E");
	}
}
