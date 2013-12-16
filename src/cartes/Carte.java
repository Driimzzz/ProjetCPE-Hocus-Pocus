package cartes;



public class Carte {
	
	enum CarteType {hocus,pocus};
	
	private CarteType type;
	
	private String nom;
	private String description;
	
	protected boolean estValide;
	
	protected Carte(){		
		estValide = true;
	}

	public Carte(String nom, String descrp)
	{
		setNom(nom);
		setDescription(descrp);
	}
	
	
	public CarteType getType() {
		return type;
	}
	public void setType(CarteType type) {
		this.type = type;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override public String toString(){
		return getType() +"-->"+ getNom() +" : "+getDescription();
	}

	public void jouerLaCarte() {
	}
	
	public void action() {}

	public void jouerLaCarte(Carte carte) {
	}
	
}
