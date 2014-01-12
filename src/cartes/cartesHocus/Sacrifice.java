package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import partie.Joueur;
import partie.Partie;

public class Sacrifice extends Hocus{
	
	private Joueur joueurVise;
	
	public Sacrifice(Partie partie){
		super(partie);
		super.setNom("Sacrifice");
		super.setDescription("Désignez un joueur pour qu'il remette deux gemmes dans le chaudron.");
	}
	
	@Override
	public void jouerLaCarte(){
		getPartie().viserUnJoueur(this.getPartie().getJoueurJouant());		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int gemmesRemises = joueurVise.perdreDesGemmes(2);
			getPartie().setChaudron(getPartie().getChaudron() + gemmesRemises);
			
			Interface.Console(joueurVise.getNom() +" remets "+ gemmesRemises +" gemmes dans le chaudron");
		}	
	}

	public Joueur getJoueurVise() {
		return joueurVise;
	}

	public void setJoueurVise(Joueur joueurVise) {
		this.joueurVise = joueurVise;
	}	
			
}
