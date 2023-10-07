public class BinNodeLeaf implements BinNode{
	private SeminarList sl;
	
	public SeminarList getSeminarList() {
	    return sl;
	}
	
	public BinNodeLeaf(Seminar seminarLocal) {
		this.sl = new SeminarList();
		sl.setSeminar(seminarLocal);
	}
	
	public boolean isLeaf() {
		return true;
	}

	public boolean isEmpty() {
		return false;
	}

	public BinNode insert(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		//case 1
			//if x, y equals existing seminar
			//increase count
			//insert to the list
	    SeminarList curList = sl;
		if(curList.getSeminar() != null && curList.getSeminar().x() == seminar.x() && curList.getSeminar().y()==seminar.y()) {
		    while(curList.getNext() != null) {
		        curList = curList.getNext();
		    }
		    SeminarList tmp = new SeminarList();
		    tmp.setSeminar(seminar);
		    curList.setNext(tmp);
		    return this;
		}
		else if(curList.getSeminar() == null) {
            curList.setSeminar(seminar);
		    return this;
		}
		 
        //case 2
            //create internal node
            //insert all seminars from the list to the same internal node 
            //insert the seminar to the internal node
            //return the internal node
		BinNodeInternal tmp = new BinNodeInternal();
		curList = sl;
		while(curList != null) {
		    tmp.insert(x, y, curList.getSeminar(), level, xWidth, yWidth);
		    curList = curList.getNext();
		}
		tmp.insert(x, y, seminar, level, xWidth, yWidth);
		return tmp;
	}

	public BinNode delete(int x, int y, Seminar seminar, int level, int xWidth, int yWidth) {
		return null;
	}

	public int search(int circuleX, int circuleY, int radius, int level, int xWidth, int yWidth) {
	    SeminarList tmp = sl;
	    if(tmp != null && tmp.getSeminar() != null && Math.pow(tmp.getSeminar().x(), 2) + Math.pow(tmp.getSeminar().y(), 2) <= radius * radius) {
            while(tmp != null) {
                System.out.println("Found a record with key value " + tmp.getSeminar().id() + " at " + tmp.getSeminar().x() + ", " + tmp.getSeminar().y());
                tmp = tmp.getNext();
            }
        }
		return 1;
	}

	public void print(int level) {	
	    for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
	    String s = "";
	    SeminarList curList = sl;
	    int count = 0;
        while(curList != null) {
            s += " " + curList.getSeminar().id();
            curList = curList.getNext();
            count++;
        }
        System.out.println("Leaf with " + count + " objects:" + s);
	}
}
