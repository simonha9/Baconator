package ca.utoronto.utm.mcs.domain;

public class ActorMovieRelationship {

	private String movieID;
	private String actorID;
	private Boolean hasRelationship;
	public String getMovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	public String getActorID() {
		return actorID;
	}
	public void setActorID(String actorID) {
		this.actorID = actorID;
	}
	public Boolean getHasRelationship() {
		return hasRelationship;
	}
	public void setHasRelationship(Boolean hasRelationship) {
		this.hasRelationship = hasRelationship;
	}
	
	
	
	
	
	

}
