package cartes;

import cartes.PileDeCartes;


public class exec {

	public static void main(String[] args) {
		PileDeCartes pile = new PileDeCartes();
		pile.ajouterUneCarte(new Sortilege(2));
		pile.ajouterUneCarte(new Sortilege(5));
		
		pile.ajouterUneCarte(new Voleur());
		pile.ajouterUneCarte(new Voleur(3));
		
				
		pile.ajouterUneCarte(new ContreSort());
		
		pile.ajouterUneCarte(new BaguetteMagique());
				
		pile.melanger();
		
		pile.afficherToutes();
		
		System.out.println("\n---simple jeu---");
		
		
		

	}

}
