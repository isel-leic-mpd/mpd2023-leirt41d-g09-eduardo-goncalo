package isel.mpd.moviesdb2.dto;

import java.util.List;

public class SearchMoviesDto {
	private int page;
	private List<MovieDto> results;
	private int total_pages;
	private int total_results;

	public List<MovieDto> getResults() {
		return results;
	}
}
