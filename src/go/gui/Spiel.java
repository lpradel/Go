/*
 * Spiel.java
 *
 * Created on Feb 24, 2011, 11:44:48 AM
 */

package go.gui;

import javax.swing.ImageIcon;

/**
 * Spiel stellt die Spieloberfläche dar. Für das Spielbrett selbst ist die
 * Klasse go.gui.Spielbrett zuständig.
 * 
 * @author sopr051
 */
@SuppressWarnings("serial")
public class Spiel extends javax.swing.JPanel {
	private String spieler1Name;
	private String spieler2Name;
	private int groesse;

	private javax.swing.JButton btnMenu;
	private javax.swing.JButton btnRedo;
	private javax.swing.JButton btnSpieler1Aufgeben;
	private javax.swing.JButton btnSpieler1Passen;
	private javax.swing.JButton btnSpieler2Aufgeben;
	private javax.swing.JButton btnSpieler2Passen;
	private javax.swing.JButton btnUndo;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JLabel lblNachrichten;
	private javax.swing.JLabel lblSpieler1Name;
	private javax.swing.JLabel lblSpieler1Punkte;
	private javax.swing.JLabel lblSpieler1PunkteLabel;
	private javax.swing.JLabel lblSpieler1SteinFarbe;
	private javax.swing.JLabel lblSpieler2Name;
	private javax.swing.JLabel lblSpieler2Punkte;
	private javax.swing.JLabel lblSpieler2PunkteLabel;
	private javax.swing.JLabel lblSpieler2SteinFarbe;
	private Spielbrett spielbrett;
	private javax.swing.JPanel pnlSpieler1;
	private javax.swing.JPanel pnlSpieler2;

	/**
	 * Erzeugt eine neue Spielview.
	 * 
	 * @param spieler1Name
	 *            Name des ersten Spielers.
	 * @param spieler2Name
	 *            Name des zweiten Spielers.
	 * @param groesse
	 *            Größe des Spielbrettes.
	 * @param main
	 *            Main-GUI übergeben, damit diese als Listener registriert wird.
	 */
	public Spiel(String spieler1Name, String spieler2Name, int groesse,
			Main main) {
		this.spieler1Name = spieler1Name;
		this.spieler2Name = spieler2Name;
		this.groesse = groesse;
		this.initComponents(main);
		this.repaint();
		this.setVisible(true);
	}

