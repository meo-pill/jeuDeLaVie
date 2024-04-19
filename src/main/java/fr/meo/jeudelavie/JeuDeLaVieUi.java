package fr.meo.jeudelavie;

import fr.meo.jeudelavie.commande.Commande;
import fr.meo.jeudelavie.commande.CommandeMeurt;
import fr.meo.jeudelavie.commande.CommandeVit;
import fr.meo.jeudelavie.observeur.Observateur;
import fr.meo.jeudelavie.visiteur.Visiteur;
import fr.meo.jeudelavie.visiteur.VisiteurClassique;
import fr.meo.jeudelavie.visiteur.VisiteurDayAndNight;
import fr.meo.jeudelavie.visiteur.VisiteurHighLife;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;

/**
 * Classe représentant l'interface graphique du jeu de la vie.
 * utilisée pour appliquer le pattern Observer
 * 
 * @see Observateur
 * @see JFrame
 */
public class JeuDeLaVieUi extends JFrame implements Observateur { // Extend the class with the appropriate superclass

  /**
   * L'instance du jeu de la vie.
   */
  private JeuDeLaVie jeu;
  /**
   * L'observateur terminal.
   */
  private JeuDeLaVieTerminal terminal = null;
  /**
   * Bouton pour passer à la génération suivante.
   */
  private JButton nextGeneration;

  /**
   * Taille par défaut de la fenêtre.
   * largeur : 1366
   */
  private final int defaultWidth = 1366;
  /**
   * Taille par défaut de la fenêtre.
   * hauteur : 768
   */
  private final int defaultHeight = 768;

  /**
   * Label pour afficher la génération.
   */
  private JLabel generationLabel;
  /**
   * Label pour afficher le nombre de cellules.
   */
  private JLabel cellulesLabel;
  /**
   * Bouton pour démarrer ou mettre en pause le jeu.
   */
  private JButton startButton;
  /**
   * Radio bouton pour le mode classique.
   */
  private JRadioButton classiqueMode;
  /**
   * Radio bouton pour le mode day and night.
   */
  private JRadioButton dayAndNightMode;
  /**
   * Radio bouton pour le mode high life.
   */
  private JRadioButton highLifeMode;

  /**
   * Couleur des boutons.
   */
  private Color buttonColor = new Color(230, 240, 255);

