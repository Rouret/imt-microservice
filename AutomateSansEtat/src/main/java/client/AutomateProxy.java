package client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Invocation.Builder;

import rest.Automate;
import rest.Resultat;
import rest.Session;

public class AutomateProxy implements Automate {
	private WebTarget cibleInitier;
	private WebTarget cibleAccepter;
	private MediaType typeContenu;
	
	public AutomateProxy(String uriBase, MediaType typeContenu){
		WebTarget cible = AppliCliente.clientJAXRS().target(uriBase);
		cibleInitier = cible.path("etat/initial");
		cibleAccepter = cible.path("etat/suivant");
		this.typeContenu = typeContenu;
	}

	public static void main(String[] args) {
		String URL_BASE = "http://localhost:9000/ms_java_war/automate";

		AutomateProxy automateProxy = new AutomateProxy(URL_BASE,MediaType.APPLICATION_JSON_TYPE);

		/*
		Session session = automateProxy.initier();

		automateProxy.accepter('a', session);

		 */




		Session session = automateProxy.initier();
		Resultat result = null;

		char[] mot = { 'a', 'b', 'a', 'a', 'a', 'b' };

		int i = 0;
		do{

			result = automateProxy.accepter(mot[i], session);
			session =  result.getId();
			i++;

		} while(result.isValide() && i < mot.length);

		if (result.isValide()) {
			System.out.println("La suite " + String.valueOf(mot) + "\" est valide ");
		} else {
			System.out.println("La suite \"" + String.valueOf(mot) + "\" n'est pas valide");
		}


	}

	@Override
	public Session initier() {
		Builder builder =  cibleInitier.request(typeContenu);
		Response response = builder.post(Entity.entity(Session.class,typeContenu));
		return response.readEntity(Session.class);
	}

	@Override
	public Resultat accepter(char x, Session id) {
		WebTarget webTarget = cibleAccepter.path("/" + x);

		webTarget = webTarget.queryParam("id",id);
		Builder builder =  webTarget.request(typeContenu);

		Resultat resultat = builder.get(Resultat.class);

		return resultat;
	}

}
