package cartes.cartesHocus;

public class Sortilege extends Hocus{

	public Sortilege(int force){	
		super();
		super.setNom("Sortilege");
		super.setDescription("Piochez des gemmes dans le chaudron");
		super.setForce(force);
		
	}
		
	public void action() {
		if(isValide())
		{
			System.out.println("pioche "+this.getForce()+" gemmes dans le chaudron");
		}
	}
	
}
