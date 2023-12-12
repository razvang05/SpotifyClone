package app;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.ArtistEntry;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Admin.
 */
public final class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Episode> episodes = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    ///metoda    sa adaug melodia in lista mare de melodii
    public static void addSong(final Song song) {
        songs.add(song);
    }

    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    public static List<Episode> getEpisodes() {
        return new ArrayList<>(episodes);
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
    public static Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if(user.isConnected() == true) {
                user.simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }

    public static List<String> getOnlineUsers() {

        List<String> onlineUsers = new ArrayList<>();
        for(User user : users) {
            if(user.isConnected() == true && (user.getType() == null || user.getType().equals("user"))) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static String addUser(String username, String userType, int age, String city) {
        // Verificăm dacă există deja un utilizator cu acest nume
        if (getUser(username) != null || getArtist(username) != null || getHost(username) != null) {
            return "The username " + username + " is already taken.";
        }

        // Creăm și adăugăm utilizatorul în funcție de tipul acestuia
        switch (userType.toLowerCase()) {
            case "user":
                users.add(new User(username, age, city));
                break;
            case "artist":
                artists.add(new Artist(username, age, city));
                break;
            case "host":
                hosts.add(new Host(username, age, city));
                break;
            default:
                return "Invalid user type.";
        }

        return "The username " + username + " has been added successfully.";
    }

    public static List<AlbumOutput> showAlbums(String artistUsername) {
        Artist artist = getArtist(artistUsername);

        List<AlbumOutput> albumsOutput = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            List<String> songNames = new ArrayList<>();
            for (Song song : album.getSongs()) {
                songNames.add(song.getName());
            }
            albumsOutput.add(new AlbumOutput(album.getName(), songNames));
        }
        return albumsOutput;
    }

    public static List<String> getTop5Albums() {
        Map<Album, Integer> albumLikes = new HashMap<>();

        // Colectăm toate albumele de la toți artiștii
        for (Artist artist : artists) {
            for (Album album : artist.getAlbums()) {
                int likesSum = album.getSongs().stream()
                        .mapToInt(Song::getLikes)
                        .sum();
                albumLikes.put(album, likesSum);
            }
        }

        return albumLikes.entrySet().stream()
                .sorted(Map.Entry.<Album, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());

    }

    public static List<Album> getAllAlbums() {
        List<Album> allAlbums = new ArrayList<>();
        for (Artist artist : artists) {
            allAlbums.addAll(artist.getAlbums());
        }
        return allAlbums;
    }


    public static void addEpisode(final Episode episode) {
        episodes.add(episode);
    }

    public static void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }
}
