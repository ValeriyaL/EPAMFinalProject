package by.liudchyk.audiotracks.entity;

/**
 * Created by Admin on 24.12.2016.
 */
public class Track extends Entity {
    private int id;
    private String title;
    private String genre;
    private String artist;
    private Double price;
    private int length;

    public Track(int id, String title, String genre, Double price, int length, String artist) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.length = length;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Double getPrice() {
        return price;
    }

    public int getLength() {
        return length;
    }

    public String getArtist() {
        return artist;
    }

    public String getLengthTranslated(){
        int minutes = length / 60;
        int seconds = length % 60;
        return new String(Integer.toString(minutes)+":"+seconds);
    }
}