package cartes;

import java.util.Stack;

import org.json.JSONArray;

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
	
	public JSONArray toJson(){
		JSONArray json = new JSONArray();
		
		for(Carte carte : listeDeCartes)
			json.put(carte.toJson());
		
		return json;
	}
	
	public void melanger(){
		//prend la premiere carte et la met aléatoirement dans le paquet
		for (int i=0;i<100;i++) {
			int changeIndex = (int) Math.round(Math.random()*(listeDeCartes.size()-1));
			listeDeCartes.add(changeIndex, listeDeCartes.pop());
		}			
	}
	

	
	public int tailleDeLaPile(){
		return listeDeCartes.size();
	}
	
}
