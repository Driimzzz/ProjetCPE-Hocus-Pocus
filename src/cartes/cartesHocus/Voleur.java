package cartes.cartesHocus;

import interfaceclientserveur.Interface;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import partie.Joueur;
import partie.Partie;

public class Voleur extends Hocus{
	
	private Joueur joueurVise;
	private Partie partie;

//	public Voleur(){		
//		super.setNom("Voleur");
//		super.setDescription("Piochez des gemmes chez un autre joueur");
//	}
	
	public Voleur(int force,Partie partie){
		super(partie);
		this.partie=partie;
		super.setNom("Voleur");
		super.setDescription("Piochez des gemmes chez un autre joueur");
		super.setForce(force);
	}
	
	@Override
	public void jouerLaCarte(){
//		
//		Scanner scan = new Scanner(System.in);
//		int numJoueur = -1;
//		//saisie protégée pour que le joueur visé existe
//		while(numJoueur<0 || numJoueur>this.getPartie().getJoueurs().size()-1){		
//			Interface.Console("A qui volez vous?");		
//			this.getPartie().afficherJoueurs();
//			numJoueur = partie.Partie.readIntValue();	//methode readinvalue créee dans la classe partie ca demande un input d'un int
//		}	
		viserUnJoueur(this.getPartie().getJoueurJouant());
		//joueurVise = this.getPartie().getJoueurs().get(numJoueur);		
		
	}
	
	@Override
	public void action() {
		if(isValide())
		{
			int gemmesVolees = joueurVise.perdreDesGemmes(this.getForce());
			Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
			joueurJouant.setGemmes(joueurJouant.getGemmes()+gemmesVolees);
			
			Interface.Console(joueurJouant.getNom() +" vole "+this.getForce()+" gemmes à "+joueurVise.getNom());
		}	
	}

	public Joueur getJoueurVise() {
		return joueurVise;
	}

	public void setJoueurVise(Joueur joueurVise) {
		this.joueurVise = joueurVise;
	}	
	// le serveur demande au client de viser
		public void viserUnJoueur(int numJoueurVisant) {
			JSONObject grosJson = new JSONObject();
			try {
				grosJson.put("methode", "viserJoueur");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			try {
				grosJson.put("numJoueurVisant", numJoueurVisant);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			JSONArray arr = new JSONArray();
			for (Joueur j : partie.getJoueurs()) {
				arr.put(j.toJson());
			}
			try {
				grosJson.put("joueurs", arr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}

			Interface.Jeu(grosJson.toString(), partie.getJoueurJouant());
		}
	
		
}