package go.gui;

import go.Zustand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;

/*
 * NeuesSpiel.java
 *
 * Created on Mar 7, 2011, 3:37:14 PM
 */

/**
 * NeuesSpiel fragt Benutzereingaben ab: Variante, Gegener, Spielfeldgröße und Farbe ab. 
 * @author sopr051
 */
@SuppressWarnings("serial")
public class NeuesSpiel extends javax.swing.JPanel implements ActionListener {

    private javax.swing.JButton btnHauptmenue;
    private javax.swing.JButton btnStarten;
    private javax.swing.JLabel lblSpielerSpielerName;
    private javax.swing.JLabel lblSpielerPartnerName;
    private javax.swing.JPanel pnlFarbe;
    private javax.swing.JPanel pnlGegner;
    private javax.swing.JPanel pnlName;
    private javax.swing.JPanel pnlSpielart;
    private javax.swing.JPanel pnlSpielgroesse;
    private javax.swing.ButtonGroup btnGrpGegner;
    private javax.swing.ButtonGroup btnGrpFarbe;
    private javax.swing.ButtonGroup btnGrpVariante;
    private javax.swing.ButtonGroup btnGrpSpielgroesse;
    private javax.swing.JRadioButton rBtn13;
    private javax.swing.JRadioButton rBtn19;
    private javax.swing.JRadioButton rBtn9;
    private javax.swing.JRadioButton rBtnKILeicht;
    private javax.swing.JRadioButton rBtnKISchwer;
    private javax.swing.JRadioButton rBtnKlassisch;
    private javax.swing.JRadioButton rBtnSchwarz;
    private javax.swing.JRadioButton rBtnMensch;
    private javax.swing.JRadioButton rBtnSteinschlag;
    private javax.swing.JRadioButton rBtnWeiss;
    private javax.swing.JTextField txtSpielerSpielerName;
    private javax.swing.JTextField txtSpielerPartnerName;
    
    public final static int SPIELART_STEINSCHLAG = 0;
    public final static int SPIELART_KLASSISCH = 1;

    /**
     * Erzeugt ein Panel des Typs NeuesSpiel.
     * @param main Main-GUI übergeben, damit diese als Listener registriert wird.
     */
    public NeuesSpiel(Main main) {
        initComponents(main);
    }

