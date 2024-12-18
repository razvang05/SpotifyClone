package app;

import app.audio.Collections.*;
import app.pages.ArtistPage;
import app.pages.HomePage;
import app.pages.HostPage;
import app.pages.LikedContentPage;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Enums;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.*;


/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        ArrayList<String> results = user.search(filters, type);
        String message = "Search returned " + results.size() + " results";
        if (!user.isConnected()) {
            message = user.getUsername() + " is offline.";
            results = new ArrayList<>();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isConnected()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        if (user != null) {
            message = user.createPlaylist(commandInput.getPlaylistName(),
                    commandInput.getTimestamp());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Switches the connection status of a user.
     * This method toggles the online/offline status of a user based on the given command input.
     *
     * @param commandInput The input command containing the user's username.
     * @return An ObjectNode representing the result of the operation.
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = user.switchConnectionStatus(commandInput.getUsername());

        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Retrieves a list of online users.
     *
     * @param commandInput The input command, used for logging purposes.
     * @return An ObjectNode with a list of online user usernames.
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> users = Admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(users));

        return objectNode;
    }

    /**
     * Adds a new user to the system.
     * This method creates a new user with the specified details and adds them to the system.
     *
     * @param commandInput The command input containing the new user's details.
     * @return An ObjectNode indicating the result of the user addition.
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        String userType = commandInput.getType();

        String message = Admin.addUser(username, userType, age, city);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Deletes a user from the system.
     * This method removes an existing user and their associated data based.
     *
     * @param commandInput The command input containing the username of the user to be deleted.
     * @return An ObjectNode indicating the result of the user deletion.
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message = Admin.deleteUser(commandInput);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds an album for an artist.
     * This method allows an artist to add a new album, provided the artist exists in the system.
     *
     * @param commandInput The command input containing the album's
     * details and the artist's username.
     * @return An ObjectNode indicating the result of the album addition.
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Host host = Admin.getHost(commandInput.getUsername());
        String message = null;
        if (artist == null && (user != null || host != null)) {
            message = commandInput.getUsername() + " is not an artist.";
        } else if (artist == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + " " + artist.addAlbum(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new event for an artist.
     * This method allows an artist to add a new event with
     * specific details such as date and location.
     *
     * @param commandInput The command input containing the event details
     * and the artist's username.
     * @return A message indicating the result of the event addition.
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Host host = Admin.getHost(commandInput.getUsername());
        String message = null;
        if (artist == null && (user != null || host != null)) {
            message = commandInput.getUsername() + " is not an artist.";
        } else if (artist == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = artist.addEvent(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new merchandise item for an artist.
     * This method allows an artist to add a new merchandise item with specific details.
     *
     * @param commandInput The command input containing the merchandise details
     * and the artist's username.
     * @return A message indicating the result of the merchandise addition.
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Host host = Admin.getHost(commandInput.getUsername());
        String message = null;
        if (artist == null && (user != null || host != null)) {
            message = commandInput.getUsername() + " is not an artist.";
        } else if (artist == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = artist.addMerch(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new podcast for a host.
     * This method allows a host to add a new podcast with a unique name and episodes.
     *
     * @param commandInput The command input containing the podcast's details.
     * @return A message indicating the result of the podcast addition.
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = Admin.getArtist(commandInput.getUsername());
        String message = null;
        if (host == null && (user != null || artist != null)) {
            message = commandInput.getUsername() + " is not a host.";
        } else if (host == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + " " + host.addPodcast(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes a podcast from a host.
     * This method allows a host to delete an existing podcast from their list.
     *
     * @param commandInput The command input containing the podcast's name.
     * @return A message indicating the result of the podcast removal.
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = Admin.getArtist(commandInput.getUsername());
        String message = null;
        if (host == null && (user != null || artist != null)) {
            message = commandInput.getUsername() + " is not a host.";
        } else if (host == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + " " + host.removePodcast(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new announcement for a host.
     * Hosts can use this method to add announcements to their page.
     *
     * @param commandInput The command input containing the announcement details.
     * @return A message indicating the result of the announcement addition.
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = Admin.getArtist(commandInput.getUsername());
        String message = null;
        if (host == null && (user != null || artist != null)) {
            message = commandInput.getUsername() + " is not a host.";
        } else if (host == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + host.addAnnouncement(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes an announcement from a host.
     * This method allows a host to delete an existing announcement from their list.
     *
     * @param commandInput The command input containing the announcement's name.
     * @return A message indicating the result of the announcement removal.
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = Admin.getArtist(commandInput.getUsername());
        String message = null;
        if (host == null && (user != null || artist != null)) {
            message = commandInput.getUsername() + " is not a host.";
        } else if (host == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + host.removeAnnouncement(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Displays all albums of a specific user.
     * This method returns a list of albums associated with a user's username.
     *
     * @param commandInput The command input containing the user's username.
     * @return An ObjectNode containing a list of albums.
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        ObjectMapper objectMapper1 = new ObjectMapper();
        List<AlbumOutput> albums = Admin.showAlbums(commandInput.getUsername());

        ObjectNode objectNode = objectMapper1.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());


        ArrayNode albumsArray = objectMapper1.createArrayNode();
        for (AlbumOutput album : albums) {
            ObjectNode albumNode = objectMapper1.createObjectNode();
            albumNode.put("name", album.getName());
            ArrayNode songsArray = objectMapper1.createArrayNode();
            for (String song : album.getSongs()) {
                songsArray.add(song);
            }
            albumNode.set("songs", songsArray);
            albumsArray.add(albumNode);
        }

        objectNode.set("result", albumsArray);

        return objectNode;
    }

    /**
     * Displays all podcasts of a specific host.
     * This method returns a list of podcasts associated with a host's username.
     *
     * @param commandInput The command input containing the host's username.
     * @return An ObjectNode containing a list of podcasts.
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        List<Map<String, Object>> podcasts = Admin.showPodcasts(commandInput);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());


        ArrayNode podcastArrayNode = objectMapper.valueToTree(podcasts);
        objectNode.set("result", podcastArrayNode);

        return objectNode;
    }

    /**
     * Retrieves the top 5 albums from the system.
     * This method returns a list of the top 5 albums based on a predefined criteria.
     *
     * @param commandInput The command input used for logging purposes.
     * @return An ObjectNode containing the top 5 albums.
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * Retrieves a list of all users in the system.
     * This method returns a list of usernames of all users, sorted in a specific order.
     *
     * @param commandInput The command input used for logging purposes.
     * @return An ObjectNode containing the list of all users.
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> results = Admin.getAllUsers();


        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Prints the current page a user is viewing.
     * This method returns information about the current page a user is on,
     * including its content.
     *
     * @param commandInput The command input containing the user's username.
     * @return An ObjectNode with the details of the current page.
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        if (!(Admin.getUser(commandInput.getUsername())).isConnected()) {

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", commandInput.getUsername() + " is offline.");

            return objectNode;
        }
        if (Admin.getUser(commandInput.getUsername())
                .getPageType()
                .equals(Enums.PageType.HOME_PAGE)) {

            HomePage homePage = new HomePage();
            String message = homePage.printCurrentPage(commandInput);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        } else if (Admin.getUser(commandInput.getUsername())
                .getPageType()
                .equals(Enums.PageType.ARTIST_PAGE)) {

            ArtistPage artistPage = new ArtistPage();
            String message = artistPage.printCurrentPage(commandInput);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        } else if (Admin.getUser(commandInput.getUsername())
                .getPageType()
                .equals(Enums.PageType.HOST_PAGE)) {

            HostPage hostPage = new HostPage();
            String message = hostPage.printCurrentPage(commandInput);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        } else if (Admin.getUser(commandInput.getUsername())
                .getPageType()
                .equals(Enums.PageType.LIKEDCONTENT_PAGE)) {

            LikedContentPage likedContentPage = new LikedContentPage();
            String message = likedContentPage.printCurrentPage(commandInput);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }


        return null;
    }

    /**
     * Changes the current page a user is viewing.
     * This method allows a user to change the current page they are viewing.
     *
     * @param commandInput The command input containing the user's username.
     * @return An ObjectNode indicating the result of the page change.
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.changePage(commandInput);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Processes the command to remove an album for a specified artist.
     *
     * @param commandInput The command input containing the artist's username and
     * the album's name.
     * @return An ObjectNode representing the response with the status of the album removal.
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {

        Host host = Admin.getHost(commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = Admin.getArtist(commandInput.getUsername());
        String message = null;
        if (artist == null && (user != null || host != null)) {
            message = commandInput.getUsername() + " is not a artist.";
        } else if (artist == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = commandInput.getUsername() + " " + artist.removeAlbum(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Processes the command to remove an event for a specified artist.
     * This method should be implemented to verify the existence of the artist and the event,
     * and handle the removal of the event accordingly.
     *
     * @param commandInput The command input containing the artist's username
     *  and the event's details.
     * @return An ObjectNode representing the response with the status of the event removal.
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {

        String message = null;

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }


    public static ObjectNode getTop5Artists(final CommandInput commandInput) {

        String message = null;

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }


}

