package fr.meo.jeudelavie.visiteur;

import fr.meo.jeudelavie.JeuDeLaVie;
import fr.meo.jeudelavie.cellule.Cellule;
import fr.meo.jeudelavie.commande.CommandeMeurt;
import fr.meo.jeudelavie.commande.CommandeVit;

/**
 * Classe représentant un visiteur pour le jeu de la vie HighLife.
 * utilisée pour appliquer le pattern Visiteur
 * 
 * @see Visiteur
 */
public class VisiteurHighLife extends Visiteur {

  /**
   * Constructeur de la classe.
   * 
   * @param jeu le jeu de la vie a visiter
   */
  public VisiteurHighLife(JeuDeLaVie jeu) {
    super(jeu);
  }

  /**
   * Méthode pour visiter une cellule vivante.
   * 
   * @param cellule la cellule a visiter
   */
  @Override
  public void visiteCelluleVivante(Cellule cellule) {

    int nbVoisines = cellule.nombreVoisinesVivantes(jeu);

    if (nbVoisines < 2 || nbVoisines > 3) {
      jeu.ajouteCommande(new CommandeMeurt(cellule));
    }
  }

  /**
   * Méthode pour visiter une cellule morte.
   * 
   * @param cellule la cellule a visiter
   */
  @Override
  public void visiteCelluleMorte(Cellule cellule) {

    int nbVoisines = cellule.nombreVoisinesVivantes(jeu);

    if (nbVoisines == 3 || nbVoisines == 6) {
      jeu.ajouteCommande(new CommandeVit(cellule));
    }
  }
}
