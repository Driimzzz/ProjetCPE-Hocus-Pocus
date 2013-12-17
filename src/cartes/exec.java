package cartes;

import cartes.Carte.CarteType;
import cartes.cartesHocus.Hocus;
import cartes.cartesHocus.Sortilege;
import cartes.cartesHocus.Voleur;
import cartes.cartesPocus.BaguetteMagique;
import cartes.cartesPocus.ContreSort;
import cartes.cartesPocus.Pocus;
import cartes.PileDeCartes;

public class exec {

	public static void main(String[] args) {
		PileDeCartes pile = new PileDeCartes();
			
		pile.ajouterUneCarte(new Sortilege(2));
		pile.ajouterUneCarte(new Voleur());

		pile.ajouterUneCarte(new BaguetteMagique());
		pile.ajouterUneCarte(new Sortilege(5));
		
		pile.ajouterUneCarte(new ContreSort());
		pile.ajouterUneCarte(new Voleur(3));
		

		pile.melanger();

		pile.afficherToutes();

		System.out.println("\n---simple jeu---");
				
		Hocus hocus = new Sortilege(4);
		
		hocus = new Voleur(3);
	    Pocus pocus = new BaguetteMagique();
	    
	    pocus.jouerLaCarte(hocus);
		pocus.action();
		hocus.action();	
		
		Carte carteHocus = new Voleur(3); 
	    pocus = new ContreSort();
	    pocus.jouerLaCarte(carteHocus);
	    pocus.action();
	    carteHocus.action();
	    

	}

}
