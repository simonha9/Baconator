package ca.utoronto.utm.mcs.services.dao;

import ca.utoronto.utm.mcs.domain.Actor;

public interface MovieDAOService extends ConnectService {

	public MovieDAO addMovie(String name, String movieID) throws Exception;
	public MovieDAO getActor(String movieID) throws Exception;
}
