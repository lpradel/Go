package go;

import java.util.List;

/**
 * Implementierung der klassischen Spielvariante von Go
 */
public class Klassisch extends Variante {

	/**
	 * Funktion zum Erstellen des Startzustandes. (Leeres Feld)
	 * 
	 * @param groesse
	 *            Spielfeldgroesse
	 */
	@Override
	public Spielbrett initialisiereSpielbrett(int groesse) {
		lnkSpielbrett = new Spielbrett(groesse);
		return lnkSpielbrett;
	}

	/**
	 * Prüft ob beide Spieler hintereinander gepasst haben, oder das Brett voll
	 * ist.
	 */
	@Override
	public boolean istZuEnde() {
		// System.out.println(zuEnde+" | "+anzahlGepasst);
		if (zuEnde)
			return true; // Falls das zuEnde flag gesetzt ist (durch zwei mal
							// Passen) ists spiel zuende
		List<Position> freieFelder = lnkSpielbrett.gibFreieFelder();
		if (freieFelder.size() == 0)
			return true;
		return false;
	}

	/**
	 * Prüft die Go-Regeln und das Spielbrett und prüft ob der Spielzug gültig
	 * ist.
	 */
	@Override
	public RegelBruch istZulaessig(Spielzug s) {
		// Zug auf dem Feld?
		if (s.gibGesetztenStein() == null)
			return RegelBruch.keiner;
		if (s.gibGesetztenStein().gibX() < 1
				|| s.gibGesetztenStein().gibX() > lnkSpielbrett.gibGroesse()
				|| s.gibGesetztenStein().gibY() < 1
				|| s.gibGesetztenStein().gibY() > lnkSpielbrett.gibGroesse()) {
			return RegelBruch.ausserhalbDesSpielfeldes;
		}
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
		// Ko-Regel verletzt?
		kopie = lnkSpielbrett.kopiereSpielbrett();
		testZug = new Spielzug(s.gibGesetztenStein(), s.gibSpieler());
		testZug.setzeGeschlageneSteine(geschlageneSteineTest);
		kopie.setzeZug(testZug);
		if (lnkSpielbrettlast != null) {
			if (kopie.equals(lnkSpielbrettlast))
				return RegelBruch.ko;
		}
		return RegelBruch.keiner; // Falls nix verletzt wurde ist spielzug zulässig.
	}
	
	public String toString()
	{
		return "Klassisches Go";
	}

	/**
	 * Berechne die Gebietspunkte von einem Spieler.
	 * 
	 * @return Gibt dei Gebietspunkte eines Spielers aus.
	 */
	@Override
	public int berechnePunkte(Spieler s) {
		int punkte=0;
		
		for(int i = 1; i<=lnkSpielbrett.gibGroesse();i++) {
			for(int j = 1; j <=lnkSpielbrett.gibGroesse();j++) {
				if(s.gibFarbe()==Zustand.schwarz) {
					if(lnkSpielbrett.gibMarkiert(i,j)==Zustand.schwarz) {
						punkte++;
						if(lnkSpielbrett.gib(new Position(i,j))==Zustand.schwarz) {
							punkte++;
						}
					}
				}
				if(s.gibFarbe()==Zustand.weiss) {
					if(lnkSpielbrett.gibMarkiert(i,j)==Zustand.weiss) {
						punkte++;
						if(lnkSpielbrett.gib(new Position(i,j))==Zustand.weiss) {
							punkte++;
						}
					}
				}
			}
		}
		return punkte;
	}

}
