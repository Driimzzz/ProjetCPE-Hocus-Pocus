package partie;

public class Joueur {
	private int gemmes;
	private Main main;
	private Grimoire grimoire;
	private String nom;
	private Partie partie;
	
	
	public Joueur(String _nom,Partie maPartie){
		this.setPartie(maPartie);
		this.setNom(_nom);
		this.setGemmes(0);
		this.setGrimoire(new Grimoire());
		this.setMain(new Main());
		System.out.println("joueur créé : " + this.getNom());
	}
	
	public void piocherCartes(int nbCartes){
		for(int i=0;i<nbCartes;i++)
			this.getMain().ajouterUneCarte(getPartie().bibliotheque.getCartes().tirerUneCarte());
		
	}
	
	
	
	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public int getGemmes() {
		return gemmes;
	}
	public void setGemmes(int gemmes) {
		this.gemmes = gemmes;
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public Grimoire getGrimoire() {
		return grimoire;
	}
	public void setGrimoire(Grimoire grimoire) {
		this.grimoire = grimoire;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

}
