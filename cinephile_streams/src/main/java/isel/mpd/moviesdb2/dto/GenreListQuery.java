package isel.mpd.moviesdb2.dto;

import java.util.List;

public class GenreListQuery {
	public  final List<GenreDto> genres;

	public GenreListQuery(List<GenreDto> genres) {
		this.genres = genres;
	}

	public List<GenreDto> getGenres() {
		return genres;
	}
}
