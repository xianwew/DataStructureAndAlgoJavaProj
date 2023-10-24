
/**
 * implement Quick sort
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * The class containing the main method.
 *
 * @author xianwei & jiren 
 * @version Oct 22nd
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class Quicksort {

    /**
     * This method is used to generate a file of a certain size, containing a
     * specified number of records.
     *
     * @param filename  the name of the file to create/write to
     * @param blockSize the size of the file to generate
     * @param format    the format of file to create
     * @throws IOException throw if the file is not open and proper
     */
    public static void generateFile(String filename, String blockSize,
            char format) throws IOException {
        FileGenerator generator = new FileGenerator();
        String[] inputs = new String[3];
        inputs[0] = "-" + format;
        inputs[1] = filename;
        inputs[2] = blockSize;
        generator.generateFile(inputs);
    }

    /**
     * @param args Command line parameters.
     * @throws IOException
     */
    public static void main(String[] args) {
        // This is the main file for the program.
        if (args != null && args.length == 3) {
//            try {
//                generateFile(args[0], "10", 'b');
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }

            if (Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > 20) {
                System.out
                        .println("The input buffer size should between 1 - 20");
                return;
            }

            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(args[0], "rw");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("The input data file cannot be opened");
                return;
            }

            try {
                FileWriter output = new FileWriter(args[2], true);
                BufferPool bufferPool = new BufferPool(Integer.valueOf(args[1]),
                        raf);
                output.write("Sort file name: " + args[0] + "\n");
                output.write(
                        "Cache Hits: " + bufferPool.getHits() + " times\n");
                output.write(
                        "Disk reads: " + bufferPool.getReads() + " times\n");
                output.write(
                        "Disk writes: " + bufferPool.getWrites() + " times\n");
                output.write("Sort time: " + bufferPool.getTime() + " ms\n");
                output.write("ReadWrite time: " + bufferPool.getReadWriteTime()
                        + " ms\n");
                output.write("\n");
                output.flush();
                output.close();
                CheckFile cf = new CheckFile();
                System.out.println("Was file sorted successfully: "
                        + cf.checkFile(args[0]));
                System.out.println("Read Write time: "
                        + bufferPool.getReadWriteTime() + " ms");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Invalid user arguments");
        }
    }
}
