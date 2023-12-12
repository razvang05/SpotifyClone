package app.audio.Collections;

import java.util.List;
import lombok.Getter;
public class AlbumOutput {

    @Getter
    private String name;
    @Getter
    private List<String> songs;

    public AlbumOutput(String name, List<String> songs) {
        this.name = name;
        this.songs = songs;
    }
}