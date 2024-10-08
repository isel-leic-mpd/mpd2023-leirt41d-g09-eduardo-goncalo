package isel.leirt.mpd.moviesdb2_tests;

import isel.mpd.moviesdb2.MoviesDbWebApi;
import isel.mpd.moviesdb2.dto.*;
import isel.mpd.requests.HttpRequest;
import isel.mpd.requests.MockRequest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MoviesDbWebApiTests  {
	@Test
	public void getGenresTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());
		final int EXPECTED_GENRES = 19;

		List<GenreDto> genres = api.getGenres();
		genres.forEach(System.out::println);
		assertEquals(EXPECTED_GENRES, genres.size());
	}

	@Test
	public void getAnimationMoviesTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());
		int animationGenreId = 16;
		List<MovieDto> series =
			api.searchByGenre(1, animationGenreId);
		series.forEach(System.out::println);
		assertEquals(20, series.size());
	}

	@Test
	public void getActorsOfAvatar2Test() {
		int  avatar2Id = 76600;
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<ActorDto> actors =
			api.movieActors(avatar2Id);
		System.out.println(actors.size());
		actors.forEach(System.out::println);
		assertEquals(57, actors.size());
	}

	@Test
	public void getRachelWoodMoviesTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		int rachelWoodId = 38940;
		List<MovieDto> movies =
			api.actorMovies(rachelWoodId);
		for(var m : movies)
			System.out.println(m);
		assertEquals(50, movies.size());
	}

	@Test
	public void firstPageOfNowPlayingMoviesTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<MovieDto> movies = api.nowPlayingMovies(1, "PT");
		for(var m : movies)
			System.out.println(m);
		assertEquals(MoviesDbWebApi.API_PAGE_SIZE, movies.size());
	}

	@Test
	public void getDetailOfAvatar2Test() {
		int AVATAR2_ID = 76600;
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		var movieDetail = api.getMovieDetail(AVATAR2_ID);

		System.out.println(movieDetail);
	}

	@Test
	public void recommendationsOfMatrix() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());
		int MATRIX_ID= 603;
		int RECOMMENDATIONS_SIZE=21;

		List<MovieDto> movies = api.getMovieRecommendations(MATRIX_ID);

		for(var m : movies)
			System.out.println(m);
		assertEquals(RECOMMENDATIONS_SIZE, movies.size());
	}

	@Test
	public void getFirstPageOfMoviesNamedAvatar() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<MovieDto> movies = api.searchByName(1,"Avatar");

		for(var m : movies)
			System.out.println(m);
		assertEquals(MoviesDbWebApi.API_PAGE_SIZE, movies.size());
	}

	@Test
	public void getPersonByNameTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		Optional<CrewDto> person = api.personByName("Steven%20Lawrence");

		System.out.println(person);

		assertEquals(person.get().getId(), 10788);
	}

	@Test
	public void getGenresNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());
		final int EXPECTED_GENRES = 19;

		List<GenreDto> genres = api.getGenres();
		genres.forEach(System.out::println);
		assertEquals(EXPECTED_GENRES, genres.size());
	}

	@Test
	public void getAnimationMoviesNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());
		int animationGenreId = 16;
		List<MovieDto> series =
				api.searchByGenre(1, animationGenreId);
		series.forEach(System.out::println);
		assertEquals(20, series.size());
	}

	@Test
	public void getActorsOfAvatar2NoConnectionTest() {
		int  avatar2Id = 76600;
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());

		List<ActorDto> actors =
				api.movieActors(avatar2Id);
		System.out.println(actors.size());
		actors.forEach(System.out::println);
		assertEquals(57, actors.size());
	}

	@Test
	public void getRachelWoodMoviesNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());

		int rachelWoodId = 38940;
		List<MovieDto> movies =
				api.actorMovies(rachelWoodId);
		for(var m : movies)
			System.out.println(m);
		assertEquals(50, movies.size());
	}


	@Test
	public void firstPageOfNowPlayingMoviesNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());

		List<MovieDto> movies = api.nowPlayingMovies(1, "PT");
		for(var m : movies)
			System.out.println(m);
		assertEquals(MoviesDbWebApi.API_PAGE_SIZE, movies.size());
	}

	@Test
	public void getDetailOfAvatar2NoConnectionTest() {
		int AVATAR2_ID = 76600;
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());

		var movieDetail = api.getMovieDetail(AVATAR2_ID);

		System.out.println(movieDetail);
	}

	@Test
	public void recommendationsOfMatrixNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());
		int MATRIX_ID= 603;
		int RECOMMENDATIONS_SIZE=21;

		List<MovieDto> movies = api.getMovieRecommendations(MATRIX_ID);

		for(var m : movies)
			System.out.println(m);
		assertEquals(RECOMMENDATIONS_SIZE, movies.size());
	}

	@Test
	public void getFirstPageOfMoviesNamedAvatarNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new MockRequest());

		List<MovieDto> movies = api.searchByName(1,"Avatar");

		for(var m : movies)
			System.out.println(m);
		assertEquals(MoviesDbWebApi.API_PAGE_SIZE, movies.size());
	}

	@Test
	public void getChrisNolanMoviesNoConnectionTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		int chrisNolanId = 525;
		List<CrewMovieDto> movies =
				api.personCredits(chrisNolanId);
		for(var m : movies)
			System.out.println(m);
		//assertEquals(50, movies.size());
	}
}
