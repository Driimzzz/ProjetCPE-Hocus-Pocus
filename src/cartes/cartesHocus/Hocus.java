package cartes.cartesHocus;

import partie.Partie;
import cartes.Carte;

public class Hocus extends Carte{
		
	private int force; //valeur du pouvoir
	private boolean jevise;
	
	@Override
	public int getForce() {
		return force;
	}
	@Override
	public void setForce(int force) {
		this.force = force;
	}
	@Override
	public boolean isJevise() {
		return jevise;
	}
	@Override
	public void setJevise(boolean jevise) {
		this.jevise = jevise;
	}
	protected Hocus(Partie partie){
		super(partie);
		setForce(0);
		super.setType(CarteType.hocus);
	}
	
	public Hocus(String nom,String descrip){		
		super(nom,descrip);
		super.setType(CarteType.hocus);
		setForce(0);
	}
	

	@Override public String toString(){
		if(getForce() != 0)
			return super.toString() + " force "+getForce();
		else
			return super.toString();
	}

}
