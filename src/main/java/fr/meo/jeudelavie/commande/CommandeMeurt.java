package fr.meo.jeudelavie.commande;

import fr.meo.jeudelavie.cellule.Cellule;

/**
 * Classe représentant une commande.
 * utilisée pour appliquer le pattern Command
 * 
 * @see Commande
 */
public class CommandeMeurt extends Commande {

  /**
   * Constructeur de la classe.
   * 
   * @param cellule la cellule sur laquelle la commande va être appliquée
   */
  public CommandeMeurt(Cellule cellule) {
    super(cellule);
  }

  /**
   * Méthode pour exécuter la commande.
   */
  @Override
  public void executer() {
    this.cellule.meurt();
  }

}
