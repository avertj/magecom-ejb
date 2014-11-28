package api.rest;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@Provider
public class MyCorsFilter extends CorsFilter {

	public MyCorsFilter() {
		super();
		getAllowedOrigins().add("http://localhost:8000"); // mettre * si ça ne marche pas
	}
}
