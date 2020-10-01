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
		return relationshipDAO.insertRelationship(relationship);
	}

	private ActorMovieRelationshipDAO getRelationshipDAO() {
		if (relationshipDAO == null) relationshipDAO = new ActorMovieRelationshipDAO(driver);
		return relationshipDAO;
	}

	@Override
	public ActorMovieRelationship getRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO relationshipDAO = getRelationshipDAO();
		return relationshipDAO.getRelationship(relationship.getActorID(), relationship.getMovieID());
	}
	
	

	
}
