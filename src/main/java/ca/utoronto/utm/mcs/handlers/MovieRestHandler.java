package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;

public class MovieRestHandler extends BaseHandler {
	
	MovieService movieService = null;
	
	public MovieRestHandler(Driver driver) {
		super(driver);
		movieService = new MovieServiceImpl(driver);
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
		Movie movie = getMovie(r);
		movie = movieService.findMovieById(movie.getId());
		String response = buildResponse(movie);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	@Override
	public void handlePut(HttpExchange r) throws Exception {
		Movie movie = getMovie(r);
		movieService.addMovie(movie);
		r.sendResponseHeaders(200, -1);
	}
	
	private String buildResponse(Movie movie) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("actors", movie.getActors());
		obj.accumulate("name", movie.getName());
		obj.accumulate("movieId", movie.getId());
		return obj.toString();
	}
	

}
