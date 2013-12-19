package cartes.cartesHocus;

import java.util.Scanner;

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
		
		Scanner scan = new Scanner(System.in);
		int numJoueur = -1;
		//saisie protégée pour que le joueur visé existe
		while(numJoueur<0 || numJoueur>this.getPartie().getJoueurs().size()-1){		
			System.out.println("A qui volez vous?");		
			numJoueur = scan.nextInt();	//choix du joueur 1 arbitrairement pour tester
		}		
		joueurVise = this.getPartie().getJoueurs().get(numJoueur);		
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			System.out.println("vol "+this.getForce()+" gemmes à un joueur");
			
			int gemmesVolees = joueurVise.perdreDesGemmes(this.getForce());
			System.out.println("Vous volez à "+joueurVise.getNom());
			System.out.println("Il lui reste "+joueurVise.getGemmes()+" gemmes");
			
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesVolees);
			
			System.out.println(joueurJouant.getNom()+" a maintenant "+joueurJouant.getGemmes()+" gemmes");	
		}	
	}
}