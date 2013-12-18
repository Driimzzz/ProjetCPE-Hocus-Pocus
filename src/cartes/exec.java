package cartes;

import partie.Partie;
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
				    
	    System.out.println("\n---simple partie---");
	    
	    int nbJoueurs = 2;
		String[] nomJoueurs = {"Alice",
		                       "Bob"};
		boolean partieRapide = false;
		
		Carte carteHocus;
		Carte cartePocus;

		Partie partie = new Partie(nbJoueurs, nomJoueurs, partieRapide);
		carteHocus = new Sortilege(3,partie);
		partie.ajouterAAireDeJeu(carteHocus);
				
//		cartePocus = new BaguetteMagique();
//		cartePocus.jouerLaCarte(carteHocus);
//		partie.ajouterAAireDeJeu(cartePocus);
				
		cartePocus = new ContreSort(partie);
		cartePocus.jouerLaCarte(carteHocus);
		partie.ajouterAAireDeJeu(cartePocus);
				
		partie.jouerLesCartesDeLaireDeJeu();

	}

}
