package partie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cartes.*;
import cartes.Carte.CarteType;

public class Partie {
	private int chaudron;// le nb de gemmes dans le chaudron

	static private List<Joueur> joueurs;
	static Bibliotheque bibliotheque;
		
	
	static private PileDeCartes aireDeJeu;
	static private PileDeCartes defausse;

	public void tourDeJeu(Joueur joueurEnCours){
		System.out.println("c'est le tour de "+ joueurEnCours.getNom());
		chaudron--;
	}
	
	//la fonction qui alterne les tours de jeu entre les joueurs
	public void jeu() {
		int indexJoueur = 0;
		while(chaudron>0){
			tourDeJeu(joueurs.get(indexJoueur));
			if(indexJoueur==joueurs.size()-1)
				indexJoueur = 0;
			else
				indexJoueur++;
		}
		//TODO creer une fonction qui classe les joueurs en fonction de leur nombre de gemmes et afficher les scores.
	}
	// constructeur de la partie
	public Partie(int nbJoueurs, String[] nomsJoueurs, boolean partieRapide) { 
		System.out.println("construction partie : ");
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new Bibliotheque(this);
		
		initJoueurs(nbJoueurs, nomsJoueurs);
		initChaudron(partieRapide); 
		
		for (int i=0;i<nbJoueurs;i++){
			System.out.println("affichage de la main de " + this.getJoueurs().get(i).getNom());
			this.getJoueurs().get(i).getMain().afficherToutes();
		}
		System.out.println("affichage de la bibliotheque :");
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
		} 
		else
			chaudron = 15;
		System.out.println("chaudron initialisé à : "+ this.getChaudron());
	}

	private void initJoueurs(int nbJoueurs, String[] nomsJoueurs) {
		
		//creation des joueurs
		joueurs = new ArrayList<Joueur>();
		for (int i=0;i<nbJoueurs;i++){
			this.getJoueurs().add(new Joueur(nomsJoueurs[i],this));
		}
		
		//distribution des cartes aux joueurs
		for (int i=0;i<nbJoueurs;i++){
			this.getJoueurs().get(i).piocherCartes(5);
		}
		
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

	public void ajouterAAireDeJeu(Carte carte){
		if(aireDeJeu.tailleDeLaPile()<1)
		{
			if(carte.getType() == CarteType.hocus)
				aireDeJeu.ajouterUneCarte(carte);
			else
				System.out.println("La premiere carte jouée doit être une HOCUS");
		}
		else
		{
			if(carte.getType() == CarteType.pocus)
				aireDeJeu.ajouterUneCarte(carte);
			else
				System.out.println("Une seule carte Hocus à la fois");
		}
			
	}
	
	public void jouerLesCartesDeLaireDeJeu()
	{
		while(aireDeJeu.tailleDeLaPile()>0){
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
