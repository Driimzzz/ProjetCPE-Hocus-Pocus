package cartes;

import java.util.Stack;

public class PileDeCartes {
	private Stack<Carte> listeDeCartes;
	
	public PileDeCartes(){
		listeDeCartes = new Stack<>();		
	}
	
	public Stack<Carte> getPileDeCarte(){
		return listeDeCartes;
	}
	
	public void ajouterUneCarte(Carte carteAjoutee){
		listeDeCartes.push(carteAjoutee);
	}
	
	public Carte tirerUneCarte(){
		return listeDeCartes.pop();
	}
	
	public void melanger(){
		//prend la premiere carte et la met aléatoirement dans le paquet
		for (int i=0;i<20;i++) {
			int changeIndex = (int) Math.round(Math.random()*(listeDeCartes.size()-1));
			listeDeCartes.add(changeIndex, listeDeCartes.pop());
		}			
	}
	
	public void afficherToutes(){
		System.out.println("----le jeu de carte----");
		for (Carte carteCourante : listeDeCartes) {
			System.out.println(carteCourante);
		}
	}
	
	public int tailleDeLaPile(){
		return listeDeCartes.size();
	}
	
}