    /**
     * Initialisiert alle Komponenten und ordnet diese auf dem Panel an.
     * @param main Main-GUI übergeben, damit diese als Listener registriert wird.
     */
    private void initComponents(Main main) {

        pnlGegner = new javax.swing.JPanel();
        rBtnMensch = new javax.swing.JRadioButton();
        rBtnKILeicht = new javax.swing.JRadioButton();
        rBtnKISchwer = new javax.swing.JRadioButton();
        btnHauptmenue = new javax.swing.JButton();
        btnStarten = new javax.swing.JButton();
        pnlSpielgroesse = new javax.swing.JPanel();
        rBtn9 = new javax.swing.JRadioButton();
        rBtn13 = new javax.swing.JRadioButton();
        rBtn19 = new javax.swing.JRadioButton();
        pnlFarbe = new javax.swing.JPanel();
        rBtnSchwarz = new javax.swing.JRadioButton();
        rBtnWeiss = new javax.swing.JRadioButton();
        pnlSpielart = new javax.swing.JPanel();
        rBtnKlassisch = new javax.swing.JRadioButton();
        rBtnSteinschlag = new javax.swing.JRadioButton();
        pnlName = new javax.swing.JPanel();
        lblSpielerSpielerName = new javax.swing.JLabel();
        txtSpielerSpielerName = new javax.swing.JTextField();
        lblSpielerPartnerName = new javax.swing.JLabel();
        txtSpielerPartnerName = new javax.swing.JTextField();

        pnlGegner.setBorder(javax.swing.BorderFactory.createTitledBorder("Gegner auswählen"));
        
        btnGrpGegner = new javax.swing.ButtonGroup();

        rBtnMensch.setText("Spieler");
        rBtnMensch.addActionListener(this);
        rBtnMensch.setActionCommand("Spieler");
        btnGrpGegner.add(rBtnMensch);

        rBtnKILeicht.setText("Computer (leicht)");
        rBtnKILeicht.addActionListener(this);
        rBtnKILeicht.setActionCommand("Spieler");
        btnGrpGegner.add(rBtnKILeicht);

        rBtnKISchwer.setText("Computer (schwer)");
        rBtnKISchwer.addActionListener(this);
        rBtnKISchwer.setActionCommand("Spieler");
        btnGrpGegner.add(rBtnKISchwer);

        javax.swing.GroupLayout pnlGegnerLayout = new javax.swing.GroupLayout(pnlGegner);
        pnlGegner.setLayout(pnlGegnerLayout);
        pnlGegnerLayout.setHorizontalGroup(
            pnlGegnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGegnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGegnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rBtnMensch)
                    .addComponent(rBtnKILeicht)
                    .addComponent(rBtnKISchwer))
                .addContainerGap(606, Short.MAX_VALUE))
        );
        pnlGegnerLayout.setVerticalGroup(
            pnlGegnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGegnerLayout.createSequentialGroup()
                .addComponent(rBtnMensch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtnKILeicht)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtnKISchwer)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnHauptmenue.setText("Zum Hauptmenü");
        btnHauptmenue.addActionListener(main);
        btnHauptmenue.setActionCommand("Zum_Hauptmenue");

        btnStarten.setText("Starten");
        btnStarten.setMaximumSize(new java.awt.Dimension(165, 29));
        btnStarten.setMinimumSize(new java.awt.Dimension(165, 29));
        btnStarten.addActionListener(main);
        btnStarten.setActionCommand("NeuesSpiel_Starten");

        pnlSpielgroesse.setBorder(javax.swing.BorderFactory.createTitledBorder("Spielgröße auswählen"));
        pnlSpielgroesse.setEnabled(false);
        
        btnGrpSpielgroesse = new javax.swing.ButtonGroup();

        rBtn9.setText("9*9");
        rBtn9.addActionListener(this);
        rBtn9.setActionCommand("Groesse");
        btnGrpSpielgroesse.add(rBtn9);

        rBtn13.setText("13*13");
        rBtn13.addActionListener(this);
        rBtn13.setActionCommand("Groesse");
        btnGrpSpielgroesse.add(rBtn13);

        rBtn19.setText("19*19");
        rBtn19.addActionListener(this);
        rBtn19.setActionCommand("Groesse");
        btnGrpSpielgroesse.add(rBtn19);

        javax.swing.GroupLayout pnlSpielgroesseLayout = new javax.swing.GroupLayout(pnlSpielgroesse);
        pnlSpielgroesse.setLayout(pnlSpielgroesseLayout);
        pnlSpielgroesseLayout.setHorizontalGroup(
            pnlSpielgroesseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpielgroesseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSpielgroesseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rBtn9)
                    .addComponent(rBtn13)
                    .addComponent(rBtn19))
                .addContainerGap(689, Short.MAX_VALUE))
        );
        pnlSpielgroesseLayout.setVerticalGroup(
            pnlSpielgroesseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpielgroesseLayout.createSequentialGroup()
                .addComponent(rBtn9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtn13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtn19))
        );

        pnlFarbe.setBorder(javax.swing.BorderFactory.createTitledBorder("Farbe von Spieler 1 auswählen"));
        
        btnGrpFarbe = new javax.swing.ButtonGroup();

        rBtnSchwarz.setText("Schwarz");
        rBtnSchwarz.addActionListener(this);
        rBtnSchwarz.setActionCommand("Farbe");
        btnGrpFarbe.add(rBtnSchwarz);

        rBtnWeiss.setText("Weiss");
        rBtnWeiss.addActionListener(this);
        rBtnWeiss.setActionCommand("Farbe");
        btnGrpFarbe.add(rBtnWeiss);

        javax.swing.GroupLayout pnlFarbeLayout = new javax.swing.GroupLayout(pnlFarbe);
        pnlFarbe.setLayout(pnlFarbeLayout);
        pnlFarbeLayout.setHorizontalGroup(
            pnlFarbeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFarbeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFarbeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rBtnSchwarz)
                    .addComponent(rBtnWeiss))
                .addContainerGap(677, Short.MAX_VALUE))
        );
        pnlFarbeLayout.setVerticalGroup(
            pnlFarbeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFarbeLayout.createSequentialGroup()
                .addComponent(rBtnSchwarz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtnWeiss)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSpielart.setBorder(javax.swing.BorderFactory.createTitledBorder("Spielart auswählen"));

        btnGrpVariante = new javax.swing.ButtonGroup();
        
        rBtnKlassisch.setText("Klassisches-Go");
        rBtnKlassisch.addActionListener(this);
        rBtnKlassisch.setActionCommand("Variante");
        btnGrpVariante.add(rBtnKlassisch);

        rBtnSteinschlag.setText("Steinschlag-Go");
        rBtnSteinschlag.addActionListener(this);
        rBtnSteinschlag.setActionCommand("Variante");
        btnGrpVariante.add(rBtnSteinschlag);
        
        javax.swing.GroupLayout pnlSpielartLayout = new javax.swing.GroupLayout(pnlSpielart);
        pnlSpielart.setLayout(pnlSpielartLayout);
        pnlSpielartLayout.setHorizontalGroup(
            pnlSpielartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpielartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSpielartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rBtnKlassisch)
                    .addComponent(rBtnSteinschlag))
                .addContainerGap(632, Short.MAX_VALUE))
        );
        pnlSpielartLayout.setVerticalGroup(
            pnlSpielartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpielartLayout.createSequentialGroup()
                .addComponent(rBtnKlassisch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtnSteinschlag)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlName.setBorder(javax.swing.BorderFactory.createTitledBorder("Name eintragen"));

        lblSpielerSpielerName.setText("Name von Spieler1:");

        lblSpielerPartnerName.setText("Name von Spieler2:");

        javax.swing.GroupLayout pnlNameLayout = new javax.swing.GroupLayout(pnlName);
        pnlName.setLayout(pnlNameLayout);
        pnlNameLayout.setHorizontalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSpielerPartnerName)
                    .addComponent(lblSpielerSpielerName))
                .addGap(18, 18, 18)
                .addGroup(pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSpielerSpielerName, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                    .addComponent(txtSpielerPartnerName, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlNameLayout.setVerticalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addGroup(pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpielerSpielerName)
                    .addComponent(txtSpielerSpielerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpielerPartnerName)
                    .addComponent(txtSpielerPartnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSpielerSpielerName.getAccessibleContext().setAccessibleName("Name von Spieler1:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGegner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFarbe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSpielart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSpielgroesse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHauptmenue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 448, Short.MAX_VALUE)
                        .addComponent(btnStarten, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSpielart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSpielgroesse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGegner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFarbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHauptmenue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStarten, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

		this.setButtonGroupEnabled(btnGrpSpielgroesse, false);
		this.setButtonGroupEnabled(btnGrpFarbe, false);
		this.setButtonGroupEnabled(btnGrpGegner, false);
		this.txtSpielerSpielerName.setEnabled(false);
		this.txtSpielerPartnerName.setEnabled(false);
		this.enableStartButton();
    }
    
    /**
	 * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels. Name
	 * Spieler 1.
	 * 
	 * @return Name von Spieler 1.
	 */
	public String gibSpieler1Name() {
		if(this.rBtnSchwarz.isSelected()){
			return this.txtSpielerSpielerName.getText();
		}
		else return this.txtSpielerPartnerName.getText();
	}
	
	/**
	 * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels. Name
	 * Spieler 2.
	 * 
	 * @return Name von Spieler 2.
	 */
	public String gibSpieler2Name() {
		if(this.rBtnWeiss.isSelected()){
			return this.txtSpielerSpielerName.getText();
		}
		else return this.txtSpielerPartnerName.getText();
	}
	
	public go.Zustand gibKIFarbe(){
		if(this.rBtnWeiss.isSelected()){
			return go.Zustand.schwarz;
		}
		else return go.Zustand.weiss;
	}

	/**
	 * Funktion zum Auslesen der Größe.
	 * 
	 * @return Größe des Spielfeldes, -1 bei Fehler.
	 */
	public int gibGroesse() {
		if (this.rBtn13.isSelected()) {
			return 13;
		} else if (this.rBtn19.isSelected()) {
			return 19;
		} else if (this.rBtn9.isSelected()) {
			return 9;
		} else {
			return -1;
		}
	}

	/**
	 * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels.
	 * 
	 * @return KI Stärke. NULL bei keiner KI.
	 */
	public go.SpielerTyp gibKI() {
		if (this.rBtnKILeicht.isSelected()) {
			return go.SpielerTyp.leicht;
		} else if (this.rBtnKISchwer.isSelected()) {
			return go.SpielerTyp.schwer;
		} else if (this.rBtnMensch.isSelected()) {
			return go.SpielerTyp.mensch;
		} else {
			return null;
		}
	}

	/**
	 * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels.
	 * Spielvariante.
	 * 
	 * @return SPIELART_STEINSCHLAG bei Steinschlag, SPIELART_KLASSISCH bei
	 *         Klassisch, -1 bei Fehler.
	 */
	public int gibVariante() {
		if (this.rBtnSteinschlag.isSelected()) {
			return this.SPIELART_STEINSCHLAG;
		} else if (this.rBtnKlassisch.isSelected()) {
			return this.SPIELART_KLASSISCH;
		} else {
			return -1;
		}
	}
	
	/**
	 * verarbeitet die ActionEvtens und Enabled und Disabled die entsprechenden Teile der GUI
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Variante")) {
			if (this.rBtnSteinschlag.isSelected()) {
				this.rBtn9.setSelected(true);
				this.setButtonGroupEnabled(btnGrpSpielgroesse, false);
				this.setButtonGroupEnabled(btnGrpFarbe, true);
				this.setButtonGroupEnabled(btnGrpGegner, true);
			} else {
				this.rBtnMensch.setSelected(true);
				this.setButtonGroupEnabled(btnGrpSpielgroesse, true);
				this.setButtonGroupEnabled(btnGrpFarbe, true);
				this.setButtonGroupEnabled(btnGrpGegner, false);
			}
		} else if (e.getActionCommand().equals("Spieler")) {
			if (this.rBtnMensch.isSelected()) {
			} else {
				if (this.rBtnKILeicht.isSelected()) {
					this.txtSpielerPartnerName.setText("Bob");
				} else if (this.rBtnKISchwer.isSelected()){
					this.txtSpielerPartnerName.setText("Alice");
				}
			}
		}
		this.enableTxtFields();		
		this.enableStartButton();
		this.setVisible(true);
	}

	/**
	 * Setzt alle (JRadio-) Buttons in der Buttongroup b auf enabled.
	 * 
	 * @param b
	 *            Die ButtonGroup, deren Wert geändert werden soll.
	 * @param enabled
	 *            Der Wert auf den Enabled gesetzt werden soll.
	 */
	private void setButtonGroupEnabled(javax.swing.ButtonGroup b,
			boolean enabled) {
		Enumeration<AbstractButton> e = b.getElements();
		while (e.hasMoreElements()) {
			e.nextElement().setEnabled(enabled);
		}
	}
	
	/**
	 * Setzt die Textfelder txtSpieler1Name und txt Spieler2Name auf en/disabled.
	 */
	private void enableTxtFields(){
		if (this.rBtnMensch.isSelected()) {
			this.txtSpielerPartnerName.setEnabled(true);
			if(this.txtSpielerPartnerName.getText().equals("Alice")||this.txtSpielerPartnerName.getText().equals("Bob")){
				this.txtSpielerPartnerName.setText("");	
			}
		} else {
			this.txtSpielerPartnerName.setEnabled(false);
		}
		this.txtSpielerSpielerName.setEnabled(true);
	}

	/**
	 * Prüft, ob der btnStarten enabled sein muss oder nicht und tut dies.
	 */
	private void enableStartButton() {
		if (this.isButtonGroupSelected(btnGrpFarbe)
				&& this.isButtonGroupSelected(btnGrpGegner)
				&& this.isButtonGroupSelected(btnGrpSpielgroesse)
				&& this.isButtonGroupSelected(btnGrpVariante)) {
			this.btnStarten.setEnabled(true);
		} else {
			this.btnStarten.setEnabled(false);
		}
	}

	/**
	 * Prüft ob irgendein Element der von b das Attribut Selected gesetzt hat.
	 * 
	 * @param b
	 *            ButtonGroup, die überprüft werden soll.
	 * @return true, wenn ein einziges Element in der ButtonGroup das Attribut
	 *         Selected gesetzt hat. False sonst.
	 */
	private boolean isButtonGroupSelected(javax.swing.ButtonGroup b) {
		Enumeration<AbstractButton> e = b.getElements();
		while (e.hasMoreElements()) {
			if (e.nextElement().isSelected())
				return true;
		}
		return false;
	}
}
