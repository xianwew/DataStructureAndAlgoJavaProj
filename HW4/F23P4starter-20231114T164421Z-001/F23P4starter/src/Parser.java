import java.util.*;
import java.io.*;

public class Parser {
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

    public Parser(int intialSize, String filenameLocal) {
        setFileName(filenameLocal);
        setController(new Controller(intialSize));
        beginParsingByLine(getFileName());
    }

    public void beginParsingByLine(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            Scanner scancmd;
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                scancmd = new Scanner(line);
                String cmd = scancmd.next();
                String type;
                switch (cmd) {
                    case "insert":
                        scancmd.useDelimiter("<SEP>");
                        String artist = scancmd.next().trim();
                        String song = scancmd.next().trim();
                        controller.insert(artist, song);
                        break;
                    case "remove":
                        type = scancmd.next().trim();
                        String token = scancmd.nextLine().trim();

                        switch (type) {
                            case "artist":
                                controller.removeArtist(token);
                                break;
                            case "song":
                                controller.removeSong(token);
                                break;
                            default:
                                System.out.println("Error bad remove type " + type);
                                break;
                        }
                        break;
                    case "print":
                        type = scancmd.next().trim();
                        switch (type) {
                            case "artist":
                                controller.printArtists();
                                break;
                            case "song":
                                controller.printSongs();
                                break;
                            case "graph":
                                controller.printGraph();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
