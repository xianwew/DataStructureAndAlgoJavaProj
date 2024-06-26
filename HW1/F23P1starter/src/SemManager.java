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

import java.io.*;

/**
 * The semSanager is used to call the main function
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 * 
 */

public class SemManager {
    /**
     * The main function of the program
     * @param args user input
     */
    public static void main(String[] args) {
        if (args != null) {
            Parser parser = new Parser();
            Object[] components = parser.initializeComponents(args);
            MemManager memManager = new MemManager();
            memManager.setMemoryPool((byte[]) components[0]);
            memManager.initializeMemManger(Integer.parseInt(args[0]));
            MyHashTable hashTable = (MyHashTable) components[1];
            File commandFile = (File) components[2];
            WorldDataBase worldDataBase = 
                    new WorldDataBase(memManager, hashTable);
            parser.processSeminars(commandFile, worldDataBase);
        }
    }
}
