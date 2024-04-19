package fr.meo.jeudelavie.observeur;

/**
 * Interface représentant un observateur.
 * utilisée pour appliquer le pattern Observer
 * 
 * @see Observable
 */
public interface Observateur {

  /**
   * Actualise l'observateur.
   */
  public void actualise();
}
