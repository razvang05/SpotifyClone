package app.user;

import app.Admin;
import app.audio.Collections.Announcement;
import app.audio.Collections.Podcast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.audio.Files.Episode;

import app.audio.LibraryEntry;
import app.player.Player;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import lombok.Getter;

/**
 * Represents a host in the system. Hosts can create and manage podcasts and announcements.
 */
public class Host extends LibraryEntry {
    @Getter
    private String username;
    @Getter
    private final int age;
    @Getter
    private final String city;
    @Getter
    private List<Podcast> podcasts;
    @Getter
    private List<Announcement> announcements = new ArrayList<>();
    private Enums.PageType pageType;

    /**
     * Constructs a new host with the given username, age, and city.
     * Initializes the list of podcasts and announcements.
     *
     * @param username The host's username.
     * @param age      The host's age.
     * @param city     The host's city.
     */
    public Host(final String username, final int age, final String city) {
        super(username);
        this.username = username;
        this.age = age;
        this.city = city;
        this.podcasts = new ArrayList<>();
    }

    /**
     * Adds a new podcast with unique episodes to the host's podcast list.
     * @param commandInput The details of the podcast to be added.
     * @return Success or error message based on the operation outcome.
     */
    public String addPodcast(final CommandInput commandInput) {
        // Verificăm dacă există deja un podcast cu acest nume
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(commandInput.getName())) {
                return "has another podcast with the same name.";
            }
        }
        Set<String> episodeNames = new HashSet<>();
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            if (!episodeNames.add(episodeInput.getName())) {
                return "has the same episode in this podcast.";
            }
        }

        Podcast newPodcast = new Podcast(commandInput.getName(), commandInput.getUsername());
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            Episode newEpisode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription());
            newPodcast.addEpisode(newEpisode);
            Admin.addEpisode(newEpisode);
        }

        podcasts.add(newPodcast);
        Admin.addPodcast(newPodcast);

        return "has added new podcast successfully.";
    }

    /**
     * Retrieves a podcast by its name from the host's podcast list.
     *
     * @param podcastName The name of the podcast to retrieve.
     * @return The podcast object if found, null otherwise.
     */
    private Podcast getPodcastByName(final String podcastName) {
        for (Podcast podcast : this.podcasts) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }
        return null;
    }


    private void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Removes a specified podcast from a host's list .
     * @param commandInput Contains the username of the host and
     * the name of the podcast to be removed.
     * @return A string message indicating the success or failure of the podcast removal.
     */
    public String removePodcast(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());


        Podcast podcast = host.getPodcastByName(commandInput.getName());
        if (podcast == null) {
            return "doesn't have a podcast with the given name.";
        }


        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.isLoadedPodcast(podcast)) {
                return "can't delete this podcast.";
            }
        }

        host.removePodcast(podcast);
        Admin.removePodcast(podcast);
        return "deleted the podcast successfully.";
    }

    /**
     * Adds a new announcement to the host's list of announcements.
     *
     * @param commandInput Details of the announcement to be added, including its name
     * and description.
     * @return A message indicating whether the announcement was successfully added or not.
     */
    public String addAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String name = commandInput.getName();
        String description = commandInput.getDescription();
        Host host = Admin.getHost(username);

        if (host.hasAnnouncementWithName(name) != null) {
            return " has already added an announcement with this name.";
        }
        Announcement newAnnouncement = new Announcement(name, description);
        host.addAnnouncement(newAnnouncement);
        return " has successfully added new announcement.";

    }

    /**
     * Adds an announcement object to the host's list of announcements.
     *
     * @param announcement The announcement to be added.
     */
    public void addAnnouncement(final Announcement announcement) {
        announcements.add(announcement);

    }

    /**
     * Checks if the host has an announcement with the given name.
     *
     * @param announcementName The name of the announcement to check.
     * @return The announcement object if found, null otherwise.
     */
    public Announcement hasAnnouncementWithName(final String announcementName) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(announcementName)) {
                return announcement;
            }
        }
        return null;
    }

    /**
     * Removes an announcement from the host's list of announcements.
     *
     * @param commandInput Contains the host's username and the name of the announcement
     * to be removed.
     * @return A message indicating the result of the announcement removal operation.
     */
    public String removeAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String name = commandInput.getName();
        Host host = Admin.getHost(username);
        Announcement announcement = host.hasAnnouncementWithName(name);
        if (announcement == null) {
            return " has no announcement with the given name.";
        }
        host.removeAnnouncement(announcement);
        return " has successfully deleted the announcement.";
    }

    /**
     * Removes an announcement object from the host's list of announcements.
     *
     * @param announcement The announcement to be removed.
     */
    public void removeAnnouncement(final Announcement announcement) {
        announcements.remove(announcement);
    }



}
