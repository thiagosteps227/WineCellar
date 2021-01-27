package com.ait.wine;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/wines")
public class WineResource {

	WineDAO dao = new WineDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Wine> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}


}
