package modele;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.annotations.ReponsesNull404GET;

import javax.ws.rs.*;

public interface EtatAutomate {

	@POST
	@Path(JAXRS.SOUS_CHEMIN_SUIVANT)
	@Produces(JAXRS.TYPE_MEDIA)
	@Consumes(JAXRS.TYPE_MEDIA)
	@ReponsesNull404GET()
	HyperLien<EtatAutomate> accepter(@QueryParam(JAXRS.CLE_ETIQUETTE) char etiquette);
}
