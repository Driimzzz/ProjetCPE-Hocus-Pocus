package partie;

import interfaceclientserveur.Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import websocket.console.Client;
import websocket.console.Message;
import websocket.console.SocketAnnotation.MessageType;
import cartes.*;
import cartes.Carte.CarteType;

public class Partie extends Thread {
	private int chaudron;// le nb de gemmes dans le chaudron

	static private List<Joueur> joueurs;
	static Bibliotheque bibliotheque;
	private int indexJoueur;
	static private PileDeCartes aireDeJeu;
	static private PileDeCartes defausse;
	Timer timerFinCarte;

	public void tourDeJeu() {
		
		for(Joueur j: getJoueurs())
		{
			if(j.getGrimoire().getPileDeCarte().size()<3)
				this.getJoueurs().get(j.getPositionPartie()).demandeCompleterGrimoire();
		}
		/*Interface.demandeAction(joueurEnCours.aDesHocusDansSonJeu(),
				joueurEnCours.peutPiocherCarte());*/

	}

	public void finDuTour(int numJoueur, int input) {

		// on vérifie l'auteur
		if (indexJoueur == numJoueur) {
			if(this.getAireDeJeu().getPileDeCarte().size()==0)
			{
				Joueur joueurEnCours = joueurs.get(indexJoueur);
				if (input == 1) {// pioche 1 gemme
					Interface.Console(joueurEnCours.getNom()
							+ " pioche 1 gemme dans le chaudron",this);
					joueurEnCours.setGemmes(joueurEnCours.getGemmes() + 1);
					this.piocherDansLeChaudron(1);
				} else if (input == 2) { // pioche 2 cartes
					joueurEnCours.piocherCartes(2);
				} else {// erreur
					Interface.Console("erreur fin du tour",this);
				}
				Interface.Console(joueurEnCours.getNom() + " a maintenant "
						+ joueurEnCours.getGemmes() + " gemmes.",this);
	
				// passe au jouer suivant
				if (indexJoueur == joueurs.size() - 1)
					indexJoueur = 0;
				else
					indexJoueur++;
				jeu();
			}
		}
		else{
			Interface.Error("Attendez la fin de la carte Hocus.",this);
		}
	}

	public void run() {
		jeu();

	}

	// la fonction qui alterne les tours de jeu entre les joueurs
	public void jeu() {
		// indexJoueur = 0;
		// while (chaudron > 0) {
		if (chaudron > 0) {
			toutesLesInfos();
			//tourDeJeu();

		} else
			finDuJeu();
	}

	public void finDuJeu() {
		Interface.Console("C'est la fin du jeu, tableau des scores : ",this);
		afficherJoueurs();
	}

	public void afficherJoueurs() {
		Interface.Console("liste des joueurs : ",this);
		for (int i = 0; i < getJoueurs().size(); i++) {
			Interface.Console(i + " : " + getJoueurs().get(i).getNom() + " : "
					+ getJoueurs().get(i).getGemmes() + " gemmes",this);
		}
	}

	
	// constructeur multijoueur de la partie
	public Partie(List<Client> clients, boolean partieRapide) {
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new Bibliotheque(this);

		indexJoueur = 0;

		// creation des joueurs
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < clients.size(); i++) {
			this.getJoueurs().add(
					new Joueur(clients.get(i).getNickname(), this, clients.get(i).getId(),i));
		}

		// distribution des cartes aux joueurs
		for (int i = 0; i < clients.size(); i++) {
			this.getJoueurs().get(i).piocherCartes(5);
		}
		initChaudron(partieRapide);

