package cartes.cartesHocus;

import cartes.PileDeCartes;
import partie.Partie;

public class Hibou extends Hocus{
	
	public Hibou(int force, Partie partie) {
		super(partie);
		super.setNom("Hibou");
		super.setDescription("Piochez des cartes dans le grimoire d'un joueur désigné");
		super.setForce(force);
		super.setJevise(true);
	}
	
	@Override
	public void jouerLaCarte(){		
		getPartie().viserUnJoueur(this.getPartie().getJoueurJouant());
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int numJoueurGrimoire = getJoueurVise().getPositionPartie();
			int numJoueurQuiChoisi = getPartie().getJoueurJouant();
			getPartie().demanderCartesDuGrimoire(numJoueurGrimoire, numJoueurQuiChoisi, this.getForce());
		}
		else
			getPartie().setAireDeJeu(new PileDeCartes());
	}

}
