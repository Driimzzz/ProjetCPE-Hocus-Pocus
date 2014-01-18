package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import partie.Joueur;
import partie.Partie;

public class Voleur extends Hocus{
	
	public Voleur(int force,Partie partie){
		super(partie);
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
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
			int gemmesVolees = getJoueurVise().perdreDesGemmes(this.getForce());
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesVolees);
			
			Interface.Console(joueurJouant.getNom() +" vole "+gemmesVolees+" gemmes à "+getJoueurVise().getNom(),this.getPartie());
		}	
	}

}