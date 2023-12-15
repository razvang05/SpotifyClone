package app.audio.Files;

import app.audio.LibraryEntry;
import app.user.Artist;

public class ArtistEntry extends LibraryEntry {

    private Artist artist;

    public ArtistEntry(final Artist artist) {
        super(artist.getUsername());
        this.artist = artist;
    }


}
