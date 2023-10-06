import java.io.*;
import java.util.*;
/**
 * Parse the user input to the program
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */
public class Parser {
    /**
     * Create Parser to read files
     * 
     * @param data: a line of the input txt file
     */
    private String data = "";

    /**
     * Dummy Parser constructor
     */
    public Parser() { 
        // Nothing here
    }

    /**
     * Create a method to initialize components
     * 
     * @param args the user input arguments
     * @return initialize components
     */
    /**
     * Get the data valuable
     * @return the data value
     */
    public String getData() {
        return this.data;
    }
    
    /**
     * Set the data value
     * @param input the input string value
     */
    public void setData(String input) {
        this.data = input;
    }

    public String[] getData(String dataLocal) {
        String data1 = "";
        String data2 = "";
        String data3 = "";
        String[] result = new String[3];
        try {
            data1 = dataLocal.split("\\s+")[2];
            data2 = dataLocal.split("\\s+")[3];
            data3 = dataLocal.split("\\s+")[4];
        }
        catch (Exception e) {
//          System.out.println("Error in getting instruction!");
        }
        result[0] = data1;
        result[1] = data2;
        result[2] = data3;
        return result;
    }
    
    /**
     * Create a method to get the instruction from the input string
     * 
     * @param dataLocal is one line of code of the user input file
     * @return output instruction
     */
    public int getInstruction(String dataLocal) {
        String tmpDataLeft = dataLocal;
        String tmpDataRight = dataLocal;
        try {
            tmpDataLeft = dataLocal.split("\\s+")[0];
            tmpDataRight = dataLocal.split("\\s+")[1];
        } 
        catch (Exception e) {
//            System.out.println("Error in getting instruction!");
        }
        if (tmpDataLeft.indexOf("insert") == 0) {
            return 11;
        } 
        else if (tmpDataLeft.indexOf("search") == 0) {
            if(tmpDataRight.indexOf("ID") == 0) {
                return 21;
            }
            else if(tmpDataRight.indexOf("cost") == 0) {
                return 22;
            }
            else if(tmpDataRight.indexOf("date") == 0) {
                return 23;
            }
            else if(tmpDataRight.indexOf("keyword") == 0) {
                return 24;
            }
            return 25;
        } 
        else if (tmpDataLeft.indexOf("delete") == 0) {
            return 31;
        }
        else if (tmpDataLeft.indexOf("print") == 0) {
            if (tmpDataRight.indexOf("date") == 0) {
                return 41;
            } 
            else if (tmpDataRight.indexOf("keyword") == 0) {
                return 42;
            }
            else if (tmpDataRight.indexOf("location") == 0) {
                return 43;
            }
            else if (tmpDataRight.indexOf("cost") == 0) {
                return 44;
            }
            return 45;
        } 
       
        return 0;
    }

    /**
     * The most important function of the 
     * parser class, it read, process the files and
     * pass it to world database for further processing
     * 
     * @param commandFile File objected created by Parser
     * @param dataBase    the class that acts 
     *      like a bridge between various class,
     *      and calls functions
     */
    public void processSeminars(
            String[] args, WorldDataBase dataBase) {
        try {
            File commandFile = new File(args[3]);
            Scanner reader = new Scanner(commandFile);
            int instruction = 0;
            if (reader.hasNextLine()) {
                data = reader.nextLine().trim();
            }
            String[] param = new String[3];
            while (true) {
                instruction = getInstruction(data);
                param = getData(data);
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
                    // System.out.println("data is: " + data);
                    switch (line) {
                        case 0:
                            try {
                                id = Integer.parseInt(tmpData.split("\\s+")[1]);
                            } 
                            catch (Exception e) {
//                              System.out.println("
//                                  Error in getting instruction!");
                            }
                            break;
                        case 1:
                            title = data.trim();
                            break;
                        case 2:
                            try {
                                String[] tmpDataArr = tmpData.split("\\s+");
                                dateTime = tmpDataArr[0];
                                length = Integer.parseInt(tmpDataArr[1]);
                                x = Short.parseShort(tmpDataArr[2]);
                                y = Short.parseShort(tmpDataArr[3]);
                                cost = Integer.parseInt(tmpDataArr[4]);
                            } 
                            catch (Exception e) {
//                              System.out.println("
//                                  Error in getting instruction!");
                            }
                            break;
                        case 3:
                            keywordList = tmpData.split("\\s+");
                            break;
                        case 4:
                            desc = data.trim();
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
                if (desc.equals("")) {
                    desc = data.trim();
                }
                Seminar seminar = new Seminar(id, title, 
                        dateTime, length, x, y, cost, keywordList, desc);
                dataBase.processCommand(instruction, id, seminar, param);
                if (!reader.hasNextLine()) {
                    reader.close();
                    instruction = getInstruction(data);
                    param = getData(data);
                    if (instruction != 0) {
                        try {
                            id = Integer.parseInt(data.split("\\s+")[1]);
                        } 
                        catch (Exception e) {
//                          System.out.println("
//                              Error in getting instruction!");
                        }
                        seminar = new Seminar(id, title, dateTime, 
                                length, x, y, cost, keywordList, desc);
                        dataBase.processCommand(instruction, id, seminar, param);
                    }
                    break;
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Error"
                    + " in reading/processing files!");
            e.printStackTrace();
        }
    }
}
