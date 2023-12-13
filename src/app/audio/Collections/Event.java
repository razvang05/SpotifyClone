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

    public Event(final String name, final String description, final LocalDate date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

}
