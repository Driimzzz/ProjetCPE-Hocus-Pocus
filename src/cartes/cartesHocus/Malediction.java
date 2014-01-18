package cartes.cartesHocus;

import partie.Partie;

public class Malediction extends Hocus{
	
	public Malediction(int force, Partie partie) {
		super(partie);
		super.setNom("Malediction");
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
	}
}
