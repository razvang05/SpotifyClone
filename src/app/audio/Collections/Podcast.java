package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Podcast extends AudioCollection {
    @Getter
    private final List<Episode> episodes;

    public Podcast(final String name, final String owner, final List<Episode> episodes) {
        super(name, owner);
        this.episodes = episodes;
    }

    public Podcast(final String name, final String username) {
        super(name, username);
        this.episodes = new ArrayList<>();
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    @Override
    public int getNumberOfTracks() {
        return episodes.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return episodes.get(index);
    }

    /**
     * Adds a new episode to the podcast.
     *
     * @param episode The episode to be added to the podcast.
     */
    public void addEpisode(final Episode episode) {
        episodes.add(episode);
    }
}
