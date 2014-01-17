package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Joueur;
import partie.Partie;
import cartes.Carte;

public class Citrouille extends Pocus{
	
	public Citrouille(Partie partie){
		super(partie);
		super.setNom("Citrouille");
		super.setDescription("La carte Hocus retourne dans la main, il ne pourra la jouer qu'au prochain tour.");
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			Carte hocus = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);
			hocus.setEstValide(false);
			hocus.setCitrouille(true);
            
            for(Carte carte : getPartie().getAireDeJeu().getPileDeCarte()){
				if( carte.getType() == CarteType.pocus)
					carte.setEstValide(false);
			}
            Joueur joueur = getPartie().getJoueurs().get(getPartie().getJoueurJouant()) ;                    
            joueur.getMain().ajouterUneCarte(hocus);
            Interface.Console("La carte "+hocus+" va dans la main de "+ joueur.getNom(),getPartie());
            
            getPartie().finCarteHocus();
		}		
	}
	
}
