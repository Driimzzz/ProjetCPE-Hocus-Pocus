package interfaceclientserveur;

import java.util.List;

import partie.Partie;
import websocket.console.Client;
import websocket.console.Message;
import websocket.console.Salle;
import websocket.console.SocketAnnotation;
import websocket.console.SocketAnnotation.MessageType;

public class Interface {

	public static String input="";

	public Interface() {

	}

	public static void Console(String str) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Console, str);
		SocketAnnotation.broadcast(message);
	}

	public static void Error(String str) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Error, str);
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
		input="";
		if (message.getType() == MessageType.Console) {

			if ("start".equals(message.getMessage())) {
				createJeu(Salle.getClients());
			} else{
				input = message.getMessage();
				Console(">"+input);
			} 
		}
		if (message.getType() == MessageType.Message) {
			SocketAnnotation.broadcast(message);
		}
	}
}
