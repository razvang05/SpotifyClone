package app.user;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.Admin;
import app.audio.Collections.Event;
import app.audio.Collections.Merch;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.SongInput;
import lombok.Getter;
import app.audio.Collections.Album;

public class Artist extends LibraryEntry {
    @Getter
    private final String username;
    @Getter
    private final int age;
    @Getter
    private final String city;
    @Getter
    private List<Album> albums;
    @Getter
    private List<Event> events = new ArrayList<>();
    @Getter
    private List<Merch> merchs = new ArrayList<>();
    private Enums.PageType pageType;

    /**
     * Creates a new artist with the specified username, age, and city.
     * Initializes empty lists for albums, events, and merchandise.
     *
     * @param username The artist's username.
     * @param age      The artist's age.
     * @param city     The artist's city.
     */
    public Artist(final String username, final int age, final String city) {
        super(username);
        this.username = username;
        this.age = age;
        this.city = city;
        this.albums = new ArrayList<>();
    }


    /**
     * Adds a new album for the artist. Ensures no duplicate album names or
     * song names within the album.
     *
     * @param commandInput Contains information about the new album,
     * including name, release year, and songs.
     * @return A message indicating the success or failure of adding the album.
     */
    public String addAlbum(final CommandInput commandInput) {
        if (getAlbumByName(commandInput.getName()) != null) {
            return "has another album with the same name.";
        }

        Set<String> songNames = new HashSet<>();
        for (SongInput songInput : commandInput.getSongs()) {
            if (!songNames.add(songInput.getName())) {
                return "has the same song at least twice in this album.";
            }
        }

        Album newAlbum = new Album(commandInput.getUsername(), commandInput.getName(),
                commandInput.getReleaseYear(), commandInput.getDescription());

        for (SongInput songInput : commandInput.getSongs()) {
            Song newSong = new Song(songInput.getName(), songInput.getDuration(),
                    newAlbum.getName(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), this.username);
            newAlbum.addSong(newSong);
            Admin.addSong(newSong);
        }

        albums.add(newAlbum);
        return "has added new album successfully.";
    }

    /**
     * Retrieves an album by its name from the artist's album list.
     *
     * @param albumName The name of the album to retrieve.
     * @return The album object if found, null otherwise.
     */
    public Album getAlbumByName(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Adds an event to the artist's event list.
     *
     * @param event The event to add.
     */
    public void addEvent(final Event event) {
        events.add(event);
    }

    /**
     * Adds a new event for the artist based on provided command input.
     *
     * @param commandInput Contains information about the event, including name,
     * description, and date.
     * @return A message indicating the success or failure of adding the event.
     */
    public String addEvent(final CommandInput commandInput) {

        String eventName = commandInput.getName();
        String description = commandInput.getDescription();
        String dateString = commandInput.getDate();
        String username = commandInput.getUsername();

        Artist artist = Admin.getArtist(username);

        if (artist.hasEventWithName(eventName) != null) {
            return username + " has another event with the same name.";
        }
        LocalDate date;
        try {
            date = parseDate(dateString);
        } catch (DateTimeParseException | InvalidDateException e) {
            return "Event for " + username + " does not have a valid date.";
        }

        Event newEvent = new Event(eventName, description, date);
        artist.addEvent(newEvent);
        return username + " has added new event successfully.";
    }

    /**
     * Checks if the artist has an event with a given name.
     *
     * @param eventName The name of the event to check.
     * @return The event object if found, null otherwise.
     */
    public Event hasEventWithName(final String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateString The date string to parse.
     * @return A LocalDate object representing the parsed date.
     * @throws DateTimeParseException   If the date string is not in the expected format.
     * @throws InvalidDateException     If the date is invalid.
     */
    private LocalDate parseDate(final String dateString) throws DateTimeParseException,
            InvalidDateException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        validateDate(date);
        return date;
    }

    /**
     * Validates a given LocalDate object.
     *
     * @param date The date to validate.
     * @throws InvalidDateException If the date is invalid.
     */
    private void validateDate(final LocalDate date) throws InvalidDateException {
        if (date.getYear() < 1900 || date.getYear() > 2023
                || date.getMonthValue() > 12
                || (date.getMonth() == Month.FEBRUARY && date.getDayOfMonth() > 28)
                || date.getDayOfMonth() > 31) {
            throw new InvalidDateException("Date is not valid.");
        }
    }

    /**
     * Custom exception class for invalid dates.
     */
    private class InvalidDateException extends Exception {
         InvalidDateException(final String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * Adds merchandise to the artist's merch list.
     *
     * @param merch The merch to add.
     */
    public void addMerch(final Merch merch) {
        merchs.add(merch);

    }

    /**
     * Adds a new merchandise item for the artist based on provided command input.
     *
     * @param commandInput Contains information about the merchandise,
     * including name, description, and price.
     * @return A message indicating the success or failure of adding the merchandise.
     */
    public String addMerch(final CommandInput commandInput) {
        String merchName = commandInput.getName();
        String description = commandInput.getDescription();
        double price = commandInput.getPrice();
        String username = commandInput.getUsername();

        Artist artist = Admin.getArtist(username);

        if (artist.hasMerchWithName(merchName) != null) {
            return username + " has merchandise with the same name.";
        }

        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        Merch newMerch = new Merch(merchName, description, price);
        artist.addMerch(newMerch);
        return username + " has added new merchandise successfully.";
    }

    /**
     * Checks if the artist has merchandise with a given name.
     *
     * @param merchName The name of the merchandise to check.
     * @return The merchandise object if found, null otherwise.
     */
    private Merch hasMerchWithName(final String merchName) {
        for (Merch merch : merchs) {
            if (merch.getName().equals(merchName)) {
                return merch;
            }
        }
        return null;
    }

}
