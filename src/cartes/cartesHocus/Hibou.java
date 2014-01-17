package cartes.cartesHocus;

import partie.Joueur;
import partie.Partie;

public class Hibou extends Hocus{

//	private Joueur joueurVise;
	
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
	}
	
//	public Joueur getJoueurVise() {
//		return joueurVise;
//	}
//
//	@Override
//	public void setJoueurVise(Joueur joueurVise) {
//		this.joueurVise = joueurVise;		
//	}	

}