		/*
		 * for (int i = 0; i < clients.size(); i++) {
		 * Interface.Console("affichage de la main de " +
		 * this.getJoueurs().get(i).getNom());
		 * this.getJoueurs().get(i).getMain().afficherToutes(); }
		 * Interface.Console("affichage de la bibliotheque :");
		 * bibliotheque.getCartes().afficherToutes();
		 */
	}

	private void initChaudron(boolean partieRapide) {
		if (!partieRapide) {
			switch (joueurs.size()) {
			case 2:
				chaudron = 20;
				break;
			case 3:
				chaudron = 25;
				break;
			case 4:
				chaudron = 30;
				break;
			case 5:
			case 6:
				chaudron = 35;
				break;
			default:
				chaudron = 0;
				throw new IllegalStateException(
						"erreur : le nombre de joueurs est incorrect");
			}
		} else
			chaudron = 15;
		/* Interface.Console("chaudron initialisé à : " + this.getChaudron()); */
	}

	

	public void piocherDansLeChaudron(int nbrDeGemmes) {
		setChaudron(chaudron - nbrDeGemmes);
		Interface.Console("Il reste " + chaudron + " gemmes dans le chaudron",this);
		if (chaudron <= 0) {
			finDuJeu();
		}
	}

	// GETTERS & SETTERS ********************
	public int getJoueurJouant() {
		return indexJoueur;
	}
	public int getJoueurByID(int ID)
	{
		for(int i=0;i<getJoueurs().size();i++)
		{
			if(getJoueurs().get(i).getId()==ID)
				return i;
		}
		return -1;
	}

	public int getChaudron() {
		return chaudron;
	}

	public void setChaudron(int chaudron) {
		this.chaudron = chaudron;
	}

	public Bibliotheque getBibliotheque() {
		return bibliotheque;
	}

	public void setBibliotheque(Bibliotheque bibliotheque) {
		Partie.bibliotheque = bibliotheque;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Joueur> joueurs) {
		Partie.joueurs = joueurs;
	}

	public PileDeCartes getAireDeJeu() {
		return aireDeJeu;
	}

	public void setAireDeJeu(PileDeCartes aireDeJeu) {
		Partie.aireDeJeu = aireDeJeu;
	}

	public boolean ajouterAAireDeJeu(Carte carte) {
		if (aireDeJeu.tailleDeLaPile() < 1) {
			if (carte.getType() == CarteType.hocus) {
				aireDeJeu.ajouterUneCarte(carte);
				return true;
			} else {
				Interface
						.Console("La premiere carte jouée doit être une HOCUS",this);
				return false;
			}
		} else {
			if (carte.getType() == CarteType.pocus) {
				aireDeJeu.ajouterUneCarte(carte);
				return true;
			} else {
				Interface.Console("Une seule carte Hocus à la fois",this);
				return false;
			}
		}

	}

	public void jouerLesCartesDeLaireDeJeu() {		
		while (aireDeJeu.tailleDeLaPile() > 1) {
			Carte currentCarte = aireDeJeu.tirerUneCarte();
			currentCarte.action();
		}		
		Carte hocus = this.getAireDeJeu().getPileDeCarte().get(0);
		hocus.action();
		
		if(!("Hibou".equals(hocus.getNom()))
				&&!("Malediction".equals(hocus.getNom()))){
				setAireDeJeu(new PileDeCartes());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		toutesLesInfos();
	}

	public PileDeCartes getDefausse() {
		return defausse;
	}

	public void setDefausse(PileDeCartes defausse) {
		Partie.defausse = defausse;
	}

	// ----------Communication client / serveur --------------
	// le serveur demande le cartes du grimoire d'un joueur
	public void demanderCartesDuGrimoire(int numJoueurGrimoire,
			int numJoueurQuiChoisi, int nbrCartes) {
		JSONObject grosJson = new JSONObject();
		try {
			grosJson.put("methode", "demandeCartesDuGrimoire");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			grosJson.put("nbrCartes", nbrCartes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			grosJson.put("numJoueurQuiChoisi", numJoueurQuiChoisi);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			grosJson.put("numJoueurGrimoire", numJoueurGrimoire);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONArray grim = this.getJoueurs().get(numJoueurGrimoire).getGrimoire()
				.toJson();

		try {
			grosJson.put("grim", grim);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Joueur j=getJoueurs().get(numJoueurQuiChoisi);
		Interface.Jeu(grosJson.toString(), j.getId(),this);

//		Message msg = new Message(MessageType.Jeu,
//				"{ methode:reponseCartesDuGrimoire;" +
//						" numJoueurVise: "+ numJoueurGrimoire + ";" +
//						" numJoueur:"+ numJoueurQuiChoisi + ";" +
//						" grimoire:" + "[0,1]"+
//						"}", numJoueurQuiChoisi);
//		Interface.gestionMessage(msg);
	}

	// reponse du client
	public void reponseCartesDuGrimoire(JSONArray carteArr, int joueurGrimoire) {
		PileDeCartes pile = this.getAireDeJeu();
		int taille = pile.tailleDeLaPile();
		Stack<Carte> pileCarte = pile.getPileDeCarte();
		Carte carteHocus = pileCarte.get(0);
		String strComp = carteHocus.getNom();
		if ("Hibou".equals(strComp)) {
			// mettre les carte choisies dans la main du joueurjouant
			for (int numCarteGrim = 0; numCarteGrim < carteArr.length(); numCarteGrim++) {
				Carte carteChoisie = this.getJoueurs().get(joueurGrimoire)
						.getGrimoire().enleverCarte(numCarteGrim);
				this.getJoueurs().get(getJoueurJouant()).getMain()
						.ajouterUneCarte(carteChoisie);
			}
			//this.getJoueurs().get(joueurGrimoire).demandeCompleterGrimoire();
		}
		else if ("Malediction".equals(strComp)) {
			// defausser les carte choisies par joueurjouant
			for (int numCarteGrim = 0; numCarteGrim < carteArr.length(); numCarteGrim++) {
				this.getJoueurs().get(joueurGrimoire).getGrimoire()
						.enleverCarte(numCarteGrim);
			}
			//this.getJoueurs().get(joueurGrimoire).demandeCompleterGrimoire();
		}
		setAireDeJeu(new PileDeCartes());
		toutesLesInfos();
		//tourDeJeu();
	}

	// le serveur envois toutes les infos relative à la partie
	public void toutesLesInfos() {
		for (int numJoueur = 0; numJoueur < getJoueurs().size(); numJoueur++) {
			JSONObject grosJson = new JSONObject();
			try {
				grosJson.put("methode", "toutesLesInfos");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage(),this);
			}
			try {
				grosJson.put("chaudron", getChaudron());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage(),this);
			}
			try {
				grosJson.put("joueurEnCour", getJoueurJouant());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage(),this);
			}
			JSONArray arr = new JSONArray();
			int n = 0;
			for (Joueur j : getJoueurs()) {
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
					Interface.Error(e.getMessage(),this);
				}
			}

			try {
				grosJson.put("joueurs", arr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage(),this);
			}

			try {
				grosJson.put("aireDeJeu", getAireDeJeu().toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage(),this);
			}
			Joueur j=joueurs.get(numJoueur);

			Interface.Jeu(grosJson.toString(), j.getId(),this);	
		}
		Joueur joueurEnCours = joueurs.get(indexJoueur);
		JsonObject json = new JsonObject();
		json.addProperty("methode", "joueurEnCour");
		Interface.Jeu(json.toString(), joueurEnCours.getId(),this);
		Interface.Console("C'est le tour de " + joueurEnCours.getNom(),this);
	}



	// informer le serveur quelle carte a été jouée par quel joueur
	public void carteJouee(int numJoueur, int numDansSaMain) {
		// protection de la donnée
		if (numJoueur <= getJoueurs().size()) {
			Carte carteJouee;
			Joueur joueur = getJoueurs().get(numJoueur);
			boolean duGrimoire;
			if (numDansSaMain < 10) {
				carteJouee = joueur.getMain().getPileDeCarte()
						.get(numDansSaMain);
				duGrimoire = false;
			} else {// 10 11 12 = les cartes du grimoire
				numDansSaMain -= 10;
				carteJouee = joueur.getGrimoire().getPileDeCarte()
						.get(numDansSaMain);
				duGrimoire = true;
			}

			// on vérifie que c'est bien à lui de jouer +hocus/pocus
			if (carteJouee.getType() == CarteType.pocus) {//POCUS
				if ("Amulette".equals(carteJouee.getNom())) {
					if (this.getAireDeJeu().getPileDeCarte().get(0).isJevise()) {
						if (this.getAireDeJeu().getPileDeCarte().get(0).getJoueurVise() != joueur) {
							Interface.Console("La carte doit être ciblée contre vous",this);
							return;//pas ok : on sort de la fonction
						} else {// c'est ok: on sort du if et on joue la carte
						}
					} else{//pas ok : on sort de la fonction
						Interface.Console("La carte doit être ciblée contre vous",this);
						return;
					}
				}
				joueur.jouerCarte(carteJouee, duGrimoire, numJoueur);
				lancerChrono();
			} else if (numJoueur == getJoueurJouant()) {//HOCUS
				// if (!carteJouee.getNom().equals("Sortilege"))// on lance le
				// // chrono et on
				// // joue la carte
				// // sortilege que
				// // quand on a
				// // vise
				// {
				
				joueur.jouerCarte(carteJouee, duGrimoire, numJoueur);
				if(!carteJouee.isJevise())
					lancerChrono();
				// }
			} else
				Interface.Error("Carte interdite de jouer",this);
		}
	}

	// le serveur demande au client de viser
	public void viserUnJoueur(int numJoueurVisant) {
		JSONObject grosJson = new JSONObject();
		try {
			grosJson.put("methode", "viserJoueur");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage(),this);
		}
		try {
			grosJson.put("numJoueurVisant", numJoueurVisant);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage(),this);
		}
		JSONArray arr = new JSONArray();
		for (Joueur j : this.getJoueurs()) {
			arr.put(j.toJson());
		}
		try {
			grosJson.put("joueurs", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage(),this);
		}
		Joueur j = getJoueurs().get(indexJoueur);
		Interface.Jeu(grosJson.toString(), j.getId(),this);
	}

	// le client repond quel joueur est visé
	public void joueurVise(int numJoueur, int numJoueurVise) {
		// on vérifie l'auteur du message
		if (numJoueur == getJoueurJouant()) {
			Joueur jVise = getJoueurs().get(numJoueurVise);
			Interface.Console(getJoueurs().get(numJoueur).getNom() + " vise "
					+ jVise.getNom(),this);
			JsonObject json = new JsonObject();
			json.addProperty("methode", "jeSuisVise");
			Interface.Jeu(json.toString(), jVise.getId(),this);
			getAireDeJeu().getPileDeCarte().get(0).setJoueurVise(jVise);
			lancerChrono();
		}
	}

	// le client informe le serveur la fin de la carte hocus
	public void finCarteHocus() {

		jouerLesCartesDeLaireDeJeu();
		tourDeJeu();
	}

	private void lancerChrono() {
		JsonObject json = new JsonObject();
		json.addProperty("methode", "lancerChrono");
		Interface.Jeu(json.toString(),this);
		// nouvelle carte on reset le timer
		if (timerFinCarte != null)
			timerFinCarte.cancel();
		timerFinCarte = new Timer();
		timerFinCarte.schedule(new TimerTaskFinTour(), 9000);
	}

	// dès qu'une carte est jouée nous avons un certain temps pour en poser
	// d'autres sinon tour suivant!
	class TimerTaskFinTour extends TimerTask {
		public void run() {
			timerFinCarte.cancel(); // Terminate the timer thread
			finCarteHocus();
		}
	}
}
