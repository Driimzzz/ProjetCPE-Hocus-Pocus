package partie;

import interfaceclientserveur.Interface;
import cartes.cartesHocus.Sortilege;
import cartes.cartesHocus.Voleur;

public class execArnaud {

	public static void main(String[] args) {
				    
	    Interface.Console("\n---simple partie---");
	    
	    int nbJoueurs = 2;
		String[] nomJoueurs = {"Alice",
		                       "Bob"};
		
		
		//création d'une partie avec Alice et Bob
		Partie partie = new Partie(nbJoueurs, nomJoueurs, true);
		
//		Joueur alice = partie.getJoueurs().get(0);
//		Joueur bob = partie.getJoueurs().get(1);
		
//		cartePocus = new ContreSort(partie);
//		partie.ajouterAAireDeJeu(cartePocus);
		
		
		partie.jeu();
		

	}

}
