package ca.utoronto.utm.mcs.services;

import ca.utoronto.utm.mcs.domain.Actor;

public interface ActorService {

	public String addActor(Actor actor) throws Exception;
	public Actor getActor(String actorId) throws Exception;
	public void computeBaconNumber() throws Exception;
	public void computeBaconPath() throws Exception;
}
