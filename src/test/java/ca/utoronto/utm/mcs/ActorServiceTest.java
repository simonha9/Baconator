package ca.utoronto.utm.mcs;

import org.junit.Test;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.domain.Actor;
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
		
		Actor responseActorID = actorService.getActorByID(actor.getId());
		Actor responseActorName = actorService.getActorByName(actor.getName());
		
		Assert.assertEquals(actor.getId(), responseActorID.getId());
		Assert.assertEquals(actor.getName(), responseActorID.getName());
		
		Assert.assertEquals(actor.getId(), responseActorName.getId());
		Assert.assertEquals(actor.getName(), responseActorName.getName());
	}
	
	public void testGetActorNotExist() throws Exception {
		Actor actor = new Actor();
		actor.setId("-1");
		actor.setName("not a real name");
		ActorService actorService = new ActorServiceImpl(connector.getDriver());
		Actor responseActorID = actorService.getActorByID(actor.getId());
		Actor responseActorName = actorService.getActorByName(actor.getName());
		
		Assert.assertNull(responseActorID);
		Assert.assertNull(responseActorName);
	}
	
	
	
}
