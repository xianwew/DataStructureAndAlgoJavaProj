public class BinNodeLeaf implements BinNode{
	private Seminar seminar;
	
	public BinNodeLeaf(Seminar seminarLocal) {
		this.seminar = seminarLocal;
	}
	
	public boolean isLeaf() {
		return false;
	}

	public boolean isEmpty() {
		return false;
	}

	public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		return null;
	}

	public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		return null;
	}

	public int search(int circuleX, int circuleY, int level, int xWidth, int yWidth) {
		return 0;
	}

	public void print(int level) {	
	}

}