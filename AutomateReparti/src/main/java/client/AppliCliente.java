package client;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;

import modele.EtatUn;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import modele.Automate;
import modele.EtatAutomate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

public class AppliCliente {

	private static Client clientJAXRS;

	static {
		ClientConfig config = new ClientConfig();

		config.register(LoggingFeature.class);
		config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
		config.register(JacksonFeature.class);

		clientJAXRS = ClientBuilder.newClient(config);
	}

	
	public static void main(String[] args) {

		System.out.println("*************");


		WebTarget cible = AppliCliente.clientJAXRS.target(JAXRS.ADRESSE_SERVEUR_AUTOMATE);
		Automate proxy = WebResourceFactory.newResource(Automate.class, cible);

		tester(proxy,'a', 'b', 'a', 'b', 'a', 'b');

		tester(proxy, 'a', 'b', 'a', 'b', 'a', 'a');

		System.exit(0);

	}


	private static EtatAutomate proxyEtat(HyperLien<EtatAutomate> h){
		WebTarget cible = AppliCliente.clientJAXRS.target(h.getUri());
		return WebResourceFactory.newResource(EtatAutomate.class, cible);
	}

	private static void tester(Automate automate, Character... mot) {
		HyperLien<EtatAutomate> automateHyperLien = automate.initier();

		StringBuilder reconnu = new StringBuilder();

		Stream<Character> characterStream =  Arrays.stream(mot);
		try {
			characterStream.forEach(character -> {
				reconnu.append(character);

				EtatAutomate etatAutomate = proxyEtat(automateHyperLien);


					automateHyperLien.setUri(etatAutomate.accepter(character).getUri());

			});
			System.out.println("préfixe reconnu : ".concat(reconnu.toString()));
		}catch (WebApplicationException e){
			System.out.println("préfixe non reconnu : ".concat(reconnu.toString()));
		}


	}

}
