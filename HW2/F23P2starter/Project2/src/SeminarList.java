public class SeminarList {

    private Seminar seminar = null;
    private SeminarList next = null;

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public SeminarList getNext() {
        return next;
    }

    public void setNext(SeminarList next) {
        this.next = next;
    }

}
