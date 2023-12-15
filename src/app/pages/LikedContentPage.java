package app.pages;

import app.Admin;
import app.user.User;
import fileio.input.CommandInput;

import java.util.stream.Collectors;

public class LikedContentPage extends Page implements Page.PrintPage {


    /**
     * Generates a message displaying the liked songs and followed playlists of a user.
     * This method creates a formatted string that shows a list of songs liked
     * by the user and a list
     * of playlists followed by the user.
     *
     * @param commandInput Contains the information needed to identify the user.
     * @return A formatted string displaying the user's liked songs and followed playlists.
     */
    public  String printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());


        String likedSongs = formatLikedSongs(user);
        String followedPlaylists = formatFollowedPlaylists(user);

        String message = "Liked songs:\n\t[" + likedSongs + "]\n\nFollowed playlists:\n\t["
                + followedPlaylists + "]";
        return message;
    }

    /**
     * Formats the liked songs of a user into a string.
     * This method takes the list of songs liked by a user and returns a string representation,
     * with each song's name followed by its artist, separated by commas.
     *
     * @param user The user whose liked songs are to be formatted.
     * @return A string representing the list of liked songs.
     */
    private static String formatLikedSongs(final User user) {
        return user.getLikedSongs().stream()
                .map(song -> song.getName() + " - " + song.getArtist())
                .collect(Collectors.joining(", "));
    }

    /**
     * Formats the followed playlists of a user into a string.
     * This method takes the list of playlists followed
     * by a user and returns a string representation,
     * with each playlist's name followed by its owner, separated by commas.
     *
     * @param user The user whose followed playlists are to be formatted.
     * @return A string representing the list of followed playlists.
     */
    private static String formatFollowedPlaylists(final User user) {
        return user.getFollowedPlaylists().stream()
                .map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                .collect(Collectors.joining(", "));
    }
}

