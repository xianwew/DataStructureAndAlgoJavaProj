import java.io.*;
import java.util.*;

/**
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */

public class Parser {
/**
 * Create Parser to read files
 * @param i the Size of components defined by user
 */
	private int i;
	
	/**
		 * The input given by the user
		 */
	public Parser() {	
		i = 0;
	}
	
	/**
	 * Create a method to initialize components
	 * @param the user input arguments
	 * @return initialize components
	 */
	public Object[] initializeComponents(String[] args) {
		if (args != null) {
			File commandFile = null;
	        try {
	    		i = Integer.parseInt(args[2]);
	        }
	        catch (Exception e) {
	        	System.out.println("Error in creating memory pool!"
	        			+ " Please enter an integar!");
	            e.printStackTrace();
	        }
	        if ((i & i - 1) != 0 || i <= 0) {
	        	System.out.println("Error in creating memory pool! "
	        			+ "The size must be the power "
	        			+ "of 2 and greater than 0!");
	        	System.exit(0);
	        }
	        
	        try {
	        	i = Integer.parseInt(args[3]);
	        }
	        catch (Exception e) {
	        	System.out.println("Error in creating hashTable!"
	        			+ " Please enter an integar!");
	            e.printStackTrace();
	        }
	        if ((i & i - 1) != 0 || i <= 0) {
	        	System.out.println("Error in creating hashTable! "
	        			+ "The size must be the power "
	        			+ "of 2 and greater than 0!");
	        	System.exit(0);
	        }
	
	        try {
	        	commandFile = new File(args[4]);
	        } 
	    	catch (Exception e) {
	            System.out.println("Error in reading files!");
	            e.printStackTrace();
	    	}
	  
	        Object[] res = {new byte[Integer.parseInt(args[2])], 
	        				new MyHashTable(Integer.parseInt(args[3])), commandFile};
	        return res;
		}
		return null;
	}
	
	/**
	 * Create a method to get the instruction from the input string
	 * @param the data is one line of code of the user input file
	 * @return output instruction
	 */
	private int getInstruction (String data) {	
		String tmpDataLeft = data;
		String tmpDataRight = data;
		try {
			tmpDataLeft = data.split("\\s+")[0];
			tmpDataRight = data.split("\\s+")[1];
		}
		catch (Exception e) {
            System.out.println("data can't be split! Error occurs!");
		}
		if (tmpDataLeft.indexOf("insert") == 0) {
			return 1;
		}
		else if (tmpDataLeft.indexOf("search") == 0) {
			return 2;
		}
		else if (tmpDataLeft.indexOf("print") == 0) {
			if(tmpDataRight.indexOf("hashtable") == 0) {
				return 3;
			}
			else if (tmpDataRight.indexOf("blocks") == 0) {
				return 4;
			}
			return 0;
		}
		else if (tmpDataLeft.indexOf("delete") == 0) {
			return 5;
		}
		return 0;
	}
	
	/**
	 * The most important function of the parser class, it read, 
	 * process the files and pass it to 
	 * world database for further processing
	 * 
	 * @param commandFile File objected created by Parser
	 * @param dataBase the class that acts like a bridge between 
	 * various class, and calls functions
	 */
	public void processSeminars(File commandFile, WorldDataBase dataBase) {	
		try {		
			Scanner reader = new Scanner(commandFile);
			String data = "";
			int instruction = 0;
			if (reader.hasNextLine()) {
				data = reader.nextLine().trim();
			}
			while (true) {
				instruction = getInstruction(data);
				int line = 0;
				int id = -1;
		        String title = "";
		        String dateTime = "";
		        int length = -1;
		        short x = -1;
		        short y = -1;
		        int cost = -1;
		        String desc = "";
		        String[] keywordList = {};
				while (true) {
					String tmpData = data.trim();
					System.out.println("data is:    " + data);
					switch (line) {
						case 0:
							try {
								id = Integer.parseInt(tmpData.split("\\s+")[1]);
							}
							catch (Exception e) {
							}
							break;
						case 1:
							title = data;
							break;
						case 2:
							String[] tmpDataArr = tmpData.split("\\s+");
							dateTime = tmpDataArr[0];
							length = Integer.parseInt(tmpDataArr[1]);
							x = Short.parseShort(tmpDataArr[2]);
							y = Short.parseShort(tmpDataArr[3]);
							cost = Integer.parseInt(tmpDataArr[4]);
							break;
						case 3:
							keywordList = tmpData.split("\\s+");
							break;
						case 4:
							desc = data;
							break;
						default:
					        break; 
					}
					line++;
					if (reader.hasNextLine()) {
						data = reader.nextLine().replaceAll("^\\s+", "");
					}	
					tmpData = data;
					while (reader.hasNextLine() && 
					tmpData.replaceAll("\\s+", "").equals("")) {
					    data = reader.nextLine().trim();
					    tmpData = data;
					}
					if (reader.hasNextLine()) {
						if (getInstruction(data) != 0) {
							break;
						}
					}
					else {
						break;
					}
				}
				Seminar seminar = new Seminar(
						id, title, dateTime, length, 
						x, y, cost, keywordList, desc);
				dataBase.processCommand(instruction, id, seminar);
				System.out.println("");
				if (!reader.hasNextLine()) {
					reader.close();
					instruction = getInstruction(data);
					if (instruction != 0) {
						System.out.println("data is:    " + data);
						id = Integer.parseInt(data.split("\\s+")[1]);
						seminar = new Seminar(
								id, title, dateTime, length, 
								x, y, cost, keywordList, desc);
						dataBase.processCommand(instruction, id, seminar);
					}
					break;
				}
			}
		}
		catch (Exception e) {
            System.out.println("Error in reading/processing files!");
            e.printStackTrace();
		}
	}
}
