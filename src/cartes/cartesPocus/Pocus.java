package cartes.cartesPocus;

import partie.Partie;
import cartes.Carte;
import cartes.cartesHocus.Hocus;
import cartes.cartesHocus.Sortilege;

public class Pocus extends Carte{
	
	protected Pocus(Partie partie) {
		super(partie);
		super.setType(CarteType.pocus);
	}
	
	protected Hocus viserUneCarteHocus(){
		//TODO choisir une carte à visée
		return (new Sortilege(4,getPartie()));		//pour tester
	}
	
}
