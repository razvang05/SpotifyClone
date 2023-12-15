package app.pages;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;
import fileio.input.CommandInput;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HomePage extends Page implements Page.PrintPage {

    /**
     * Prints the current page for a specific user based on their activity.
     * This method generates a list of top liked songs and followed playlists for the user.
     *
     * @param commandInput The input command containing user details.
     * @return A string message that includes the top liked songs and followed playlists.
     */
    public  String printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String likedSongs = getTopLikedSongs(user);
        String followedPlaylists = getTopFollowedPlaylists(user);

        return "Liked songs:\n\t[" + likedSongs + "]\n\nFollowed playlists:\n\t["
                + followedPlaylists + "]";
    }

    /**
     * Retrieves top 5 liked songs of a user based on likes.
     *
     * @param user The user whose liked songs are to be retrieved.
     * @return A string representing the top 5 liked songs.
     */
    private String getTopLikedSongs(final User user) {
        return user.getLikedSongs().stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                .limit(5)
                .map(song -> song.getName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Retrieves top 5 followed playlists of a user based on total likes.
     *
     * @param user The user whose followed playlists are to be retrieved.
     * @return A string representing the top 5 followed playlists.
     */
    private String getTopFollowedPlaylists(final User user) {
        return user.getFollowedPlaylists().stream()
                .sorted(Comparator.comparingInt(Playlist::getTotalLikes).reversed())
                .limit(5)
                .map(playlist -> playlist.getName())
                .collect(Collectors.joining(", "));
    }
}

