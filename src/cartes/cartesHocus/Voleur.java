package cartes.cartesHocus;

import partie.Joueur;
import partie.Partie;

public class Voleur extends Hocus{
	
	private Joueur joueurVise;

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
	
	@Override
	public void jouerLaCarte(){
		//TODO viser un joueur		
		int numJoueur = 1;	//choix du joueur 1 arbitrairement pour tester
		//ob
		joueurVise = this.getPartie().getJoueurs().get(numJoueur);		
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			System.out.println("vol "+this.getForce()+" gemmes à un joueur");
			
			int gemmesVolees = joueurVise.perdreDesGemmes(this.getForce());
			System.out.println("Vous voulez à "+joueurVise.getNom());
			System.out.println("Il lui reste "+joueurVise.getGemmes()+" gemmes");
			
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesVolees);
			
			System.out.println("Vous voulez à "+joueurJouant.getNom());
			System.out.println("Il lui reste "+joueurJouant.getGemmes()+" gemmes");	
		}	
	}
}