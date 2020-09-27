package ca.utoronto.utm.mcs.services;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public interface ActorService extends HttpHandler{

	public void addActor(HttpExchange r) throws Exception;
	public void getActor(HttpExchange r) throws Exception;
	public void computeBaconNumber(HttpExchange r) throws Exception;
	public void computeBaconPath(HttpExchange r) throws Exception;
}
