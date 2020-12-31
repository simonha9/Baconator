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

public class ActorMovieRelationshipServiceTest extends TestCase {

	Neo4jConnector connector = Neo4jConnector.getInstance();
	
	@Test
	public void testCreateRead() throws Exception {
		ActorMovieRelationship rel = new ActorMovieRelationship();
		rel.setActorID("123");
		rel.setMovieID("321");
		
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		relationshipService.addRelationship(rel);
		
		ActorMovieRelationship responseRel = relationshipService.getRelationship(rel);
		
		Assert.assertEquals(rel.getActorID(), responseRel.getActorID());
		Assert.assertEquals(rel.getMovieID(), responseRel.getMovieID());
		
	}
	
	@Test
	public void testNoRelationship() throws Exception {
		ActorMovieRelationship relNotExist = new ActorMovieRelationship();
		
		relNotExist.setActorID("not exist");
		relNotExist.setMovieID("123");
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(connector.getDriver());
		ActorMovieRelationship responseRel = relationshipService.getRelationship(relNotExist);
		
		Assert.assertNull(responseRel);
	}
	
}
