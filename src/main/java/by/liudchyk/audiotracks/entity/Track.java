package by.liudchyk.audiotracks.entity;

/**
 * Class {@code Track} is used to store Track entity.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Entity
 */
public class Track extends Entity {
    /**
     * The track's id.
     */
    private int id;
    /**
     * The track's title.
     */
    private String title;
    /**
     * The track's genre.
     */
    private String genre;
    /**
     * The track's artist.
     */
    private String artist;
    /**
     * The track's price.
     */
    private Double price;
    /**
     * The track's length in seconds.
     */
    private int length;

    /**
     * Creates new object of Track entity
     *
     * @param id     is track's id
     * @param title  is track's title
     * @param genre  is track's genre
     * @param price  is track's price
     * @param length is track's length in seconds
     * @param artist is track's artist(s)
     */
    public Track(int id, String title, String genre, Double price, int length, String artist) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.length = length;
        this.artist = artist;
    }

    /**
     * Gets track's id
     *
     * @return track's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets track's title
     *
     * @return track's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets track's genre
     *
     * @return track's genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets track's price
     *
     * @return track's price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Gets track's length in seconds
     *
     * @return track's length in seconds
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets track's artist
     *
     * @return track's artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets track's length in minutes
     *
     * @return track's length in minutes
     */
    public String getLengthTranslated() {
        int minutes = length / 60;
        int seconds = length % 60;
        return new String(Integer.toString(minutes) + ":" + seconds);
    }
}