package cartes;

import cartes.Carte;

public class Pocus extends Carte{
	
	protected Pocus() {
		super();
		super.setType(CarteType.pocus);
	}
	
	protected Hocus viserUneCarteHocus(){
		//TODO choisir une carte à visée
		return (new Sortilege(4));		//pour tester
	}
	
}
