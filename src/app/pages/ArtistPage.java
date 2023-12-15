package app.pages;

import app.Admin;
import app.audio.Collections.Album;
import app.user.Artist;
import app.user.User;
import fileio.input.CommandInput;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ArtistPage extends Page implements Page.PrintPage {

    /**
     * Prints the current page of an artist as viewed by a user.
     * This method formats and displays the artist's albums, merchandise, and events.
     *
     * @param commandInput The input command containing the user's username.
     * @return A string message that includes the artist's albums, merchandise, and events.
     */
    public  String printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Artist artist = user.getCurrentArtist();
        String message = null;

        String albums = artist.getAlbums().isEmpty() ? "[]"
                :
                artist.getAlbums().stream()
                        .map(Album::getName)
                        .collect(Collectors.joining(", ", "[", "]"));

        // Formatăm articolele de merch
        String merchs = artist.getMerchs().isEmpty() ? "[]"
                :
                artist.getMerchs().stream()
                        .map(item -> String.format("%s - %s:\n\t%s",
                                item.getName(),
                                item.getPrice() % 1 == 0
                                        ?
                                        Integer.toString((int)item.getPrice())
                                        :
                                        Double.toString(item.getPrice()),
                                item.getDescription()))
                        .collect(Collectors.joining(", ", "[", "]"));

        // Formatăm evenimentele
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String events = artist.getEvents().isEmpty() ? "[]"
                :
                artist.getEvents().stream()
                        .map(event -> String.format("%s - %s:\n\t%s",
                                event.getName(),
                                event.getDate().format(formatter),
                                event.getDescription()))
                        .collect(Collectors.joining(", ", "[", "]"));

        message = "Albums:\n\t" + albums + "\n\nMerch:\n\t" + merchs + "\n\nEvents:\n\t" + events;
        return message;
    }
}