  /**
   * Constructeur de la classe.
   * attache l'observateur à l'instance du jeu de la vie
   * 
   * @param jeu l'instance du jeu de la vie
   */
  public JeuDeLaVieUi(JeuDeLaVie jeu) {
    this.jeu = jeu;
    this.jeu.attacheObservateur(this);
    for (Observateur o : this.jeu.getObservateurs()) {
      if (o instanceof JeuDeLaVieTerminal) {
        this.terminal = (JeuDeLaVieTerminal) o;
        break;
      }
    }
    if (this.terminal == null) {
      this.terminal = new JeuDeLaVieTerminal(jeu);
    }

    /*
     * Selection du mode de jeu
     */
    JPanel modSelector = new JPanel();
    modSelector.setLayout(new BoxLayout(modSelector, BoxLayout.PAGE_AXIS));

    /* mode classique */
    this.classiqueMode = new JRadioButton("Classique");
    classiqueMode.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jeu.setVisiteur(new VisiteurClassique(jeu));
      }
    });

    /* mode day and night */
    this.dayAndNightMode = new JRadioButton("Day and Night");
    dayAndNightMode.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jeu.setVisiteur(new VisiteurDayAndNight(jeu));
      }
    });

    /* mode high life */
    this.highLifeMode = new JRadioButton("High Life");
    highLifeMode.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jeu.setVisiteur(new VisiteurHighLife(jeu));
      }
    });

    /* Ajout des modes de jeu */
    modSelector.add(classiqueMode);
    modSelector.add(dayAndNightMode);
    modSelector.add(highLifeMode);

    /*
     * Bouton de démarrage
     */
    this.startButton = new JButton();
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) { // inverse l'état de jeu
        jeu.setRun(!jeu.getRun());
      }
    });
    startButton.setFont(new Font("", Font.PLAIN, 40));
    startButton.setBackground(buttonColor);

    this.nextGeneration = new JButton("Next Generation");
    nextGeneration.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (jeu.getRun()) { // Si le jeu est en pause
          jeu.setRun(false); // On met le jeu en pause
        }
        jeu.calculerGenerationSuivante(); // On calcule la génération suivante
        jeu.notifieObservateur(); // On notifie les observateurs
      }
    });

    nextGeneration.setFont(new Font("", Font.PLAIN, 40));
    nextGeneration.setBackground(buttonColor);

    /* Un slider allant de 1000 a 50 pour set le délais */
    JSlider slider = new JSlider(20, 1000, 500);
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(javax.swing.event.ChangeEvent e) {
        jeu.setDelai(slider.getMaximum() - slider.getValue());
      }
    });

    /* Ajout du rendu */
    RenduJeuDeLaVie rendu = new RenduJeuDeLaVie();

    /* Un slider allant de 1 a 30 pour set le rayon */
    JSlider rayonSlider = new JSlider(1, 30, 1);
    rayonSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(javax.swing.event.ChangeEvent e) {
        rendu.setRayon(rayonSlider.getValue());
      }
    });

    /* des bouton pour depalcer la matrice */

    int pullSpeed = 50;
    int offset = 10;
    // Créez un Timer pour chaque direction
    Timer timerUp = new Timer(pullSpeed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        rendu.offsetY += offset;
        rendu.repaint();
      }
    });

    // Répétez pour timerDown, timerLeft, timerRight avec les ajustements appropriés
    JButton up = new JButton("↑");
    up.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        timerUp.start();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        timerUp.stop();
      }
    });

    Timer timerDown = new Timer(pullSpeed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        rendu.offsetY -= offset;
        rendu.repaint();
      }
    });

    JButton down = new JButton("↓");
    down.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        timerDown.start();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        timerDown.stop();
      }
    });

    Timer timerLeft = new Timer(pullSpeed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        rendu.offsetX += offset;
        rendu.repaint();
      }
    });

    JButton left = new JButton("←");
    left.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        timerLeft.start();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        timerLeft.stop();
      }
    });

    Timer timerRight = new Timer(pullSpeed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        rendu.offsetX -= offset;
        rendu.repaint();
      }
    });
    
    JButton right = new JButton("→");
    right.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        timerRight.start();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        timerRight.stop();
      }
    });


    /* on met les bouton dans une matrice */
    JPanel movePanel = new JPanel();
    movePanel.add(up);
    movePanel.add(down);
    movePanel.add(left);
    movePanel.add(right);

    JToolBar toolBar = new JToolBar();

    /* Ajout des composants */
    toolBar.add(startButton);
    toolBar.add(nextGeneration);

    toolBar.add(slider);
    toolBar.add(modSelector);

    toolBar.setFloatable(false);

    this.add(toolBar, BorderLayout.NORTH);

    /* Statistiques du jeu */
    JPanel stats = new JPanel();
    stats.setSize(100, 500);

    this.generationLabel = new JLabel();
    this.cellulesLabel = new JLabel();

    // Réinitialisation de la grille
    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jeu.reinitialiser();
        jeu.setRun(false);
      }
    });

    JButton randomFill = new JButton("Random Fill");
    randomFill.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jeu.initialiseGrille();
      }
    });

    JTextArea usage = new JTextArea(
        "Placer : Click Gauche\n"
            + "Enlever : Click Droit\n"
            + "Zoom : Molette\n"
            + "Déplacer : Flèches\n"
            + "Rayon : Slider bas\n"
            + "Vitesse : Slider haut\n",
        6, 16);
    usage.setEditable(false);

    stats.add(generationLabel);
    stats.add(cellulesLabel);

    stats.add(new JSeparator(SwingConstants.HORIZONTAL));
    stats.add(usage);
    stats.add(new JSeparator(SwingConstants.HORIZONTAL));
    stats.add(rayonSlider);
    stats.add(movePanel);
    stats.add(new JSeparator(SwingConstants.HORIZONTAL));
    stats.add(resetButton);
    JTextArea density = new JTextArea("Densité : ", 1, 1);
    density.setEditable(false);
    stats.add(density);
    JSlider densitySlider = new JSlider(0, 100, 50);
    densitySlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(javax.swing.event.ChangeEvent e) {
        jeu.setDensity((Double) (densitySlider.getValue() / 100.0));
      }
    });
    stats.add(densitySlider);
    stats.add(randomFill);
    stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
    this.add(stats, BorderLayout.WEST);

    this.add(rendu, BorderLayout.CENTER);

    this.setSize(defaultWidth, defaultHeight);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * Méthode permettant de dessiner la grille.
   */
  @Override
  public void actualise() {
    this.generationLabel.setText("Generation : " + this.terminal.getGeneration());
    this.cellulesLabel.setText("Cellules : " + this.terminal.getNbCellules());

    if (jeu.getRun()) {
      this.startButton.setText("Pause");
    } else {
      this.startButton.setText("Start");
    }

    Visiteur v = jeu.getVisiteur();
    if (v instanceof VisiteurClassique) {
      this.classiqueMode.setSelected(true);
      this.dayAndNightMode.setSelected(false);
      this.highLifeMode.setSelected(false);
    } else if (v instanceof VisiteurDayAndNight) {
      this.dayAndNightMode.setSelected(true);
      this.classiqueMode.setSelected(false);
      this.highLifeMode.setSelected(false);
    } else if (v instanceof VisiteurHighLife) {
      this.highLifeMode.setSelected(true);
      this.classiqueMode.setSelected(false);
      this.dayAndNightMode.setSelected(false);
    }

    this.repaint();
  }

  /**
   * Classe représentant le rendu du jeu de la vie.
   * utilisée pour appliquer le pattern Observer
   * 
   * @see Observateur
   * @see JPanel
   * @see MouseWheelListener
   * @see MouseMotionListener
   * @see MouseListener
   */
  public class RenduJeuDeLaVie extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

    /** Décalage en x. */
    private int offsetX;
    /** Décalage en y. */
    private int offsetY;
    /** Dernier bouton pressé. */
    private int lastPressed;
    /** Échelle. */
    private double scale = 10;
    /** Dernière commande. */
    private Commande lastCommande;
    /** Rayon d'action des action. */
    private int rayon = 1;

    /** Coordonner x de la cellule pointer par la souris. */
    private int celluleX;
    /** Coordonner y de la cellule pointer par la souris. */
    private int celluleY;

    /**
     * Constructeur de la classe.
     */
    RenduJeuDeLaVie() {
      this.setVisible(true);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addMouseWheelListener(this);
      // commence au centre
      offsetX = - (int) (jeu.getxmax() * scale / 2);
      offsetY = - (int) (jeu.getymax() * scale / 2);
    }

    /**
     * Méthode permettant de dessiner la grille.
     * 
     * @param g l'instance de Graphics
     */
    @Override
    public void paint(Graphics g) {
      super.paint(g);

      for (int x = 0; x < jeu.getxmax(); x++) {
        for (int y = 0; y < jeu.getymax(); y++) {

          int tailleZone = (int) (scale * 0.95);

          int cx = (int) (x * scale + offsetX);
          if (cx + tailleZone < 0 || cx > this.getWidth()) {
            continue;
          }
          int cy = (int) (y * scale + offsetY);
          if (cy + tailleZone < 0 || cy > this.getWidth()) {
            continue;
          }
          if (jeu.getGrillexy(x, y).estVivante()) {
            g.fillRect(cx, cy, tailleZone, tailleZone);
          }
        }
      }

      g.drawRect(offsetX, offsetY, (int) (jeu.getxmax() * scale), (int) (jeu.getymax() * scale));
    }

    /**
     * Méthode permettant de gérer le zoom via la molette de la souris.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      double oldScale = this.scale;

      // On change la taille multiplicativement, car c'est beaucoup plus agréable
      this.scale *= e.getWheelRotation() > 0 ? (this.scale < 1.2 ? 1 : 0.9) : (this.scale > 200 ? 1 : 1.1);

      // L'offset change pour que l'on zoom en centrant sur le curseur
      offsetX -= ((e.getX() - offsetX) * ((this.scale / oldScale) - 1));
      offsetY -= ((e.getY() - offsetY) * ((this.scale / oldScale) - 1));
      repaint();
    }

    /**
     * Ajoute une commande manuelle et l'execute.
     * 
     * @param e l'instance de MouseEvent
     * @param c la commande a appliquer
     */
    public void commandeManuelle(MouseEvent e, Commande c) {
      if (lastCommande == null || !lastCommande.equals(c)
          && celluleX >= 0 && celluleY >= 0
          && celluleX < jeu.getxmax() && celluleY < jeu.getymax()) {
        lastCommande = c;
        c.executer();
      }
    }

    /**
     * Setter du rayon.
     * si le rayon est entre 0 et 30
     * sinon on ne change pas le rayon
     * 
     * @param rayon le rayon a set
     */
    public void setRayon(int rayon) {
      if (rayon > 0 && rayon < 30) {
        this.rayon = rayon;
      }
    }

    /**
     * Méthode permettant d'actualiser la cellule pointer par la souris.
     * 
     * @param e l'instance de MouseEvent
     */
    public void actualiserCellule(MouseEvent e) {

      celluleX = (int) ((e.getX() - offsetX) / scale);
      celluleY = (int) ((e.getY() - offsetY) / scale);
    }

    /**
     * Méthode permettant de gérer les événements de la souris en cliquant.
     * 
     * @param lastPressed le dernier bouton pressé
     * @param e l'instance de MouseEvent
     */
    private void gestionClick(int lastPressed, MouseEvent e) {
      if (lastPressed == MouseEvent.BUTTON1) { // Si on clique gauche on ajoute des cellules
        for (int i = -rayon; i <= rayon; i++) {
          for (int j = -rayon; j <= rayon; j++) {
            if ((i * i + j * j) <= (rayon * rayon)) {
              commandeManuelle(e, new CommandeVit(jeu.getGrillexy(celluleX + i, celluleY + j)));
            }
          }
        }
      }

      if (lastPressed == MouseEvent.BUTTON3) { // Si on clique droit on enlève des cellules
        for (int i = -rayon; i <= rayon; i++) {
          for (int j = -rayon; j <= rayon; j++) {
            if ((i * i + j * j) <= (rayon * rayon)) {
              commandeManuelle(e, new CommandeMeurt(jeu.getGrillexy(celluleX + i, celluleY + j)));
            }
          }
        }
      }
    }
      

    /**
     * Méthode permettant de gérer les événements de la souris en cliquant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
      lastPressed = e.getButton();
      actualiserCellule(e);

      gestionClick(lastPressed, e);

      repaint();
    }

    /**
     * Méthode permettant de gérer les événements de la souris en drag.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {

      actualiserCellule(e);

      gestionClick(lastPressed, e);

      repaint();
    }

    /**
     * Méthode permettant de gérer les événements de la souris en relâchant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Méthode permettant de gérer les événements de la souris en entrant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Méthode permettant de gérer les événements de la souris en sortant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Méthode permettant de gérer les événements de la souris en cliquant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Méthode permettant de gérer les événements de la souris en bougeant.
     * 
     * @param e l'instance de MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }
  }
}