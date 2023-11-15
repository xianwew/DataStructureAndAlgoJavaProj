
public class Controller {
    private Hash artists;
    private Hash songs;
    private Graph graph;

    public Controller(int sizeLocal) {
        artists = new Hash(sizeLocal);
        songs = new Hash(sizeLocal);
        graph = new Graph(sizeLocal);
    }

    public void insert(String artist, String song) {
        boolean success = graph.insert(artist, song);
        if(!success) {
            System.out.println("|" + artist + "<SEP>" + song + "| duplicates a record already in the database.");
            return;
        }
        GraphList artistNode = graph.findArtist(artist);
        GraphList songNode = graph.findArtist(song);
        artists.insert(artist, artistNode);
        System.out.println("|" + artist + "| is added to the Artist database.");
        songs.insert(song, songNode);
        System.out.println("|" + song + "| is added to the Song database.");
    }

    public void removeArtist(String artist) {
        GraphList artistNode = artists.getValue(artist);
        if(artistNode == null) {
            System.out.println("|" + artist + "| does not exist in the Artist database.");
            return;
        }
        
        GraphList curArtistSong = artistNode.getNext();
        while(curArtistSong != null) {
            GraphList songWrittenByThatArtist = songs.getValue(curArtistSong.getValue());
            GraphList artistWroteSameSong = songWrittenByThatArtist.getNext();
            while(artistWroteSameSong != null) {
                if(artistWroteSameSong.getValue() == artist) {
                    graph.remove(artistWroteSameSong);
                }
            }
            
            if(songWrittenByThatArtist.getNext() == null) {
                graph.remove(songWrittenByThatArtist);
                songs.delete(curArtistSong.getValue());
            }
            
            curArtistSong = curArtistSong.getNext();
        }
        
        artists.delete(artist);
        graph.remove(artistNode);
    }

    public void removeSong(String song) {
        GraphList songNode = songs.getValue(song);
        if(songNode == null) {
            System.out.println("|" + song + "| does not exist in the Song database.");
            return;
        }
        
        GraphList curArtist = songNode.getNext();
        while(curArtist != null) {
            GraphList artistWroteSameSong = artists.getValue(curArtist.getValue());
            GraphList songWrittebyThatArtist = artistWroteSameSong.getNext();
            while(songWrittebyThatArtist != null) {
                if(songWrittebyThatArtist.getValue() == song) {
                    graph.remove(songWrittebyThatArtist);
                }
            }
            
            if(artistWroteSameSong.getNext() == null) {
                graph.remove(artistWroteSameSong);
                artists.delete(curArtist.getValue());
            }
            
            curArtist = curArtist.getNext();
        }
        
        songs.delete(song);
        graph.remove(songNode);
    }
}
