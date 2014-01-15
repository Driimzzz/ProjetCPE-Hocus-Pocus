package cartes.cartesHocus;

import java.util.List;

import partie.Grimoire;
import partie.Joueur;
import partie.Partie;

public class Vortex extends Hocus{

	public Vortex(Partie partie) {
		super(partie);
		super.setNom("Vortex");
		super.setDescription("Echanger votre grimoire avec votre voisin de gauche");
	}


	@Override
	public void action() {
		if (isValide()) {
			
			List<Joueur> joueurs = getPartie().getJoueurs();
			Grimoire premierGrim = joueurs.get(0).getGrimoire();
			for (int i=0;i<joueurs.size()-1;i++){
				joueurs.get(i).setGrimoire(joueurs.get(i+1).getGrimoire());
			}
			joueurs.get(joueurs.size()-1).setGrimoire(premierGrim);			
		}
	}


}
