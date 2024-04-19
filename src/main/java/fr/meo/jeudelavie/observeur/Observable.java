package fr.meo.jeudelavie.observeur;

import java.util.List;

/**
 * Interface représentant un objet observable.
 * utilisée pour appliquer le pattern Observer
 * 
 * @see Observateur
 */
public interface Observable {

  /**
   * Attache un observateur à l'objet observable.
   * 
   * @param o l'observateur à attacher
   */
  public void attacheObservateur(Observateur o);

  /**
   * Détache un observateur de l'objet observable.
   * 
   * @param o l'observateur à détacher
   */
  public void detacheObservateur(Observateur o);

  /**
   * Notifie les observateurs de l'objet observable.
   */
  public void notifieObservateur();

  /**
   * Renvoie la liste des observateurs.
   * 
   * @return la liste des observateurs
   */
  public List<Observateur> getObservateurs();
}
