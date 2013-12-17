package cartes.cartesPocus;

import cartes.Carte;

public class ContreSort extends Pocus{
	
	private Carte carteVisee;
	
	public Carte getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public ContreSort(){	
		super();
		super.setNom("Contre Sort");
		super.setDescription("Détruisez la carte HOCUS");
	}
	
	@Override
	public void jouerLaCarte(Carte visee){
		//Hocus visee = super.viserUneCarteHocus();
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
