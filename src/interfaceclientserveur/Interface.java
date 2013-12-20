package interfaceclientserveur;

import partie.Partie;
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

	public static void gestionMessage(Message message) {
		if (message.getType() == MessageType.Users) {
			String[] elements = message.getMessage().split(",");
			createJeu(elements.length, elements);
		}
	}

}
