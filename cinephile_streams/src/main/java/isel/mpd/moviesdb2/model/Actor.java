package isel.mpd.moviesdb2.model;

import java.util.stream.Stream;

public class Actor {
	private int id;
	private String name;
	private double popularity;

	private Stream<Movie> movies;

	public Actor(int id, String name, double popularity, Stream<Movie> series) {
		this.id = id;
		this.name = name;
		this.popularity = popularity;
		this.movies = series;
	}

	Stream<Movie> getMovies() {
		return movies;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public double getPopularity() {
		return popularity;
	}

	@Override
	public String toString() {
		return "{ "
			+ "name=" + name
			+ ", id = " + id
			+ ", popularity=" + popularity
			+ " }";
	}
}
