package fr.meo.jeudelavie.cellule;

import fr.meo.jeudelavie.visiteur.Visiteur;

/**
 * Interface représentant l'état d'une cellule.
 * utilisée pour appliquer le pattern State
 * 
 * @see Cellule
 */
interface CelluleEtat {

  /**
   * Passe la cellule à l'état "vivante".
   * 
   * @return l'état "vivant"
   */
  public CelluleEtat vit();

  /**
   * Passe la cellule à l'état "morte".
   * 
   * @return l'état "mort"
   */
  public CelluleEtat meurt();

  /**
   * Retourne vrai si la cellule est vivante faux sinon.
   * 
   * @return vrai si la cellule est vivante faux sinon
   */
  public boolean estVivante();

  /**
   * Accepte un visiteur.
   * implémente le pattern Visiteur
   * 
   * @param visiteur le visiteur à accepter
   * @param cellule  la cellule à visiter
   */
  public void accepte(Visiteur visiteur, Cellule cellule);
}