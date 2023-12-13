package app.audio.Collections;

import java.util.List;
import lombok.Getter;
public class AlbumOutput {

    @Getter
    private String name;
    @Getter
    private List<String> songs;

    public AlbumOutput(final String name, final List<String> songs) {
        this.name = name;
        this.songs = songs;
    }
}