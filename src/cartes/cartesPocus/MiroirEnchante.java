package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;
import cartes.Carte;

public class MiroirEnchante extends Pocus {

	private Carte carteVisee;
	private Joueur joueurVisant;

	public Carte getCarteVisee() {
		return carteVisee;
	}

	public void setCarteVisee(Carte carteVisee) {
		this.carteVisee = carteVisee;
	}

	public MiroirEnchante(Partie partie) {
		super(partie);
		super.setNom("Miroir Enchante");
		super.setDescription("Redirigez la carte HOCUS ciblée contre vous");
	}

	@Override
	public void jouerLaCarte(int numJoueurVisant) {
		Carte visee = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);
		joueurVisant = getPartie().getJoueurs().get(numJoueurVisant);
		getPartie().viserUnJoueur(numJoueurVisant);
		this.setCarteVisee(visee);		
	}

	@Override
	public void action() {
		if (isValide()) {
			//carteVisee.setJoueurVise(this.getJoueurVise());
			Interface.Console("La carte " + carteVisee + " redirigée vers " + carteVisee.getJoueurVise().getNom(),
					getPartie());
		}
		else
			carteVisee.setJoueurVise(this.joueurVisant);
	}

}
