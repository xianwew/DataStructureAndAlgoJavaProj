
/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author xianwei & jiren
 * @version Oct 2023
 */

// On my honor:
// - I have not used source code obtained from another current or
//   former student, or any other unauthorized source, either
//   modified or unmodified.
//
// - All source code and documentation used in my program is
//   either my original work, or was derived by me from the
//   source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
//   anyone other than my partner (in the case of a joint
//   submission), instructor, ACM/UPE tutors or the TAs assigned
//   to this course. I understand that I may discuss the concepts
//   of this program with other students, and that another student
//   may help me debug my program so long as neither of us writes
//   anything during the discussion or modifies any computer file
//   during the discussion. I have violated neither the spirit nor
//   letter of this restriction.

public class SemSearch {
    /**
     * @param args Command line parameters
     */
    public static void main(String[] args) {
        // This is the main file for the program.
        Seminar dum = new Seminar();
        if (args != null) {
            WorldDataBase world = new WorldDataBase(Integer.valueOf(args[0]));
            Parser parser = new Parser();
            parser.processSeminars(args, world);
        }

//        BinTree tmp = new BinTree(4);
//        tmp.print(0, tmp.getRoot());
//        
//        String refOut = "";
//        
//        
//        int id = 12;
//        String title = "";
//        String date = "";
//        int length = 1;
//        short x = 16;
//        short y = 16;
//        int cost = 2;
//        String[] keywords = new String[3];
//        String desc = "";
//        Seminar seminar = new Seminar(id, title, date, length, x, y, cost, keywords, desc);
//       
//        tmp.insert(seminar);
//        BinNode node = tmp.getRoot();
//        tmp.print(0, node);
//        refOut = "";
    }
}
