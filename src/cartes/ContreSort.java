package cartes;

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
		super.setDescription("D�truisez la carte HOCUS");
	}
	
	@Override
	public void jouerLaCarte(Carte visee){
		//Hocus visee = super.viserUneCarteHocus();
		this.setCarteVisee(visee);
	}
	
	@Override
	public void action() {
		if(this.estValide)
			carteVisee.estValide = false;
	}
}
