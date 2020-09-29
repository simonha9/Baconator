package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorMovieRelationshipDAO;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;

public class ActorMovieRelationshipServiceImpl implements ActorMovieRelationshipService {

	Driver driver;
	
	ActorMovieRelationshipDAO relationshipDAO = null;
	
	public ActorMovieRelationshipServiceImpl(Driver driver) {
		this.driver = driver;
	}

	@Override
	public ActorMovieRelationship addRelationship(ActorMovieRelationship relationship) throws Exception {
		ActorMovieRelationshipDAO actorDAO = getRelationshipDAO();
		
		//Check if exists
		//Actor existingActor = actorDAO.getActor(actor.getId());
		
//		if (existingActor != null) throw new NodeAlreadyExistsException("Node already exists");
		return relationshipDAO.insertRelationship(relationship);
	}

	private ActorMovieRelationshipDAO getRelationshipDAO() {
		if (relationshipDAO == null) relationshipDAO = new ActorMovieRelationshipDAO(driver);
		return relationshipDAO;
	}
	

	
}
