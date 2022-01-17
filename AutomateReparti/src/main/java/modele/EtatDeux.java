package modele;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriBuilder;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;

@Singleton
@Path(JAXRS.CHEMIN_DEUX)
public class EtatDeux implements EtatAutomate {

	public EtatDeux() {
		System.out.println("Initialisation de la ressource de type "
				+ this.getClass());
	}

	@Override
	public
	HyperLien<EtatAutomate> accepter(char x){
		if(x == 'b') {
			return new HyperLien<EtatAutomate>(UriBuilder.fromPath(JAXRS.PROJET).path(EtatUn.class).build());
		}else{
			throw new WebApplicationException();
		}
	}
	
	
}
