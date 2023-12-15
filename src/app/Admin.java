package app;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.Player;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Enums;
import fileio.input.*;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Admin.
 */
public final class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Artist> artists = new ArrayList<>();
    @Getter
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

    /**
     * Adds a new song to the global song list.
     * @param song The song to be added.
     */
    public static void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Adds a new podcast to the global podcast list.
     * @param podcast The podcast to be added.
     */
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

    /**
     * Gets artist.
     *
     * @param username the username
     * @return the artist
     */
    public static Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Gets host.
     *
     * @param username the username
     * @return the host
     */
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
            if (user.isConnected()) {
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

    /**
     * @return A list of strings, each representing the username of an online user.
     */
    public static List<String> getOnlineUsers() {

        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isConnected() && (user.getType() == null
                    || user.getType().equals("user"))) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * Adds a new user, artist, or host to the system.
     *
     * @param username The username of the new user.
     * @param userType The type of the user (user, artist, or host).
     * @param age      The age of the user.
     * @param city     The city of the user.
     * @return A success or error message.
     */
    public static String addUser(final String username, final String userType,
                                 final int age, final String city) {

        // Verificăm dacă există deja un utilizator cu acest nume
        if (getUser(username) != null || getArtist(username) != null
                || getHost(username) != null) {
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

    /**
     * Deletes a user, artist, or host from the system.
     *
     * @param commandInput The command input containing the username of the user to be deleted.
     * @return A success or error message.
     */
    public static String deleteUser(final CommandInput commandInput) {

        User userToDelete = Admin.getUser(commandInput.getUsername());
        Artist artistToDelete = Admin.getArtist(commandInput.getUsername());
        Host hostToDelete = Admin.getHost(commandInput.getUsername());

        if (userToDelete == null && artistToDelete == null && hostToDelete == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        if (userToDelete != null) {
            for (User users : Admin.getUsers()) {
                if (isListeningToPlaylistOf(users, userToDelete)) {
                    return commandInput.getUsername() + " can't be deleted.";
                }
            }
            Admin.deleteUser(userToDelete);
            return commandInput.getUsername() + " was successfully deleted.";
        }

        if (hostToDelete != null) {
            if (isHostPageCurrentlyAccessed(hostToDelete)) {
                return commandInput.getUsername() + " can't be deleted.";
            }


            if (isHostPodcastCurrentlyPlayed(hostToDelete)) {
                return commandInput.getUsername() + " can't be deleted.";
            }


            deleteHost(hostToDelete);
            return commandInput.getUsername() + " was successfully deleted.";
        }

        if (artistToDelete != null) {
            if (isArtistPageCurrentlyAccessed(artistToDelete)
                    || isArtistAlbumCurrentlyPlayed(artistToDelete)
                    || isArtistSongCurrentlyPlayed(artistToDelete)
                    || isArtistSongInPlaylistCurrentlyPlayed(artistToDelete)) {
                return commandInput.getUsername() + " can't be deleted.";
            }
        }
        deleteArtist(artistToDelete);
        return commandInput.getUsername() + " was successfully deleted.";

    }

    /**
     * Deletes an album and its associated songs.
     *
     * @param album The album to be deleted.
     */
    private static void deleteAlbum(final Album album) {

        List<Song> songsToRemove = new ArrayList<>(Admin.getSongs());


        for (User user : users) {
            user.getLikedSongs().removeIf((song -> album.getSongs().contains(song)));
        }

        for (Song song : album.getSongs()) {
            songs.remove(song);
        }


        album.getSongs().clear();
    }

    /**
     * Deletes all albums of a specific artist.
     *
     * @param artist The artist whose albums are to be deleted.
     */
    private static void deleteAllAlbumsOfArtist(final Artist artist) {

        List<Album> albumsToRemove = new ArrayList<>(artist.getAlbums());


        for (Album album : albumsToRemove) {
            deleteAlbum(album);
        }


        artist.getAlbums().clear();
    }

    /**
     * Deletes an artist from the system.
     *
     * @param artist The artist to be deleted.
     */
    private static void deleteArtist(Artist artist) {

        deleteAllAlbumsOfArtist(artist);


        artist.getEvents().clear();



        Admin.getArtists().remove(artist);
    }

    /**
     * Checks if a specific artist's page is currently being accessed by any user.
     * @param artist The artist to check for.
     * @return true if any user is currently on the artist's page, false otherwise.
     */
    private static boolean isArtistPageCurrentlyAccessed(final Artist artist) {
        for (User user : Admin.getUsers()) {
            if (user.getPageType() == Enums.PageType.ARTIST_PAGE
                    && user.getCurrentArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if any user is currently playing an album of a specific artist.
     * @param artist The artist whose album playback is to be checked.
     * @return true if an album of the artist is currently being played, false otherwise.
     */
    private static boolean isArtistAlbumCurrentlyPlayed(final Artist artist) {
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.getCurrentAudioFile() instanceof Song) {
                Song song = (Song) player.getCurrentAudioFile();
                if (song.getArtist().equals(artist.getUsername())
                        && player.getType().equals("album")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if any user is currently playing a song by a specific artist.
     * @param artist The artist whose song playback is to be checked.
     * @return true if a song by the artist is currently being played, false otherwise.
     */
    private static boolean isArtistSongCurrentlyPlayed(final Artist artist) {
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.getCurrentAudioFile() instanceof Song) {
                Song song = (Song) player.getCurrentAudioFile();
                if (song.getArtist().equals(artist.getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if any user is currently playing a playlist containing
     * a song by a specific artist.
     * @param artist The artist to check for in the playlists.
     * @return true if a song by the artist is in a currently played playlist, false otherwise.
     */
    private static boolean isArtistSongInPlaylistCurrentlyPlayed(final Artist artist) {
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.getCurrentAudioFile() instanceof Song) {
                Song song = (Song) player.getCurrentAudioFile();
                if (song.getArtist().equals(artist.getUsername())
                        && player.getType().equals("playlist")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Checks if a specific host's page is currently being accessed by any user.
     * @param host The host to check for.
     * @return true if any user is currently on the host's page, false otherwise.
     */
    private static boolean isHostPageCurrentlyAccessed(final Host host) {
        for (User user : users) {
            if (user.getPageType() == Enums.PageType.HOST_PAGE
                    && user.getCurrentHost().equals(host)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if any user is currently playing a podcast of a specific host.
     * @param host The host whose podcast playback is to be checked.
     * @return true if a podcast of the host is currently being played, false otherwise.
     */
    private static boolean isHostPodcastCurrentlyPlayed(final Host host) {
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            for (Podcast podcast : host.getPodcasts()) {
                if (player.isLoadedPodcast(podcast)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Deletes a host and all their associated data from the system.
     * @param host The host to be deleted.
     */
    private static void deleteHost(final Host host) {

        for (Podcast podcast : host.getPodcasts()) {
            Admin.removePodcast(podcast);
        }


        host.getAnnouncements().clear();


        Admin.getHosts().remove(host);
    }

    /**
     * Checks if a user is listening to a playlist owned by another user.
     * @param listener The user who is potentially listening.
     * @param owner The owner of the playlist.
     * @return true if the listener is playing the owner's playlist, false otherwise.
     */
    private static boolean isListeningToPlaylistOf(final User listener, final User owner) {
        if (listener.getPlayer().getCurrentAudioFile() == null
                || !listener.getPlayer().getType().equals("playlist")) {
            return false;
        }

        Playlist currentPlaylist = (Playlist) listener.getPlayer()
                .getSource().getAudioCollection();

        return currentPlaylist.getOwner().equals(owner.getUsername());
    }

    /**
     * Deletes a user and all their associated data from the system.
     * @param user The user to be deleted.
     */
    public static void deleteUser(final User user) {


        for (Playlist playlist : user.getFollowedPlaylists()) {
            playlist.decreaseFollowers();
        }
        for (Playlist playlist : new ArrayList<>(user.getPlaylists())) {

            deletePlaylist(playlist);
        }
        users.remove(user);
    }

    /**
     * Deletes a playlist and all its associated data from the system.
     * @param playlist The playlist to be deleted.
     */
    public static void deletePlaylist(final Playlist playlist) {

        for (User user : users) {
            user.getFollowedPlaylists().remove(playlist);
        }


    }

    /**
     * Retrieves the albums of a specific artist.
     * @param artistUsername The username of the artist.
     * @return A list of AlbumOutput objects representing the artist's albums.
     */
    public static List<AlbumOutput> showAlbums(final String artistUsername) {
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

    /**
     * Displays the podcasts of a specific host.
     * @param commandInput The command input containing the host's username.
     * @return A list of maps, each representing a podcast with its details.
     */
    public static List<Map<String, Object>> showPodcasts(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());

        List<Map<String, Object>> podcastList = new ArrayList<>();

        for (Podcast podcast : host.getPodcasts()) {
            Map<String, Object> podcastDate = new HashMap<>();
            podcastDate.put("name", podcast.getName());
            List<String> episodeNames = podcast.getEpisodes().stream()
                    .map(Episode::getName)
                    .collect(Collectors.toList());
            podcastDate.put("episodes", episodeNames);
            podcastList.add(podcastDate);
        }

        return podcastList;
    }

    /**
     * Retrieves the top 5 albums based on likes.
     *
     * @return List of top 5 album names.
     */
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

    /**
     * Retrieves the list of all albums in the system.
     *
     * @return List of all albums.
     */
    public static List<Album> getAllAlbums() {
        List<Album> allAlbums = new ArrayList<>();
        for (Artist artist : artists) {
            allAlbums.addAll(artist.getAlbums());
        }
        return allAlbums;
    }

    /**
     * Retrieves the list of all users in the system.
     *
     * @return List of all users.
     */
    public static List<String> getAllUsers() {
        List<String> userNames = new ArrayList<>();


        List<String> normalUsers = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());


        List<String> artistUsers = artists.stream()
                .map(Artist::getUsername)
                .collect(Collectors.toList());


        List<String> hostUsers = hosts.stream()
                .map(Host::getUsername)
                .collect(Collectors.toList());


        userNames = Stream.of(normalUsers, artistUsers, hostUsers)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return userNames;
    }


    /**
     * Adds a new episode to the list of episodes.
     * @param episode The episode to be added.
     */
    public static void addEpisode(final Episode episode) {
        episodes.add(episode);
    }

    /**
     * Removes a specified podcast from the list of podcasts.
     * @param podcast The podcast to be removed.
     */
    public static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Removes a specified album from an artist's album list.
     * @param album The album to be removed.
     * @param artist The artist whose album is being removed.
     */
    public static void removeAlbum(final Album album, final Artist artist) {
        artist.getAlbums().remove(album);

    }
}

