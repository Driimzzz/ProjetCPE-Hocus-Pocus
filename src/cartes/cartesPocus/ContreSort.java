package cartes.cartesPocus;

import partie.Partie;
import cartes.Carte;

public class ContreSort extends Pocus{
	
	private Carte carteVisee;
	
	public Carte getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public ContreSort(Partie partie){	
		super(partie);
		super.setNom("Contre Sort");
		super.setDescription("Détruisez la carte HOCUS");
	}
	
	@Override
	public void jouerLaCarte(){
		Carte visee = super.viserUneCarteHocus();
		//TODO demander de viser un joueur
		this.setCarteVisee(visee);
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			carteVisee.setEstValide(false);
			System.out.println("La carte "+carteVisee+" est détruite");
		}		
	}
}
