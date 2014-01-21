package partie;

import cartes.*;
import cartes.cartesHocus.Abracadabra;
import cartes.cartesHocus.BouleDeCristal;
import cartes.cartesHocus.Hibou;
import cartes.cartesHocus.Inspiration;
import cartes.cartesHocus.Malediction;
import cartes.cartesHocus.Sacrifice;
import cartes.cartesHocus.Sortilege;
import cartes.cartesHocus.Voleur;
import cartes.cartesHocus.Vortex;
import cartes.cartesPocus.Amulette;
import cartes.cartesPocus.BaguetteMagique;
import cartes.cartesPocus.ChatNoir;
import cartes.cartesPocus.Citrouille;
import cartes.cartesPocus.ContreSort;
import cartes.cartesPocus.Eclair;
import cartes.cartesPocus.MiroirEnchante;
import cartes.cartesPocus.Sablier;

public class Bibliotheque {
	private PileDeCartes cartes;

	private Partie partie;

	public Bibliotheque(int nbrJoueurs,Partie maPartie) { // constructeur de la bilbiotheque
											// qui initialise le paquet de
											// cartes
		setPartie(maPartie);
		
		this.cartes = new PileDeCartes();

		// initialisations cartes voleurs
		for (int i = 0; i < 5; i++)
			// 5 cartes voleur 1
			this.cartes.ajouterUneCarte(new Voleur(1, maPartie));
		for (int i = 0; i < 4; i++)
			// 4 cartes voleur 2
			this.cartes.ajouterUneCarte(new Voleur(2, maPartie));
		for (int i = 0; i < 3; i++)
			// 3 cartes voleur 3
			this.cartes.ajouterUneCarte(new Voleur(3, maPartie));
		for (int i = 0; i < 2; i++)
			// 2 cartes voleur 4
			this.cartes.ajouterUneCarte(new Voleur(4, maPartie));
		// 1 carte voleur 5
		this.cartes.ajouterUneCarte(new Voleur(5, maPartie));

		// init cartes Sortilege
		for (int i = 0; i < 5; i++)
			// 5 cartes Sortilege 1
			this.cartes.ajouterUneCarte(new Sortilege(1, maPartie));
				for (int i = 0; i < 4; i++)
			// 4 cartes Sortilege 2
			this.cartes.ajouterUneCarte(new Sortilege(2, maPartie));
		for (int i = 0; i < 3; i++)
			// 3 cartes Sortilege 3
			this.cartes.ajouterUneCarte(new Sortilege(3, maPartie));
		for (int i = 0; i < 2; i++)
			// 2 cartes Sortilege 4
			this.cartes.ajouterUneCarte(new Sortilege(4, maPartie));
		// 1 carte Sortilege 5
		this.cartes.ajouterUneCarte(new Sortilege(5, maPartie));

		// 4 baguettes magiques
		for (int i = 0; i < 4; i++)
			this.cartes.ajouterUneCarte(new BaguetteMagique(maPartie));

		// 2 contre-sorts
		for (int i = 0; i < 2; i++)
			this.cartes.ajouterUneCarte(new ContreSort(maPartie));

		// 2 sacrifices
		for (int i = 0; i < 2; i++)
			this.cartes.ajouterUneCarte(new Sacrifice(2, maPartie));
		
		// 2 chats noirs
		for (int i = 0; i < 2; i++)
			this.cartes.ajouterUneCarte(new ChatNoir(maPartie));

		// 2 eclairs
		for (int i = 0; i < 2; i++)
			this.cartes.ajouterUneCarte(new Eclair(maPartie));
		
		// amulettes et miroir enchanté en fonction du nombre de joueurs
		if(nbrJoueurs<3)
		{
			for (int i = 0; i < 7; i++)
				this.cartes.ajouterUneCarte(new Amulette(maPartie));
		}
		else
		{
			for (int i = 0; i < 3; i++)
				this.cartes.ajouterUneCarte(new MiroirEnchante(maPartie));
			for (int i = 0; i < 4; i++)
				this.cartes.ajouterUneCarte(new Amulette(maPartie));
		}
		
		
		
		this.cartes.ajouterUneCarte(new Malediction(2,maPartie));
		this.cartes.ajouterUneCarte(new Malediction(1,maPartie));
		this.cartes.ajouterUneCarte(new Malediction(1,maPartie));
		
		this.cartes.ajouterUneCarte(new Hibou(1, maPartie));
		this.cartes.ajouterUneCarte(new Hibou(1, maPartie));
		this.cartes.ajouterUneCarte(new Hibou(2, maPartie));
		
		this.cartes.ajouterUneCarte(new Inspiration(2,maPartie));
		this.cartes.ajouterUneCarte(new Inspiration(2,maPartie));
		this.cartes.ajouterUneCarte(new Inspiration(2,maPartie));
		
		this.cartes.ajouterUneCarte(new Inspiration(3,maPartie));
		this.cartes.ajouterUneCarte(new Inspiration(3,maPartie));
		
		this.cartes.ajouterUneCarte(new Abracadabra(maPartie));
		this.cartes.ajouterUneCarte(new Abracadabra(maPartie));
		
		this.cartes.ajouterUneCarte(new Vortex(maPartie));
		this.cartes.ajouterUneCarte(new Vortex(maPartie));
		
		//3 citrouilles
		for (int i = 0; i < 3; i++)
			this.cartes.ajouterUneCarte(new Citrouille(maPartie));
		
		// 2 Sabliers
		this.cartes.ajouterUneCarte(new Sablier(maPartie));
		this.cartes.ajouterUneCarte(new Sablier(maPartie));
		
		//3 boules de cristal
		this.cartes.ajouterUneCarte(new BouleDeCristal(maPartie));
		this.cartes.ajouterUneCarte(new BouleDeCristal(maPartie));
		this.cartes.ajouterUneCarte(new BouleDeCristal(maPartie));

		this.cartes.melanger();
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public PileDeCartes getCartes() {
		return cartes;
	}

	public void setCartes(PileDeCartes cartes) {
		this.cartes = cartes;
	}

	public Carte tirerUneCarte() {
		if(cartes.tailleDeLaPile()<1){
			getPartie().getDefausse().melanger();
			setCartes(getPartie().getDefausse());
		}			
			
		Carte carteTiree = cartes.tirerUneCarte();
		
		//TODO Completer grimoire
		
		return carteTiree;
	}

}
