package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import fileio.input.EpisodeInput;

import java.util.ArrayList;
import java.util.List;

public final class Podcast extends AudioCollection {
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

    public void addEpisode(final Episode episode) {
        episodes.add(episode);
    }
}
