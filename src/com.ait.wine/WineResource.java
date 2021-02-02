package com.ait.wine;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/wines")
public class WineResource {

	WineDAO dao = new WineDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Wine> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}
	
	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	//http://localhost:8080/wines/{id}
	public Response findById(@PathParam("id") int id) {
		Wine wine = dao.findById(id);
		return Response.status(200).entity(wine).build();
	}
	
	@GET @Path("search/{query}")
	@Produces({MediaType.APPLICATION_JSON})
	//http://localhost:8080/wines/search/{query}
	public Response findByName(@PathParam("query") String name) {
		List<Wine> wines = dao.findByName(name);
		return Response.status(200).entity(wines).build();
	}
	
	@GET @Path("/query")
	@Produces({MediaType.APPLICATION_JSON})
	//http://localhost:8080/wines/query
	public Response findByCountryAndGrapes(
			@QueryParam("country") String country,
			@QueryParam("grapes") String grapes){
		List<Wine> wines = dao.findByCountryAndGrapes(country, grapes);
		return Response.status(200).entity(wines).build();
		}
	
}


