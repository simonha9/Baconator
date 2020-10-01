package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
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
			movie.setName(deserialized.getString("name"));
		if (deserialized.has("movieId"))
			movie.setId(deserialized.getString("movieId"));
		
		return movie;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		MovieService movieService = getMovieService();
		Movie movie = getMovie(r);
		if (movie.getId() == null) throw new MissingInformationException("Required information is missing");
		movie = movieService.getMovieByID(movie.getId());
		if (movie == null) throw new NodeNotExistException("That node does not exist");
		String response = buildResponse(movie);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	@Override
	public void handlePost(HttpExchange r) throws Exception {
		MovieService movieService = getMovieService();
		Movie movie = getMovie(r);
		if (movie.getName() == null || movie.getId() == null) 
			throw new MissingInformationException("Required info is missing");
		Movie existingMovie = movieService.getMovieByID(movie.getId());
		if (existingMovie != null) throw new NodeAlreadyExistsException("That node already exists");
		movieService.addMovie(movie);
		r.sendResponseHeaders(200, -1);
	}
	
	private MovieService getMovieService() {
		if (movieService == null) movieService = new MovieServiceImpl(driver);
		return movieService;
	}
	
	private String buildResponse(Movie movie) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("actors", movie.getActors());
		obj.accumulate("name", movie.getName());
		obj.accumulate("movieId", movie.getId());
		return obj.toString();
	}
	

}
