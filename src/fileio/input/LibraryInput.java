package fileio.input;

import java.util.ArrayList;

public final class LibraryInput {
    private ArrayList<SongInput> songs;
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<UserInput> users;
    private static LibraryInput instance = null;

    public LibraryInput() {
    }

    /**
     * Returns the singleton instance of the LibraryInput class.
     * If the instance does not exist, it creates a new one.
     * If it already exists, it returns the existing instance.
     *
     * @return The single instance of the LibraryInput class.
     */
    public static LibraryInput getInstance() {
        if (instance == null) {
            instance = new LibraryInput();
        }
        return instance;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "LibraryInput{"
                + "songs=" + songs
                + ", podcasts=" + podcasts
                + ", users=" + users
                + '}';
    }
}
