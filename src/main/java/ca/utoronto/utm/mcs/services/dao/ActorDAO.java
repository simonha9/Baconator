package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.services.db.Neo4jActorMovies;

public class ActorDAO {

	public String name;
	public String actorID;
	
	public ActorDAO(String name, String actorID) {
		super();
		this.name = name;
		this.actorID = actorID;
	}
	
	public ActorDAO() {
		super();
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActorID() {
		return actorID;
	}

	public void setActorID(String actorID) {
		this.actorID = actorID;
	}
}
