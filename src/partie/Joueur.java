package partie;

import interfaceclientserveur.Interface;
import cartes.Carte;

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
		Interface.Console("joueur créé : " + this.getNom());
	}
	
	public void jouerCarte(Carte carteJouee){
		this.getMain().getPileDeCarte().remove(carteJouee);
		carteJouee.jouerLaCarte();
		this.getPartie().ajouterAAireDeJeu(carteJouee);
	}
	
	
	//
	public void piocherCartes(int nbCartes){
		for(int i=0;i<nbCartes;i++)
			this.getMain().ajouterUneCarte(getPartie().getBibliotheque().getCartes().tirerUneCarte());
		
	}
	
	//Le joueur perds des gemmes
	public int perdreDesGemmes(int nbrVoles){
		if(gemmes-nbrVoles<0)
			nbrVoles = gemmes;
		gemmes -= nbrVoles;		
		return nbrVoles ;
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
