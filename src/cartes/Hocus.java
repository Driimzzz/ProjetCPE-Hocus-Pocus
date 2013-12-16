package cartes;


public class Hocus extends Carte{
		
	private int force; //valeur du pouvoir
	
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
	
	protected Hocus(){
		super();
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
