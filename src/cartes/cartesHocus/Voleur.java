package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import java.util.Scanner;

import partie.Joueur;
import partie.Partie;

public class Voleur extends Hocus{
	
	private Joueur joueurVise;
	private Partie partie;

//	public Voleur(){		
//		super.setNom("Voleur");
//		super.setDescription("Piochez des gemmes chez un autre joueur");
//	}
	
	public Voleur(int force,Partie partie){
		super(partie);
		this.partie=partie;
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
		super.setForce(force);
	}
	
	@Override
	public void jouerLaCarte(){
//		
//		Scanner scan = new Scanner(System.in);
//		int numJoueur = -1;
//		//saisie protégée pour que le joueur visé existe
//		while(numJoueur<0 || numJoueur>this.getPartie().getJoueurs().size()-1){		
//			Interface.Console("A qui volez vous?");		
//			this.getPartie().afficherJoueurs();
//			numJoueur = partie.Partie.readIntValue();	//methode readinvalue créee dans la classe partie ca demande un input d'un int
//		}	
		partie.viserUnJoueur(this.getPartie().getJoueurJouant());
		//joueurVise = this.getPartie().getJoueurs().get(numJoueur);		
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			Interface.Console("vol "+this.getForce()+" gemmes à un joueur");
			
			int gemmesVolees = joueurVise.perdreDesGemmes(this.getForce());
			Interface.Console("Vous volez à "+joueurVise.getNom());
			Interface.Console("Il lui reste "+joueurVise.getGemmes()+" gemmes");
			
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesVolees);
			
			Interface.Console(joueurJouant.getNom()+" a maintenant "+joueurJouant.getGemmes()+" gemmes");	
		}	
	}

	public Joueur getJoueurVise() {
		return joueurVise;
	}

	public void setJoueurVise(Joueur joueurVise) {
		this.joueurVise = joueurVise;
	}	
	
}