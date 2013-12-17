package partie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cartes.*;

public class Partie {
	private int chaudron;// le nb de gemmes dans le chaudron
	private Bibliotheque bibliotheque;
	private List<Joueur> joueurs;
	private PileDeCartes aireDeJeu;
	private PileDeCartes defausse;

	public Partie(int nbJoueurs, String[] nomsJoueurs, boolean partieRapide) { // constructeur de la partie
		System.out.println("construction partie : ");
		this.initJoueurs(nbJoueurs, nomsJoueurs);
		this.initBiblio();
		this.initChaudron(partieRapide); 

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
		} 
		else
			chaudron = 15;
		System.out.println("chaudron initialisé à : "+ this.getChaudron());
	}

	private void initJoueurs(int nbJoueurs, String[] nomsJoueurs) {
		this.joueurs = new ArrayList<Joueur>();
		for (int i=0;i<nbJoueurs;i++){
			this.getJoueurs().add(new Joueur(nomsJoueurs[i]));
		}
	}

	private void initBiblio() {

	}

	// GETTERS & SETTERS ********************

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
		this.bibliotheque = bibliotheque;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public PileDeCartes getAireDeJeu() {
		return aireDeJeu;
	}

	public void setAireDeJeu(PileDeCartes aireDeJeu) {
		this.aireDeJeu = aireDeJeu;
	}

	public PileDeCartes getDefausse() {
		return defausse;
	}

	public void setDefausse(PileDeCartes defausse) {
		this.defausse = defausse;
	}

}
