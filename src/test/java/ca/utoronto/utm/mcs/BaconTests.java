package ca.utoronto.utm.mcs;

import java.util.List;

import org.junit.Test;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.ActorMovieRelationshipServiceImpl;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class BaconTests extends TestCase {

	Neo4jConnector connector = Neo4jConnector.getInstance();
	
	@Test
	public void testGetBaconNumberSelf() throws Exception {
		Actor actor = new Actor();
		actor.setName("Kevin Bacon");
		actor.setId("nm0000102");
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		actorService.addActor(actor);
		int baconNum = actorService.computeBaconNumber(actor.getId());
		Assert.assertEquals(baconNum, 0);
	}
	
	@Test
	public void testGetBaconNumber1Movie() throws Exception {
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		Actor actor = new Actor();
		actor.setName("Kevin Bacon");
		actor.setId("nm0000102");
		actorService.addActor(actor);
		
		Actor otherActor = new Actor();
		otherActor.setName("actor guy 123");
		otherActor.setId("123");
		actorService.addActor(otherActor);
		
		Movie movie = new Movie();
		movie.setName("moviename");
		movie.setId("111");
		movieService.addMovie(movie);
		
		ActorMovieRelationship rel1 = new ActorMovieRelationship();
		rel1.setActorID(actor.getId());
		rel1.setMovieID(movie.getId());
		relationshipService.addRelationship(rel1);
		
		ActorMovieRelationship rel2 = new ActorMovieRelationship();
		rel2.setActorID(otherActor.getId());
		rel2.setMovieID(movie.getId());
		relationshipService.addRelationship(rel2);
		
		int baconNum = actorService.computeBaconNumber(otherActor.getId());
		Assert.assertEquals(baconNum, 1);
	}
	
	@Test
	public void testGetBaconPath1Movie() throws Exception {
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		Actor actor = new Actor();
		actor.setName("Kevin Bacon");
		actor.setId("nm0000102");
		actorService.addActor(actor);
		
		Actor otherActor = new Actor();
		otherActor.setName("actor guy 123");
		otherActor.setId("123");
		actorService.addActor(otherActor);
		
		Movie movie = new Movie();
		movie.setName("moviename");
		movie.setId("111");
		movieService.addMovie(movie);
		
		ActorMovieRelationship rel1 = new ActorMovieRelationship();
		rel1.setActorID(actor.getId());
		rel1.setMovieID(movie.getId());
		relationshipService.addRelationship(rel1);
		
		ActorMovieRelationship rel2 = new ActorMovieRelationship();
		rel2.setActorID(otherActor.getId());
		rel2.setMovieID(movie.getId());
		relationshipService.addRelationship(rel2);
		
		List<ActorMovieRelationship> rels = actorService.computeBaconPath(otherActor.getId());
		Assert.assertEquals(rels.size(), 2);
	}
	
	@Test
	public void testGetBaconNumber2Movie() throws Exception {
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		Actor actor = new Actor();
		actor.setName("Kevin Bacon");
		actor.setId("nm0000102");
		actorService.addActor(actor);
		
		Actor otherActor = new Actor();
		otherActor.setName("actor guy 123");
		otherActor.setId("123");
		actorService.addActor(otherActor);
		
		Actor actor3 = new Actor();
		actor3.setName("actor guy 321");
		actor3.setId("321");
		actorService.addActor(actor3);
		
		Movie movie = new Movie();
		movie.setName("moviename");
		movie.setId("111");
		movieService.addMovie(movie);
		
		Movie movie2 = new Movie();
		movie2.setName("other moviename");
		movie2.setId("222");
		movieService.addMovie(movie2);
		
		ActorMovieRelationship rel1 = new ActorMovieRelationship();
		rel1.setActorID(actor.getId());
		rel1.setMovieID(movie.getId());
		relationshipService.addRelationship(rel1);
		
		ActorMovieRelationship rel2 = new ActorMovieRelationship();
		rel2.setActorID(otherActor.getId());
		rel2.setMovieID(movie.getId());
		relationshipService.addRelationship(rel2);
		
		ActorMovieRelationship rel3 = new ActorMovieRelationship();
		rel3.setActorID(otherActor.getId());
		rel3.setMovieID(movie2.getId());
		relationshipService.addRelationship(rel3);
		
		ActorMovieRelationship rel4 = new ActorMovieRelationship();
		rel4.setActorID(actor3.getId());
		rel4.setMovieID(movie2.getId());
		relationshipService.addRelationship(rel4);
		
		int baconNum = actorService.computeBaconNumber(actor3.getId());
		Assert.assertEquals(baconNum, 2);
	}
	
	@Test
	public void testGetBaconPath2Movie() throws Exception {
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		MovieService movieService = new MovieServiceImpl(connector.getDriver());
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		Actor actor = new Actor();
		actor.setName("Kevin Bacon");
		actor.setId("nm0000102");
		actorService.addActor(actor);
		
		Actor otherActor = new Actor();
		otherActor.setName("actor guy 123");
		otherActor.setId("123");
		actorService.addActor(otherActor);
		
		Actor actor3 = new Actor();
		actor3.setName("actor guy 321");
		actor3.setId("321");
		actorService.addActor(actor3);
		
		Movie movie = new Movie();
		movie.setName("moviename");
		movie.setId("111");
		movieService.addMovie(movie);
		
		Movie movie2 = new Movie();
		movie2.setName("other moviename");
		movie2.setId("222");
		movieService.addMovie(movie2);
		
		ActorMovieRelationship rel1 = new ActorMovieRelationship();
		rel1.setActorID(actor.getId());
		rel1.setMovieID(movie.getId());
		relationshipService.addRelationship(rel1);
		
		ActorMovieRelationship rel2 = new ActorMovieRelationship();
		rel2.setActorID(otherActor.getId());
		rel2.setMovieID(movie.getId());
		relationshipService.addRelationship(rel2);
		
		ActorMovieRelationship rel3 = new ActorMovieRelationship();
		rel3.setActorID(otherActor.getId());
		rel3.setMovieID(movie2.getId());
		relationshipService.addRelationship(rel3);
		
		ActorMovieRelationship rel4 = new ActorMovieRelationship();
		rel4.setActorID(actor3.getId());
		rel4.setMovieID(movie2.getId());
		relationshipService.addRelationship(rel4);
		
		List<ActorMovieRelationship> rels = actorService.computeBaconPath(actor3.getId());
		Assert.assertEquals(rels.size(), 4);
	}
	
}
