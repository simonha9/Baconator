package ca.utoronto.utm.mcs.domain;

import java.util.List;

public class ActorMovieRelationship extends BaseDataEntity {

	private static final long serialVersionUID = 58059863990569983L;
	
	public String movieID;
	public int ordering;
	public String actorID;
	public String category;
	private String job;
	private List<String> characters;
	public String getMovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getActorID() {
		return actorID;
	}
	public void setActorID(String actorID) {
		this.actorID = actorID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public List<String> getCharacters() {
		return characters;
	}
	public void setCharacters(List<String> characters) {
		this.characters = characters;
	}
	
	
	
	

}
