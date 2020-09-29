package ca.utoronto.utm.mcs.services.dao.impl;

import ca.utoronto.utm.mcs.services.dao.MovieDAO;
import ca.utoronto.utm.mcs.services.dao.MovieDAOService;
import ca.utoronto.utm.mcs.services.db.Neo4jActorMovies;

public class MovieDAOServiceImpl implements MovieDAOService {

	
	/**
	 * Save an actor in the database, 
	 * if an actor with the same actorID already exists, return 400
	 * If the actor does not exist, add into database and return 200
	 * if exception is thrown, return 500
	 */
	@Override
	public MovieDAO addMovie(String name, String movieID) throws Exception {
		MovieDAO movieDAO = new MovieDAO();
		movieDAO.setMovieName(name);
		movieDAO.setMovieID(movieID);
		Neo4jActorMovies db = new Neo4jActorMovies();
		db.insertMovie(movieDAO);
		return movieDAO;
	}

	@Override
	public MovieDAO getActor(String movieID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
