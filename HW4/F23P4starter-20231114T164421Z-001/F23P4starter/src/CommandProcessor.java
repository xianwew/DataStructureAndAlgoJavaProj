import java.util.*;
import java.io.*;

public class CommandProcessor {
    private Controller controller;
    private String fileName;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CommandProcessor(int intialSize, String filenameLocal) {
        setFileName(filenameLocal);
        setController(new Controller(intialSize));
        beginParsingByLine(getFileName());
    }

    public void beginParsingByLine(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            Scanner scancmd;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                scancmd = new Scanner(line);
                String cmd = scancmd.next();
                String type;
                switch (cmd) {
                    case "insert":
                        scancmd.useDelimiter("<SEP>");
                        String artist = scancmd.next();
                        String song = scancmd.next();
                        System.out.println("Insert " + artist + " \\ " + song);
                        break;
                    case "remove":
                        type = scancmd.next();
                        String token = scancmd.nextLine();

                        switch (type) {
                            case "artist":
                                System.out.println("Artist Delete: " + token);
                                break;
                            case "song":
                                System.out.println("Song Delete: " + token);
                                break;
                            default:
                                System.out.println("Error bad remove type " + type);
                                break;
                        }
                        break;
                    case "print":
                        type = scancmd.next();
                        switch (type) {
                            case "artist":
                                System.out.println("Print artist mode");
                                break;
                            case "song":
                                System.out.println("Print song mode");
                                break;
                            case "blocks":
                                System.out.println("Print block mode");
                                break;
                            default:
                                System.out.println("Error bad print type" + type);
                                break;
                        }
                        break;
                    default:
                        System.out.println("Unrecognized input");
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
