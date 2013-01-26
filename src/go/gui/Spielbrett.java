package go.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * Spielbrett-GUI verwaltet das Brett und setzt die Steine endgültig
 * 
 * @author sopr051 sopr054
 * 
 */
@SuppressWarnings("serial")
public class Spielbrett extends JLayeredPane {

	private javax.swing.JLabel lblSpielbrettBild;
	private int groesse;
	private JLabel[][] feld;
	private JLabel[][] feldMarkierung;
	private JLabel brett;
	private JLabel brettMarkierung;
	private boolean markieren;
	private boolean simulation;

	/**
	 * Konstruktor initialisiert die Komponenten
	 * 
	 * @param groesse
	 *            Spiefeldbrettgröße
	 * @param main
	 *            MainGUI, die alle Aktionen abfängt
	 * @param simulation
	 *            Für Simulationen
	 */
	public Spielbrett(int groesse, Main main, boolean simulation) {
		super();
		this.simulation = simulation;
		this.groesse = groesse;
		this.initComponents(main);
	}

	private void initComponents(Main main) {
		lblSpielbrettBild = new javax.swing.JLabel();

		lblSpielbrettBild
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		if (this.simulation) {
			lblSpielbrettBild
					.setIcon(new javax.swing.ImageIcon("img/feldGo9.gif"));
			((ImageIcon) lblSpielbrettBild.getIcon()).getImage().flush();
			lblSpielbrettBild
					.setIcon(new javax.swing.ImageIcon("img/feldGo9.gif"));
		} else {
			lblSpielbrettBild.setIcon(new javax.swing.ImageIcon("gif/introGo"
					+ groesse + ".gif"));
			((ImageIcon) lblSpielbrettBild.getIcon()).getImage().flush();
			lblSpielbrettBild.setIcon(new javax.swing.ImageIcon("gif/introGo"
					+ groesse + ".gif"));
		}
		lblSpielbrettBild.setBounds(0, 0, 500, 500);
		int gridgroesse, blockgroesse;
		if (groesse == 9) {
			gridgroesse = 375; // ein block =
			// 41,6666666666666666666666666666666666666666..........
			blockgroesse = 42;
		} else if (groesse == 13) {
			gridgroesse = 412; // ein block = 31,25
			blockgroesse = 32;
		} else if (groesse == 19) {
			gridgroesse = 437; // ein block= 22,7272727272727272727272727272
			blockgroesse = 23;
		} else {
			gridgroesse = 0;
			blockgroesse = 0;
		}
		brett = new JLabel();
		this.add(lblSpielbrettBild, javax.swing.JLayeredPane.DEFAULT_LAYER);
		this.feld = new JLabel[groesse + 1][groesse + 1];
		for (char c = 'a', i = 1; i <= groesse; i++, c++) {
			for (int j = 1; j <= groesse; j++) {
				feld[i][j] = new JLabel();
				feld[i][j].setName(c + "" + j);
				if (!this.simulation) {
					feld[i][j].addMouseListener(main);
				}
				brett.add(feld[i][j].getName(), feld[i][j]);
				feld[i][j].setBounds((i - 1) * blockgroesse,
						((j - 1) * blockgroesse) - (j - 1) / 2, blockgroesse,
						blockgroesse);
			}
		}
		brettMarkierung = new JLabel();
		this.feldMarkierung = new JLabel[groesse + 1][groesse + 1];
		for (char c = 'a', i = 1; i <= groesse; i++, c++) {
			for (int j = 1; j <= groesse; j++) {
				feldMarkierung[i][j] = new JLabel();
				feldMarkierung[i][j].setName(c + "" + j);
				feldMarkierung[i][j].addMouseListener(main);
				brettMarkierung.add(feldMarkierung[i][j].getName(),
						feldMarkierung[i][j]);
				feldMarkierung[i][j].setBounds((i - 1) * blockgroesse,
						((j - 1) * blockgroesse) - (j - 1) / 2, blockgroesse,
						blockgroesse);
			}
		}
		if (groesse == 19) {
			brett.setBounds((500 - gridgroesse) / 2,
					(500 - gridgroesse) / 2 + 6, gridgroesse, gridgroesse);
			brettMarkierung.setBounds((500 - gridgroesse) / 2,
					(500 - gridgroesse) / 2 + 6, gridgroesse, gridgroesse);
		} else {
			brett.setBounds((500 - gridgroesse) / 2, (500 - gridgroesse) / 2,
					gridgroesse, gridgroesse);
			brettMarkierung.setBounds((500 - gridgroesse) / 2,
					(500 - gridgroesse) / 2, gridgroesse, gridgroesse);
		}
		this.add(brett, JLayeredPane.DRAG_LAYER);
		this.add(brettMarkierung, JLayeredPane.MODAL_LAYER);
	}

