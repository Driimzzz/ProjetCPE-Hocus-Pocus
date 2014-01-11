package partie;

import cartes.*;
import cartes.cartesHocus.Sortilege;
import cartes.cartesHocus.Voleur;
import cartes.cartesPocus.BaguetteMagique;
import cartes.cartesPocus.ContreSort;

public class Bibliotheque {
	private PileDeCartes cartes;
	
	private Partie partie;

	public Bibliotheque(Partie maPartie){ //constructeur de la bilbiotheque qui initialise le paquet de cartes
		this.cartes = new PileDeCartes();
		
		//initialisations cartes voleurs
		for (int i=0;i<5;i++) //5 cartes voleur 1
			this.cartes.ajouterUneCarte(new Voleur(1,maPartie));
		for (int i=0;i<4;i++) //4 cartes voleur 2
			this.cartes.ajouterUneCarte(new Voleur(2,maPartie));
		for (int i=0;i<3;i++) //3 cartes voleur 3
			this.cartes.ajouterUneCarte(new Voleur(3,maPartie));
		for (int i=0;i<2;i++) //2 cartes voleur 4
			this.cartes.ajouterUneCarte(new Voleur(4,maPartie));
		//1 carte voleur 5
		this.cartes.ajouterUneCarte(new Voleur(5,maPartie));
		
		//init cartes Sortilege
		for (int i=0;i<5;i++) //5 cartes Sortilege 1
			this.cartes.ajouterUneCarte(new Sortilege(1,maPartie));
		for (int i=0;i<4;i++) //4 cartes Sortilege 2
			this.cartes.ajouterUneCarte(new Sortilege(2,maPartie));
		for (int i=0;i<3;i++) //3 cartes Sortilege 3
			this.cartes.ajouterUneCarte(new Sortilege(3,maPartie));
		for (int i=0;i<2;i++) //2 cartes Sortilege 4
			this.cartes.ajouterUneCarte(new Sortilege(4,maPartie));
		//1 carte Sortilege 5
		this.cartes.ajouterUneCarte(new Sortilege(5,maPartie));
		
		//4 baguettes magiques
		for (int i=0;i<4;i++) 
			this.cartes.ajouterUneCarte(new BaguetteMagique(maPartie));
		
		//2 contre-sorts
		for (int i=0;i<4;i++) 
			this.cartes.ajouterUneCarte(new ContreSort(maPartie));
		
		this.cartes.melanger();
	}
	
	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}
	
	public PileDeCartes getCartes() {
		return cartes;
	}

	public void setCartes(PileDeCartes cartes) {
		this.cartes = cartes;
	}
	
}