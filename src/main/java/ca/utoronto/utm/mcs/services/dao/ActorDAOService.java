package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.domain.Actor;

public interface ActorDAOService extends ConnectService {

	public ActorDAO addActor(String name, String actorID) throws Exception;
	public ActorDAO getActor(String actorID) throws Exception;
}