	/**
	 * Initialisert alle Komponenten.
	 * 
	 * @param main
	 *            Main-GUI übergeben, damit diese als Listener registriert wird.
	 */
	private void initComponents(Main main) {

		btnUndo = new javax.swing.JButton();
		btnRedo = new javax.swing.JButton();
		btnMenu = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		pnlSpieler1 = new javax.swing.JPanel();
		lblSpieler1Name = new javax.swing.JLabel();
		btnSpieler1Passen = new javax.swing.JButton();
		btnSpieler1Aufgeben = new javax.swing.JButton();
		lblSpieler1PunkteLabel = new javax.swing.JLabel();
		lblSpieler1Punkte = new javax.swing.JLabel();
		lblSpieler1SteinFarbe = new javax.swing.JLabel();
		pnlSpieler2 = new javax.swing.JPanel();
		lblSpieler2Name = new javax.swing.JLabel();
		btnSpieler2Passen = new javax.swing.JButton();
		btnSpieler2Aufgeben = new javax.swing.JButton();
		lblSpieler2PunkteLabel = new javax.swing.JLabel();
		lblSpieler2Punkte = new javax.swing.JLabel();
		lblSpieler2SteinFarbe = new javax.swing.JLabel();
		lblNachrichten = new javax.swing.JLabel();
		spielbrett = new Spielbrett(this.groesse, main, false);

		setMaximumSize(new java.awt.Dimension(800, 580));
		setMinimumSize(new java.awt.Dimension(800, 580));
		setPreferredSize(new java.awt.Dimension(800, 580));

		btnUndo.setText("Undo");
		btnUndo.setPreferredSize(new java.awt.Dimension(150, 30));
		btnUndo.addActionListener(main);
		btnUndo.setActionCommand("Spiel_Undo");

		btnRedo.setText("Redo");
		btnRedo.setPreferredSize(new java.awt.Dimension(150, 30));
		btnRedo.addActionListener(main);
		btnRedo.setActionCommand("Spiel_Redo");

		btnMenu.setText("Menü (Esc)");
		btnMenu.setMaximumSize(new java.awt.Dimension(90, 30));
		btnMenu.setMinimumSize(new java.awt.Dimension(90, 30));
		btnMenu.setPreferredSize(new java.awt.Dimension(90, 30));
		btnMenu.addActionListener(main);
		btnMenu.setActionCommand("Spiel_Menue");

		pnlSpieler1.setBorder(null);

		lblSpieler1Name
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler1Name.setText(spieler1Name);
		lblSpieler1Name
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		btnSpieler1Passen.setText("Passen");
		btnSpieler1Passen.setEnabled(false);
		btnSpieler1Passen.addActionListener(main);
		btnSpieler1Passen.setActionCommand("Spiel_Passen");

		btnSpieler1Aufgeben.setText("Aufgeben");
		btnSpieler1Aufgeben.setEnabled(false);
		btnSpieler1Aufgeben.addActionListener(main);
		btnSpieler1Aufgeben.setActionCommand("Spiel_Aufgeben");

		lblSpieler1PunkteLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler1PunkteLabel.setText("Punkte");

		lblSpieler1Punkte.setFont(new java.awt.Font("DejaVu Sans", 0, 24));
		lblSpieler1Punkte
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler1Punkte.setText("0");

		lblSpieler1SteinFarbe
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler1SteinFarbe.setIcon(new ImageIcon("img/schwarz"
				+ groesse + ".gif"));
		lblSpieler1SteinFarbe
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout pnlSpieler1Layout = new javax.swing.GroupLayout(
				pnlSpieler1);
		pnlSpieler1.setLayout(pnlSpieler1Layout);
		pnlSpieler1Layout
				.setHorizontalGroup(pnlSpieler1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlSpieler1Layout
										.createSequentialGroup()
										.addGroup(
												pnlSpieler1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlSpieler1Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				pnlSpieler1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lblSpieler1Name,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblSpieler1PunkteLabel,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblSpieler1Punkte,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnSpieler1Passen,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnSpieler1Aufgeben,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)))
														.addComponent(
																lblSpieler1SteinFarbe,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																120,
																Short.MAX_VALUE))
										.addContainerGap()));
		pnlSpieler1Layout
				.setVerticalGroup(pnlSpieler1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlSpieler1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblSpieler1Name)
										.addGap(18, 18, 18)
										.addComponent(lblSpieler1PunkteLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(lblSpieler1Punkte)
										.addGap(18, 18, 18)
										.addComponent(btnSpieler1Passen)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												221, Short.MAX_VALUE)
										.addComponent(
												lblSpieler1SteinFarbe,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												96,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(
												btnSpieler1Aufgeben)
										.addContainerGap()));

		pnlSpieler2.setBorder(null);
		pnlSpieler2.setPreferredSize(new java.awt.Dimension(198, 522));

		lblSpieler2Name
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler2Name.setText(spieler2Name);
		lblSpieler2Name
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		btnSpieler2Passen.setText("Passen");
		btnSpieler2Passen.setEnabled(false);
		btnSpieler2Passen.addActionListener(main);
		btnSpieler2Passen.setActionCommand("Spiel_Passen");

		btnSpieler2Aufgeben.setText("Aufgeben");
		btnSpieler2Aufgeben.setEnabled(false);
		btnSpieler2Aufgeben.addActionListener(main);
		btnSpieler2Aufgeben.setActionCommand("Spiel_Aufgeben");

		lblSpieler2PunkteLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler2PunkteLabel.setText("Punkte");

		lblSpieler2Punkte.setFont(new java.awt.Font("DejaVu Sans", 0, 24));
		lblSpieler2Punkte
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler2Punkte.setText("0");

		lblSpieler2SteinFarbe
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSpieler2SteinFarbe.setIcon(new ImageIcon("img/weiss"
				+ groesse + ".gif"));
		lblSpieler2SteinFarbe
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		lblSpieler2SteinFarbe.setMaximumSize(new java.awt.Dimension(50, 50));
		lblSpieler2SteinFarbe.setMinimumSize(new java.awt.Dimension(50, 50));

		javax.swing.GroupLayout pnlSpieler2Layout = new javax.swing.GroupLayout(
				pnlSpieler2);
		pnlSpieler2.setLayout(pnlSpieler2Layout);
		pnlSpieler2Layout
				.setHorizontalGroup(pnlSpieler2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlSpieler2Layout
										.createSequentialGroup()
										.addGroup(
												pnlSpieler2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlSpieler2Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				pnlSpieler2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lblSpieler2Name,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblSpieler2PunkteLabel,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblSpieler2Punkte,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnSpieler2Passen,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnSpieler2Aufgeben,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								108,
																								Short.MAX_VALUE)))
														.addComponent(
																lblSpieler2SteinFarbe,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																120,
																Short.MAX_VALUE))
										.addContainerGap()));
		pnlSpieler2Layout
				.setVerticalGroup(pnlSpieler2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlSpieler2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblSpieler2Name)
										.addGap(18, 18, 18)
										.addComponent(lblSpieler2PunkteLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(lblSpieler2Punkte)
										.addGap(18, 18, 18)
										.addComponent(btnSpieler2Passen)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												221, Short.MAX_VALUE)
										.addComponent(
												lblSpieler2SteinFarbe,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												96,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(
												btnSpieler2Aufgeben)
										.addContainerGap()));

		lblNachrichten
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSeparator1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																776,
																Short.MAX_VALUE)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				btnUndo,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				btnRedo,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblNachrichten,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				476,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btnMenu,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				118,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				pnlSpieler1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spielbrett,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				500,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				pnlSpieler2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				132,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnUndo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnRedo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnMenu,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblNachrichten))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																spielbrett,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																522,
																Short.MAX_VALUE)
														.addComponent(
																pnlSpieler1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																pnlSpieler2,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
	}

	/**
	 * Verbirgt die Buttons für Passen, da diese in Steinschlag nicht erlaubt
	 * sind.
	 */
	public void buttonsFuerSteinschlagKonfigurieren() {
		this.btnSpieler1Passen.setVisible(false);
		this.btnSpieler2Passen.setVisible(false);
	}

	public void undoButtonAktivieren(boolean aktiviert) {
		this.btnUndo.setEnabled(aktiviert);
	}

	public void redoButtonAktivieren(boolean aktiviert) {
		this.btnRedo.setEnabled(aktiviert);
	}

	public void setzePunkteSpieler1(int punkte) {
		this.lblSpieler1Punkte.setText("" + punkte);
	}

	public void setzePunkteSpieler2(int punkte) {
		this.lblSpieler2Punkte.setText("" + punkte);
	}

	public void spielzugNichtZulaessig(go.RegelBruch r) {
		// Je nach enum ausgeben
		String fehlermeldung = "";

		switch (r) {
		case feldBesetzt:
			fehlermeldung = "Dieses Feld ist schon besetzt!";
			break;
		case ko:
			fehlermeldung = "KO-Regel: Es würde die selbe Spielsituation entstehen!";
			break;
		case selbstMord:
			fehlermeldung = "\"Selbstmord\" eines Steins ist nicht erlaubt!";
			break;
		default:
			break;
		}

		/* Anzeigen der Meldung */
		this.lblNachrichten.setText(fehlermeldung);
	}

	/**
	 * Führt den Spielerwechsel auf der Oberfläche aus (Buttons werden
	 * enabled/disabled, Nachricht wird angezeigt, wer am Zug ist.
	 */
	private go.Spieler letzterSpieler;

	public void spielerWechsel(go.Spieler spieler) {
		if (spieler != null) {
			this.letzterSpieler = spieler;
			this.btnSpieler1Aufgeben.setEnabled(false);
			this.btnSpieler1Passen.setEnabled(false);
			this.btnSpieler2Aufgeben.setEnabled(false);
			this.btnSpieler2Passen.setEnabled(false);
			if (spieler.gibTyp() == go.SpielerTyp.mensch) {
				if (spieler.gibFarbe() == go.Zustand.schwarz) {
					this.btnSpieler1Aufgeben.setEnabled(true);
					this.btnSpieler1Passen.setEnabled(true);
					this.lblNachrichten.setText(spieler1Name + " ist am Zug.");
				} else {
					this.btnSpieler2Aufgeben.setEnabled(true);
					this.btnSpieler2Passen.setEnabled(true);
					this.lblNachrichten.setText(spieler2Name + " ist am Zug.");
				}
			} else {
				if (!this.btnRedo.isEnabled()) {
					this.lblNachrichten.setText(spieler.gibName()
							+ " denkt gerade ...");
				} else {
					// Bei Redo soll nicht "denkt gerade" angezeigt werden!
					this.lblNachrichten.setText("Für " + spieler.gibName()
							+ "\'s Zug auf \"Redo\" klicken.");
				}
			}
		}
	}

	public void setzeNachricht(String s) {
		this.lblNachrichten.setText(s);
	}

	public void spielEnde() {
		this.btnMenu.setActionCommand("Spiel_MenueEnde");
		this.btnSpieler1Passen.setVisible(false);
		this.btnSpieler1Aufgeben.setVisible(false);
		this.btnSpieler2Passen.setVisible(false);
		this.btnSpieler2Aufgeben.setVisible(false);
	}

	public void sperreButtons() {
		this.btnMenu.setEnabled(false);
		this.btnSpieler1Aufgeben.setEnabled(false);
		this.btnSpieler1Passen.setEnabled(false);
		this.btnSpieler2Aufgeben.setEnabled(false);
		this.btnSpieler2Passen.setEnabled(false);
		this.btnUndo.setEnabled(false);
		this.btnRedo.setEnabled(false);
	}

	public void entsperreButtons() {
		this.btnMenu.setEnabled(true);
		this.spielerWechsel(letzterSpieler);
	}

	/**
	 * Reicht das Update, dass von der Main-GUI abgefangen wird an das
	 * Spielbrett weiter.
	 * 
	 * @param s
	 *            Das Spielbrett des Models, welches dargestellt werden soll.
	 * @param o
	 *            nicht benutzt.
	 */
	public void update(go.Spielbrett s, Object o) {
		this.spielbrett.update(s, o);
	}

	/**
	 * In der Situation der Uneinigkeit sollen einfach ein paar Buttons
	 * ausgeblendet werden
	 */
	public void setzeButtonsKlassisch() {
		this.btnUndo.setText("Uneinig");
		this.btnUndo.setActionCommand("Spiel_uneinig");
		this.btnUndo.setEnabled(true);
		this.btnRedo.setText("Weiter");
		this.btnRedo.setActionCommand("Spiel_markierenWeiss");
		this.btnRedo.setEnabled(true);
		this.btnSpieler1Passen.setVisible(false);
		this.btnSpieler2Passen.setVisible(false);
		this.spielbrett.setzeMarkieren(true);
	}

	/**
	 * Falls nach der Uneinigkeit weitergespielt wird
	 */
	public void setzeButtonsWeiterspielen() {
		this.btnUndo.setText("Undo");
		this.btnUndo.setActionCommand("Spiel_Undo");
		this.btnRedo.setText("Redo");
		this.btnRedo.setActionCommand("Spiel_Redo");
		this.btnSpieler1Passen.setVisible(true);
		this.btnSpieler2Passen.setVisible(true);
		this.spielbrett.setzeMarkieren(false);
	}

	public void setUneinigEnabled() {
		btnUndo.setEnabled(false);
		btnUndo.setVisible(false);
	}

	public void setRedoCommand(String s) {
		// hammer dirty aber mir viel ohne GUI Änderung nichts schöneres ein
		btnRedo.setActionCommand(s);
	}
}
