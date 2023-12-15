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

    /**
            * Constructs a new Album object with specified details.
            * It sets up an empty list of songs which can be added to the album later.
            *
            * @param username    The username of the artist who created the album.
            * @param name        The name of the album.
            * @param releaseYear The year when the album was released.
            * @param description A brief description of the album.
 */
    public Album(final String username, final String name,
                 final int releaseYear, final String description) {

        super(name, username);
        this.username = username;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.owner = username;
        this.songs = new ArrayList<>();
    }

    /**
     * Adds a song to the playlist.
     * This method appends the specified song to the end of the list of songs in this playlist.
     *
     * @param song The song to be added to the playlist.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Calculates the total number of likes for all songs in the playlist.
     * This method sums up the likes of each song in the playlist and returns the total count.
     *
     * @return The total number of likes for all songs in this playlist.
     */
    public int getTotalLikes() {
        return songs.stream().mapToInt(Song::getLikes).sum();
    }

    /**
     * Returns the number of tracks in the playlist.
     * This method calculates the size of the songs list, representing the total number of tracks.
     *
     * @return The number of songs (tracks) in the playlist.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * This method returns the song located at the specified index in the list of songs.
     *
     * @param index The index of the song to be retrieved.
     * @return The song at the specified index in this playlist.
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

}


