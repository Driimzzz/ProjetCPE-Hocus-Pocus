package interfaceclientserveur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

import cartes.Carte;
import cartes.Carte.CarteType;
import partie.Grimoire;
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


	// multijoueur (il faut ouvrir plusieurs fenêtres pour le simuler)
	public static void creerJeu(List<Client> clients) {
		partie = new Partie(clients, true);
		partie.start();
	}

	
	
	
	// choix d'une action demande au client
	public static void demandeAction(boolean carteHocus,boolean peuPiocherCartes ) {
		JsonObject json = new JsonObject();
		json.addProperty("methode", "demandeAction");
		json.addProperty("peuCarteHocus", carteHocus); // si le joueur peu jouer
		json.addProperty("peuPiocherCartes", peuPiocherCartes);
		Jeu(json.toString(), partie.getJoueurJouant());


	}

	// choix d'une action demande au client
	public static void reponseAction(int numJoueur,String action) {
		switch (action) {
		case "jouerHocus":
			break;
		case "piocherGemme":
			partie.finDuTour(numJoueur,1);
			break;
		case "piocherCartes":
			partie.finDuTour(numJoueur,2);
			break;
		default:
			Error("Erreur Json dans la sythaxe de l'action");
			break;
		}
		partie.toutesLesInfos();
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
					partie.carteJouee(message.getAuteur(), numCarte);
				break;

			case "joueurVise":
				int numJoueurVise;
				try {
					numJoueurVise = json.getInt("numJoueurVise");
					partie.joueurVise(message.getAuteur(),numJoueurVise);
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage());
				}
				break;

			case "finCarteHocus":
				partie.finCarteHocus();
				break;

			case "reponseAction":
				String action = "";
				try {
					action = json.getString("action");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// piocherGemme OU piocherCartes OU jouerHocus
				reponseAction(message.getAuteur(),action);
				break;

			case "completerGrimoire":
				try {
					if(message.getAuteur() == json.getInt("numJoueur")){
						partie.getJoueurs().get(message.getAuteur()).completerGrimoire(json.getInt("numCarte"));
						partie.toutesLesInfos();
					}
					break;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			case "reponseCartesDuGrimoire":
				try {
					if(message.getAuteur() == json.getInt("numJoueur")){
						
						int joueurGrimoire = json.getInt("numJoueurVise");						
						JSONArray carteArr = json.getJSONArray("grimoire");						
						partie.reponseCartesDuGrimoire(carteArr, joueurGrimoire);	
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
