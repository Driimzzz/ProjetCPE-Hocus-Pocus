package partie;

import interfaceclientserveur.Interface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;



import websocket.console.Client;
import cartes.*;
import cartes.Carte.CarteType;

public class Partie {
	private int chaudron;// le nb de gemmes dans le chaudron

	static private List<Joueur> joueurs;
	static Bibliotheque bibliotheque;
	private int indexJoueur;
	static private PileDeCartes aireDeJeu;
	static private PileDeCartes defausse;

	public void tourDeJeu(Joueur joueurEnCours) {
		Interface.Console("c'est le tour de " + joueurEnCours.getNom());
		Interface.Console("vous avez "+ joueurEnCours.getGemmes()+" gemmes.");
		
		int numCarte = -2;
		while (numCarte!=-1 && chaudron > 0 && joueurEnCours.getMain().tailleDeLaPile() > 0){ //tant qu'on veut jouer et qu'on a des cartes
			while(numCarte<-1 || numCarte>joueurEnCours.getMain().tailleDeLaPile()-1){	 //saisie protegee
				joueurEnCours.getMain().afficherToutes();
				Interface.Console("quelle carte jouer ? (-1 pour ne rien jouer)");
				numCarte = readIntValue();	
			}	
			if(numCarte>=0){
				joueurEnCours.jouerCarte(joueurEnCours.getMain().getPileDeCarte().elementAt(numCarte));
				numCarte = -2;
			}
			jouerLesCartesDeLaireDeJeu();
			Interface.Console("vous avez maintenant "+ joueurEnCours.getGemmes()+" gemmes.");
			
		}
		if(chaudron>0){
			finDuTour(joueurEnCours);
		}
		else{
			finDuJeu();
		}
	}

	public void finDuTour(Joueur joueurEnCours){
		Interface.Console("c'est la fin de votre tour, vous pouvez : 1=piocher une gemme dans le chaudron OU 2=piocher deux cartes : ");
		int input = -1;
		while(input!=1 && input!=2){		
			Interface.Console("entrez 1 ou 2:");
			input = readIntValue();
		}	
		if (input==1){//pioche 1 gemme
			Interface.Console("vous piochez 1 gemme dans le chaudron");
			joueurEnCours.setGemmes(joueurEnCours.getGemmes()+1);
			this.piocherDansLeChaudron(1);
			
		}
		else if (input ==2 ){ //pioche 2 cartes
			Interface.Console("vous piochez 2 cartes");
			joueurEnCours.piocherCartes(2);
		}
		else{//erreur
			Interface.Console("erreur fin du tour");
		}
		Interface.Console("vous avez maintenant "+ joueurEnCours.getGemmes()+" gemmes.");
	}
	
	// la fonction qui alterne les tours de jeu entre les joueurs
	public void jeu() {
		indexJoueur = 0;
		while (chaudron > 0) {
			tourDeJeu(joueurs.get(indexJoueur));
			if (indexJoueur == joueurs.size() - 1)
				indexJoueur = 0;
			else
				indexJoueur++;
		}
		finDuJeu();
	}
	
	public void finDuJeu(){
		Interface.Console("c'est la fin du jeu, tableau des scores : ");
		afficherJoueurs();
	}
	
	public static int readIntValue() throws InputMismatchException
	{
	    Scanner in = new Scanner(System.in);  
	    int integer;
	    Interface.Console("Enter an integer value: ");
	    integer = in.nextInt();
	    return integer;
	}
	
	public void afficherJoueurs(){
		Interface.Console("liste des joueurs : ");
		for(int i=0;i<getJoueurs().size();i++){
			Interface.Console(i+" : "+ getJoueurs().get(i).getNom() + " : " + getJoueurs().get(i).getGemmes() + " gemmes" );
		}
	}

	// constructeur non mutlijoueur de la partie
	public Partie(int nbJoueurs, String[] nomsJoueurs, boolean partieRapide) {
		Interface.Console("construction partie : ");
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new Bibliotheque(this);

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

			// creation des joueurs
			joueurs = new ArrayList<Joueur>();
			for (int i = 0; i < clients.size(); i++) {
				this.getJoueurs().add(new Joueur(clients.get(i).getNickname(), this));
			}

			// distribution des cartes aux joueurs
			for (int i = 0; i < clients.size(); i++) {
				this.getJoueurs().get(i).piocherCartes(5);
			}
			initChaudron(partieRapide);

			for (int i = 0; i < clients.size(); i++) {
				Interface.Console("affichage de la main de "
						+ this.getJoueurs().get(i).getNom());
				this.getJoueurs().get(i).getMain().afficherToutes();
			}
			Interface.Console("affichage de la bibliotheque :");
			bibliotheque.getCartes().afficherToutes();
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
		Interface.Console("chaudron initialisé à : " + this.getChaudron());
	}

	//non mutlijoueur
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
		Interface.Console("il reste " + chaudron + " gemmes dans le chaudron");
		if (chaudron <= 0){
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

	public void ajouterAAireDeJeu(Carte carte) {
		if (aireDeJeu.tailleDeLaPile() < 1) {
			if (carte.getType() == CarteType.hocus)
				aireDeJeu.ajouterUneCarte(carte);
			else
				Interface
						.Console("La premiere carte jouée doit être une HOCUS");
		} else {
			if (carte.getType() == CarteType.pocus)
				aireDeJeu.ajouterUneCarte(carte);
			else
				Interface.Console("Une seule carte Hocus à la fois");
		}

	}

	public void jouerLesCartesDeLaireDeJeu() {
		while (aireDeJeu.tailleDeLaPile() > 0) {
			Carte currentCarte = aireDeJeu.tirerUneCarte();
			currentCarte.action();
		}
		setAireDeJeu(new PileDeCartes());
	}

	public PileDeCartes getDefausse() {
		return defausse;
	}

	public void setDefausse(PileDeCartes defausse) {
		Partie.defausse = defausse;
	}

}
