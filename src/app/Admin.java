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

    public static String deleteUser(final CommandInput commandInput) {
        // Verificăm dacă există un utilizator cu acest nume
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

            // Verifică dacă podcasturile host-ului sunt ascultate în prezent
            if (isHostPodcastCurrentlyPlayed(hostToDelete)) {
                return commandInput.getUsername() + " can't be deleted.";
            }

            // Procesul de ștergere a host-ului
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

    private static void deleteAlbum(final Album album) {
        // Creează o copie a listei de melodii pentru a evita ConcurrentModificationException
        List<Song> songsToRemove = new ArrayList<>(Admin.getSongs());

        // Șterge fiecare melodie din album
        for (User user : users) {
            user.getLikedSongs().removeIf((song -> album.getSongs().contains(song)));
        }

        for (Song song : album.getSongs()) {
            songs.remove(song);
        }

        // Șterge melodiile din album
        album.getSongs().clear();
    }

    private static void deleteAllAlbumsOfArtist(final Artist artist) {
        // Creează o copie a listei de albume pentru a evita ConcurrentModificationException
        List<Album> albumsToRemove = new ArrayList<>(artist.getAlbums());

        // Șterge fiecare album
        for (Album album : albumsToRemove) {
            deleteAlbum(album);
        }

        // Șterge lista de albume a artistului
        artist.getAlbums().clear();
    }

    private static void deleteArtist(Artist artist) {
        // Șterge toate albumele artistului
        deleteAllAlbumsOfArtist(artist);

        // Șterge toate evenimentele artistului
        artist.getEvents().clear();


        // Șterge artistul din lista de artiști
        Admin.getArtists().remove(artist);
    }


    private static boolean isArtistPageCurrentlyAccessed(final Artist artist) {
        for (User user : Admin.getUsers()) {
            if (user.getPageType() == Enums.PageType.ARTIST_PAGE
                    && user.getCurrentArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }

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


    private static boolean isHostPageCurrentlyAccessed(final Host host) {
        for (User user : users) {
            if (user.getPageType() == Enums.PageType.HOST_PAGE
                    && user.getCurrentHost().equals(host)) {
                return true;
            }
        }
        return false;
    }

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

    private static void deleteHost(final Host host) {
        // Șterge toate podcasturile host-ului
        for (Podcast podcast : host.getPodcasts()) {
            Admin.removePodcast(podcast);
        }

        // Șterge toate anunțurile host-ului
        host.getAnnouncements().clear();

        // În cele din urmă, șterge host-ul din lista de host-uri
        Admin.getHosts().remove(host);
    }


    private static boolean isListeningToPlaylistOf(final User listener, final User owner) {
        if (listener.getPlayer().getCurrentAudioFile() == null
                || !listener.getPlayer().getType().equals("playlist")) {
            return false;
        }

        Playlist currentPlaylist = (Playlist) listener.getPlayer()
                .getSource().getAudioCollection();

        return currentPlaylist.getOwner().equals(owner.getUsername());
    }

    public static void deleteUser(final User user) {
        // Elimină utilizatorul din lista de utilizatori

        for (Playlist playlist : user.getFollowedPlaylists()) {
            playlist.decreaseFollowers();
        }
        for (Playlist playlist : new ArrayList<>(user.getPlaylists())) {
            // Presupunând că există o metodă pentru a șterge playlisturi
            deletePlaylist(playlist);
        }
        users.remove(user);
    }

    public static void deletePlaylist(final Playlist playlist) {
        // Elimină playlistul din orice locație unde este referit
        for (User user : users) {
            user.getFollowedPlaylists().remove(playlist);
        }

        // Și alte operații necesare pentru a elimina playlistul
    }


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

    public static List<String> getAllUsers() {
        List<String> userNames = new ArrayList<>();

        // Adaugă numele utilizatorilor normali
        List<String> normalUsers = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        // Adaugă numele artiștilor
        List<String> artistUsers = artists.stream()
                .map(Artist::getUsername)
                .collect(Collectors.toList());

        // Adaugă numele hostilor
        List<String> hostUsers = hosts.stream()
                .map(Host::getUsername)
                .collect(Collectors.toList());

        // Combină și sortează toate numele
        userNames = Stream.of(normalUsers, artistUsers, hostUsers)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return userNames;
    }


    public static void addEpisode(final Episode episode) {
        episodes.add(episode);
    }

    public static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    public static void removeAlbum(final Album album, final Artist artist) {
        artist.getAlbums().remove(album);

    }
}
