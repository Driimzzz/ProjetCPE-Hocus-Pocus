package partie;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import interfaceclientserveur.Interface;
import cartes.Carte;
import cartes.Carte.CarteType;

public class Joueur {
	private int gemmes;
	private int id;
	private int positionPartie;
	private Main main;
	private Grimoire grimoire;
	private String nom;
	private Partie partie;

	public Joueur(String _nom, Partie maPartie,int id, int positionPartie) {
		this.setPartie(maPartie);
		this.setId(id);
		this.setPositionPartie(positionPartie);
		this.setNom(_nom);
		this.setGemmes(10);
		this.setGrimoire(new Grimoire());
		this.setMain(new Main());
		for(int i=0;i<3;i++)
			this.getGrimoire().ajouterUneCarte(getPartie().getBibliotheque().getCartes().tirerUneCarte());
		Interface.Console("joueur créé : " + this.getNom());
	}

	public void jouerCarte(Carte carteJouee, boolean duGrimoire, int numJoueur) {

		if (this.getPartie().ajouterAAireDeJeu(carteJouee)) { // si on avait
			// le droit
			// de jouer
			// la carte
			if (!duGrimoire)
				this.getMain().getPileDeCarte().remove(carteJouee);
			else{
				this.getGrimoire().getPileDeCarte().remove(carteJouee);
				demandeCompleterGrimoire();
			}
			if("Chat Noir".equals(carteJouee.getNom())){
				carteJouee.setJoueurVise(partie.getJoueurs().get(numJoueur));//pour definir dans quelle main ira la carte
				carteJouee.jouerLaCarte();
			}				
			else
				carteJouee.jouerLaCarte();
		}
		partie.toutesLesInfos();
	}

	public void completerGrimoire(int numCarte){
		if((numCarte>=0)&&(numCarte<this.getMain().tailleDeLaPile())){
			Carte carteABouger = this.getMain().getPileDeCarte().get(numCarte);
			this.getMain().getPileDeCarte().remove(carteABouger);
			this.getGrimoire().ajouterUneCarte(carteABouger);
			
			if(getGrimoire().getPileDeCarte().size()<3){ //cas où plusieurs carte du grimoires on été retirées
				try {
					Thread.sleep(500); // besoin d'attendre pour que le client ne soit pas surchargé
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.demandeCompleterGrimoire();
			}
		}
	}
	
	public void piocherCartes(int nbCartes) {
		for (int i = 0; i < nbCartes; i++)
			this.getMain().ajouterUneCarte(
					getPartie().getBibliotheque().getCartes().tirerUneCarte());

	}

	// Le joueur perds des gemmes
	public int perdreDesGemmes(int nbrVoles) {
		if (gemmes - nbrVoles < 0){
			nbrVoles = gemmes;
			gemmes = 0;
		}
		else
			gemmes -= nbrVoles;
		return nbrVoles;
	}
	public boolean aDesHocusDansSonJeu(){
		for (int i=0;i<this.getMain().tailleDeLaPile();i++){
			if (this.getMain().getPileDeCarte().get(i).getType() == Carte.CarteType.hocus)
				return true;
		}
		for (int i=0;i<this.getGrimoire().tailleDeLaPile();i++){
			if (this.getGrimoire().getPileDeCarte().get(i).getType() == Carte.CarteType.hocus)
				return true;
		}	
		return false;
	}
	public boolean peutPiocherCarte(){
		if ((this.getGrimoire().tailleDeLaPile() + this.getMain().tailleDeLaPile()) >=9 )
			return false;
		else
			return true;
	}
	
	public void demandeCompleterGrimoire(){
		//pour completer il faut des cartes dans sa main !!
		if(this.getMain().getPileDeCarte().size()>0)
		{
			JSONObject json = new JSONObject();
			try {
				json.put("methode", "demandeCompleterGrimoire");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			try {
				json.put("numeroJoueur", this.getId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Interface.Error(e.getMessage());
			}
			try {
				json.put("main", this.main.toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Interface.Jeu(json.toString(), this.getId());
		}
		
	}
	
	
	public JSONObject toJson(int numJoueur, int j) {
		JSONObject json = new JSONObject();

		try {
			json.put("id", j);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			json.put("nom", this.getNom());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			json.put("nbrGemme", this.gemmes);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			json.put("grimoire", this.grimoire.toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (numJoueur == j) {
			try {
				json.put("main", this.main.toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				json.put("main", this.main.getPileDeCarte().size());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return json;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("nom", this.getNom());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			json.put("nbrGemme", this.gemmes);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			json.put("main", this.main.toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			json.put("grimoire", this.grimoire.toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	
	
	public int getPositionPartie() {
		return positionPartie;
	}

	public void setPositionPartie(int positionPartie) {
		this.positionPartie = positionPartie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public int getGemmes() {
		return gemmes;
	}

	public void setGemmes(int gemmes) {
		this.gemmes = gemmes;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Grimoire getGrimoire() {
		return grimoire;
	}

	public void setGrimoire(Grimoire grimoire) {
		this.grimoire = grimoire;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
