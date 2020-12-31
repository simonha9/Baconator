package ca.utoronto.utm.mcs;

import org.junit.Test;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;
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
		
		Movie responseMovie = movieService.findMovieById(movie.getId());
		
		Assert.assertEquals(movie.getId(), responseMovie.getId());
		Assert.assertEquals(movie.getName(), responseMovie.getName());
	}
	
	@Test
	public void testGetMovieNotExist() throws Exception {
		Movie movie = new Movie();
		movie.setName("not a move");
		movie.setId("-1");
		Movie responseMovie = null;
		
		try {
			MovieService movieService = new MovieServiceImpl(connector.getDriver());
			responseMovie = movieService.findMovieById(movie.getId());
		} catch (NodeNotExistException e) {
			Assert.assertNull(responseMovie);
		}
	}
	
	
}
