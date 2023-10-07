public class BinNodeLeaf implements BinNode{
    public class seminarList{
        private Seminar seminar = null;
        private seminarList next = null; 
        
        public Seminar getSeminar() {
            return seminar;
        }
        
        public void setSeminar(Seminar seminar) {
            this.seminar = seminar;
        } 
        
        public seminarList getNext() {
            return next;
        }
        
        public void setNext(seminarList next) {
            this.next = next;
        }
    }

    
	private seminarList sl;
	
	public BinNodeLeaf(Seminar seminarLocal) {
		this.sl = new seminarList();
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
	    seminarList curList = sl;
		if(curList.getSeminar() != null && curList.getSeminar().x() == seminar.x() && curList.getSeminar().y()==seminar.y()) {
		    while(curList.getNext() != null) {
		        curList = curList.getNext();
		    }
		    seminarList tmp = new seminarList();
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

	public int search(int circuleX, int circuleY, int level, int xWidth, int yWidth) {
		return 0;
	}

	public void print(int level) {	
	    for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
	    String s = "";
	    seminarList curList = sl;
	    int count = 0;
        while(curList != null) {
            s += " " + curList.getSeminar().id();
            curList = curList.getNext();
            count++;
        }
        System.out.println("Leaf with " + count + " objects:" + s);
	}
}
