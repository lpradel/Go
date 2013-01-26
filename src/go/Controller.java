package go;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Der absolute Main-Controller, kann alles, weiß alles, vor allem KI verwalten
 * 
 */
public class Controller implements Observer {

	/* #go.Spielausgang lnkSpielausgang */
	/* #go.Spieler lnkSpieler */

	/* #Spielausgang lnkSpielausgang */
	/* #Spieler lnkSpieler */

	private Statistik lnkStatistik;
	/**
	 * optional werden 0-2 KIs eingefügt, deren Kontrolle der Controller
	 * übernimmt
	 * 
	 * @supplierCardinality 0..2
	 */
	private KI lnkKIHaupt;

	/**
	 * Der Controller hat die GUI, um diese zu aktualisieren je nach Spielstand.
	 */
	private go.gui.Main lnkGUI;

	/**
	 * Der Controller hat das Spiel und führt auf diesem Aktionen aus.
	 */
	private Spiel lnkSpiel;
	private Spielstand lnkSpielstand;
	private boolean kiDarfNichtSetzen;
	private boolean markieren;
	private Zustand zustandMarkierung;
	// private Spielbrett uneinigkeitsBrett; //da ich Steine lösche und markiere
	// muss ich irgendwie zu dem Uneinigkeitsbrett zurückkehren, meiner Meinung
	// nach Aufgabe des Controllers, daher hier gespeichert.
	// rausgenommen weil vielleicht doch nicht
	public static final String SPEICHER_ORDNER = "./savegames/";

	// private Spielstand lnkSpielstand;

	public Controller() {
		lnkGUI = new go.gui.Main(this);
		this.zustandMarkierung = Zustand.frei;
	}

	/**
	 * Verarbeitet den Klick auf eine Position des Spielbretts, indem es auf
	 * Spiel die Methode "FuehreSpielzugAus aufruft.
	 * 
	 * @param pos
	 *            Position, die gesetzt werden soll.
	 */
	public void positionGeklickt(Position pos) {
		if (this.markieren) {
			this.lnkSpiel.gibSpielbrett().setzeMarkierung(zustandMarkierung,
					pos);
		} else {
			try {
				this.kiDarfNichtSetzen = false;
				Spielzug sz = new Spielzug(pos, lnkSpielstand
						.gibAktuellenSpieler());
				this.lnkSpiel.fuehreSpielzugAus(sz);
			} catch (UngueltigerZugException e) {
				this.lnkGUI.spiel_spielzugNichtZulaessig(e.gibRegelBruch());
			}
		}
	}

	/**
	 * Verarbeitet den Klick auf den Button "Beenden" aus der GUI, indem die GUI
	 * die Methode aufruft.
	 */
	public void beendenGeklickt() {
		return;
	}

	/**
	 * Verarbeitet den Klick auf den Button "redo" aus der GUI, indem die GUI
	 * diese Methode aufruft. Der Klick wird durch Delegation an die Spielklasse
	 * aufgelöst. Ein Redo-Flag wird in der Klasse gesetzt
	 */
	public void redoGeklickt() {
		this.kiDarfNichtSetzen = true;
		try {
			this.lnkSpiel.redo();
		} catch (Exception e) {
			// darf nicht auftreten!
		}
	}

	/**
	 * Verarbeitet den Klick auf den Button "redo" aus der GUI, indem die GUI
	 * diese Methode aufruft. Der Klick wird durch Delegation an die Spielklasse
	 * aufgelöst.
	 */
	public void undoGeklickt() {
		this.kiDarfNichtSetzen = true;
		try {
			this.lnkSpiel.undo();
		} catch (Exception e) {
			// darf nicht auftreten!
		}
	}

