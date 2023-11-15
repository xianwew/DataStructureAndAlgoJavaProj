
public class Controller {
	private Hash artists;
	private Hash songs;
    private Graph graph;
	
    public Hash getArtists() {
        return artists;
    }

    public void setArtists(Hash artists) {
        this.artists = artists;
    }

    public Hash getSongs() {
        return songs;
    }

    public void setSongs(Hash songs) {
        this.songs = songs;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
	public Controller(int sizeLocal) {
	    setArtists(new Hash(sizeLocal));
	    setSongs(new Hash(sizeLocal));
	    setGraph(new Graph(sizeLocal));

	}
	
	public void insert() {
	    
	}

	
	
	
	
}
