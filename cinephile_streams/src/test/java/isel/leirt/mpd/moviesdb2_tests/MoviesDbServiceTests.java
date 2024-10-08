package isel.leirt.mpd.moviesdb2_tests;

import isel.mpd.moviesdb2.MoviesDbService;
import isel.mpd.moviesdb2.MoviesDbWebApi;
import isel.mpd.moviesdb2.dto.MovieDto;
import isel.mpd.moviesdb2.model.CrewMovie;
import isel.mpd.moviesdb2.model.Genre;
import isel.mpd.moviesdb2.model.Movie;
import isel.mpd.moviesdb2.model.MovieDetail;
import isel.mpd.requests.CounterRequest;
import isel.mpd.requests.HttpRequest;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static isel.mpd.streams.StreamUtils.sortedIntersection;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class MoviesDbServiceTests {

	@Test
	public void getGenresTest() {
		CounterRequest req = new CounterRequest( new HttpRequest() );
		MoviesDbService serv =
			new MoviesDbService(new MoviesDbWebApi(req));

		Stream<Genre> sgenres = serv.getGenres();
		assertEquals(0, req.getCount());

		List<Genre> genres = sgenres.collect(toList());
		genres.forEach(System.out::println);
		assertEquals(19, genres.size());
		assertEquals(1, req.getCount());
	}

	@Test
	public void getActionAndAdventureMoviesTest() {
		CounterRequest req = new CounterRequest( new HttpRequest() );
		int actionAndAdventureGenreId = 10759;
		int maxMovies = 40;

		MoviesDbService serv =
			new MoviesDbService(
				new MoviesDbWebApi(
					req
				)
			);


		Stream<Movie> movies =
			serv.searchByGenre(actionAndAdventureGenreId, maxMovies);

		assertEquals(0, req.getCount());
		System.out.println(movies.count());

		assertEquals(2, req.getCount());

		Stream<Movie> firstTen =
				serv.searchByGenre(actionAndAdventureGenreId, maxMovies)
				.limit(10);

		assertEquals(2, req.getCount());

		firstTen.forEach(movie -> {
			System.out.println(movie.getName() + " actors:");
			movie.getActors().forEach(System.out::println);
			System.out.println();
		});
		assertEquals(13, req.getCount());
	}

	@Test
	public void getActorsOfWestWorldMovieTest() {
		int westWorldMovieId = 2362; // WestWorld, 1973
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
			  new MoviesDbService(new MoviesDbWebApi(req));

		MovieDetail movie =
			serv.getMovieDetail(westWorldMovieId);
		assertEquals(1, req.getCount());

		var actorsList = movie.getActors().collect(toList());
		actorsList.forEach(System.out::println);
		assertEquals(2, req.getCount());
		assertEquals(60, actorsList.size());
		assertEquals(2, req.getCount());
	}

	@Test
	public void getActorsOfWestWorldMovieMultipleCallTest() {
		int westWorldMovieId = 2362;
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
			new MoviesDbService(new MoviesDbWebApi(req));

		MovieDetail movie =
			serv.getMovieDetail(westWorldMovieId);
		assertEquals(1, req.getCount());
		movie.getActors().forEach(System.out::println);
		assertEquals(2, req.getCount());
		movie.getActors().forEach(System.out::println);
		assertEquals(3, req.getCount());
	}

	@Test
	public void getRachelWoodMoviesTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
			new MoviesDbService(new MoviesDbWebApi(req));

		int rachelWoodId = 38940;
		Stream<Movie> moviesStream =
			serv.getActorMovies(rachelWoodId);
		assertEquals(0, req.getCount());

		var moviesList = moviesStream.collect(toList());

		moviesList.forEach(System.out::println);
		assertEquals(1, req.getCount());
		assertEquals(50, moviesList.size());
	}


	@Test
	public void getGodfatherMoviesTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
				new MoviesDbService(new MoviesDbWebApi(req));

		Stream<Movie> moviesStream =
				serv.searchByName("Godfather", 120);
		assertEquals(0, req.getCount());

		var moviesList = moviesStream.collect(toList());

		moviesList.forEach(System.out::println);
		assertEquals(6, req.getCount());
		assertEquals(106, moviesList.size());
	}

	@Test
	public void getCommonRecomendationsTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
				new MoviesDbService(new MoviesDbWebApi(req));

		int TERMINATOR_ID= 218;
		int TERMINATOR2_ID = 280;
		int MATRIX_ID = 603;

		Stream<Movie> moviesStream =
				serv.getCommonRecommendation(TERMINATOR_ID,TERMINATOR2_ID);

		assertEquals(0, req.getCount());

		var moviesList = moviesStream.collect(toList());

		assertEquals(2, req.getCount());
		assertEquals(11, moviesList.size());

		Stream<Movie> moviesStream2 =
				serv.getCommonRecommendation(MATRIX_ID,MATRIX_ID);

		var moviesList2 = moviesStream2.collect(toList());

		assertEquals(4, req.getCount());
		assertEquals(21, moviesList2.size());

		Stream<Movie> moviesStream3 =
				serv.getCommonRecommendation(TERMINATOR_ID,MATRIX_ID);

		var moviesList3 = moviesStream3.collect(toList());

		assertEquals(6, req.getCount());
		assertEquals(3, moviesList3.size());
	}

	@Test
	public void getPersonCreditsTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv =
				new MoviesDbService(new MoviesDbWebApi(req));

		Stream<CrewMovie> movieStream =
				serv.personCredits(10788);

		var movieList = movieStream.collect(toList());

		movieList.forEach(System.out::println);
		assertEquals(26, movieList.size());
	}

	@Test
	public void getAllChristopherNolanMoviesFrom2000To2010() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		MoviesDbService serv = new MoviesDbService(new MoviesDbWebApi(req));
		var startDate = LocalDate.of(2000, 12, 31);
		var endDate = LocalDate.of(2010, 1, 1);
		var chrisNolanId = 525;
		var moviesInfo =
				serv.moviesOfPersonOnPeriod(chrisNolanId, startDate, endDate)
						.filter( m -> m.getJob().equals("Director"));
		assertEquals(0, req.getCount());
		int[] nMovies = {0};
		moviesInfo.forEach( m -> { System.out.println(m); nMovies[0]++; });
		assertEquals(6, nMovies[0]);
		assertEquals(13, req.getCount());
	}
}
