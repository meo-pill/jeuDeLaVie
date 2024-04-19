package fr.meo.jeudelavie.cellule;

import fr.meo.jeudelavie.JeuDeLaVie;
import fr.meo.jeudelavie.visiteur.Visiteur;

/**
 * Classe représentant une cellule.
 * utilisée pour appliquer le pattern State
 */
public class Cellule {

  /**
   * La position en x de la cellule.
   */
  private int xpos;
  /**
   * La position en y de la cellule.
   */
  private int ypos;
  /**
   * l'état de la cellule.
   * utilisé pour appliquer le pattern State
   */
  private CelluleEtat etat;

  /**
   * Constructeur de la classe.
   * 
   * @param x    la position en x de la cellule
   * @param y    la position en y de la cellule
   * @param etat l'état de la cellule
   */
  public Cellule(int x, int y, CelluleEtat etat) {
    this.xpos = x;
    this.ypos = y;
    this.etat = etat;
  }

  /**
   * Passe la cellule à l'état "vivante".
   */
  public void vit() {
    this.etat = this.etat.vit();
  }

  /**
   * Passe la cellule à l'état "morte".
   */
  public void meurt() {
    this.etat = this.etat.meurt();
  }

  /**
   * Retourne vrai si la cellule est vivante.
   * 
   * @return vrai si la cellule est vivante
   */
  public boolean estVivante() {
    return this.etat.estVivante();
  }

  /**
   * Compte son nombre de voisine.
   * 
   * @param jeu l'instance du jeux de la vie
   * @return le nombre de cellule voisine
   */
  public int nombreVoisinesVivantes(JeuDeLaVie jeu) {
    int nb = 0;
    for (int i = this.xpos - 1; i <= this.xpos + 1; i++) {
      for (int j = this.ypos - 1; j <= this.ypos + 1; j++) {
        if (i >= 0 && i < jeu.getxmax() && j >= 0 && j < jeu.getymax() && !(i == this.xpos && j == this.ypos)
            && jeu.getGrillexy(i, j).estVivante()) {
          nb++;
        }
      }
    }
    return nb;
  }

  /**
   * Accepte un visiteur.
   * implémente le pattern Visiteur
   * 
   * @param visiteur le visiteur à accepter
   */
  public void accepte(Visiteur visiteur) {
    this.etat.accepte(visiteur, this);
  }
}
