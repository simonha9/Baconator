package ca.utoronto.utm.mcs.services.dao;

import java.util.List;

public class ActorDAO {

	public String name;
	public String actorID;
	public List<String> movies;
	
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

	public List<String> getMovies() {
		return movies;
	}

	public void setMovies(List<String> movies) {
		this.movies = movies;
	}
	
}
