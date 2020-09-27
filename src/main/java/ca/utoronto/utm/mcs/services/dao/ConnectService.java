package ca.utoronto.utm.mcs.services.dao;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public interface ConnectService {

	public static String uriDb = "bolt://localhost:7687";
    public static String uriUser ="http://localhost:8080";
    public static Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));
}
