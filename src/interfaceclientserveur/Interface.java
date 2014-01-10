package interfaceclientserveur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cartes.Carte;
import cartes.Carte.CarteType;
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

	public static void Jeu(String str, int destination) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Jeu, str);
		message.setDestination(destination);
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
		partie.start();
	}

	// le serveur envois toutes les infos relative à la partie
	public static void toutesLesInfos() {
		for (int numJoueur = 0; numJoueur < partie.getJoueurs().size(); numJoueur++) {
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
			try {
				grosJson.put("joueurEnCour", partie.getJoueurJouant());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}
			JSONArray arr = new JSONArray();
			int n = 0;
			for (Joueur j : partie.getJoueurs()) {
				arr.put(j.toJson(numJoueur, n));
				n++;
			}
			if (numJoueur >= 0) {
				try {
					JSONObject buffer = (JSONObject) arr.get(numJoueur);
					// arr.remove(numJoueur);
					arr.put(numJoueur, arr.get(0));
					// arr.remove(0);
					arr.put(0, buffer);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				grosJson.put("joueurs", arr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}

			try {
				grosJson.put("aireDeJeu", partie.getAireDeJeu().toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage());
			}

			Jeu(grosJson.toString(), numJoueur);
		}
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
		if (numJoueur <= partie.getJoueurs().size()) {
			Joueur joueur = partie.getJoueurs().get(numJoueur);
			Carte carteJouee = joueur.getMain().getPileDeCarte()
					.get(numDansSaMain);

			if (carteJouee.getType() == CarteType.pocus)
				joueur.jouerCarte(carteJouee);
			else if (numJoueur == partie.getJoueurJouant())
				joueur.jouerCarte(carteJouee);
			else
				Error("Carte interdite de jouer");
		}
	}

	// le serveur demande au client de viser
	public static void viserUnJoueur(int numJoueurVisant) {
		JSONObject grosJson = new JSONObject();
		try {
			grosJson.put("methode", "viserJoueur");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Error(e.getMessage());
		}
		try {
			grosJson.put("numJoueurVisant", numJoueurVisant);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Error(e.getMessage());
		}
		JSONArray arr = new JSONArray();
		for (Joueur j : partie.getJoueurs()) {
			arr.put(j.toJson());
		}
		try {
			grosJson.put("joueurs", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Error(e.getMessage());
		}

		Jeu(grosJson.toString(), partie.getJoueurJouant());
	}

	// le client repond quel joueur est visé
	public static void joueurVise(int numJoueurVise) {
		Joueur jVise = partie.getJoueurs().get(numJoueurVise);
		Console("vous visez le joueur numéro " + numJoueurVise);
		partie.getAireDeJeu().getPileDeCarte().get(0).setJoueurVise(jVise);
	}

	// le client informe le serveur la fin de la carte hocus
	public static void finCarteHocus() {
		partie.jouerLesCartesDeLaireDeJeu();
		partie.tourDeJeu();
	}

	// choix d'une action demande au client
	public static void demandeAction(boolean carteHocus) {
		JsonObject json = new JsonObject();
		json.addProperty("methode", "demandeAction");
		json.addProperty("peuCarteHocus", carteHocus); // si le joueur peu jouer
														// une carte Hocus
		Jeu(json.toString(), partie.getJoueurJouant());

		// //Pour le test cote serveur
		// int input = -1;
		// while(input!=1 && input!=2){
		// Interface.Console("entrez 0 ou 1 ou 2:");
		// input = Partie.readIntValue();
		// }
		// String strMsg="";
		// switch (input){
		// case 0:
		// strMsg= "{methode:reponseAction;action:jouerHocus}";
		// break;
		// case 1:
		// strMsg= "{methode:reponseAction;action:piocherGemme}";
		// break;
		// case 2:
		// strMsg= "{methode:reponseAction;action:piocherCartes}";
		// break;
		// }
		// Message message = new Message(MessageType.Jeu, strMsg);
		// gestionMessage(message);
	}

	// choix d'une action demande au client
	public static void reponseAction(String action) {
		switch (action) {
		case "jouerHocus":
			break;
		case "piocherGemme":
			partie.finDuTour(1);
			break;
		case "piocherCartes":
			partie.finDuTour(2);
			break;
		default:
			Error("Erreur Json dans la sythaxe de l'action");
			break;
		}
		toutesLesInfos();
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
				/*
				 * JSONArray arr = new JSONArray(); try { arr = (JSONArray)
				 * json.get("joueurs"); } catch (JSONException e1) { // TODO
				 * Auto-generated catch block Error(e1.getMessage()); }
				 * List<String> joueurs = new ArrayList<>(); for (int i = 0; i <
				 * arr.length(); i++) { try { joueurs.add(arr.getString(i)); }
				 * catch (JSONException e) { // TODO Auto-generated catch block
				 * Error(e.getMessage()); } }
				 * 
				 * String[] joueursTab = joueurs .toArray(new
				 * String[joueurs.size()]);
				 */
				creerJeu(SocketAnnotation.getSalle().getClients());

				break;

			case "carteJouee":
				bonJSON = true;

				int numCarte = 0;

				try {
					numCarte = json.getInt("numCarte");
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage());
				}
				if (bonJSON)
					carteJouee(message.getAuteur(), numCarte);
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

			case "reponseAction":
				String action = "";
				try {
					action = json.getString("action");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// piocherGemme OU piocherCartes OU jouerHocus
				reponseAction(action);
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
