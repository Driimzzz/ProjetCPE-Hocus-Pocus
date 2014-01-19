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
	private static int numAuteur = -1;

	private static Partie partie;

	public static Partie getPartie() {
		return partie;
	}

	public static void setPartie(Partie partie) {
		Interface.partie = partie;
	}

	public Interface() {

	}

	public static void Console(String str,Partie partie) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Console, str);
		SocketAnnotation.broadcast(message,partie);
	}

	public static void Error(String str,Partie partie) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Error, str);
		SocketAnnotation.broadcast(message,partie);
	}

	public static void Jeu(String str,Partie partie) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Jeu, str);
		SocketAnnotation.broadcast(message,partie);
	}

	public static void Jeu(String str, int destination,Partie partie) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Jeu, str);
		message.setDestination(destination);
		SocketAnnotation.broadcast(message,partie);
	}

	public static void Autorisation(String str,Partie partie) {
		System.out.println(str);
		Message message = new Message(
				websocket.console.SocketAnnotation.MessageType.Autorisation,
				str);
		SocketAnnotation.broadcast(message,partie);
	}

	// multijoueur (il faut ouvrir plusieurs fenêtres pour le simuler)
	public static void creerJeu() {
		if(SocketAnnotation.getSalle().getClientsLibre().size()>1)
		{
			Partie partie = new Partie(SocketAnnotation.getSalle().getClientsLibre(), true);
			partie.start();
			for (Client c : SocketAnnotation.getSalle().getClientsLibre()) {
				c.setPartie(partie);
			}
		}
	}
	
	public static void quitterPartie(Client client)
	{
		partie=client.getPartie();
		if(partie!=null)
			partie.quitterPartie(client.getId());
	}
	
	// choix d'une action demande au client
	public static void reponseAction(int numJoueur, String action) {
		switch (action) {
		case "jouerHocus":
			break;
		case "piocherGemme":
			partie.finDuTour(numJoueur, 1);
			break;
		case "piocherCartes":
			partie.finDuTour(numJoueur, 2);
			break;
		default:
			Error("Erreur Json dans la sythaxe de l'action",partie);
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
				Error(e.getMessage(),partie);
			}
			String methode = "error";
			try {
				methode = (String) json.get("methode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Error(e.getMessage(),partie);
			}

			boolean bonJSON = true;

			switch (methode) {
			case "creerJeu":

				creerJeu();

				break;

			case "carteJouee":
				bonJSON = true;
				partie = message.getAuteur().getPartie();
				//partie.afficherJoueurs();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				System.out.println(message.getAuteur().getId());
				int numCarte = 0;

				try {
					numCarte = json.getInt("numCarte");
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage(),partie);
				}
				if (bonJSON)
					partie.carteJouee(numAuteur, numCarte);
				break;

			case "joueurVise":
				int numJoueurVise;
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				try {
					numJoueurVise = json.getInt("numJoueurVise");
					partie.joueurVise(numAuteur, numJoueurVise);
				} catch (JSONException e) {
					bonJSON = false;
					Error(e.getMessage(),partie);
				}
				break;

			case "finCarteHocus":
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				partie.finCarteHocus();
				break;

			case "reponseAction":
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				String action = "";
				try {
					action = json.getString("action");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// piocherGemme OU piocherCartes OU jouerHocus
				reponseAction(numAuteur, action);
				break;

			case "completerGrimoire":
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				try {
					if (numAuteur == json.getInt("numJoueur")) {
						partie.getJoueurs().get(numAuteur)
								.completerGrimoire(json.getInt("numCarte"));
						partie.toutesLesInfos();
					}
					break;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			case "reponseCartesDuGrimoire":
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				try {
					if (numAuteur == json.getInt("numJoueur")) {

						int joueurGrimoire = json.getInt("numJoueurVise");
						JSONArray carteArr = json.getJSONArray("grimoire");
						partie.reponseCartesDuGrimoire(carteArr, joueurGrimoire);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "reponseChoixBouleDeCristal":
				partie = message.getAuteur().getPartie();
				JSONArray carteArr;
				try {
					carteArr = json.getJSONArray("cartes");
					partie.reponseCartesBouleDeCristal(carteArr);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "rejouerJeu":
				//un des clients recoit rejouer jeu tous les autres rejouetn le jeu
				partie = message.getAuteur().getPartie();
				numAuteur = partie.getnumJoueurByID(message.getAuteur().getId());
				 JSONObject json1=new JSONObject();
				 try {
					json1.put("methode", "rejouerJeu");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Interface.Jeu(json1.toString(), partie);
				break;
				
			case "pseudoDuJeu":

				try {
					String pseudo = (String) json.get("pseudo");
					message.getAuteur().setNickname(pseudo);
					Message message2 = new Message(MessageType.Jeu,listeJoueurs(), message.getAuteur());
					SocketAnnotation.broadcast(message2, null);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Error(e.getMessage(), partie);
				}
				break;

			default:
				Error("erreur dans la sythaxe json de methode",partie);
				break;
			}
		}
		input = "";
		if (message.getType() == MessageType.Console) {
			input = message.getMessage();
			Console(">" + input,partie);
		}
		if (message.getType() == MessageType.Message) {
			message.setMessage(message.getAuteur().getNickname() + ": "
					+ message.getMessage());
			partie = message.getAuteur().getPartie();
				SocketAnnotation.broadcast(message, partie);
		}
	}
	
	public static String listeJoueurs() {
		JSONObject grosJson = new JSONObject();
		try {
			grosJson.put("methode", "listeJoueurs");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Error(e.getMessage(), null);
		}

		JSONArray arr = new JSONArray();
		for (Client j : Salle.getClientsLibre()) {
			arr.put(j.toJson());
		}

		try {
			grosJson.put("joueurs", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage(),null);
		}
		return grosJson.toString();
	}

}
