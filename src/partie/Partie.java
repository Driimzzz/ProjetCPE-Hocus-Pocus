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
	
	static class bibliotheque extends Bibliotheque{
		
	}
	static private List<joueur> joueurs;
	static private bibliotheque bibliotheque;
	
	
	static class joueur extends Joueur{

		public joueur(String _nom) {
			super(_nom);
			// TODO Auto-generated constructor stub
		}
		
		public void piocherCartes(int nbCartes){
			for(int i=0;i<nbCartes;i++)
				this.getMain().ajouterUneCarte(Partie.bibliotheque.getCartes().tirerUneCarte());
			
		}
		
	}
	
	static private PileDeCartes aireDeJeu;
	static private PileDeCartes defausse;

	public Partie(int nbJoueurs, String[] nomsJoueurs, boolean partieRapide) { // constructeur de la partie
		System.out.println("construction partie : ");
		aireDeJeu = new PileDeCartes();
		defausse = new PileDeCartes();

		bibliotheque = new bibliotheque();
		
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
		joueurs = new ArrayList<joueur>();
		for (int i=0;i<nbJoueurs;i++){
			this.getJoueurs().add(new joueur(nomsJoueurs[i]));
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

	public void setBibliotheque(partie.Partie.bibliotheque bibliotheque) {
		Partie.bibliotheque = bibliotheque;
	}

	public List<joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<joueur> joueurs) {
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
