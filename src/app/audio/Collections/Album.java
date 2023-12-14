package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Album extends AudioCollection {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int releaseYear;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String username;
    @Getter
    private String owner;
    @Getter
    @Setter
    private List<Song> songs;

    public Album(final String username, final String name, final int releaseYear, final String description) {
        super(name, username);
        this.username = username;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.owner = username;
        this.songs = new ArrayList<>();
    }



    // Metoda pentru adăugarea unei melodii în album
    public void addSong(final Song song) {
        songs.add(song);
    }

    public int getTotalLikes() {
        return songs.stream().mapToInt(Song::getLikes).sum();
    }


    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

}

