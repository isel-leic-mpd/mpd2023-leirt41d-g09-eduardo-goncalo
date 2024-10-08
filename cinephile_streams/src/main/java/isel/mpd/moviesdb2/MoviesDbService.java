package isel.mpd.moviesdb2;

import isel.mpd.moviesdb2.dto.*;
import isel.mpd.moviesdb2.model.*;
import isel.mpd.streams.StreamUtils;
import isel.mpd.streams.spliterators.SortedIntersection;

import java.time.LocalDate;
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
				.map(m -> getMovieDetail(m.getId()));
	}

	public Stream<Movie> getCommonRecommendation (int movieId1, int movieId2){
		Comparator<MovieDto> cmp = (n,m) -> n.getId() - m.getId();
		return StreamUtils.sortedIntersection(cmp,
						Stream.of(1).
								flatMap(__ -> api.getMovieRecommendations(movieId1).stream().sorted(cmp)),
						Stream.of(1).
								flatMap(__ -> api.getMovieRecommendations(movieId2).stream().sorted(cmp)))
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

	public Stream<CrewMovie> personCredits(int personId) {
		return Stream.of(1)
				.flatMap(__ -> api.personCredits(personId).stream())
				.map(this::dtoToCrewMovie);
	}

	public Stream<MovieInfo> moviesOfPersonOnPeriod(int personId,
													LocalDate startDate,
													LocalDate endDate) {
//	return personCredits(personId)
//			.takeWhile(m -> (
//							(m.getReleaseDate().isEqual(startDate) ||
//									m.getReleaseDate().isAfter(startDate)) &&
//									m.getReleaseDate().isBefore(endDate)
//					)
//			)
//			.map(this::getMovieInfo);
		return personCredits(personId)
				.filter(m -> (
						(m.getReleaseDate().isEqual(startDate) ||
						 m.getReleaseDate().isAfter(startDate)) &&
						 m.getReleaseDate().isBefore(endDate)
						)
				)
				.map(this::getMovieInfo);
	}

	public Stream<Actor> workedWithDirector(int directorId) {
		return personCredits(directorId)
				.flatMap(Movie::getActors);
	}

	public MovieDetail getMovieDetail(int movieId) {
		return dtoToMovieDetail(api.getMovieDetail(movieId));
	}

	public MovieInfo getMovieInfo(CrewMovie crewMovie) {
		return new MovieInfo(crewMovie);
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

	private CrewMovie dtoToCrewMovie(CrewMovieDto dto) {
		return new CrewMovie(
				dto.getReleaseDate(),
				dto.getTitle(),
				dto.getId(),
				dto.getPopularity(),
				getMovieActors(dto.getId()),
				getGenres(dto.getGenreIds()),
				getMovieRecommendations(dto.getId()),
				dto.getJob(),
				dto.getDepartment());
	}

	public MoviesDbService(MoviesDbWebApi api) {
		this.api = api;
	}
}
