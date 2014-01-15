package cartes.cartesPocus;

import cartes.Carte;
import partie.Partie;

public class Eclair extends Pocus{
	
	public Eclair(Partie partie){
		super(partie);
		super.setNom("Eclair");
		super.setDescription("Potège la carte hocus de toutes les cartes Pocus sauf baguette magique");
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			Carte hocus = this.getPartie().getAireDeJeu().getPileDeCarte().get(0);	
			hocus.setEstValide(true);
			
			for(Carte carte : getPartie().getAireDeJeu().getPileDeCarte()){
				if( (carte.getType() == CarteType.pocus) && !("Baguette Magique".equals(carte.getNom())))
					carte.setEstValide(false);
			}
			
			getPartie().finCarteHocus();
		}		
	}

}
