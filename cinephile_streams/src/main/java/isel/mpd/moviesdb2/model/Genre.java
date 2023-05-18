package isel.mpd.moviesdb2.model;

import java.util.stream.Stream;

public class Genre {
	private int id;
	private String name;

	Stream<Movie> movies;

	public Genre(int id, String name, Stream<Movie>  series) {
		this.id = id;
		this.name =  name;
		this.movies = series;
	}

	public Stream<Movie> getMovies() {
		return movies;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{ "
			+ "name = " + name
			+ ", id = " + id
			+ " }";
	}
}
