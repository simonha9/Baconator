package ca.utoronto.utm.mcs;

import org.junit.Test;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.ActorMovieRelationshipServiceImpl;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MovieServiceTest extends TestCase {

	Neo4jConnector connector = Neo4jConnector.getInstance();
	
	@Test
	public void testCreateRead() throws Exception {
		Movie movie = new Movie();
		movie.setName("movieName");
		movie.setId("321");
		
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		movieService.addMovie(movie);
		
		Movie responseMovie = movieService.getMovieByID(movie.getId());
		
		Assert.assertEquals(movie.getId(), responseMovie.getId());
		Assert.assertEquals(movie.getName(), responseMovie.getName());
	}
	
	@Test
	public void testGetMovieNotExist() throws Exception {
		Movie movie = new Movie();
		movie.setName("not a move");
		movie.setId("-1");
		
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		Movie responseMovie = movieService.getMovieByID(movie.getId());
		
		Assert.assertNull(responseMovie);
	}
	
	
}
