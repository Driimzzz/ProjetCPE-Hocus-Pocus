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
		super.setJevise(false);
		
	}
		
	public void action() {
		if(isValide())
		{
			int gemmesPiochees = this.getPartie().piocherDansLeChaudron(getForce());
			
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesPiochees);
			Interface.Console(joueurJouant.getNom() + " lance un sortilege "+gemmesPiochees+" contre le chaudron",this.getPartie());
			if(getPartie().getChaudron() == 0)
				getPartie().finDuJeu();
		}
	}
	
}
