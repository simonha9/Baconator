package ca.utoronto.utm.mcs.services.impl;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.Utils;
import ca.utoronto.utm.mcs.services.dao.ActorDAO;
import ca.utoronto.utm.mcs.services.dao.MovieDAOService;
import ca.utoronto.utm.mcs.services.dao.impl.MovieDAOServiceImpl;

public class MovieServiceImpl implements MovieService {

	MovieDAOService movieDAOService = new MovieDAOServiceImpl();

	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				getMovie(r);
			} else if (r.getRequestMethod().equals("POST")) {
				addMovie(r);
			}
		} catch (MissingInformationException | JSONException e) {
			e.printStackTrace();
			r.sendResponseHeaders(400, -1);
		} catch (Exception e) {
			e.printStackTrace();
			r.sendResponseHeaders(500, -1);
		}
	}

	@Override
	public void addMovie(HttpExchange r) throws Exception {
		Movie movie = mapBodyToMovie(r);
		//Check if exists
		if (movie.getPrimaryTitle() != null && movie.getId() != null) {
			movieDAOService.addMovie(movie.getPrimaryTitle(), movie.getId());
		} else {
			throw new MissingInformationException("Information required is missing");
		}
		r.sendResponseHeaders(200, -1);
	}

	@Override
	public void getMovie(HttpExchange r) throws Exception {
		return;

	}
	
	private Movie mapBodyToMovie(HttpExchange r) throws IOException, JSONException {
		String body = Utils.convert(r.getRequestBody());
		JSONObject deserialized = new JSONObject(body);
		Movie movie = new Movie();

		if (deserialized.has("name")) 
			movie.setPrimaryTitle(deserialized.getString("name"));
		if (deserialized.has("movieId"))
			movie.setId(deserialized.getString("movieId"));
		
		return movie;
	}

}
