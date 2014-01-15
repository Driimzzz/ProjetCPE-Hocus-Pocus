<<<<<<< HEAD
package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import partie.Joueur;
import partie.Partie;

public class Sacrifice extends Hocus{
	
	private Joueur joueurVise;
	
	public Sacrifice(Partie partie){
		super(partie);
		super.setNom("Sacrifice");
		super.setDescription("D�signez un joueur pour qu'il remette deux gemmes dans le chaudron.");
		super.setJevise(true);
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
			
			Interface.Console(joueurVise.getNom() +" remets "+ gemmesRemises +" gemmes dans le chaudron",this.getPartie());
		}	
	}

	public Joueur getJoueurVise() {
		return joueurVise;
	}

	public void setJoueurVise(Joueur joueurVise) {
		this.joueurVise = joueurVise;
	}	
			
}
=======
package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import partie.Joueur;
import partie.Partie;

public class Sacrifice extends Hocus{
	
	private Joueur joueurVise;
	
	public Sacrifice(int force, Partie partie){
		super(partie);
		super.setNom("Sacrifice");
		super.setDescription("D�signez un joueur pour qu'il remette des gemmes dans le chaudron.");
		super.setForce(force);
		super.setJevise(true);
	}
	
	@Override
	public void jouerLaCarte(){
		getPartie().viserUnJoueur(this.getPartie().getJoueurJouant());		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int gemmesRemises = joueurVise.perdreDesGemmes(this.getForce());
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
>>>>>>> 7ea0710b91d107d45f8f59e4a4fe7b297b1947ee
