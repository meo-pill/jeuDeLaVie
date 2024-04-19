package fr.meo.jeudelavie.commande;

import fr.meo.jeudelavie.cellule.Cellule;

/**
 * Classe abstraite représentant une commande.
 * utilisée pour appliquer le pattern Command
 */
public abstract class Commande {

  /**
   * La cellule sur laquelle la commande va être appliquée.
   * non mis en private pour permettre l'accès aux classes filles
   */
  protected Cellule cellule;

  /**
   * Constructeur de la classe.
   * 
   * @param cellule la cellule sur laquelle la commande va être appliquée
   */
  public Commande(Cellule cellule) {
    this.cellule = cellule;
  }

  /**
   * Méthode abstraite pour exécuter la commande.
   */
  public abstract void executer();
}
