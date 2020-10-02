package ca.utoronto.utm.mcs.services.impl;

import java.util.List;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorMovieRelationshipDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;

public class ActorMovieRelationshipServiceImpl implements ActorMovieRelationshipService {

	ActorMovieRelationshipDAO relationshipDAO = null;
	Driver driver;
	
	public ActorMovieRelationshipServiceImpl(Driver driver) {
		this.driver = driver;
		relationshipDAO = new ActorMovieRelationshipDAO(driver);
	}

	@Override
	public ActorMovieRelationship addRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		if (relationship.getActorID() == null || relationship.getMovieID() == null) 
			throw new MissingInformationException("Required info is missing");
		checkActorExists(relationship.getActorID());
		checkMovieExists(relationship.getMovieID());
		ActorMovieRelationship existingRel = relationshipDAO.getRelationship(relationship.getActorID(), relationship.getMovieID());
		if (existingRel != null) throw new NodeAlreadyExistsException("That relationship already exists");
		return relationshipDAO.saveRelationship(relationship);
	}

	@Override
	public ActorMovieRelationship getRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		if (relationship.getActorID() == null || relationship.getMovieID() == null) throw new MissingInformationException("Required information is missing");
		checkActorExists(relationship.getActorID());
		checkMovieExists(relationship.getMovieID());
		return relationshipDAO.getRelationship(relationship.getActorID(), relationship.getMovieID());
	}

	@Override
	public List<String> findActorsByMovieId(String movieId) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		return relationshipDAO.findActorsByMovieId(movieId);
	}

	@Override
	public List<String> findMoviesByActorId(String actorId) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		return relationshipDAO.findMoviesByActorId(actorId);
	}
	
	private ActorMovieRelationshipDAO getRelationshipDAO() {
		return relationshipDAO;
	}
	
	private void checkActorExists(String actorID) throws Exception {
		ActorService actorService = new ActorServiceImpl(driver);
		Actor actor = actorService.findActorById(actorID);
		if (actor == null) throw new NodeNotExistException("That node does not exist");
	}
	
	private void checkMovieExists(String movieID) throws Exception {
		MovieService movieService = new MovieServiceImpl(driver);
		Movie movie = movieService.findMovieById(movieID);
		if (movie == null) throw new NodeNotExistException("That node does not exist");
	}
	
}
