package app.user;
import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Podcast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.Player;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;

public class Host {
    @Getter
    private String username;
    @Getter
    private final int age;
    @Getter
    private final String city;
    @Getter
    private List<Podcast> podcasts; // presupunem că avem o clasă Podcast definită anterior
    private Enums.PageType pageType;

    public Host(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.podcasts = new ArrayList<>();
    }

    // Metode pentru gestionarea podcasturilor
    public String addPodcast(CommandInput commandInput) {
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

    private Podcast getPodcastByName(String podcastName) {
        for (Podcast podcast : this.podcasts) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }
        return null;
    }

    // Metoda pentru a elimina un podcast din lista
    private void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    public String removePodcast(CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());


        Podcast podcast = host.getPodcastByName(commandInput.getName());
        if (podcast == null) {
            return "doesn't have a podcast with the given name.";
        }

        // Verificăm dacă vreun utilizator normal are podcastul încărcat
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.isLoadedPodcast(podcast)) {
                return "can't delete this podcast.";
            }
        }

        // Ștergem podcastul
        host.removePodcast(podcast);
        Admin.removePodcast(podcast); // Presupunem că Admin are o metodă pentru a șterge podcasturile
        return "deleted the podcast successfully.";
    }


}
