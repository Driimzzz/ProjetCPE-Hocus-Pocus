package partie;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import interfaceclientserveur.Interface;
import cartes.Carte;
import cartes.Carte.CarteType;

public class Joueur {
	private int gemmes;
	private Main main;
	private Grimoire grimoire;
	private String nom;
	private Partie partie;

	public Joueur(String _nom, Partie maPartie) {
		this.setPartie(maPartie);
		this.setNom(_nom);
		this.setGemmes(10);
		this.setGrimoire(new Grimoire());
		this.setMain(new Main());
		Interface.Console("joueur créé : " + this.getNom());
	}

	public void jouerCarte(Carte carteJouee) {

		if (this.getPartie().ajouterAAireDeJeu(carteJouee)) { // si on avait
																// le droit
																// de jouer
																// la carte
			this.getMain().getPileDeCarte().remove(carteJouee);
			carteJouee.jouerLaCarte();
		}
		Interface.toutesLesInfos();
	}

	//
	public void piocherCartes(int nbCartes) {
		for (int i = 0; i < nbCartes; i++)
			this.getMain().ajouterUneCarte(
					getPartie().getBibliotheque().getCartes().tirerUneCarte());

	}

	// Le joueur perds des gemmes
	public int perdreDesGemmes(int nbrVoles) {
		if (gemmes - nbrVoles < 0)
			nbrVoles = gemmes;
		gemmes -= nbrVoles;
		return nbrVoles;
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

		return json;
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
