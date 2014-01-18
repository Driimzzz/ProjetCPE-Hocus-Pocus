package cartes.cartesPocus;

import partie.Partie;
import cartes.Carte;

public class Pocus extends Carte{
	
	protected Pocus(Partie partie) {
		super(partie);
		super.setType(CarteType.pocus);
	}
	
}
