package cartes.cartesPocus;

import partie.Partie;
import cartes.cartesHocus.Hocus;

public class BaguetteMagique extends Pocus {

	private Hocus carteVisee;

	public Hocus getCarteVisee() {
		return carteVisee;
	}

	public void setCarteVisee(Hocus carteVisee) {
		this.carteVisee = carteVisee;
	}

	public BaguetteMagique(Partie partie) {
		super(partie);
		super.setNom("Baguette Magique");
		super.setDescription("Doublez la valeur d'une carte HOCUS");		
	}
		
	@Override
	public void jouerLaCarte(Hocus visee){
//		Hocus visee = super.viserUneCarteHocus();
				
		if (visee.getForce() != 0)
			setCarteVisee(visee);
		else
			System.out.println("La carte hocus en question ne peu pas etre cible d'une Baguette Magique.");
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int force = carteVisee.getForce();
			carteVisee.setForce(force*2);
		}
	}
}
