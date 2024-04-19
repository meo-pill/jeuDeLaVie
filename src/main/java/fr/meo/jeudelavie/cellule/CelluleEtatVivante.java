package fr.meo.jeudelavie.cellule;

import fr.meo.jeudelavie.visiteur.Visiteur;

/**
 * Classe représentant l'état "vivant" d'une cellule.
 * implémentant l'interface CelluleEtat
 * utilisée pour appliquer le pattern State
 * implémentant le pattern Singleton
 * 
 * @see CelluleEtat
 */
public class CelluleEtatVivante implements CelluleEtat {

  /**
   * L'instance unique de la classe.
   * utilisée pour appliquer le pattern Singleton
   */
  private static final CelluleEtatVivante instance = new CelluleEtatVivante();

  /**
   * Constructeur privé de la classe.
   * utilisé pour appliquer le pattern Singleton
   */
  private CelluleEtatVivante() {
  }

  /**
   * Passe la cellule à l'état "vivante".
   * 
   * @return l'état "vivant"
   */
  @Override
  public CelluleEtat vit() {
    return this;
  }

  /**
   * Passe la cellule à l'état "morte".
   * 
   * @return l'état "mort"
   */
  @Override
  public CelluleEtat meurt() {
    return CelluleEtatMort.getInstance();
  }

  /**
   * Retourne vrai si la cellule est vivante.
   * 
   * @return vrai si la cellule est vivante
   */
  @Override
  public boolean estVivante() {
    return true;
  }

  /**
   * Retourne l'instance unique de la classe.
   * 
   * @return l'instance unique de la classe
   */
  public static CelluleEtat getInstance() {
    return instance;
  }

  /**
   * Accepte un visiteur.
   * implémente le pattern Visiteur
   * 
   * @param visiteur le visiteur à accepter
   * @param cellule  la cellule à visiter
   */
  @Override
  public void accepte(Visiteur visiteur, Cellule cellule) {
    visiteur.visiteCelluleVivante(cellule);
  }
}