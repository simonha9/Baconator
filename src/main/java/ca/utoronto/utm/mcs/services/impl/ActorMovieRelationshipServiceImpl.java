package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorDAO;
import ca.utoronto.utm.mcs.dao.ActorMovieRelationshipDAO;
import ca.utoronto.utm.mcs.dao.MovieDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;

public class ActorMovieRelationshipServiceImpl implements ActorMovieRelationshipService {

	Driver driver;
	
	ActorMovieRelationshipDAO relationshipDAO = null;
	
	public ActorMovieRelationshipServiceImpl(Driver driver) {
		this.driver = driver;
	}

	@Override
	public ActorMovieRelationship addRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		checkActorExists(relationship);
		checkMovieExists(relationship);
		ActorMovieRelationship existingRel = relationshipDAO.hasRelationship(relationship.getActorID(), relationship.getMovieID());
		if (existingRel != null) throw new NodeAlreadyExistsException("That relationship already exists");
		return relationshipDAO.insertRelationship(relationship);
	}

	private ActorMovieRelationshipDAO getRelationshipDAO() {
		if (relationshipDAO == null) relationshipDAO = new ActorMovieRelationshipDAO(driver);
		return relationshipDAO;
	}

	@Override
	public Boolean hasRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		checkActorExists(relationship);
		checkMovieExists(relationship);
		relationship = relationshipDAO.hasRelationship(relationship.getActorID(), relationship.getMovieID());
		if (relationship == null) return false;
		return relationship.actorID != null && relationship.getMovieID() != null;
	}
	
	private void checkActorExists(ActorMovieRelationship relationship) throws NodeNotExistException {
		ActorDAO actorDAO = new ActorDAO(driver);
		Actor actor = actorDAO.getActorByID(relationship.getActorID());
		if (actor == null) throw new NodeNotExistException("That node does not exist");
	}
	
	private void checkMovieExists(ActorMovieRelationship relationship) throws NodeNotExistException {
		MovieDAO movieDAO = new MovieDAO(driver);
		Movie movie = movieDAO.getMovie(relationship.getMovieID());
		if (movie == null) throw new NodeNotExistException("That node does not exist");
	}

	
}
