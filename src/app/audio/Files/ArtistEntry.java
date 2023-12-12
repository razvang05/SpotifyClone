package app.audio.Files;

import app.audio.LibraryEntry;
import app.user.Artist;

public class ArtistEntry extends LibraryEntry {

    private Artist artist;

    public ArtistEntry(Artist artist) {
        super(artist.getUsername());
        this.artist = artist;
    }

    //@Override
//    public boolean matchesName(String name) {
//        return artist.getUsername().equalsIgnoreCase(name);
//    }

}
