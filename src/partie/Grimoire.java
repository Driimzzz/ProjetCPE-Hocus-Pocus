package partie;

import interfaceclientserveur.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import cartes.*;

public class Grimoire extends PileDeCartes {
	
	public Carte enleverCarte(int numeroCarte){
		Carte result = this.getPileDeCarte().get(numeroCarte);
		this.getPileDeCarte().remove(numeroCarte);
		return result;
	}
	
	
}
