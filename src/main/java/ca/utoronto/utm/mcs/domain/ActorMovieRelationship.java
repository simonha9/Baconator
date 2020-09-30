package ca.utoronto.utm.mcs.domain;

import java.util.List;

public class ActorMovieRelationship extends BaseDataEntity {

	private static final long serialVersionUID = 58059863990569983L;
	
	public String movieID;
	public String actorID;
	public Boolean hasRelationship;
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
