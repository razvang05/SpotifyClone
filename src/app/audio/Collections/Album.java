package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import fileio.input.SongInput;
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
    private List<Song> songs;

    public Album(String name, int releaseYear, String description) {
        super(name, "owner");
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = new ArrayList<>();
    }



    // Metoda pentru adăugarea unei melodii în album
    public void addSong(Song song) {
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
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }

}

