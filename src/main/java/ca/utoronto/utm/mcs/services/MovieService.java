package ca.utoronto.utm.mcs.services;

import ca.utoronto.utm.mcs.domain.Movie;

public interface MovieService {

	public String addMovie(Movie movie) throws Exception;
	public Movie getMovie(String movieId) throws Exception;
}
