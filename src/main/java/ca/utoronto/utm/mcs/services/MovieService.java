package ca.utoronto.utm.mcs.services;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public interface MovieService extends HttpHandler{

	public void addMovie(HttpExchange r) throws Exception;
	public void getMovie(HttpExchange r) throws Exception;
}
