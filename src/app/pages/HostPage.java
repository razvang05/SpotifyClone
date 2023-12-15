package app.pages;

import app.Admin;
import app.user.Host;
import app.user.User;
import fileio.input.CommandInput;

import java.util.stream.Collectors;

public class HostPage extends Page implements Page.PrintPage {

    /**
     * Prints the current page of a host as viewed by a user.
     * This method formats and displays the host's podcasts and announcements.
     *
     * @param commandInput The input command containing the user's username.
     * @return A string message that includes the host's podcasts and announcements.
     */
    public String printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Host host  = user.getCurrentHost();
        String message = null;



        // Obține informațiile despre podcasturi și anunțuri ale hostului
        String podcasts = host.getPodcasts().isEmpty() ? "[]"
                :
                host.getPodcasts().stream()
                        .map(podcast -> {
                            String episodes = podcast.getEpisodes().stream()
                                    .map(episode -> episode.getName()
                                            +
                                            " - " + episode.getDescription())
                                    .collect(Collectors.joining(", ", "[", "]"));
                            return podcast.getName() + ":\n\t" + episodes;
                        })
                        .collect(Collectors.joining("\n, ", "[", "\n]"));

        // Formatează lista de anunțuri
        String announcements = host.getAnnouncements().isEmpty() ? "[]"
                :
                host.getAnnouncements().stream()
                        .map(announcement -> announcement.getName() + ":\n\t"
                                +
                                announcement.getDescription())
                        .collect(Collectors.joining("\n, ", "[", "\n]"));


        // Construiește răspunsul
        message = "Podcasts:\n\t" + podcasts + "\n\nAnnouncements:\n\t" + announcements;
        return message;
    }

}
