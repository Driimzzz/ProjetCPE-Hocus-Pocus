package cartes.cartesHocus;

import partie.Partie;

public class Voleur extends Hocus{
	
	//TODO joueur ciblé

//	public Voleur(){		
//		super.setNom("Voleur");
//		super.setDescription("Piochez des gemmes chez un autre joueur");
//	}
	
	public Voleur(int force,Partie partie){
		super(partie);
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
		super.setForce(force);
	}
	
	public void action() {
		if(isValide())
		{
			System.out.println("vol "+this.getForce()+" gemmes à un joueur");
		}	
	}
}