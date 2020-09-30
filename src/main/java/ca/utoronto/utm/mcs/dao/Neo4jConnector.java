package ca.utoronto.utm.mcs.dao;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnector {

	private static Driver driver = null;
	public static String uriDb = "bolt://localhost:7687";
	private static String username = "neo4j";
	private static String password = "1234";
	private static final Neo4jConnector connector = new Neo4jConnector();
	
	private Neo4jConnector() {
		Neo4jConnector.driver = GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));
	};
	
	public static Neo4jConnector getInstance() {
		return connector;
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public void finalize() {
		driver.close();
	}
	
}
