package cartes.cartesPocus;

import partie.Partie;
import cartes.Carte;
import cartes.cartesHocus.Hocus;

public class BaguetteMagique extends Pocus {

	private Carte carteVisee;

	public Carte getCarteVisee() {
		return carteVisee;
	}

	public void setCarteVisee(Carte visee) {
		this.carteVisee = visee;
	}

	public BaguetteMagique(Partie partie) {
		super(partie);
		super.setNom("Baguette Magique");
		super.setDescription("Doublez la valeur de la carte HOCUS");		
	}
		

	public void jouerLaCarte(){
		Carte visee = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);		
		
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
			System.out.println("Baguette magique : "+ carteVisee);
		}
	}
}
