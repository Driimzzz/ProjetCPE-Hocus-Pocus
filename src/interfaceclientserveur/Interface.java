package interfaceclientserveur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import cartes.Carte;
import partie.Joueur;
import partie.Partie;
import websocket.console.Client;
import websocket.console.Message;
import websocket.console.Salle;
import websocket.console.SocketAnnotation;
import websocket.console.SocketAnnotation.MessageType;

public class Interface {

	public static String input = "";

	private static Partie partie;

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

	public static void creerJeu(String[] nomJoueurs) {
		partie = new Partie(nomJoueurs.length, nomJoueurs, true);
		// partie.jeu();
	}

	// multijoueur (il faut ouvrir plusieurs fenêtres pour le simuler)
	public static void creerJeu(List<Client> clients) {
		partie = new Partie(clients, true);
		partie.jeu();
	}

	// peu renvoyer au client la main d'un joueur selon son numero.
	// TODO du coté client afficher ou mettre à jour la main du joueur
	public static void laMainDuJoueur(int numeroDuJoueur) {
		Joueur joueurVise = partie.getJoueurs().get(numeroDuJoueur);
		JSONObject json = new JSONObject();
		try {
			json.put("numeroJoueur", numeroDuJoueur);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			json.put("mainJoueur", joueurVise.getMain().toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Console(json.toString());
	}

	// retourner au client quel joueur doit jouer
	public static void joueurEnCour() {
		int num = partie.getJoueurJouant();
		Console("C'est le tour du joueur numero " + num);
		Console("Nommé : " + partie.getJoueurs().get(num).getNom());
	}

	// informer le serveur quelle carte a été jouée par quel joueur
	public static void carteJouee(int numJoueur, int numDansSaMain) {
		Joueur joueur = partie.getJoueurs().get(numJoueur);
		Carte carteJouee = joueur.getMain().getPileDeCarte().get(numDansSaMain);
		joueur.jouerCarte(carteJouee);
		Console(joueur.getNom() + " joue la carte " + carteJouee.getNom()
				+ carteJouee.getForce());
	}

	// le serveur demande au client de viser
	public static int viserUnJoueur(int numJoueurVisant) {
		Console("Le joueur numéro " + numJoueurVisant
				+ " doit viser un joueur. Lequel?");
		int numJoueurVise = 1;// TODO recupérer un numero venant du cient je
								// sais pas faire
		return numJoueurVise;
	}

	public static void gestionMessage(String data) {
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String methode = "error";
		try {
			methode = (String) json.get("methode");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Console(methode);
		switch (methode) {
		case "creerJeu":
			JSONArray arr = new JSONArray();
			try {
				arr = (JSONArray) json.get("joueurs");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<String> joueurs = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				try {
					joueurs.add(arr.getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String[] joueursTab = joueurs.toArray(new String[joueurs.size()]);
			creerJeu(joueursTab);

			break;

		case "carteJouee":
			boolean bonJSON = true;
			int numJoueur = 0;
			int numCarte = 0;
			try {
				numJoueur = json.getInt("numJoueur");
			} catch (JSONException e) {
				bonJSON = false;
				e.printStackTrace();
			}
			try {
				numCarte = json.getInt("numCarte");
			} catch (JSONException e) {
				bonJSON = false;
				e.printStackTrace();
			}
			if (bonJSON)
				carteJouee(numJoueur, numCarte);
			break;

		default:
			Console("erreur dans la sythaxe json de methode");
			break;
		}
	}
}
