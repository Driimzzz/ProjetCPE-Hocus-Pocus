package cartes;

import interfaceclientserveur.Interface;
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
		
		Interface.gestionMessage("{methode:creerJeu;"
				+ "joueurs:[Alice,Bob,Claire]"
				+ "}");		
		
		Partie p = Interface.getPartie();
		
		p.getJoueurs().get(1).setGemmes(5);
		
		Carte carteHocus;
		carteHocus = new Voleur(3, p);
		p.getJoueurs().get(0).getMain().getPileDeCarte().set(1, carteHocus);
		
		Interface.setPartie(p);
		
		Interface.gestionMessage("{methode:carteJouee;numJoueur:0;numCarte:1}");
		
		Interface.gestionMessage("{methode:joueurVise;numJoueurVise:1}");
		
		Interface.gestionMessage("{methode:finCarteHocus}");
		
//				    
//		Interface.Console("\n---simple partie---");
//	    
//	    int nbJoueurs = 2;
//		String[] nomJoueurs = {"Alice",
//		                       "Bob"};
//		
//		boolean partieRapide = false;
//		
//		Carte carteHocus;
//		Carte cartePocus;
//		
//		//création d'une partie avec Alice et Bob
//		Partie partie = new Partie(nbJoueurs, nomJoueurs, partieRapide);
//		
//		//ajout de 5 gemmes a bob
//		partie.getJoueurs().get(1).setGemmes(5);
//		
//		carteHocus = new Voleur(3, partie);
//		partie.ajouterAAireDeJeu(carteHocus);
//		
////		carteHocus = new Sortilege(3, partie);
////		partie.ajouterAAireDeJeu(carteHocus);
////		
////		cartePocus = new BaguetteMagique(partie);
////		partie.ajouterAAireDeJeu(cartePocus);
//		
////		cartePocus = new ContreSort(partie);
////		partie.ajouterAAireDeJeu(cartePocus);
//		
//		partie.jouerLesCartesDeLaireDeJeu();
		
	}

}
