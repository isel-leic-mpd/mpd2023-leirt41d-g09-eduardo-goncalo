package isel.mpd.moviesdb2.model;

import java.time.LocalDate;
import java.util.stream.Stream;

public class Movie {
	private  final LocalDate release_date;

	private  final int id;
	private  final String name;
	private  final double popularity;
	private  final Stream<Actor> actors;
	private  final Stream<Genre> genres;
 	private	 final Stream<MovieDetail> recommendations;

	public Movie(LocalDate start_date,
				 String name,
				 int id,
				 double popularity,
				 Stream<Actor> actors,
				 Stream<Genre> genres,
				 Stream<MovieDetail> recommendations
	             ) {
		this.id = id;
		this.name = name;
		this.actors = actors;
		this.genres = genres;
	    this.popularity = popularity;
	    this.release_date = start_date;
		this.recommendations = recommendations;
	}

	public Stream<Actor> getActors() {
		return actors;
	}

	public Stream<Genre> getGenres() {
		return genres;
	}

	public Stream<MovieDetail> getRecommendations() {
		return recommendations;
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

	public LocalDate getReleaseDate() {
		return release_date;
	}


	protected String showContent() {
		return
			  "series name=" + getName()
			+ ", start_date=" + getReleaseDate()
			+ ", series_id=" + id
			+ ", popularity=" + popularity;
	}

	@Override
	public String toString() {
		return "{ "
			+ showContent()
			+ " }";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Movie.class) return false;
		Movie other = (Movie) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
