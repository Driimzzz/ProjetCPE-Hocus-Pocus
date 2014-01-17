package cartes.cartesHocus;

import partie.Joueur;
import partie.Partie;

public class BouleDeCristal extends Hocus{

//	private Joueur joueurVise;
	
	public BouleDeCristal(Partie partie) {
		super(partie);
		super.setNom("BouleDeCristal");
		super.setDescription("Regardez les 4 premieres cartes de la bibliotheque et remettez les dans l'ordre de votre choix.");
	}
	
	@Override
	public void jouerLaCarte(){		
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int numJoueurQuiChoisi=getPartie().getJoueurJouant();
			getPartie().demanderCartesBouleDeCristal(numJoueurQuiChoisi,4);
		}
	}
}
