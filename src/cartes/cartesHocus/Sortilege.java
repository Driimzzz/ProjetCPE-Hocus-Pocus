package cartes.cartesHocus;

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
			System.out.println("pioche "+this.getForce()+" gemmes dans le chaudron");
			System.out.println("il rests "+ this.getPartie().getChaudron() +" gemmes dans le chaudron");
		}
	}
	
}
