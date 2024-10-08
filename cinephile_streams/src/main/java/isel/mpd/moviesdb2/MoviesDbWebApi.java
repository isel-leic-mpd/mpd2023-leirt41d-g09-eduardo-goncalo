package isel.mpd.moviesdb2;

import com.google.gson.Gson;
import isel.mpd.moviesdb2.dto.*;
import isel.mpd.moviesdb2.model.CrewMovie;
import isel.mpd.moviesdb2.model.Movie;
import isel.mpd.moviesdb2.model.MovieInfo;
import isel.mpd.requests.MockRequest;
import isel.mpd.requests.Request;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static isel.mpd.requests.MockRequest.saveOn;

public class MoviesDbWebApi {
	public final static int API_PAGE_SIZE=20;

	private static final String API_KEY = getApiKeyFromResources();
	private static final String MOVIES_DB_ENDPOINT =  "https://api.themoviedb.org/3/";

	private static final String MOVIES_GENRES =
		MOVIES_DB_ENDPOINT + "genre/movie/list?api_key=" + API_KEY;

	private static final String NOW_PLAYING_MOVIES =
		MOVIES_DB_ENDPOINT
			+ "movie/now_playing?page=%d&region=%s&language=pt&api_key="
			+ API_KEY;

	private static final String MOVIE_DETAIL =
		MOVIES_DB_ENDPOINT + "movie/%d?api_key=" + API_KEY;

	private static final String MOVIE_RECOMMENDATIONS =
		MOVIES_DB_ENDPOINT + "movie/%d/recommendations?api_key=" + API_KEY;

	private static final String MOVIES_SEARCH_BY_GENRES =
		MOVIES_DB_ENDPOINT
			+ "discover/movie?sort_by=vote_average.desc&vote_count.gte=500&with_genres=%s&page=%d&api_key="
			+ API_KEY;

	private static final String MOVIES_SEARCH_BY_NAME =
		MOVIES_DB_ENDPOINT + "search/movie?query=%s&page=%d&api_key=" + API_KEY;

	private static final String MOVIES_CREDITS =
		MOVIES_DB_ENDPOINT + "movie/%d/credits?api_key="+ API_KEY;

	private static final String ACTOR_MOVIES =
		MOVIES_DB_ENDPOINT + "person/%d/movie_credits?api_key=" + API_KEY;

	private static final String CREW_INFO =
			MOVIES_DB_ENDPOINT + "search/person?query=%s&api_key=" + API_KEY;

	protected final Gson gson;
	private final Request req;

	/**
	 * Retrieve API-KEY from resources
	 * @return
	 */
	private static String getApiKeyFromResources() {
		try {
			URL keyFile =
				ClassLoader.getSystemResource("movies_db_api_key.txt");
			try (BufferedReader reader =
					 new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
				return reader.readLine();
			}

		} catch(IOException e) {
			throw new IllegalStateException(
				"YOU MUST GET a KEY from  themoviedb.org and place it in src/main/resources/movies_db_api_key.txt");
		}
	}

	public List<GenreDto> getGenres() {
		String path = MOVIES_GENRES;
		Reader reader = req.getReader(path);
		GenreListQuery genresQuery =
			gson.fromJson(reader, GenreListQuery.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return genresQuery.getGenres();
	}

	public List<MovieDto> nowPlayingMovies(int page, String region) {
		String path = String.format(NOW_PLAYING_MOVIES, page, region);
		Reader reader = req.getReader(path);
		MoviesQueryDto movies =
			gson.fromJson(reader, MoviesQueryDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return movies.getResults();
	}

	public MovieDetailDto getMovieDetail(int movieId) {
		String path = String.format(MOVIE_DETAIL, movieId);
		Reader reader = req.getReader(path);
		MovieDetailDto movieDetail =
			gson.fromJson(reader, MovieDetailDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return movieDetail;
	}

	public List<MovieDto> getMovieRecommendations(int movieId) {
		String path = String.format(MOVIE_RECOMMENDATIONS, movieId);
		Reader reader = req.getReader(path);
		MoviesQueryDto recommendations =
			gson.fromJson(reader, MoviesQueryDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return recommendations.getResults();
	}

	public List<MovieDto> searchByGenre(int page, int ... genres) {
		String path = String.format(MOVIES_SEARCH_BY_GENRES, Arrays.toString(genres), page);
		Reader reader = req.getReader(path);
		SearchMoviesDto series = gson.fromJson(reader, SearchMoviesDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return series.getResults();
	}

	public List<MovieDto> searchByName(int page, String nameMatch) {
		String path =  String.format(MOVIES_SEARCH_BY_NAME, nameMatch, page);
		Reader reader = req.getReader(path);
		SearchMoviesDto series =
			gson.fromJson(reader, SearchMoviesDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return series.getResults();
	}

	public List<ActorDto> movieActors(int tvSeriesId) {
		String path =  String.format(MOVIES_CREDITS, tvSeriesId);
		Reader reader = req.getReader(path);

		GetActorsDto credits =
			gson.fromJson(reader, GetActorsDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return credits.getCast();
	}

	public List<MovieDto> actorMovies(int actorId) {
		String path = String.format(ACTOR_MOVIES,actorId);
		Reader reader = req.getReader(path);

		GetActorMoviesDto credits =
				gson.fromJson(reader, GetActorMoviesDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return credits.getCast();
	}

	public List<CrewMovieDto> personCredits(int personId) {//Mockrequest
		String path = String.format(ACTOR_MOVIES,personId);
		Reader reader = req.getReader(path);

		GetCrewMoviesDto credits =
				gson.fromJson(reader, GetCrewMoviesDto.class);
		if(!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return credits.getcrew();
	}

	public Optional<CrewDto> personByName(String match) {
		String path = String.format(CREW_INFO, match);
		Reader reader = req.getReader(path);
		CrewDto person =
				gson.fromJson(reader, CrewDto.class);
		if (!(req instanceof MockRequest))
			saveOn(path, req.getReader(path));
		return Optional.of(person);
	}

	public MoviesDbWebApi(Request req) {
		this.req = req;
		gson = new Gson();
	}
}
