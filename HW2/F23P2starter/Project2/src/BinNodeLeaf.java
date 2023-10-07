public class BinNodeLeaf implements BinNode{
	private Seminar seminar;
	private int count = 0;
	
	
	public int getCount () {
		return count;
	}
	
	public void setCount (int countLoc) {
		this.count = countLoc;
	}
	
	public BinNodeLeaf(Seminar seminarLocal) {
		this.setSeminar(seminarLocal);
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

	public Seminar getSeminar() {
		return seminar;
	}

	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}

}
