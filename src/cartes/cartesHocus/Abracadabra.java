package cartes.cartesHocus;

import partie.Joueur;
import partie.Main;
import partie.Partie;

public class Abracadabra extends Hocus{
        
//        private Joueur joueurVise;
        
        public Abracadabra(Partie partie){
                super(partie);
                super.setNom("Abracadabra");
                super.setDescription("Echanger votre main avec celle d'un autre joueur");
                super.setJevise(true);
        }
        
        @Override
        public void jouerLaCarte(){

                getPartie().viserUnJoueur(this.getPartie().getJoueurJouant());        
                
        }
        
        @Override
        public void action() {
                if(isValide())
                {
                        Joueur joueurJouant = this.getPartie().getJoueurs().get(this.getPartie().getJoueurJouant());
                        
                        Main buff = joueurJouant.getMain();
                        joueurJouant.setMain(getJoueurVise().getMain());
                        getJoueurVise().setMain(buff);
                        
                }        
        }

//        public Joueur getJoueurVise() {
//                return joueurVise;
//        }
//        
//        @Override
//        public void setJoueurVise(Joueur joueurVise) {
//                this.joueurVise = joueurVise;
//        }                
                
}