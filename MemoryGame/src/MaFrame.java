
/*
*
*@author SAAL Fatma
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MaFrame extends JFrame {
	/**
	 * 
	 */
	int nbImages = 16;
	int imagesRoutournees = 0;
	int numClicks;
	Timer myTimer;
	int indiceCourant;
	int indiceClickAvant;
	int score = 0;
	int NbEssaye;
	boolean estFinie = false;

	/*
	 * le Tableau qui contient les boutons de la grille
	 */
	static JButton Bouttons[] = new JButton[16];

	private static final long serialVersionUID = 1L;
	/* ListeIcone c'est la liste qui contient les images du jeu */
//il faut peut etre utiliser a la place du premier arrayList un List
	static final ArrayList<ImageIcon> ListeIcone = new ArrayList<ImageIcon>() {
		{
			add(new ImageIcon("images/Bird.gif"));
			add(new ImageIcon("images/Bird2.gif"));
			add(new ImageIcon("images/Cat.gif"));
			add(new ImageIcon("images/Cat2.gif"));
			add(new ImageIcon("images/Dog.gif"));
			add(new ImageIcon("images/Dog2.gif"));
			add(new ImageIcon("images/Rabbit.gif"));
			add(new ImageIcon("images/Pig.gif"));

		}
	};

	/*
	 * Cette méthode permet de retourner le bouton a l'indice i
	 */
	public static JButton getBoutton(int i) {
		return Bouttons[i];
	}

	// les boutons Recommencer Quitter et a propos
	JButton btnRecommencer = new JButton(new ImageIcon("images/recommencer.gif"));
	JButton btnQuitter = new JButton(new ImageIcon("images/quitter.gif"));
	JButton Apropos = new JButton("A propos");

	/* image du point d'interrogation */
	ImageIcon IconeFermer = new ImageIcon("images/Inconnu.gif");
	// le label qui contient le score a afficher
	JLabel label = new JLabel("   Score : " + score);
	// le label qui contient le nombre d'essayes a afficher
	JLabel labelNbEssaye = new JLabel("Nombre d'essayes : " + NbEssaye);

	// creation du menu
	JMenuBar menu = new JMenuBar(); // le menuBar
	JMenu fichier = new JMenu("Fichier");
	JMenu aide = new JMenu("Aide");
	// menuItem
	JMenuItem re = new JMenuItem();// recommencer
	JMenuItem que = new JMenuItem();// quitter
	JMenuItem apropo = new JMenuItem();// a propos

	public MaFrame() {

		setTitle("Memory");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* Menu du jeu */
		menu.add(fichier);
		menu.add(aide);
		re.setText("Recommencer");
		que.setText("Quitter");
		apropo.setText("A propos");
		fichier.add(re);
		fichier.add(que);
		aide.add(apropo);
		// shuffle permet de mélanger les images dans la liste
		Collections.shuffle(ListeIcone);

		// panel contient les 16 bouttons
		JPanel panel = new JPanel(new GridLayout(4, 4));
		// panel2 contient les bouttons recommencer quitter ,a propos et le label score
		JPanel panel2 = new JPanel(new GridBagLayout());

		panel2.add(btnRecommencer);
		panel2.add(btnQuitter);
		panel2.add(Apropos);
		panel2.add(label);

		btnRecommencer.addActionListener(new recommencer());
		btnQuitter.addActionListener(new quitter());

		re.addActionListener(new recommencer());
		que.addActionListener(new quitter());
		Apropos.addActionListener(new Apropos());
		apropo.addActionListener(new Apropos());

		for (int i = 0; i < nbImages; i++) {

			Bouttons[i] = new JButton(IconeFermer);
			Bouttons[i].addActionListener(new ImageButtonListener());

			Bouttons[i].setPreferredSize(new Dimension(60, 60));
			panel.add(Bouttons[i]);

		}
		setJMenuBar(menu);
		add(panel, BorderLayout.CENTER);
		add(panel2, BorderLayout.NORTH);
		add(labelNbEssaye, BorderLayout.SOUTH);
		labelNbEssaye.setLocation(250, 250);
		setVisible(true);
		myTimer = new Timer(1000, new TimerListener());
	}

	/*
	 * méthode qui retourne le nombre d'images de la grille
	 * 
	 * @return int nombre d'images
	 *
	 */
	public int getNbImages() {
		return this.nbImages;
	}

	/*
	 * @return le nombre d'essayes
	 */
	public int getNbEssayes() {
		return this.NbEssaye;
	}

	/*
	 * un evenement qui permet de recommencer une partie apres avoir gagné ou en
	 * cliquant sur le bouton recommencer
	 */
	private class recommencer implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == btnRecommencer || evt.getSource() == re) {
				int n = JOptionPane.showConfirmDialog(null, "Voulez vous recommencer la partie?", "Recommencer",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					new ModeleMemory();
					MaFrame m = new MaFrame();
					m.setVisible(true);
					// permet de placer la fenetre du jeu au milieu d'ecran
					Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
					m.setLocation((screen.width - 500) / 2, (screen.height - 500) / 2);
					dispose();
				}

			}
		}
	}

	/*
	 * cet evenement permet d'afficher les information qui sont disponible dans le
	 * bouton A propos
	 */
	private class Apropos implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == Apropos || evt.getSource() == apropo) {
				JOptionPane d = new JOptionPane();
				d.showMessageDialog(null,
						"Projet1 poo SAAL Fatma, L2 informatique,Université d'Artois,Faculté des sciences Jean PERRIN  ");
			}
		}
	}

	/*
	 * evenement qui permet de quitter la partie .
	 */
	private class quitter implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == btnQuitter || evt.getSource() == que) {
				int n = JOptionPane.showConfirmDialog(null, "Voulez vous quitter la partie?", "Recommencer",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					System.exit(0);
					;
				}
			}
		}
	}

	/*
	 * Un Timer qui se déclenche une fois deux images sont different , cela
	 * permettera de remettre l'image qui cache l'image souhaitée
	 */
	private class TimerListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Bouttons[indiceCourant].setIcon(IconeFermer);
			Bouttons[indiceClickAvant].setIcon(IconeFermer);
			myTimer.stop();
		}
	}

	private class ImageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			System.out.println(actionEvent.getSource());

			if (myTimer.isRunning()) {
				return;
			}
			numClicks++;
			System.out.println(numClicks);
			for (int i = 0; i < 16; i++) {
				if (actionEvent.getSource() == Bouttons[i]) {
					System.out.println("i");
					System.out.println(ModeleMemory.getValeur(i));
					if (ModeleMemory.getValeur(i) == 1) {
						Bouttons[i].setIcon(ListeIcone.get(0));
					} else if (ModeleMemory.getValeur(i) == 2) {
						Bouttons[i].setIcon(ListeIcone.get(1));
					} else if (ModeleMemory.getValeur(i) == 3) {
						Bouttons[i].setIcon(ListeIcone.get(2));
					} else if (ModeleMemory.getValeur(i) == 4) {
						Bouttons[i].setIcon(ListeIcone.get(3));
					} else if (ModeleMemory.getValeur(i) == 5) {
						Bouttons[i].setIcon(ListeIcone.get(4));
					} else if (ModeleMemory.getValeur(i) == 6) {
						Bouttons[i].setIcon(ListeIcone.get(5));
					} else if (ModeleMemory.getValeur(i) == 7) {
						Bouttons[i].setIcon(ListeIcone.get(6));
					} else if (ModeleMemory.getValeur(i) == 8) {
						Bouttons[i].setIcon(ListeIcone.get(7));
					}

					indiceCourant = i;
				}
			}

			if (numClicks % 2 == 0) {
				if (indiceCourant == indiceClickAvant) {
					numClicks -= 1;
					return;
				}
				if (ModeleMemory.getValeur(indiceCourant) != ModeleMemory.getValeur(indiceClickAvant)) {
					NbEssaye++;
					labelNbEssaye.setText("Nombre d'essayes : " + NbEssaye);
					myTimer.start();
				} else {

					score++;
					NbEssaye++;
					label.setText("   Score: " + score);
					labelNbEssaye.setText("Nombre d'essayes : " + NbEssaye);
					imagesRoutournees += 2;
					Bouttons[indiceCourant].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
					Bouttons[indiceClickAvant].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));

				}

			} else {
				indiceClickAvant = indiceCourant;
			}

			if (imagesRoutournees == 16) {

				estFinie = true;

			}

			if (estFinie) {
				JOptionPane.showMessageDialog(null, "Vous avez Gagné");
				int n = JOptionPane.showConfirmDialog(null, "Voulez vous recommencer une partie?", "Restart",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					new ModeleMemory();
					MaFrame m = new MaFrame();
					m.setVisible(true);
					Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
					m.setLocation((screen.width - 500) / 2, (screen.height - 500) / 2);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Au revoir , cliquez sur ok pour fermer la fenetre");
					System.exit(0);
				}

			}
			// System.out.println("imagesroutournes="+imagesRoutournees);
		}

	}

	public static void main(String[] args) {
		MaFrame fen = new MaFrame();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		fen.setLocation((screen.width - 500) / 2, (screen.height - 500) / 2);
	}
}
