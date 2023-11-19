/**
 * Controller class
 *
 * @author <Xianwei && Jiren>
 * @version <Nov, 2023>
 */

public class Controller {
    private Hash artists;
    private Hash songs;
    private Graph graph;

    /**
     * Constructs a new Controller instance with the specified size.
     *
     * @param sizeLocal The size to initialize the hash tables and graph with.
     */
    public Controller(int sizeLocal) {
        artists = new Hash(sizeLocal, true);
        songs = new Hash(sizeLocal, false);
        graph = new Graph(sizeLocal);
    }

    /**
     * Checks if a given artist-song pair already exists in the database.
     *
     * @param artist The artist's name.
     * @param song   The song's name.
     * @return true if the artist-song pair exists, false otherwise.
     */
    private boolean existInDataBase(String artist, String song) {
        GraphList artistNode = artists.getValue(artist);
        GraphList artistsWroteThatSong = songs.getValue(song);
        if (artistsWroteThatSong == null || artistNode == null) {
            return false;
        }

        GraphList curArtist = artistsWroteThatSong.getNext();
        while (curArtist != null) {
            if (curArtist.getId() == artistNode.getId()) {
                return true;
            }
            curArtist = curArtist.getNext();
        }

        return false;
    }

    /**
     * Inserts a new artist-song pair into the database and updates the graph
     * accordingly. If the pair already exists, a message is printed, and no
     * insertion is performed.
     *
     * @param artist The artist's name.
     * @param song   The song's name.
     */
    public void insert(String artist, String song) {
        if (existInDataBase(artist, song)) {
            System.out.println("|" + artist + "<SEP>" + song
                    + "| duplicates a record already in the database.");
            return;
        }

        GraphList artistNode = artists.getValue(artist);
        GraphList songNode = songs.getValue(song);
        int artistNodeIndex = artistNode == null ? -1 : artistNode.getId();
        int songNodeIndex = songNode == null ? -1 : songNode.getId();
        GraphList[] insertedNodes = graph.insert(artistNodeIndex,
                songNodeIndex);

        if (artists.insert(artist, insertedNodes[0], false)) {
            System.out.println(
                    "|" + artist + "| is added to the Artist database.");
        }

        if (songs.insert(song, insertedNodes[1], false)) {
            System.out.println("|" + song + "| is added to the Song database.");
        }
    }

    /**
     * Removes an artist from the database.
     *
     * @param artist The artist's name to remove.
     */
    public void removeArtist(String artist) {
        GraphList artistNode = artists.getValue(artist);
        if (artistNode == null) {
            System.out.println(
                    "|" + artist + "| does not exist in the Artist database.");
            return;
        }

        graph.removeNodeInTheWholeGraph(artistNode);
        artists.delete(artist);
        System.out.println(
                "|" + artist + "| is removed from the Artist database.");
    }

    /**
     * Removes a song from the database.
     *
     * @param song The song's name to remove.
     */
    public void removeSong(String song) {
        GraphList songNode = songs.getValue(song);
        if (songNode == null) {
            System.out.println(
                    "|" + song + "| does not exist in the Song database.");
            return;
        }

        graph.removeNodeInTheWholeGraph(songNode);
        songs.delete(song);
        System.out.println("|" + song + "| is removed from the Song database.");
    }

    /**
     * Prints the list of artists in the database.
     */
    public void printArtists() {
        artists.printHashtable();
    }

    /**
     * Prints the list of songs in the database.
     */
    public void printSongs() {
        songs.printHashtable();
    }

    /**
     * Prints information about the graph.
     */
    public void printGraph() {
        graph.printGraph();
    }

}
