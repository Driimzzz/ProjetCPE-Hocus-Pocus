package partie;

import java.util.Stack;

import cartes.*;

public class Grimoire extends PileDeCartes {
        
        public Carte enleverCarte(Carte carteAEnlever, Joueur proprietaireDeCeGrimoire){
                Stack<Carte> pile = this.getPileDeCarte();
                pile.removeElement(carteAEnlever);
                proprietaireDeCeGrimoire.demandeCompleterGrimoire();
                return carteAEnlever;
        }
        
        
}