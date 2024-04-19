package fr.meo.jeudelavie;

import fr.meo.jeudelavie.cellule.Cellule;
import fr.meo.jeudelavie.cellule.CelluleEtatMort;
import fr.meo.jeudelavie.cellule.CelluleEtatVivante;
import fr.meo.jeudelavie.commande.Commande;
import fr.meo.jeudelavie.observeur.Observable;
import fr.meo.jeudelavie.observeur.Observateur;
import fr.meo.jeudelavie.visiteur.Visiteur;
import fr.meo.jeudelavie.visiteur.VisiteurDayAndNight;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



/**
 * Classe principale du programme.
 * Cette classe permet de gérer le jeu de la vie.
 * Elle permet de créer une grille de cellule, de la distribuer à un visiteur,
 * de calculer la génération suivante, de notifier les observateurs et d'exécuter les commandes.
 * Elle permet aussi de changer le visiteur, de récupérer le délai de l'execution, de changer l'état de l'execution,
 * de réinitialiser la grille et de récupérer les observateurs.
 * Elle implémente les interfaces Observable et Commande.
 * 
 * @version 1.0
 * @author meo
 * @see Observable
 */
public class JeuDeLaVie implements Observable  {

  /** Matrice pour le jeu de la vie. */
  private Cellule[][] grille;
  /** Taille maximale en x de la grille. */
  private int xmax;
  /** Taille maximale en y de la grille. */
  private int ymax;
  /** Liste des commande. */
  private List<Commande> commandes;
  /** Liste des observateur. */
  private List<Observateur> observateurs;
  /** Visiteur. */
  private Visiteur visiteur;

  /** Génération. */
  private int generation;

  /** Paramètre de l'execution. */
  private Boolean run = false;
  /** Délai de l'execution. */
  private int delai;

  /**
   * Constructeur de la classe.
   */
  public JeuDeLaVie()  {
    this.xmax = 1000;
    this.ymax = 1000;
    this.grille = new Cellule[this.xmax][this.ymax];
    this.initialiseGrille();
    //dort pendant 200 ms
    try {
      Thread.sleep(200);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.observateurs = new ArrayList<Observateur>();
    this.commandes = new ArrayList<Commande>();
    this.visiteur = new VisiteurDayAndNight(this);

    this.delai = 200;

  }

  /**
   * Getter de xmax.
   *
   * @return la valeur de xmax
   */
  public int getxmax()  {
    return this.xmax;
  }

  /**
   * Getter de ymax.
   * 
   * @return la valeur de ymax
   */ 
  public int getymax() {
    return this.ymax;
  }

  /**
   * Méthode d'initialisation de la newGrille.
   * priver pour limiter l’accès
   */ 
  public void initialiseGrille() { 
    this.generation = 0;
    for (int i = 0; i < this.xmax; i++)  {
      for (int j = 0; j < this.ymax; j++) {
        if (Math.random() < 0.5) {
          this.grille[i][j] = new Cellule(i, j, CelluleEtatVivante.getInstance());
        } else {
          this.grille[i][j] = new Cellule(i, j, CelluleEtatMort.getInstance());
        }
      }
    }
  }

  /**
   * Donne la cellule se trouvant au coordonnée x y.
   * 
   * @param x la coordonnée x de la cellule
   * @param y la coordonnée y de la cellule
   * @return la cellule au coordonné verifier ou null si la cellule n'existe pas
   */ 
  public Cellule getGrillexy(int x, int y) {
    return this.grille[x][y];
  }

  /**
   * Cette méthode permet de modifier les valeurs de xmax et ymax.
   * et de redimensionner si nécessaire la grille
   * 
   * @param x la nouvelle valeur de x
   * @param y la nouvelle valeur de y
   */ 
  public void setxy(int x, int y) {
    if (x < 0 || y < 0) {
      return;
    } else {
      this.grille = new Cellule[x][y];
      this.initialiseGrille();
    }
  }

  /**
   * Cette méthode permet d'ajouter un observateur à la liste des observateurs.
   * implémente le design pattern Observer
   * 
   * @param o l'observateur à ajouter
   */ 
  @Override
  public void attacheObservateur(Observateur o) {
    this.observateurs.add(o);
  }

  /**
   * Cette méthode permet de retirer un observateur de la liste des observateurs.
   * implémente le design pattern Observer
   * 
   * @param o l'observateur à retirer 
   */
  @Override
  public void detacheObservateur(Observateur o) {
    this.observateurs.remove(o);
  }
 
  /**
   * Cette méthode permet de récupérer la liste des observateurs.
   *  
   * @return la liste des observateurs
   */
  @Override
  public List<Observateur> getObservateurs() {
    return this.observateurs;
  }

  /**
   * Cette méthode permet de notifier les observateurs.
   * implémente le design pattern Observer 
   */
  @Override
  public void notifieObservateur() {
    for (Observateur o : this.observateurs) {
      o.actualise();
    }
  }
 
  /**
   * Cette méthode ajoute une commande à  la liste des commandes.
   * permet d'appliquer le design pattern Command
   * 
   * @param c la commande à ajouter
   */
  public void ajouteCommande(Commande c) {
    this.commandes.add(c);
  }
 
  /**  
   * Cette méthode exécute toutes les commandes de la liste des commandes.
   * permet d'appliquer le design pattern Command
   */
  public void executeCommandes() {
    for (Commande c : this.commandes) {
      c.executer();
    }
     
    this.commandes.clear();
  }
 
  /**
   * Méthode pour changer le visiteur.
   * 
   * @param v le visiteur à changer
   */
  public void setVisiteur(Visiteur v) {
    this.visiteur = v;
  } 

  /**
   * Getter du visiteur.
   * 
   * @return le visiteur
   * 
   */
  public Visiteur getVisiteur() {
    return this.visiteur; 
  }  
  
  /**
   * Cette méthode permet de distribuer un visiteur.
   * sur toutes les cellules de la grille
   * permet d'appliquer le design pattern Visiteur
   */
  public void distribueVisiteur() {
    for (int i = 0; i < this.xmax; i++) {
      for (int j = 0; j < this.ymax; j++) {
        this.grille[i][j].accepte(this.visiteur);
      }
    }
  }

  /**
   * Cette méthode permet de calculer la génération suivante.
   */
  public void calculerGenerationSuivante() {
    this.distribueVisiteur();
    this.executeCommandes();
    this.notifieObservateur();
    this.generation++;
  }

  /**
   * Cette méthode permet d'obtenir le délai de l'execution.
   * 
   * @return le délai de l'execution en ms
   */ 
  public int getDelai() {
    return this.delai;
  }

  
  /**
   * Setter du délai.
   * 
   * @param delai le nouveau délai
   */
  public void setDelai(int delai) {
    this.delai = delai;
  }

  /**
   * Getter de la génération.
   * 
   * @return la génération actuelle
   */
  public int getGeneration() {
    return generation;
  }

  /**
   * Cette méthode permet d'obtenir l'état de l'execution.
   *  
   * @return l'état de l'execution
   */
  public Boolean getRun() {
    return this.run;
  }

  /** 
   * Cette méthode permet de changer l 'état de l'execution.
   *   
   * @param run le nouvel état de l'execution
   */
  public void setRun(Boolean run) {
    this.run = run;
  }

  /**
   * Réinitialise la grille.
   */
  public void reinitialiser() {
    for (int i = 0; i < this.xmax; i++) {
      for (int j = 0; j < this.ymax; j++) {
        this.grille[i][j].meurt();
      }
    }
    this.generation = 0;
    this.notifieObservateur();
  }
}
