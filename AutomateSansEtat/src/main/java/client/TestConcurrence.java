package client;

import rest.Automate;
import rest.Session;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import javax.ws.rs.client.WebTarget;

public class TestConcurrence {
    public static void main(String[] args) {
        String adresse = "http://localhost:9000/ms_java_war/automate/concurrent";
        System.out.println("*************");

        WebTarget cible = AppliCliente.clientJAXRS().target(adresse);
        Automate automateProxyJersey = WebResourceFactory.newResource(Automate.class, cible);

        int MAX = 100; // à augmenter possiblement

        Session[] sessions = new Session[MAX];
        for (int i = 0; i < MAX; i++) {
            sessions[i] = automateProxyJersey.initier();
            System.out.println(sessions[i].getNumero());
        }
        System.out.println("*************");
    }
}
