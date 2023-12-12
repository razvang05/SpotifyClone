package app.audio.Collections;

import lombok.Getter;
public class Merch {
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private double price;

    public Merch(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
