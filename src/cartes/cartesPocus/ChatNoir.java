<<<<<<< HEAD
package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;
import cartes.Carte;

public class ChatNoir extends Pocus{

	private Carte carteVisee;
	
	private Joueur joueur;
	
	public Carte getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public ChatNoir(Partie partie){	
		super(partie);
		super.setNom("Chat Noir");
		super.setDescription("Détruisez la carte HOCUS");
	}
	
	public void setJoueurVise(Joueur jVise) {
		joueur = jVise;
	}
	
	@Override
	public void jouerLaCarte(){
		Carte visee = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);	
//		joueur = getPartie().getJoueurs().get(getPartie().getJoueurJouant());
		this.setCarteVisee(visee);
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			this.getPartie().getAireDeJeu().getPileDeCarte().get(0).setEstValide(false);
			joueur.getMain().ajouterUneCarte(carteVisee);
//			saMain.ajouterUneCarte(carteVisee);
//			joueur.setMain(saMain);
			Interface.Console("La carte "+carteVisee+" va dans la main de "+ joueur.getNom(),this.getPartie());
		}		
	}
	
}
=======
package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;
import cartes.Carte;

public class ChatNoir extends Pocus{

	private Carte carteVisee;
	
	private Joueur joueur;
	
	public Carte getCarteVisee() {
		return carteVisee;
	}
	
	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public ChatNoir(Partie partie){	
		super(partie);
		super.setNom("Chat Noir");
		super.setDescription("Détruisez la carte HOCUS");
	}
	
	public void setJoueurVise(Joueur jVise) {
		joueur = jVise;
	}
	
	@Override
	public void jouerLaCarte(){
		Carte visee = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);	
//		joueur = getPartie().getJoueurs().get(getPartie().getJoueurJouant());
		this.setCarteVisee(visee);
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			this.getPartie().getAireDeJeu().getPileDeCarte().get(0).setEstValide(false);
			joueur.getMain().ajouterUneCarte(carteVisee);
//			saMain.ajouterUneCarte(carteVisee);
//			joueur.setMain(saMain);
			Interface.Console("La carte "+carteVisee+" va dans la main de "+ joueur.getNom());
		}		
	}
	
}
>>>>>>> 7ea0710b91d107d45f8f59e4a4fe7b297b1947ee
