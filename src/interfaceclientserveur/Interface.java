package interfaceclientserveur;

import java.util.List;

import partie.Partie;
import websocket.console.Client;
import websocket.console.Message;
import websocket.console.SocketAnnotation;
import websocket.console.SocketAnnotation.MessageType;

public class Interface {
	public Interface() {

	}

	public static void Console(String str) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Message, str);
		SocketAnnotation.broadcast(message);
	}

	public static void createJeu(int nbJoueurs, String[] nomJoueurs) {
		Partie partie = new Partie(nbJoueurs, nomJoueurs, true);
		partie.jeu();
	}

	// multijoueur (il faut ouvrir plusieurs fenêtres pour le simuler)
	public static void createJeu(List<Client> clients) {
		Partie partie = new Partie(clients, true);
		partie.jeu();
	}

	public static void gestionMessage(Message message) {
		if (message.getType() == MessageType.Console) {
			String[] elements = message.getMessage().split(":");
			if ("Users".equals(elements[0])) {
				elements = message.getMessage().split(":$,");
				createJeu(elements.length, elements);
			}
			if ("Start".equals(elements[0])) {
				createJeu(SocketAnnotation.getSalle().getClients());
			}

		}
		if (message.getType() == MessageType.Message) {
			SocketAnnotation.broadcast(message);
		}
	}
}