	/**
	 * Verarbeitet den Klick auf den Button "laden" aus der GUI, indem die GUI
	 * diese Methode aufruft. Der Klick wird durch Delegation an die Spielklasse
	 * aufgelöst.
	 */
	public void laden() {
		this.lnkSpielstand = this.lnkGUI.laden_gibSpielstand();
		// Laden des Spielstandes in Spiel
		this.lnkSpiel = new Spiel(this.lnkSpielstand);
		Variante var = this.lnkSpielstand.gibVariante();
		Spieler spieler1 = this.lnkSpielstand.gibSpieler1();
		Spieler spieler2 = this.lnkSpielstand.gibSpieler2();
		if (var instanceof Steinschlag) {
			this.lnkGUI.spiel_buttonsFuerSteinschlagKonfigurieren();
			if (this.lnkSpielstand.gibSpieler1().gibTyp() == SpielerTyp.leicht) {
				this.lnkKIHaupt = new KI(spieler1, new Leicht(lnkSpielstand
						.gibSpieler1().gibFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			} else if (this.lnkSpielstand.gibSpieler1().gibTyp() == SpielerTyp.schwer) {
				this.lnkKIHaupt = new KI(spieler1, new Schwer(lnkSpielstand
						.gibSpieler1().gibFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			}
			if (this.lnkSpielstand.gibSpieler2().gibTyp() == SpielerTyp.leicht) {
				this.lnkKIHaupt = new KI(spieler2, new Leicht(lnkSpielstand
						.gibSpieler1().gibFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			} else if (this.lnkSpielstand.gibSpieler2().gibTyp() == SpielerTyp.schwer) {
				this.lnkKIHaupt = new KI(spieler2, new Schwer(lnkSpielstand
						.gibSpieler2().gibFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			}
		} else {
			// Spieler auf Mensch setzen, da unsere KI kein Klassisch kann.
			spieler1.setzeTyp(SpielerTyp.mensch);
			spieler2.setzeTyp(SpielerTyp.mensch);
		}
		// Observer setzen
		this.lnkSpiel.gibSpielbrett().addObserver(lnkGUI);
		this.lnkSpielstand.addObserver(this);
		if (this.lnkKIHaupt != null) {
			this.lnkKIHaupt.addObserver(this);
		}
		// Update vom Spielbrett machen, falls Spieler hinzugefügt wurden.
		this.lnkSpiel.gibSpielbrett().updateUI();
		this.kiDarfNichtSetzen = false;
		this.update(lnkSpielstand, lnkSpielstand.gibAktuellenSpieler());
	}

	/**
	 * Verarbeitet den Klick auf den Button "speichern" aus der GUI, indem die
	 * GUI diese Methode aufruft. Der Klick wird durch Delegation an die
	 * Spielstandklasse aufgelöst.
	 */
	@SuppressWarnings("static-access")
	public void speichern() {
		if(lnkSpiel.istSpielZuEnde()==true) {
			if(this.gibSpielausgang(lnkSpielstand.gibSpieler1())==Spielausgang.sieg) {
				lnkSpielstand.setzeSpielAusgang(Zustand.schwarz);
			} else if(this.gibSpielausgang(lnkSpielstand.gibSpieler1())==Spielausgang.sieg) {
				lnkSpielstand.setzeSpielAusgang(Zustand.weiss);
			} else {
				lnkSpielstand.setzeSpielAusgang(Zustand.frei);
			}
		}
		lnkSpielstand.setzeKommentar(lnkGUI.speichern_gibKommentar());
		lnkSpielstand.setzeTitel(lnkSpielstand.gibSpieler1().gibName() + " vs "
				+ lnkSpielstand.gibSpieler2().gibName());
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		lnkSpielstand.setzeDatum(formatter.format(date));
		formatter.applyPattern("yyyy-MM-dd_hh-mm-ss");
		try {
			lnkSpielstand.speichern(this.SPEICHER_ORDNER
					+ formatter.format(date) + ".xml");
			this.lnkGUI.speichern_erfolgreich();
		} catch (SpeichernException e) {
			this.lnkGUI.zeigeFehlerAn("Konnte nicht speichern!");
		}
	}

	private void spielEnde() {
		if (this.lnkSpielstand.gibVariante() instanceof Steinschlag) {
			this.aktualisiereStatistik();
		} else if (this.zustandMarkierung == Zustand.frei) {
			this.zustandMarkierung = Zustand.schwarz;
			this.markieren = true;
			this.lnkGUI.actionPerformed(new ActionEvent(this, 0,
					"Spiel_Klassisch"));
		}
	}

	/**
	 * fragt das Spiel nach den erlangten Punkten der einzelnen Spieler und
	 * entscheidet wer gewonnen hat und trägt das Ergebniss in die Statistik
	 * ein. Hilfsmethode von Update.
	 */
	@SuppressWarnings("static-access")
	private void aktualisiereStatistik() {
		try {
			this.lnkStatistik = lnkStatistik.gibStatistik();
		} catch (StatistikException e) {
			this.lnkGUI
					.zeigeFehlerAn("Die Statistikdatei ist fehlerhaft, Ihre Statistik wurde zurückgesetzt.");
		}
		this.lnkGUI.setzeSteinNichtSetzbar(true);
		Spieler spieler1, spieler2;
		spieler1 = lnkSpielstand.gibSpieler1();
		spieler2 = lnkSpielstand.gibSpieler2();
		this.lnkStatistik.aktualisiereEintrag(spieler1, this.gibSpielausgang(spieler1), this.gibStatistikunterteilung());
		this.lnkStatistik.aktualisiereEintrag(spieler2, this.gibSpielausgang(spieler2), this.gibStatistikunterteilung());
		/*
		if (this.lnkSpiel.gibPunkte(spieler1) > this.lnkSpiel.gibPunkte(spieler2)) {
			this.lnkStatistik.aktualisiereEintrag(spieler1, Spielausgang.sieg,
					Statistikunterteilung.Steinschlag);
			this.lnkStatistik.aktualisiereEintrag(spieler2,
					Spielausgang.niederlage, Statistikunterteilung.Steinschlag);
			this.lnkGUI.spiel_setzeNachricht(spieler1.gibName()
					+ " hat gewonnen!");
			this.lnkSpielstand.setzeSpielAusgang(Zustand.schwarz);
		} else if (this.lnkSpiel.gibPunkte(spieler1) < this.lnkSpiel
				.gibPunkte(spieler2)) {
			this.lnkStatistik.aktualisiereEintrag(spieler2, Spielausgang.sieg,
					Statistikunterteilung.Steinschlag);
			this.lnkStatistik.aktualisiereEintrag(spieler1,
					Spielausgang.niederlage, Statistikunterteilung.Steinschlag);
			this.lnkGUI.spiel_setzeNachricht(spieler2.gibName()
					+ " hat gewonnen!");
			this.lnkSpielstand.setzeSpielAusgang(Zustand.weiss);
		} else {
			this.lnkStatistik.aktualisiereEintrag(spieler2,
					Spielausgang.unentschieden,
					Statistikunterteilung.Steinschlag);
			this.lnkStatistik.aktualisiereEintrag(spieler1,
					Spielausgang.unentschieden,
					Statistikunterteilung.Steinschlag);
			this.lnkGUI.spiel_setzeNachricht("Es steht unentschieden.");
			this.lnkSpielstand.setzeSpielAusgang(Zustand.frei);
		}
		*/
		try {
			this.lnkStatistik.speichern();
		} catch (Exception e) {
			this.lnkGUI
					.zeigeFehlerAn("Die Statistik konnte nicht gespeichert werden");
		}
		
		this.lnkGUI.actionPerformed(new java.awt.event.ActionEvent(this, 0,
				"Spiel_Ende"));
		if (this.lnkSpiel.gibPunkte(spieler1) > this.lnkSpiel.gibPunkte(spieler2)) {
			this.lnkGUI.spiel_setzeNachricht(spieler1.gibName()
					+ " hat gewonnen!");
		} else if (this.lnkSpiel.gibPunkte(spieler1) < this.lnkSpiel
				.gibPunkte(spieler2)) {
			this.lnkGUI.spiel_setzeNachricht(spieler2.gibName()
					+ " hat gewonnen!");
		} else {
			this.lnkGUI.spiel_setzeNachricht("Es steht unentschieden.");
		}
		this.lnkGUI.spiel_setzePunkteSpieler1(this.lnkSpiel.gibPunkte(spieler1));
		this.lnkGUI.spiel_setzePunkteSpieler2(this.lnkSpiel.gibPunkte(spieler2));
	}
	
	private Statistikunterteilung gibStatistikunterteilung(){
		if(this.lnkSpiel.gibVariante() instanceof Steinschlag){
			return Statistikunterteilung.Steinschlag;
		}
		else{
			switch(this.lnkSpielstand.gibGroesse()){
			case 9: return Statistikunterteilung.Klassisch9x9;
			case 13: return Statistikunterteilung.Klassisch13x13;
			case 19: return Statistikunterteilung.Klassisch19x19;
			}
		}
		return null;
	}
	
	private Spielausgang gibSpielausgang(Spieler spieler){
		Spieler spieler1, spieler2;
		spieler1 = this.lnkSpielstand.gibSpieler1();
		spieler2 = this.lnkSpielstand.gibSpieler2();
		if (this.lnkSpiel.gibPunkte(spieler1) > this.lnkSpiel.gibPunkte(spieler2)) {
			if(spieler.equals(spieler1))
				return Spielausgang.sieg;
			else return Spielausgang.niederlage;
		} else if (this.lnkSpiel.gibPunkte(spieler1) < this.lnkSpiel
				.gibPunkte(spieler2)) {
			if(spieler.equals(spieler1))
				return Spielausgang.niederlage;
			else return Spielausgang.sieg;
		} else {
			return Spielausgang.unentschieden;
		}
	}

	/**
	 * Verarbeitet den Klick auf den Button "Aufgeben" aus der GUI, wobei dabei
	 * der Spieler, welcher aufgeben geklickt hat, noch ermittelt werden muss.
	 * Da der Spieler, welcher aufgeben geklickt hat, auf jedenfall verloren
	 * hat. Wird das Ergebnis des Spiels sofort in die Statistik eingetragen.
	 */
	@SuppressWarnings("static-access")
	public void aufgegebenGeklickt() {
		try {
			this.lnkStatistik = lnkStatistik.gibStatistik();
		} catch (StatistikException e) {
			lnkGUI
					.zeigeFehlerAn("Die Statistikdatei ist fehlerhaft, Ihre Statistik wurde zurückgesetzt.");
		}
		Spieler spieler1, spieler2;
		spieler1 = lnkSpielstand.gibSpieler1();
		spieler2 = lnkSpielstand.gibSpieler2();
		if (spieler1.equals(lnkSpielstand.gibAktuellenSpieler())) {
			lnkStatistik.aktualisiereEintrag(spieler2, Spielausgang.sieg,
					this.gibStatistikunterteilung());
			lnkStatistik.aktualisiereEintrag(spieler1, Spielausgang.aufgegeben,
					this.gibStatistikunterteilung());
			this.lnkSpielstand.setzeSpielAusgang(Zustand.weiss);
		} else {
			lnkStatistik.aktualisiereEintrag(spieler1, Spielausgang.sieg,
					this.gibStatistikunterteilung());
			lnkStatistik.aktualisiereEintrag(spieler2, Spielausgang.aufgegeben,
					this.gibStatistikunterteilung());
			this.lnkSpielstand.setzeSpielAusgang(Zustand.schwarz);
		}
		lnkSpiel.setzeEnde(true);
		lnkGUI.actionPerformed(new java.awt.event.ActionEvent(this, 0,
				"Spiel_MenueEnde"));
	}

	public void passenGeklickt() {
		this.positionGeklickt(null);
	}

	public void weiterspielen() {
		try {
			this.lnkSpiel.gibSpielbrett().loescheMarkierungen();
			this.lnkSpiel.undo();
			this.lnkSpiel.undo();
			this.lnkSpiel.setzeEnde(false);
			this.zustandMarkierung = Zustand.frei;
			this.markieren = false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void einigGeklickt() {
		if (this.zustandMarkierung == Zustand.schwarz) {
			this.zustandMarkierung = Zustand.weiss;
		} else if (this.zustandMarkierung == Zustand.weiss) {
			// yihaa Sie sind sich einig
			// Spielpunkte auf jeden fall aktualisieren!
			this.markieren = false;
			this.zustandMarkierung = Zustand.frei;
			this.aktualisiereStatistik();
		}
	}

	/**
	 * startet ein neues Spiel, indem es zunächst einen neuen Spielstand mit den
	 * vorher vom Benutzer gemachten und an den Controller übermittelten
	 * Informationen erstellt. Dieser erstellt wiederum die beiden Spieler. Der
	 * fertige Spielstand wird anschließend vom neu erzeugten Spiel geladen. Bei
	 * Bedarf werden anschließend zusätzlich KIs erzeugt, die je einen Spieler
	 * erhalten.
	 */
	public void neuesSpielStarten() {
		Spieler spieler1, spieler2;
		Variante var;
		// 1. Spieler soll immer schwarz sein
		spieler1 = new Spieler(Zustand.schwarz, lnkGUI
				.neuesSpiel_gibSpieler1Name(), SpielerTyp.mensch);
		spieler2 = new Spieler(Zustand.weiss, lnkGUI
				.neuesSpiel_gibSpieler2Name(), SpielerTyp.mensch);
		// Variante erzeugen
		if (this.lnkGUI.neuesSpiel_gibVariante() == go.gui.NeuesSpiel.SPIELART_STEINSCHLAG) {
			var = new Steinschlag();
			this.lnkGUI.spiel_buttonsFuerSteinschlagKonfigurieren();
		} else {
			var = new Klassisch();
		}
		// Spielstand erzeugen
		this.lnkSpielstand = new Spielstand(spieler1, spieler2, var, lnkGUI
				.neuesSpiel_gibGroesse());
		// Spiel erzeugen
		this.lnkSpiel = new Spiel(var, lnkSpielstand, var
				.initialisiereSpielbrett(lnkGUI.neuesSpiel_gibGroesse()));
		// KI erzeugen
		switch (lnkGUI.neuesSpiel_gibKI()) {
		case leicht:
			if (lnkGUI.neuesSpiel_gibKIFarbe() == Zustand.schwarz) {
				spieler1.setzeTyp(SpielerTyp.leicht);
				lnkKIHaupt = new KI(spieler1, new Leicht(lnkGUI
						.neuesSpiel_gibKIFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			} else {
				spieler2.setzeTyp(SpielerTyp.leicht);
				lnkKIHaupt = new KI(spieler2, new Leicht(lnkGUI
						.neuesSpiel_gibKIFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			}
			break;
		case schwer:
			if (lnkGUI.neuesSpiel_gibKIFarbe() == Zustand.schwarz) {
				spieler1.setzeTyp(SpielerTyp.schwer);
				lnkKIHaupt = new KI(spieler1, new Schwer(lnkGUI
						.neuesSpiel_gibKIFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			} else {
				spieler2.setzeTyp(SpielerTyp.schwer);
				lnkKIHaupt = new KI(spieler2, new Schwer(lnkGUI
						.neuesSpiel_gibKIFarbe(), new KIVariante(var)),
						this.lnkSpiel.gibSpielbrett());
			}
			break;
		}
		// Observer setzen
		this.lnkSpiel.gibSpielbrett().addObserver(lnkGUI);
		this.lnkSpielstand.addObserver(this);
		if (this.lnkKIHaupt != null) {
			this.lnkKIHaupt.addObserver(this);
		}
		// Update vom Spielbrett machen, falls Spieler hinzugefügt wurden.
		this.lnkSpiel.gibSpielbrett().updateUI();
		this.update(lnkSpielstand, lnkSpielstand.gibAktuellenSpieler());
	}

	/**
	 * 
	 * Methode, die vom Spielstand aufgerufen wird, wenn ein Zug durchgeführt
	 * wurde. Es wird überprüft, ob im Spiel das Attribut „zuEnde“ gesetzt ist;
	 * in dem Fall wird spielEnde() zum Ermitteln des Gewinners durchgeführt.
	 * Falls nicht, wird die GUI über den Spielerwechsel informiert, um den
	 * nächsten Spieler zum Zug aufzufordern. Sonst wird das Spielende
	 * eingeleitet. Zusätzlich wird ein boolean-Wert übergeben, der angibt, ob
	 * der Spieler an der Reihe die KI ist, damit die GUI ggf. Buttons sperren
	 * kann. Falls der Spieler keine KI ist, ist die Methode hier beendet. In
	 * dem Fall, dass der aktuelle Spieler KI ist und zuletzt kein Redo
	 * durchgeführt wurde (Redo-Flag auf false), wird die Zug-Methode der KI
	 * ausgeführt, die einen Spielzug zurückgibt. Dieser Spielzug wird
	 * anschließend mit der „fuehreSpielzugAus“-Methode vom Spiel angewandt.
	 * 
	 * @param spielstand
	 *            Wird durch das Observerpattern beim update der Methode als
	 *            geupdatetes Objekt übergeben
	 * @param aktSpieler
	 *            gibt den aktuellen Spieler an
	 */
	public synchronized void update(Observable observable, Object aktSpieler) {
		if (observable instanceof Spielstand) {
			// Spielstand update
			Spieler spieler = (Spieler) aktSpieler;
			if (spieler == null) {
				// weil notify nicht vernünftig implementiert wurde ...
				spieler = this.lnkSpielstand.gibAktuellenSpieler();
			}
			// GUI
			this.lnkGUI.spiel_redoButtonAktivieren(this.lnkSpielstand
					.istRedoMoeglich());
			this.lnkGUI.spiel_undoButtonAktivieren(this.lnkSpielstand
					.istUndoMoeglich());
			this.lnkGUI.spiel_setzePunkteSpieler1(this.lnkSpielstand
					.gibPunkte(this.lnkSpielstand.gibSpieler1()));
			this.lnkGUI.spiel_setzePunkteSpieler2(this.lnkSpielstand
					.gibPunkte(this.lnkSpielstand.gibSpieler2()));
			this.lnkGUI.spiel_spielerWechsel(spieler);
			// KI
			if (spieler.gibTyp() != SpielerTyp.mensch
					&& !this.kiDarfNichtSetzen && !lnkSpiel.istSpielZuEnde()) {
				this.lnkKIHaupt.erzeugeSpielzug();
			}
			if (lnkSpiel.istSpielZuEnde()) {
				this.spielEnde();
			}
		} else if (observable instanceof KI) {
			// KI update
			try {
				this.lnkSpiel.fuehreSpielzugAus(this.lnkKIHaupt.gibSpielzug());
			} catch (UngueltigerZugException e) {
				System.out
						.println("Wir haben mist gemacht, die KI kann nicht vernünftig spielen!\n"
								+ e.gibRegelBruch());
			}
		}
	}
}
