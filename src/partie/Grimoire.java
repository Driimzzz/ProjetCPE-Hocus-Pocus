package partie;

import interfaceclientserveur.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import cartes.*;

public class Grimoire extends PileDeCartes {
        
        public Carte enleverCarte(Carte carteAEnlever, Joueur proprietaireDeCeGrimoire){
                this.getPileDeCarte().removeElement(carteAEnlever);
                proprietaireDeCeGrimoire.demandeCompleterGrimoire();
                return carteAEnlever;
        }
        
        
}