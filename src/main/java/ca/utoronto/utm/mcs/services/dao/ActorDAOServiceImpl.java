package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.services.db.Neo4jActorMovies;

public class ActorDAOServiceImpl implements ActorDAOService {

	/**
	 * Save an actor in the database, 
	 * if an actor with the same actorID already exists, return 400
	 * If the actor does not exist, add into database and return 200
	 * if exception is thrown, return 500
	 */
	@Override
	public ActorDAO addActor(String name, String actorID) throws Exception {
		ActorDAO actorDAO = new ActorDAO();
		actorDAO.setName(name);
		actorDAO.setActorID(actorID);
		Neo4jActorMovies db = new Neo4jActorMovies();
		db.insertActor(actorDAO);
		return actorDAO;
	}

	/**
	 * Gets an actor from the database using actorID
	 */
	@Override
	public ActorDAO getActor(String actorID) throws Exception {
		Neo4jActorMovies db = new Neo4jActorMovies();
		return db.getActor(actorID);
	}
	
	
}
