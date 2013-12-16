package cartes;

public class Voleur extends Hocus{
	
	//TODO joueur cibl�

	public Voleur(){		
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
	}
	
	public Voleur(int force){		
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
		super.setForce(force);
	}
	
	public void action() {
		if(this.estValide)
		{
			System.out.println("vol "+this.getForce()+" gemmes � un joueur");
		}
	}
}