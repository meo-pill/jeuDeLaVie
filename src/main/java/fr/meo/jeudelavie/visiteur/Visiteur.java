package fr.meo.jeudelavie.visiteur;

import fr.meo.jeudelavie.JeuDeLaVie;
import fr.meo.jeudelavie.cellule.Cellule;

/**
 * Classe abstraite représentant un visiteur.
 * utilisée pour appliquer le pattern Visiteur
 */
public abstract class Visiteur {

  /** Le jeu de la vie a visiter. */
  protected JeuDeLaVie jeu;

  /**
   * Constructeur de la classe.
   * 
   * @param jeu le jeu de la vie a visiter
   */
  public Visiteur(JeuDeLaVie jeu) {
    this.jeu = jeu;
  }

  /**
   * Méthode pour visiter une cellule vivante.
   * 
   * @param cellule la cellule a visiter
   */
  public abstract void visiteCelluleVivante(Cellule cellule);

  /**
   * Méthode pour visiter une cellule morte.
   * 
   * @param cellule la cellule a visiter
   */
  public abstract void visiteCelluleMorte(Cellule cellule);
}
