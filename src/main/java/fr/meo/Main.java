package fr.meo;

import fr.meo.jeudelavie.JeuDeLaVie;
import fr.meo.jeudelavie.JeuDeLaVieTerminal;
import fr.meo.jeudelavie.JeuDeLaVieUi;

/**
 * Classe principale du programme.
 * 
 * @version 1.0
 * @see JeuDeLaVie
 * @see JeuDeLaVieTerminal
 * @see JeuDeLaVieUi
 */
public class Main {
  /**
   * Classe principale du programme.
   * 
   * @param args les arguments de la ligne de commande
   */
  public static void main(String[] args) {
    JeuDeLaVie jeu = new JeuDeLaVie();
    new JeuDeLaVieTerminal(jeu);
    new JeuDeLaVieUi(jeu);

    while (true) {
      jeu.notifieObservateur();

      if (jeu.getRun()) {
        jeu.calculerGenerationSuivante();
        try {
          Thread.sleep(jeu.getDelai());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }
  }
}
