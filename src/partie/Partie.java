package partie;

import interfaceclientserveur.Interface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import websocket.console.Client;
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
		Joueur joueurEnCours = joueurs.get(indexJoueur);
		Interface.Console("c'est le tour de " + joueurEnCours.getNom());

		if (joueurEnCours.getMain().getPileDeCarte().size() >= 6) {
			if (!(joueurEnCours.getMain().getPileDeCarte().size() < 1))
				Interface.demandeAction(true,false);
			else
				Interface.demandeAction(false,false);
		}else{
			if (!(joueurEnCours.getMain().getPileDeCarte().size() < 1))
				Interface.demandeAction(true,true);
			else
				Interface.demandeAction(false,true);
		}

		// int numCarte = -2;
		// while (numCarte!=-1 && chaudron > 0 &&
		// joueurEnCours.getMain().tailleDeLaPile() > 0){ //tant qu'on veut
		// jouer et qu'on a des cartes
		// while(numCarte<-1 ||
		// numCarte>joueurEnCours.getMain().tailleDeLaPile()-1){ //saisie
		// protegee
		// joueurEnCours.getMain().afficherToutes();
		// Interface.Console("quelle carte jouer ? (-1 pour ne rien jouer)");
		// numCarte = readIntValue();
		// }
		// if(numCarte>=0){
		// joueurEnCours.jouerCarte(joueurEnCours.getMain().getPileDeCarte().elementAt(numCarte));
		// numCarte = -2;
		// }
		//
		// jouerLesCartesDeLaireDeJeu();
		// Interface.Console("vous avez maintenant "+
		// joueurEnCours.getGemmes()+" gemmes.");
		// }
		// if(chaudron>0){
		// finDuTour(joueurEnCours);
		// }
		// else{
		// finDuJeu();
		// }
	}

	public void finDuTour(int numJoueur, int input) {
		// Interface.Console("c'est la fin de votre tour, vous pouvez : 1=piocher une gemme dans le chaudron OU 2=piocher deux cartes : ");
		// int input = -1;
		// while(input!=1 && input!=2){
		// Interface.Console("entrez 1 ou 2:");
		// input = readIntValue();
		// }

		// on vérifie l'auteur
		if (indexJoueur == numJoueur) {
			Joueur joueurEnCours = joueurs.get(indexJoueur);
			if (input == 1) {// pioche 1 gemme
				Interface.Console(joueurEnCours.getNom()
						+ " pioche 1 gemme dans le chaudron");
				joueurEnCours.setGemmes(joueurEnCours.getGemmes() + 1);
				this.piocherDansLeChaudron(1);
			} else if (input == 2) { // pioche 2 cartes
				if (joueurEnCours.getMain().getPileDeCarte().size()<6) {
					Interface.Console(joueurEnCours.getNom() + " pioche 2 cartes");
					if(6-joueurEnCours.getMain().getPileDeCarte().size()>=2)
						joueurEnCours.piocherCartes(2);
					else
						joueurEnCours.piocherCartes(6-joueurEnCours.getMain().getPileDeCarte().size());
				}
			} else {// erreur
				Interface.Console("erreur fin du tour");
			}
			Interface.Console(joueurEnCours.getNom() + " a maintenant "
					+ joueurEnCours.getGemmes() + " gemmes.");

			// passe au jouer suivant
			if (indexJoueur == joueurs.size() - 1)
				indexJoueur = 0;
			else
				indexJoueur++;
			jeu();
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
			tourDeJeu();

		} else
			finDuJeu();
	}

	public void finDuJeu() {
		Interface.Console("c'est la fin du jeu, tableau des scores : ");
		afficherJoueurs();
	}

	/*
	 * public static int readIntValue() {
	 * 
	 * do{ try { Thread.sleep(100);
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }while("".equals(Interface.input)); int
	 * nbr=Integer.parseInt(Interface.input); Interface.input=""; return nbr;
	 * 
	 * }
	 */

	public void afficherJoueurs() {
		Interface.Console("liste des joueurs : ");
		for (int i = 0; i < getJoueurs().size(); i++) {
			Interface.Console(i + " : " + getJoueurs().get(i).getNom() + " : "
					+ getJoueurs().get(i).getGemmes() + " gemmes");
		}
	}

	// constructeur non mutlijoueur de la partie
	public Partie(int nbJoueurs, String[] nomsJoueurs, boolean partieRapide) {
		Interface.Console("construction partie : ");
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new Bibliotheque(this);

		indexJoueur = 0;

		initJoueurs(nbJoueurs, nomsJoueurs);
		initChaudron(partieRapide);

		for (int i = 0; i < nbJoueurs; i++) {
			Interface.Console("affichage de la main de "
					+ this.getJoueurs().get(i).getNom());
			this.getJoueurs().get(i).getMain().afficherToutes();
		}
		Interface.Console("affichage de la bibliotheque :");
		bibliotheque.getCartes().afficherToutes();
	}

	// constructeur multijoueur de la partie
	public Partie(List<Client> clients, boolean partieRapide) {
		Interface.Console("construction partie : ");
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new Bibliotheque(this);

		indexJoueur = 0;

		// creation des joueurs
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < clients.size(); i++) {
			this.getJoueurs().add(
					new Joueur(clients.get(i).getNickname(), this));
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

	// non mutlijoueur
	private void initJoueurs(int nbJoueurs, String[] nomsJoueurs) {

		// creation des joueurs
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < nbJoueurs; i++) {
			this.getJoueurs().add(new Joueur(nomsJoueurs[i], this));
		}

		// distribution des cartes aux joueurs
		for (int i = 0; i < nbJoueurs; i++) {
			this.getJoueurs().get(i).piocherCartes(5);
		}

	}

	public void piocherDansLeChaudron(int nbrDeGemmes) {
		setChaudron(chaudron - nbrDeGemmes);
		Interface.Console("Il reste " + chaudron + " gemmes dans le chaudron");
		if (chaudron <= 0) {
			finDuJeu();
		}
	}

	// GETTERS & SETTERS ********************
	public int getJoueurJouant() {
		return indexJoueur;
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
						.Console("La premiere carte jouée doit être une HOCUS");
				return false;
			}
		} else {
			if (carte.getType() == CarteType.pocus) {
				aireDeJeu.ajouterUneCarte(carte);
				return true;
			} else {
				Interface.Console("Une seule carte Hocus à la fois");
				return false;
			}
		}

	}

	public void jouerLesCartesDeLaireDeJeu() {
		while (aireDeJeu.tailleDeLaPile() > 0) {
			Carte currentCarte = aireDeJeu.tirerUneCarte();
			currentCarte.action();
		}
		setAireDeJeu(new PileDeCartes());
		toutesLesInfos();
	}

	public PileDeCartes getDefausse() {
		return defausse;
	}

	public void setDefausse(PileDeCartes defausse) {
		Partie.defausse = defausse;
	}

	// le serveur envois toutes les infos relative à la partie
	public void toutesLesInfos() {
		for (int numJoueur = 0; numJoueur < getJoueurs().size(); numJoueur++) {
			JSONObject grosJson = new JSONObject();
			try {
				grosJson.put("methode", "toutesLesInfos");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			try {
				grosJson.put("chaudron", getChaudron());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			try {
				grosJson.put("joueurEnCour", getJoueurJouant());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
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
					Interface.Error(e.getMessage());
				}
			}

			try {
				grosJson.put("joueurs", arr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}

			try {
				grosJson.put("aireDeJeu", getAireDeJeu().toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}

			Interface.Jeu(grosJson.toString(), numJoueur);
		}
	}

	// retourner au client quel joueur doit jouer
	public void joueurEnCour() {
		int num = getJoueurJouant();
		Interface.Console("C'est le tour du joueur numero "
				+ getJoueurs().get(num).getNom());
	}

	// informer le serveur quelle carte a été jouée par quel joueur
	public void carteJouee(int numJoueur, int numDansSaMain) {
		// protection de la donnée
		if (numJoueur <= getJoueurs().size()) {

			Joueur joueur = getJoueurs().get(numJoueur);
			Carte carteJouee = joueur.getMain().getPileDeCarte()
					.get(numDansSaMain);
			// on vérifie que c'est bien à lui de jouer +hocus/pocus
			if (carteJouee.getType() == CarteType.pocus) {
				joueur.jouerCarte(carteJouee);
				lancerChrono();
			} else if (numJoueur == getJoueurJouant()) {
				joueur.jouerCarte(carteJouee);
				lancerChrono();
			} else
				Interface.Error("Carte interdite de jouer");
		}
	}

	// le serveur demande au client de viser
	public void viserUnJoueur(int numJoueurVisant) {
		JSONObject grosJson = new JSONObject();
		try {
			grosJson.put("methode", "viserJoueur");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage());
		}
		try {
			grosJson.put("numJoueurVisant", numJoueurVisant);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage());
		}
		JSONArray arr = new JSONArray();
		for (Joueur j : getJoueurs()) {
			arr.put(j.toJson());
		}
		try {
			grosJson.put("joueurs", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Interface.Error(e.getMessage());
		}

		Interface.Jeu(grosJson.toString(), getJoueurJouant());
	}

	// le client repond quel joueur est visé
	public void joueurVise(int numJoueur, int numJoueurVise) {
		// on vérifie l'auteur du message
		if (numJoueur == getJoueurJouant()) {
			Joueur jVise = getJoueurs().get(numJoueurVise);
			Interface.Console(getJoueurs().get(numJoueur).getNom() + " vise "
					+ jVise.getNom());
			getAireDeJeu().getPileDeCarte().get(0).setJoueurVise(jVise);
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
		Interface.Jeu(json.toString(), -1);
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