	/**
	 * führt das update auf dem Spielbrett nur endgültig aus, welches die
	 * Main-GUI empfängt
	 * 
	 * @param s
	 *            Spielbrett, welches dargestellt/aktualisiert werden soll
	 * @param o
	 *            ungenutzt
	 */
	public void update(go.Spielbrett s, Object o) {
		//System.out.println("update");
		for (int i = 1; i <= groesse; i++) {
			for (int j = 1; j <= groesse; j++) {
				// alle icons flushen
				if ((ImageIcon) feld[i][j].getIcon() != null) {
					((ImageIcon) feld[i][j].getIcon()).getImage().flush();
				}
				if(((ImageIcon) feldMarkierung[i][j].getIcon() !=null)) {
					((ImageIcon) feldMarkierung[i][j].getIcon()).getImage().flush();
				}
			}
		}
		for (int i = 1; i <= groesse; i++) {
			for (int j = 1; j <= groesse; j++) {
				// nur Steine neu malen, wenn auch welche gesetzt werden können
				if (s.gibGeaendert(i, j) && !this.markieren) {
					feld[i][j].setIcon(gibSteinZuZustand(s.gib(new go.Position(
							i, j))));
					s.setzeGeaendert(false, i, j);
				} else {
					feld[i][j].setIcon(gibSteinZuZustandStat(s
							.gib(new go.Position(i, j))));
				}
			}
		}
		// Wir wollen ja ein bisschen performant sein, daher nur Markieren,
		// wenn auch am Ende
		for (int i = 1; i <= groesse; i++) {
			for (int j = 1; j <= groesse; j++) {
				if (s.gibGeaendert(i, j)) {
					feldMarkierung[i][j].setIcon(gibSchraffierungZuZustand(s
							.gibMarkiert(new go.Position(i, j))));
					s.setzeGeaendert(false, i, j);
				} else {
					feldMarkierung[i][j]
							.setIcon(gibSchraffierungZuZustandStat(s
									.gibMarkiert(new go.Position(i, j))));
				}
			}
		}
		this.repaint();
	}

	/**
	 * Gibt ein ImageIcon zu go.Zustand zurück.
	 * 
	 * @param s
	 *            der Zustand, der dargestellt werden soll.
	 * @return weiss, schwarz oder null.
	 */
	private ImageIcon gibSteinZuZustandStat(go.Zustand s) {
		if (s.equals(go.Zustand.weiss)) {
			return new ImageIcon("img/weiss" + groesse + ".gif");
		} else if (s.equals(go.Zustand.schwarz)) {
			return new ImageIcon("img/schwarz" + groesse + ".gif");
		} else
			return null;
	}

	/**
	 * Gibt ein ImageIcon zu go.Zustand zurück.
	 * 
	 * @param s
	 *            der Zustand, der dargestellt werden soll.
	 * @return weiss, schwarz oder null.
	 */
	private ImageIcon gibSteinZuZustand(go.Zustand s) {
		if (s.equals(go.Zustand.weiss)) {
			ImageIcon icon = new ImageIcon("gif/weiss" + groesse + ".gif");
			// gifcounter++;
			return icon;
		} else if (s.equals(go.Zustand.schwarz)) {
			ImageIcon icon = new ImageIcon("gif/schwarz" + groesse + ".gif");
			// gifcounter++;
			return icon;
		} else
			return null;
	}

	private ImageIcon gibSchraffierungZuZustand(go.Zustand s) {
		if (s.equals(go.Zustand.weiss)) {
			return new ImageIcon("gif/markiertweiss" + groesse + ".gif");
		} else if (s.equals(go.Zustand.schwarz)) {
			return new ImageIcon("gif/markiertschwarz" + groesse + ".gif");
		} else
			return null;
	}

	private ImageIcon gibSchraffierungZuZustandStat(go.Zustand s) {
		if (s.equals(go.Zustand.weiss)) {
			return new ImageIcon("img/markiertweiss" + groesse + ".gif");
		} else if (s.equals(go.Zustand.schwarz)) {
			return new ImageIcon("img/markiertschwarz" + groesse + ".gif");
		} else
			return null;
	}

	public void setzeMarkieren(boolean markiere) {
		this.markieren = markiere;
	}
}
