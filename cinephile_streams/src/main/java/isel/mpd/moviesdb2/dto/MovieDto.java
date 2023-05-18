package isel.mpd.moviesdb2.dto;

import java.time.LocalDate;

public class MovieDto {
    private int id;
    private String original_title;
    private String title;
    private double popularity;
    private String character;
    private String poster_path;
    private  int[] genre_ids;
    private String release_date;
    private double vote_average;
    private long vote_count;

    protected String showContent() {
        return "title=" + original_title
                + ", id = " + id
                + ", popularity=" + popularity
                + ", rating=" + (int) Math.round(vote_average * 10)
                + ", release_date=" + getReleaseDate()
                + ", vote_count=" + vote_count;
    }

    public String toString() {
        return "{ "
            + showContent()
            + " }";
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public int getId() {
        return id;
    }

    public double getPopularity() {
        return popularity;
    }

    public LocalDate getReleaseDate() {
        try {
            return LocalDate.parse(release_date);
        }
        catch(Exception e) {
            return LocalDate.now();
        }
    }

    public int[] getGenreIds() {
        return genre_ids;
    }
}
