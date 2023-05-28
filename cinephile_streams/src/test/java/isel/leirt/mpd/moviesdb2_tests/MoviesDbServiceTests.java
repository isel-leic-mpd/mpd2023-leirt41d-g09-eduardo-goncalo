package isel.leirt.mpd.moviesdb2_tests;

import isel.mpd.moviesdb2.MoviesDbService;
import isel.mpd.moviesdb2.MoviesDbWebApi;
import isel.mpd.moviesdb2.model.Genre;
import isel.mpd.moviesdb2.model.Movie;
import isel.mpd.moviesdb2.model.MovieDetail;
import isel.mpd.requests.CounterRequest;
import isel.mpd.requests.HttpRequest;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

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
		assertEquals(61, actorsList.size());
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
		assertEquals(105, moviesList.size());
	}
}
