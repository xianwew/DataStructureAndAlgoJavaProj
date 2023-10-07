public class BinNodeLeaf implements BinNode{
	private Seminar seminar;
	private int count = 0;
	//List with seminars
	
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
		//case 1
			//if x, y equals existing seminar
			//increase count
			//insert to the list
		
		//case 2
			//create internal node
			//insert all seminars from the list to the same internal node 
			//insert the seminar to the internal node
			//return the internal node
			
		return this;
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
