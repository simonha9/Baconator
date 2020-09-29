package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;

public class MovieRestHandler extends BaseHandler {
	
	MovieService movieService = null;
	
	public MovieRestHandler(Driver driver) {
		super(driver);
	}
	
	private Movie getMovie(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Movie movie = new Movie();
		if (deserialized.has("name")) 
			movie.setPrimaryTitle(deserialized.getString("name"));
		if (deserialized.has("movieId"))
			movie.setId(deserialized.getString("movieId"));
		
		return movie;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		MovieService movieService = getMovieService();
		Movie movie = getMovie(r);
		movieService.getMovie(movie.getId());
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {
		MovieService movieService = getMovieService();
		Movie movie = getMovie(r);
		if (movie.getPrimaryTitle() == null || movie.getId() == null) 
			throw new MissingInformationException("Required info is missing");
		movieService.addMovie(movie);
		r.sendResponseHeaders(200, -1);
	}
	
	private MovieService getMovieService() {
		if (movieService == null) movieService = new MovieServiceImpl(driver);
		return movieService;
	}
	
	

}
