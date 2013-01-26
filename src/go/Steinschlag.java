package go;

import java.util.List;

/**
 * Implementierung der Spielvariante Steinschlag-Go
 */
public class Steinschlag extends Variante {
	/**
	 * Funktion zum Erstellen des Startzustandes. (4 Initialssteine)
	 * 
	 * @param groesse
	 *            Groesse des Spielfeldes
	 */
	@Override
	public Spielbrett initialisiereSpielbrett(int groesse) {
		//EIGENTLICH muss man sich beim initialisieren die Steine vom init-Stack holen.
		lnkSpielbrett = new Spielbrett(groesse);
		lnkSpielbrett.setzeStein(new Position(4, 6), Zustand.schwarz);
		lnkSpielbrett.setzeStein(new Position(5, 5), Zustand.schwarz);
		lnkSpielbrett.setzeStein(new Position(5, 6), Zustand.weiss);
		lnkSpielbrett.setzeStein(new Position(4, 5), Zustand.weiss);
		this.lnkSpielbrett.setzeGeaendert(true, 4, 6);
		this.lnkSpielbrett.setzeGeaendert(true, 5, 5);
		this.lnkSpielbrett.setzeGeaendert(true, 4, 5);
		this.lnkSpielbrett.setzeGeaendert(true, 5, 6);
		return lnkSpielbrett;
	}

	/**
	 * Prüft ob ein Stein geschlagen wurde, oder das Brett voll ist.
	 */
	@Override
	public boolean istZuEnde() {
		if (zuEnde)
			return true; // Falls das zuEnde flag gesetzt ist (durch
		// geschlageneSteine) ists spiel zuende
		List<Position> freieFelder = lnkSpielbrett.gibFreieFelder();
		if (freieFelder.size() == 0)
			return true;
		if (letzterZug == null)
			return false;
		for (Position pos : freieFelder) {
			if (letzterZug.gibSpieler().gibFarbe() == Zustand.schwarz) {
				if (istZulaessig(new Spielzug(pos, new Spieler(Zustand.weiss,
						"", SpielerTyp.mensch))) == RegelBruch.keiner)
					return false;
			}
			if (letzterZug.gibSpieler().gibFarbe() == Zustand.weiss) {
				if (istZulaessig(new Spielzug(pos, new Spieler(Zustand.schwarz,
						"", SpielerTyp.mensch))) == RegelBruch.keiner)
					return false;
			}
		}
		zuEnde = true; // Wenn alle Positionen besetzt sind ists zuende.
		return true;
	}

	/**
	 * Prüft die Go-Regeln und das Spielbrett und prüft ob der Spielzug gültig
	 * ist.
	 */
	@Override
	public RegelBruch istZulaessig(Spielzug s) {
		if (s.gibGesetztenStein() == null)
			return RegelBruch.keiner;
		// Zug auf dem Feld?
		if (s.gibGesetztenStein().gibX() < 1
				|| s.gibGesetztenStein().gibX() > 9
				|| s.gibGesetztenStein().gibY() < 1
				|| s.gibGesetztenStein().gibY() > 9)
			return RegelBruch.ausserhalbDesSpielfeldes;
		// Feld frei?
		if (lnkSpielbrett.gib(s.gibGesetztenStein()) != Zustand.frei)
			return RegelBruch.feldBesetzt;
		// Selbstmord-Regel verletzt?
		Spielbrett kopie = lnkSpielbrett.kopiereSpielbrett();
		List<Position> geschlageneSteineTest = gibGeschlageneSteine(s);
		Spielzug testZug = new Spielzug(s.gibGesetztenStein(), s.gibSpieler());
		testZug.setzeGeschlageneSteine(geschlageneSteineTest);
		kopie.setzeZug(testZug);
		if (kopie.gibFreiheiten(kopie.gibKette(s.gibGesetztenStein())).size() == 0) {
			return RegelBruch.selbstMord;
		}
		return RegelBruch.keiner; // Falls nix verletzt wurde ist spielzug
		// zulässig.
	}

	public String toString() {
		return "Steinschlag Go";
	}

	/**
	 * Berechne die Gebietspunkte von einem Spieler.
	 * 
	 * @return Gibt dei Gebietspunkte eines Spielers aus.
	 */
	@Override
	public int berechnePunkte(Spieler s) {
		return 0;

	}

}
