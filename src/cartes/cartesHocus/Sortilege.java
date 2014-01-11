package cartes.cartesHocus;

import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;

public class Sortilege extends Hocus{

	public Sortilege(int force,Partie partie){	
		super(partie);
		super.setNom("Sortilege");
		super.setDescription("Piochez des gemmes dans le chaudron");
		super.setForce(force);
		
	}
		
	public void action() {
		if(isValide())
		{
			this.getPartie().piocherDansLeChaudron(getForce());
			
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+this.getForce());
			Interface.Console(joueurJouant.getNom() + "lance un sortilege "+this.getForce()+" contre le chaudron");
		}
	}
	
}
