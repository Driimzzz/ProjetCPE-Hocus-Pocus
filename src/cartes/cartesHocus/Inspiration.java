<<<<<<< HEAD
package cartes.cartesHocus;

import cartes.Carte;
import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;

public class Inspiration extends Hocus{

	public Inspiration(int force,Partie partie){	
		super(partie);
		super.setNom("Inspiration");
		super.setDescription("Piochez des cartes dans la bibliotheque");
		super.setForce(force);
		super.setJevise(false);
		
	}
		
	public void action() {
		if(isValide())
		{
			Joueur joueurJouant = getPartie().getJoueurs().get(getPartie().getJoueurJouant());
			joueurJouant.piocherCartes(getForce());
			Interface.Console(joueurJouant.getNom()+" pioche "+getForce()+" carte(s)",this.getPartie());
		}
	}
	
}
=======
package cartes.cartesHocus;

import cartes.Carte;
import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;

public class Inspiration extends Hocus{

	public Inspiration(int force,Partie partie){	
		super(partie);
		super.setNom("Inspiration");
		super.setDescription("Piochez des cartes dans la bibliotheque");
		super.setForce(force);
		super.setJevise(false);
		
	}
		
	public void action() {
		if(isValide())
		{
			Joueur joueurJouant = getPartie().getJoueurs().get(getPartie().getJoueurJouant());
			joueurJouant.piocherCartes(getForce());
			Interface.Console(joueurJouant.getNom()+" pioche "+getForce()+" carte(s)");
		}
	}
	
}
>>>>>>> 7ea0710b91d107d45f8f59e4a4fe7b297b1947ee
