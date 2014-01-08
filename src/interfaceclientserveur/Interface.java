package interfaceclientserveur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
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

	public static Partie getPartie() {
		return partie;
	}

	public static void setPartie(Partie partie) {
		Interface.partie = partie;
	}

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

	public static void Jeu(String str) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Jeu, str);
		SocketAnnotation.broadcast(message);
	}

	public static void Autorisation(String str) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Autorisation,
				str);
		SocketAnnotation.broadcast(message);
	}

	public static void creerJeu(String[] nomJoueurs) {
		partie = new Partie(nomJoueurs.length, nomJoueurs, true);
		partie.start();
	}

	// multijoueur (il faut ouvrir plusieurs fenêtres pour le simuler)
	public static void creerJeu(List<Client> clients) {
		partie = new Partie(clients, true);
		partie.jeu();
	}

	
	//le serveur envois toutes les infos relative à la partie
		public static void toutesLesInfos(){
			JSONObject grosJson = new JSONObject();
			try {
				grosJson.put("methode", "toutesLesInfos");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			try {
				grosJson.put("chaudron", partie.getChaudron());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			JSONArray arr = new JSONArray();
			for(Joueur j : partie.getJoueurs()){
				arr.put(j.toJson());
			}
			try {
				grosJson.put("joueurs", arr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			
			Jeu(grosJson.toString());
		}
		
	// peu renvoyer au client la main d'un joueur selon son numero.
	// TODO du coté client afficher ou mettre à jour la main du joueur
	public static void lesMainsDesJoueurs() {
		JSONObject headJson = new JSONObject();
		try {
			headJson.put("methode", "afficherToutesLesMains");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Error(e.getMessage());
		}

		JSONArray arr = new JSONArray();

		for (int numeroDuJoueur = 0; numeroDuJoueur < partie.getJoueurs()
				.size(); numeroDuJoueur++) {
			Joueur joueurVise = partie.getJoueurs().get(numeroDuJoueur);
			JSONObject json = new JSONObject();
			try {
				json.put("numeroJoueur", numeroDuJoueur);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			try {
				json.put("mainJoueur", joueurVise.getMain().toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}

			arr.put(json);
		}

		try {
			headJson.put("mains", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Jeu(headJson.toString());
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
		joueur.jouerCarte(carteJouee); // TODO verification autorisation de
										// jouer
		Console(joueur.getNom() + " joue la carte " + carteJouee.getNom()
				+ carteJouee.getForce());
	}

	// le serveur demande au client de viser
	public static void viserUnJoueur(int numJoueurVisant) {
		Console("Le joueur numéro " + numJoueurVisant
				+ " doit viser un joueur. Lequel?");
	}

	// le client repond quel joueur est visé
	public static void joueurVise(int numJoueurVise) {
		Joueur jVise = partie.getJoueurs().get(numJoueurVise);
		Console("vous visez le joueur numéro " + numJoueurVise);
		partie.getAireDeJeu().getPileDeCarte().get(0).setJoueurVise(jVise);
	}

	public static void finCarteHocus() {
		partie.jouerLesCartesDeLaireDeJeu();
	}

	public static void gestionMessage(Message message) {

		if (message.getType() == MessageType.Jeu) {
			JSONObject json = new JSONObject();
			try {
				json = new JSONObject(message.getMessage());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			String methode = "error";
			try {
				methode = (String) json.get("methode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}

			boolean bonJSON = true;

			switch (methode) {
			case "creerJeu":
				JSONArray arr = new JSONArray();
				try {
					arr = (JSONArray) json.get("joueurs");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					Error(e1.getMessage());
				}
				List<String> joueurs = new ArrayList<>();
				for (int i = 0; i < arr.length(); i++) {
					try {
						joueurs.add(arr.getString(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Error(e.getMessage());
					}
				}

				String[] joueursTab = joueurs
						.toArray(new String[joueurs.size()]);
				creerJeu(joueursTab);

				break;

			case "carteJouee":
				bonJSON = true;
				int numJoueur = 0;
				int numCarte = 0;
				try {
					numJoueur = json.getInt("numJoueur");
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage());
				}
				try {
					numCarte = json.getInt("numCarte");
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage());
				}
				if (bonJSON)
					carteJouee(numJoueur, numCarte);
				break;

			case "joueurVise":
				int numJoueurVise;
				try {
					numJoueurVise = json.getInt("numJoueurVise");
					joueurVise(numJoueurVise);
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage());
				}
				break;

			case "finCarteHocus":
				finCarteHocus();
				break;

			default:
				Error("erreur dans la sythaxe json de methode");
				break;
			}
		}
		input = "";
		if (message.getType() == MessageType.Console) {
			input = message.getMessage();
			Console(">" + input);
		}
		if (message.getType() == MessageType.Message) {
			SocketAnnotation.broadcast(message);
		}
	}
}
