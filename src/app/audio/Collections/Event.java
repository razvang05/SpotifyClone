package app.audio.Collections;
import lombok.Getter;

import java.time.LocalDate;

public class Event {
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private LocalDate date;

    public Event(String name, String description, LocalDate date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

}
