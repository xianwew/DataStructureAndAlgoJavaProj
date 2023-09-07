import java.io.*;
import java.util.*;


public class Parser {
	private int i;
	public Parser() {
		i = 0;
	}
	
	public Object[] initializeComponents(String[] args) {
		if(args != null) {
			File CommandFile = null;
	        try {
	    		i = Integer.parseInt(args[2]);
	        }
	        catch (Exception e) {
	        	System.out.println("Error in creating memory pool! Please enter an integar!");
	            e.printStackTrace();
	        }
	        if ((i & i - 1) != 0 || i <= 0) {
	        	System.out.println("Error in creating memory pool! The size must be the power of 2 and greater than 0!");
	        	System.exit(0);
	        }
	        
	        try {
	        	i = Integer.parseInt(args[3]);
	        }
	        catch (Exception e) {
	        	System.out.println("Error in creating hashTable! Please enter an integar!");
	            e.printStackTrace();
	        }
	        if ((i & i - 1) != 0 || i <= 0) {
	        	System.out.println("Error in creating hashTable! The size must be the power of 2 and greater than 0!");
	        	System.exit(0);
	        }
	
	        try {
	        	CommandFile = new File(args[4]);
	        } 
	    	catch (Exception e) {
	            System.out.println("Error in reading files!");
	            e.printStackTrace();
	    	}
	  
	        Object[] res = { new byte[Integer.parseInt(args[2])], new MyHashTable(Integer.parseInt(args[3])), CommandFile};
	        return res;
		}
		return null;
	}
	
	private int GetInstruction(String data){
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
			if(tmpDataRight.indexOf("hashtable") == 0){
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
	
	public void ProcessSeminars(File CommandFile, WorldDataBase dataBase) {
		try {		
			Scanner Reader = new Scanner(CommandFile);
			String data = "";
			int instruction = 0;
			if(Reader.hasNextLine()) {
				data = Reader.nextLine().trim();
			}
			while (true) {
				instruction = GetInstruction(data);
				int line = 0;
				int ID = -1;
		        String title = "";
		        String dateTime = "";
		        int length = -1;
		        short x = -1;
		        short y = -1;
		        int cost = -1;
		        String desc = "";
		        String[] keywordList = {};
				while(true){
					String tmpData = data.trim();
					System.out.println("data is:    " + data);
					switch(line){
						case 0:
							try {
								ID = Integer.parseInt(tmpData.split("\\s+")[1]);
							}
							catch(Exception e){
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
					if (Reader.hasNextLine()) {
						data = Reader.nextLine().replaceAll("^\\s+", "");
					}	
					tmpData = data;
					while (Reader.hasNextLine() && tmpData.replaceAll("\\s+", "") == "") {
					    data = Reader.nextLine().trim();
					    tmpData = data;
					}
					if (Reader.hasNextLine()) {
						if (GetInstruction(data) != 0) {
							break;
						}
					}
					else {
						break;
					}
				}
				Seminar seminar = new Seminar(ID, title, dateTime, length, x, y, cost, keywordList, desc);
				dataBase.processCommand(instruction, ID, seminar);
				System.out.println("");
				if (!Reader.hasNextLine()) {
					Reader.close();
					instruction = GetInstruction(data);
					if (instruction != 0) {
						System.out.println("data is:    " + data);
						ID = Integer.parseInt(data.split("\\s+")[1]);
						seminar = new Seminar(ID, title, dateTime, length, x, y, cost, keywordList, desc);
						dataBase.processCommand(instruction, ID, seminar);
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
