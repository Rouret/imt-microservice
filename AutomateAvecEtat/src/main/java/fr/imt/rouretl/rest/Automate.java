package fr.imt.rouretl.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface Automate {

	@POST
	@Path("etat/initial")
	@Produces(MediaType.APPLICATION_JSON)
	Session initier();

	@POST
	@Path("etat/suivant/{lettre}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Resultat accepter(@PathParam("lettre") char x,Session id);


	@PUT
	@Path("fin")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	void clore(Session id);
}

