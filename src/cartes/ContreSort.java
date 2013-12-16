package cartes;

public class ContreSort extends Pocus{
	
	private Hocus carteVisee;
	
	public Hocus getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Hocus carteVisee) {
		this.carteVisee = carteVisee;
	}

	public ContreSort(){	
		super();
		super.setNom("Contre Sort");
		super.setDescription("Détruisez la carte HOCUS");
	}
	
	public void jouerLaCarte(){
		Hocus visee = super.viserUneCarteHocus();
		this.setCarteVisee(visee);
	}
	
	public void action() {
		if(this.estValide)
			carteVisee.estValide = false;
	}
}
