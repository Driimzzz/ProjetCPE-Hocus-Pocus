package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Partie;
import cartes.Carte;

public class Amulette extends Pocus{
	
	private Carte carteVisee;
	
	public Carte getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public Amulette(Partie partie){	
		super(partie);
		super.setNom("Amulette");
		super.setDescription("Détruisez la carte HOCUS ciblée contre vous");
	}
	
	@Override
	public void jouerLaCarte(){
		Carte visee = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);		
		this.setCarteVisee(visee);
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			carteVisee.setEstValide(false);
			Interface.Console("La carte "+carteVisee+" est détruite");
		}		
	}
}
