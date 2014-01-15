package partie;

import interfaceclientserveur.Interface;
import cartes.Carte;
import cartes.PileDeCartes;

public class Main extends PileDeCartes {

	@Override
	public void ajouterUneCarte(Carte carteAjoutee){
		if(getPileDeCarte().size()<6)
			getPileDeCarte().push(carteAjoutee);
		else {
			Interface.Console("Vous ne pouvez pas avoir plus de 6 cartes",null);
		}
	}
}
