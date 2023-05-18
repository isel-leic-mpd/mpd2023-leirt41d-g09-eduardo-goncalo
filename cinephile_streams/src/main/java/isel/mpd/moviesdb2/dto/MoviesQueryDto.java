package isel.mpd.moviesdb2.dto;

import java.util.List;

public class MoviesQueryDto {
    private List<MovieDto> results;
    private int total_pages;
    private int total_results;

    public List<MovieDto> getResults() {
        return results;
    }

    public int getTotalResults() {
        return total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }
}
