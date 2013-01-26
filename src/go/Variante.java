package go;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse der Spiel-Variante (Klassisch & Steinschlag)
 */
public abstract class Variante {
	/**
	 * @supplierCardinality 1
	 */
	protected Spielbrett lnkSpielbrett;
	protected Spielbrett lnkSpielbrettlast;
	protected boolean zuEnde;
	protected int anzahlGepasst = 0;
	protected Spielzug letzterZug;

	/**
	 * Initialisiert das Spielbrett und gibt dieses zurück.
	 * 
	 * @param groesse
	 *            Groesse des Spielbretts
	 */
	public abstract Spielbrett initialisiereSpielbrett(int groesse);

	/**
	 * Berechne die Gebietspunkte von einem Spieler.
	 * 
	 * @return Gibt dei Gebietspunkte eines Spielers aus.
	 */
	public abstract int berechnePunkte(Spieler s);

	/**
	 * Gibt an,ob das Spiel zu ende ist.
	 * 
	 * @return Gibt true aus,wenn das Spiel fertig ist,sonst false.
	 */
	public abstract boolean istZuEnde();

	/**
	 * Liefert die geschlagene Steine .
	 * 
	 * @param Nimmt
	 *            zu ueberpruefenden Spielzug s
	 * @return Gibt eine Liste von Positionen der geschlagenen Steine aus.
	 */
	protected List<Position> gibGeschlageneSteine(Spielzug s) {
		List<Position> list = new ArrayList<Position>();
		List<Kette> ketten = new ArrayList<Kette>();
		ketten = lnkSpielbrett.gibKetten();
		List<Kette> gegnerketten = new ArrayList<Kette>();

		


		for (int i = 0; i < ketten.size(); i++) {
			if (lnkSpielbrett.gib(ketten.get(i).get(0)) != s.gibSpieler()
					.gibFarbe()) {
				gegnerketten.add(ketten.get(i));
			}
		}
		for (Kette kette : gegnerketten) {
			if (lnkSpielbrett.gibFreiheiten(kette).size() == 1
					//hier ist was komisch!!!
					&& lnkSpielbrett.gibFreiheiten(kette).get(0).equals(
							s.gibGesetztenStein())) {
				list.addAll(kette);

			}
		}
		return list;
		
	}

	/**
	 * Ändert des Brett mit einem durchgeführten Spielzug.
	 * 
	 * @return Gibt den Spielzug aus ,durch den das Brett geändert wird.
	 */
	public Spielzug erzeugeSpielzug(Spielzug s) throws UngueltigerZugException {
		RegelBruch b = istZulaessig(s);
		if (b!=RegelBruch.keiner) {
			throw new UngueltigerZugException(
					"Eingegebener Spielzug ungültig. Position: "
							+ s.gibGesetztenStein(), b);
		}
		if(s.gibGesetztenStein()!=null){ //dirty fix wegen exception bei zweimal passen wenn eine kette nur eine Freiheit
		s.setzeGeschlageneSteine(gibGeschlageneSteine(s));
		}
		letzterZug = s;
		// Klassisch-Only: Falls 2mal gepasst: zuEnde = true
		if (this instanceof Klassisch) {
			lnkSpielbrettlast = lnkSpielbrett.kopiereSpielbrett();
			if (s.gibGesetztenStein() == null) {
				anzahlGepasst++;
			} else {
				anzahlGepasst = 0;
			}
			if (anzahlGepasst == 2) {
				zuEnde = true;
			}
		
		
		}
		// Steinschlag-Only: Falls Steine geschlagen wurden: zuEnde = true
		if (this instanceof Steinschlag) {
			if(s.gibGeschlageneSteine().size()>0){
				zuEnde = true;
			}
		}
		
		return s;
	}

	/**
	 * Überprüft den Spielzug.
	 * 
	 * @param s
	 *            Der Spielzug ,der überprüft wird.
	 * @return Gibt true aus,wenn der Zug zulässig ist.Sonst gibt false aus.
	 */
	public abstract RegelBruch istZulaessig(Spielzug s);

}
