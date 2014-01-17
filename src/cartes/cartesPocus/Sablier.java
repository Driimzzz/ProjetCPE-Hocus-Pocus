package cartes.cartesPocus;

import interfaceclientserveur.Interface;
import partie.Partie;

public class Sablier extends Pocus{
	
	public Sablier(Partie partie){
		super(partie);
		super.setNom("Sablier");
		super.setDescription("Mettre fin au tour du joueur");
	}
	
	@Override
	public void action() {
		if(isValide()){
			//getPartie().finCarteHocus();
			getPartie().setFinDuTourDeJeu(true);
			Interface.Console("Mettre fin au tour du joueur et on résou la carte hocus", getPartie());
		}
		
	}

	
}
