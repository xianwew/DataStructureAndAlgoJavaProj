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
 * The semSanager is used to store the seminar objects, 
 * or retrieve seminar objects from the memory pool.
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 * 
 * @FreeList dummy: the dummy node, 
 * or it is called the header of the list
 * @size: the size of the memory pool.
 * @memory pool: the place to store seminar objects.
 */

/**
 * The main function of the program.
 * It firstly initializes all the components
 * and then calls function.
 */
public class SemManager {
    public static void main(String[] args) {
        if (args != null) {
            Parser parser = new Parser();
            Object[] components = parser.initializeComponents(args);
            MemManager memManager = new MemManager();
            memManager.memoryPool = (byte[]) components[0];
            memManager.initializeMemManger(Integer.parseInt(args[0]));
            MyHashTable hashTable = (MyHashTable) components[1];
            File commandFile = (File) components[2];
            WorldDataBase worldDataBase = new WorldDataBase(memManager, hashTable);
            parser.processSeminars(commandFile, worldDataBase);
        }
    }
}
