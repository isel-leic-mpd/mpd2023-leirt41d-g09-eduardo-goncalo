package isel.mpd.moviesdb2;

import isel.mpd.moviesdb2.dto.ActorDto;
import isel.mpd.moviesdb2.dto.GenreDto;
import isel.mpd.moviesdb2.dto.MovieDetailDto;
import isel.mpd.moviesdb2.dto.MovieDto;
import isel.mpd.moviesdb2.model.Actor;
import isel.mpd.moviesdb2.model.Genre;
import isel.mpd.moviesdb2.model.Movie;
import isel.mpd.moviesdb2.model.MovieDetail;
import isel.mpd.streams.StreamUtils;
import isel.mpd.streams.spliterators.SortedIntersection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static isel.mpd.queries.StreamIterable.range;

public class MoviesDbService {

	private MoviesDbWebApi api;
	private final static int API_PAGE_SIZE=20;


	public Stream<Movie> searchByGenre(int genreId, int maxMovies) {
		return IntStream.rangeClosed(1, maxMovies/API_PAGE_SIZE)
				.mapToObj(p -> api.searchByGenre(p, genreId))
				.takeWhile(l -> l.size() > 0)
				.flatMap(List::stream)
				.map(this::dtoToMovie);
	}

	public Stream<Movie> searchByName(String match, int maxMovies) {
		return IntStream.rangeClosed(1, maxMovies/API_PAGE_SIZE)
				.mapToObj(p -> api.searchByName(p, match))
				.takeWhile(l -> l.size() > 0)
				.flatMap(List::stream)
				.map(this::dtoToMovie);
	}

	public Stream<Genre> getGenres() {
		return Stream.of(1)
				.flatMap(__ -> api.getGenres().stream())
				.map(this::dtoToGenre);
	}

	private Stream<Genre> getGenres(int[] ids) {
//		return getGenres()
//				.filter(g -> Arrays.stream(ids).anyMatch(i -> i == g.getId()));
		return Stream.of();
	}


	public Stream<MovieDetail> getMovieRecommendations(int movieId) {
		return Stream.of(1)
				.flatMap(__ -> api.getMovieRecommendations(movieId).stream())
				.map(this::dtoToMovie)
				.flatMap(Movie::getRecommendations);
	}

	private Stream<Movie> getCommonRecommendation (int movieId1, int movieId2){
		return StreamUtils.sortedIntersection((n,m) -> n.getId() - m.getId(),
						Stream.of(1).
								flatMap(__ -> api.getMovieRecommendations(movieId1).stream()),
						Stream.of(1).
								flatMap(__ -> api.getMovieRecommendations(movieId2).stream()))
				.map(this::dtoToMovie);
	}

	public Stream<Actor> getMovieActors(int movieId) {
		return Stream.of(1)
				.flatMap(__ -> api.movieActors(movieId).stream())
				.map(this::dtoToActor);
	}

	public Stream<Movie> getActorMovies(int actorId) {
		return Stream.of(1)
				.flatMap(__ -> api.actorMovies(actorId).stream())
				.map(this::dtoToMovie);
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
