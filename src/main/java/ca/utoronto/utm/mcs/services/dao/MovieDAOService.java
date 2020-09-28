package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.domain.Actor;

public interface MovieDAOService extends ConnectService {

	public ActorDAO addMovie(String name, String movieID) throws Exception;
	public ActorDAO getActor(String movieID) throws Exception;
}
