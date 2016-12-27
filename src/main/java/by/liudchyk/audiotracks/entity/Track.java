package by.liudchyk.audiotracks.entity;

/**
 * Created by Admin on 24.12.2016.
 */
public class Track extends Entity{
    private int id;
    private String title;
    private int genre;
    private Double price;
    private int length;

    public Track(int id, String title, int genre, Double price, int length) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getGenre() {
        return genre;
    }

    public Double getPrice() {
        return price;
    }

    public int getLength() {
        return length;
    }
}