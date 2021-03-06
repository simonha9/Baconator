package ca.utoronto.utm.mcs;

import org.junit.Test;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ActorServiceTest extends TestCase {

	Neo4jConnector connector = Neo4jConnector.getInstance();
	
	@Test
	public void testCreateRead() throws Exception {
		Actor actor = new Actor();
		actor.setName("name");
		actor.setId("123");
		
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		actorService.addActor(actor);
		
		Actor responseActorID = actorService.findActorById(actor.getId());
		
		Assert.assertEquals(actor.getId(), responseActorID.getId());
		Assert.assertEquals(actor.getName(), responseActorID.getName());
	}
	
	public void testGetActorNotExist() throws Exception {
		Actor actor = new Actor();
		actor.setId("-1");
		actor.setName("not a real name");
		Actor responseActorID = null;
		try {
			ActorService actorService = new ActorServiceImpl(connector.getDriver());
			responseActorID = actorService.findActorById(actor.getId());
		} catch (NodeNotExistException e) {
			Assert.assertNull(responseActorID);
		}
	}
	
	
	
}
