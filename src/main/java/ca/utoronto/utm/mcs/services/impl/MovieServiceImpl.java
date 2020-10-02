package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.MovieDAO;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.MovieService;

public class MovieServiceImpl implements MovieService {

	MovieDAO movieDAO = null;
	Driver driver;
	
	public MovieServiceImpl(Driver driver) {
		this.driver = driver;
		movieDAO = new MovieDAO(driver);
	}
	
	@Override
	public String addMovie(Movie movie) throws Exception {
		MovieDAO movieDAO = getMovieDAO();
		if (movie.getName() == null || movie.getId() == null) 
			throw new MissingInformationException("Required info is missing");
		Movie existingMovie = movieDAO.findMovieById(movie.getId());
		if (existingMovie != null) throw new NodeAlreadyExistsException("That node already exists");
		return movieDAO.insertMovie(movie);
	}

	@Override
	public Movie findMovieById(String movieId) throws Exception {
		MovieDAO movieDAO = getMovieDAO();
		if (movieId == null) throw new MissingInformationException("Required information is missing");
		Movie movie = movieDAO.findMovieById(movieId);
		if (movie == null) throw new NodeNotExistException("That node does not exist");
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(driver);
		movie.getActors().addAll(relationshipService.findActorsByMovieId(movieId));
		return movie;
	}
	
	private MovieDAO getMovieDAO() {
		return movieDAO;
	}

	
}
