package fr.meo.jeudelavie.visiteur;

import fr.meo.jeudelavie.JeuDeLaVie;
import fr.meo.jeudelavie.cellule.Cellule;
import fr.meo.jeudelavie.commande.CommandeMeurt;
import fr.meo.jeudelavie.commande.CommandeVit;

/**
 * Classe représentant un visiteur pour le jeu de la vie de DayAndNight.
 * une cellule morte y naît à l'étape suivante si elle est entourée de 3, 6, 7
 * ou 8 voisines vivantes
 * une cellule vivante survit à l'étape suivante si elle est entourée de 3, 4,
 * 6, 7 ou 8 cellules vivantes
 * utilisée pour appliquer le pattern Visiteur
 * 
 * @see Visiteur
 */
public class VisiteurDayAndNight extends Visiteur {

  /**
   * Constructeur de la classe.
   * 
   * @param jeu le jeu de la vie a visiter
   */
  public VisiteurDayAndNight(JeuDeLaVie jeu) {
    super(jeu);
  }

  /**
   * Méthode pour visiter une cellule vivante.
   * 
   * @param cellule la cellule a visiter
   */
  @Override
  public void visiteCelluleVivante(Cellule cellule) {
    int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
    if (nbVoisins < 3 || nbVoisins == 5) {
      this.jeu.ajouteCommande(new CommandeMeurt(cellule));
    }
  }

  /**
   * Méthode pour visiter une cellule morte.
   * 
   * @param cellule la cellule a visiter
   */
  @Override
  public void visiteCelluleMorte(Cellule cellule) {
    if (cellule.nombreVoisinesVivantes(jeu) == 3 || cellule.nombreVoisinesVivantes(jeu) > 5) {
      this.jeu.ajouteCommande(new CommandeVit(cellule));
    }
  }

}
