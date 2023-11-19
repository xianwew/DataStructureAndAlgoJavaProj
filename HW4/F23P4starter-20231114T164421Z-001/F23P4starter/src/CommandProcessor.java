import java.util.*;
import java.io.*;

/**
 * CommandProcessor class
 *
 * @author <Xianwei && Jiren>
 * @version <Nov, 2023>
 */
public class CommandProcessor {
    private Controller controller;
    private String fileName;

    /**
     * Sets the Controller instance that will be used to manage the artist-song
     * database.
     *
     * @param controller The Controller object to set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the name of the input file containing commands.
     *
     * @return The name of the input file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the name of the input file containing commands.
     *
     * @param fileName The name of the input file to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Constructs a new CommandProcessor instance with the specified initial
     * size and input file name. It initializes the Controller and begins
     * parsing commands from the input file.
     *
     * @param initialSize   The initial size for the artist-song table and
     *                      graph.
     * @param filenameLocal The name of the input file to process.
     */
    public CommandProcessor(int initialSize, String filenameLocal) {
        setFileName(filenameLocal);
        setController(new Controller(initialSize));
        beginParsingByLine(getFileName());
    }

    /**
     * Reads and processes commands from the input file line by line.
     *
     * @param filename The name of the input file to read and process.
     */
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
                        }
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
