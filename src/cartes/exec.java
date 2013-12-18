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
		
		partie.getJoueurs().get(1).setGemmes(5);
		
		carteHocus = new Voleur(6, partie);
		partie.ajouterAAireDeJeu(carteHocus);
				
		partie.jouerLesCartesDeLaireDeJeu();

	}

}
