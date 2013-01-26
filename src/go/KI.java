package go;

import java.util.Observable;

/**
 * Main-Klasse fuer die KI zur Verwaltung der Spieler und als Unterklassen gibts
 * die genauen Implementationen (in KIStrategie).
 * 
 * @author sopr055
 */
public class KI extends Observable implements Runnable {

	private Spieler lnkSpieler;
	private KIStrategie lnkKIStrategie;

	private Spielbrett runnable_brett;
	private Position runnable_pos;

	/**
	 * Konstruktor der die KI mit einem Spielerobjekt und einer KI Strategie
	 * initialisiert
	 * 
	 * @param spieler
	 * @param strategie
	 */
	public KI(Spieler spieler, KIStrategie strategie, Spielbrett brett) {
		this.setzeSpieler(spieler);
		this.setzeKIStrategie(strategie);
		this.runnable_brett = brett;
	}

	/**
	 * Setzt die KIStrategie (durch den Controller).
	 * 
	 * @param kis
	 */
	public void setzeKIStrategie(KIStrategie kis) {
		this.lnkKIStrategie = kis;
	}

	/**
	 * Setze den Spieler auf die KI.
	 * 
	 * @param spieler
	 */
	public void setzeSpieler(Spieler spieler) {
		this.lnkSpieler = spieler;
	}

	/**
	 * Gib die Daten ueber die KI als Spieler her.
	 */
	public Spieler gibSpieler() {
		return this.lnkSpieler;
	}

	public synchronized void run() {
		// diese Aktion dauer lange!
		this.runnable_pos = this.lnkKIStrategie.erzeugePosition(runnable_brett);
		this.setChanged();
		this.notifyObservers();
	}

	public synchronized Spielzug gibSpielzug() {
		return (new Spielzug(this.runnable_pos, lnkSpieler));
	}

	public synchronized void erzeugeSpielzug() {
		new Thread(this).start();
	}
}
