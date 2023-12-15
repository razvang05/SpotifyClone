package app.pages;

import app.Admin;
import app.user.User;
import fileio.input.CommandInput;

import java.util.stream.Collectors;

public class LikedContentPage extends Page implements Page.PrintPage {


    public  String printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());


        String likedSongs = formatLikedSongs(user);
        String followedPlaylists = formatFollowedPlaylists(user);

        String message = "Liked songs:\n\t[" + likedSongs + "]\n\nFollowed playlists:\n\t["
                + followedPlaylists + "]";
        return message;
    }

    private static String formatLikedSongs(final User user) {
        return user.getLikedSongs().stream()
                .map(song -> song.getName() + " - " + song.getArtist())
                .collect(Collectors.joining(", "));
    }

    private static String formatFollowedPlaylists(final User user) {
        return user.getFollowedPlaylists().stream()
                .map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                .collect(Collectors.joining(", "));
    }
}

