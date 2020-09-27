package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.services.db.Neo4jActorMovies;

public class ActorDAOServiceImpl implements ActorDAOService {

	/**
	 * Save an actor in the database, 
	 * if an actor with the same actorID already exists, return 400
	 * If the actor does not exist, add into database and return 200
	 * if exception is thrown, return 500
	 */
	@Override
	public void addActor(ActorDAO actor) throws Exception {
		System.out.println("Creating db instance");
		Neo4jActorMovies db = new Neo4jActorMovies();
		System.out.println("Created db instance");
		System.out.println("Inserting actor from DAO");
		db.insertActor(actor);
		System.out.println("Inserted actor from DAO");
	}
}
