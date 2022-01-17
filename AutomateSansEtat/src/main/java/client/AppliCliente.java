package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import rest.Automate;
import rest.Resultat;
import rest.Session;
import rest.jaxb.FournisseurTraduction;

public class AppliCliente {

	public static Client clientJAXRS() {
		ClientConfig config = new ClientConfig();
		config.register(LoggingFeature.class);
		config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
		config.register(FournisseurTraduction.class);
		config.register(JacksonFeature.class);
		return ClientBuilder.newClient(config);
	}

	public static void main(String[] args) {

		String adresse = "http://localhost:9000/ms_java_war/automate";
		
		System.out.println("*************");


		WebTarget cible = clientJAXRS().target(adresse);
		Automate proxy = WebResourceFactory.newResource(Automate.class, cible);

		test(proxy);
	
		System.out.println("*************");
		
	}

	private static void test(Automate automate) {
		char[] mot = { 'a', 'b', 'a', 'a', 'a', 'b' };

		Resultat res = null;
		Session session = automate.initier();
		int i = 0;

		do{

			res = automate.accepter(mot[i], session);

			session = res.getId();
			i++;

		} while(res.isValide() && i < mot.length);

		if (res.isValide()) {
			System.out.println("La suite " + String.valueOf(mot) + "\" est valide ");
		} else {
			System.out.println("La suite \"" + String.valueOf(mot) + "\" n'est pas valide");
		}
	}

}
