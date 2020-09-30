package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.MovieDAO;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.services.MovieService;

public class MovieServiceImpl implements MovieService {

	Driver driver;
	
	private MovieDAO movieDAO = null;
	
	public MovieServiceImpl(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public String addMovie(Movie movie) throws Exception {
		MovieDAO movieDAO = getMovieDAO();
		Movie existingMovie = movieDAO.getMovie(movie.getId());
		if (existingMovie != null) throw new NodeAlreadyExistsException("Node already exists");
		return movieDAO.insertMovie(movie);
	}

	@Override
	public Movie getMovie(String movieId) throws Exception {
		MovieDAO movieDAO = getMovieDAO();
		Movie movie = movieDAO.getMovie(movieId);
		if (movie == null) return null;
		movie.getActors().addAll(movieDAO.getActorsByMovieId(movieId));
		return movie;
	}
	
	private MovieDAO getMovieDAO() {
		if (movieDAO == null) movieDAO = new MovieDAO(driver);
		return movieDAO;
	}

	
}
