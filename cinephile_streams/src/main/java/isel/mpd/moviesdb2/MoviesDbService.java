package isel.mpd.moviesdb2;

import isel.mpd.moviesdb2.dto.ActorDto;
import isel.mpd.moviesdb2.dto.GenreDto;
import isel.mpd.moviesdb2.dto.MovieDetailDto;
import isel.mpd.moviesdb2.dto.MovieDto;
import isel.mpd.moviesdb2.model.Actor;
import isel.mpd.moviesdb2.model.Genre;
import isel.mpd.moviesdb2.model.Movie;
import isel.mpd.moviesdb2.model.MovieDetail;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MoviesDbService {

	private MoviesDbWebApi api;
	private final static int API_PAGE_SIZE=20;


	public Stream<Movie> searchByGenre(int genreId, int maxMovies) {
		// TO IMPLEMENT
		return null;
	}

	public Stream<Movie> searchByName(String match, int maxMovies) {
		// TO IMPLEMENT
		return null;
	}

	public Stream<Genre> getGenres() {
		// TO IMPLEMENT
		return null;

	}

	private Stream<Genre> getGenres(int[] ids) {
		// TO IMPLEMENT
		return null;
	}

	public Stream<MovieDetail> getMovieRecommendations(int movieId) {
		// TO IMPLEMENT
		return null;

	}

	public Stream<Actor> getMovieActors(int movieId) {
		// TO IMPLEMENT
		return null;
	}

	public Stream<Movie> getActorMovies(int actorId) {
		// TO IMPLEMENT
		return null;

	}

	public MovieDetail getMovieDetail(int movieId) {
		return dtoToMovieDetail(api.getMovieDetail(movieId));
	}

	private Movie dtoToMovie(MovieDto dto) {
		return new Movie(
			dto.getReleaseDate(),
			dto.getTitle(),
			dto.getId(),
			dto.getPopularity(),
			getMovieActors(dto.getId()),
			getGenres(dto.getGenreIds()),
			getMovieRecommendations(dto.getId()));
	}

	private MovieDetail dtoToMovieDetail(MovieDetailDto dto) {
		return new MovieDetail(
			dto.getReleaseDate(),
			dto.getTitle(),
			dto.getId(),
			dto.getPopularity(),
			getMovieActors(dto.getId()),
			getGenres(dto.getGenreIds()),
			getMovieRecommendations(dto.getId()),
			dto.getRuntime(),
			dto.getBudget(),
			dto.getRevenue(),
			dto.getCompanies());
	}

	private Genre dtoToGenre(GenreDto dto) {
	    return new Genre(dto.getId(),
		                 dto.getName(),
						 // tente alterar este código para
						 // poder especificar o máximo de filmes na
						 // chamada ao método getMovies de Genre
		                 searchByGenre(dto.getId(), 20));
	}

	private Actor dtoToActor(ActorDto dto) {
		return new Actor(dto.getId(),
						dto.getName(),
						dto.getPopularity(),
						getActorMovies(dto.getId()));
	}

	public MoviesDbService(MoviesDbWebApi api) {
		this.api = api;
	}
}
