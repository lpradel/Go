package go;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Das Spielbrett enthält ein Feld, in dem gespeichert ist, an welcher Position
 * sich welche Farbe befindet
 */
public class Spielbrett extends Observable {

	private Zustand[][] feld;
	private int groesse;
	private boolean[][] changed;
	private Zustand[][] markiert;

	/**
	 * Erstellt ein neues Spielbrett und es wird die Größe übergeben
	 * 
	 * @param groesse
	 *            Größe des Spielfeldes
	 */
	public Spielbrett(int groesse) {
		this.groesse = groesse;
		this.feld = new Zustand[groesse + 1][groesse + 1];
		this.markiert = new Zustand[groesse + 1][groesse + 1];
		changed = new boolean[groesse + 1][groesse + 1];
		for (int m = 1; m <= groesse; m++) {
			for (int n = 1; n <= groesse; n++) {
				changed[m][n] = false;
			}
		}
		for (int i = 0; i < this.feld.length; i++) { // Alle Positionen werden
			// zunächst in den
			// Zustand frei gebracht
			for (int j = 0; j < this.feld.length; j++) {
				feld[i][j] = Zustand.frei;
				markiert[i][j] = Zustand.frei;
			}
		}
	}

	/**
	 * Gibt die Größe des Spielbretts zurück
	 */
	public int gibGroesse() {
		return this.groesse;
	}

	/**
	 * Setzt einen Zug auf dem Spielbrett (Steine werden hinzugefügt und
	 * entfernt).
	 * 
	 * @param sz
	 *            Der zu setzende Spielzug.
	 */
	public void setzeZug(Spielzug sz) {
		if (sz.gibGesetztenStein() != null) {
			feld[sz.gibGesetztenStein().gibX()][sz.gibGesetztenStein().gibY()] = sz
					.gibSpieler().gibFarbe();
			List<Position> geschlageneSteine = sz.gibGeschlageneSteine();
			for (Position pos : geschlageneSteine) {
				feld[pos.gibX()][pos.gibY()] = Zustand.frei;
				changed[pos.gibX()][pos.gibY()] = true;
			}
			changed[sz.gibGesetztenStein().gibX()][sz.gibGesetztenStein()
					.gibY()] = true;
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Methode die einen Stein aufs Spielbrett setzt (wird zum Initialisieren
	 * von Steinschlag benötigt)
	 * 
	 * @param p
	 *            Position des Steins
	 * @param farbe
	 *            Farbe des Steins
	 */
	public void setzeStein(Position p, Zustand farbe) {
		if (p.gibX() <= groesse && p.gibX() >= 1 && p.gibY() >= 1
				&& p.gibY() <= groesse) {
			feld[p.gibX()][p.gibY()] = farbe;
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Nimmt einen Spielzug zurück (Steine werden gelöscht und hinzugefügt).
	 * 
	 * @param sz
	 *            Der zurückzunehmende Spielzug.
	 */
	public void nimmZugZurueck(Spielzug sz) {
		if (sz.gibGesetztenStein() != null) {
			feld[sz.gibGesetztenStein().gibX()][sz.gibGesetztenStein().gibY()] = Zustand.frei;
		}
		for (Position pos : sz.gibGeschlageneSteine()) {
			if (sz.gibSpieler().gibFarbe() == Zustand.weiss) {
				feld[pos.gibX()][pos.gibY()] = Zustand.schwarz;
			} else {
				feld[pos.gibX()][pos.gibY()] = Zustand.weiss;
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Kopiert das Spielbrett.
	 * 
	 * @return Das kopierte Spielbrett.
	 */
	public Spielbrett kopiereSpielbrett() {
		Spielbrett kopie = new Spielbrett(groesse);
		for (int i = 0; i < this.feld.length; i++) {
			for (int j = 0; j < this.feld.length; j++) {
				kopie.feld[i][j] = this.feld[i][j];
			}
		}
		return kopie;
	}

	/**
	 * Gibt die Belegung/den Zustand der aktuellen Position zurück.
	 * 
	 * @param pos
	 *            die Position, die abgefragt werden soll.
	 * @return die Belegung der Position.
	 */
	public Zustand gib(Position pos) {
		if ( pos.gibX() > this.groesse || pos.gibX() <= 0 ) return null;
		if ( pos.gibY() > this.groesse || pos.gibY() <= 0 ) return null;
		return feld[pos.gibX()][pos.gibY()];
	}

	public Zustand gibMarkiert(Position pos) {
		return this.gibMarkiert(pos.gibX(), pos.gibY());
	}

	public Zustand gibMarkiert(int x, int y) {
		return this.markiert[x][y];
	}

	private void setzeMarkiert(Zustand markiert, Position pos) {
		this.setzeMarkiert(markiert, pos.gibX(), pos.gibY());
	}

	private void setzeMarkiert(Zustand markiert, int x, int y) {
		this.markiert[x][y] = markiert;
		this.setzeGeaendert(true, x, y);
	}

	public boolean gibGeaendert(Position pos) {
		return this.gibGeaendert(pos.gibX(), pos.gibY());
	}

	public boolean gibGeaendert(int x, int y) {
		return this.changed[x][y];
	}

	public void setzeGeaendert(boolean geaendert, Position pos) {
		this.setzeGeaendert(geaendert, pos.gibX(), pos.gibY());
	}

	public void setzeGeaendert(boolean geaendert, int x, int y) {
		this.changed[x][y] = geaendert;
	}

	/**
	 * markiert tote ketten
	 * 
	 * @deprecated
	 */
	public void markiereToteKetten() {
		List<Kette> ketten = this.gibKetten();
		for (Kette k : ketten) {
			if (this.gibFreiheiten(k).size() <= 1) {
				Zustand typ = Zustand.frei;
				if (gib(k.get(0)) == Zustand.schwarz) {
					typ = Zustand.weiss;
				} else if (gib(k.get(0)) == Zustand.weiss) {
					typ = Zustand.schwarz;
				}
				this.markiereKette(k, typ);
			}
		}
	}

	public void markiereKette(Kette k, Zustand z) {
		for (Position pos : k) {
			this.setzeMarkiert(z, pos);
		}
	}

	/**
	 * Diese Methode verdoppelt scheinbar die Methode setze Markiert, hierbei
	 * geht es aber tatsächlich um das Markieren eines einzelnen Feldes, daher
	 * muss die GUI danach geupdated werde, wer will kann die Methoden trotzdem
	 * zusammenlegen
	 * 
	 * @param z
	 * @param pos
	 */
	public void setzeMarkierung(Zustand z, Position pos) {
		if (this.gibMarkiert(pos) == z && this.gib(pos) == Zustand.frei) {
			this.setzeMarkiert(Zustand.frei, pos);
		} else if (this.gibMarkiert(pos) == Zustand.frei
				&& this.gib(pos) == Zustand.frei) {
			this.setzeMarkiert(z, pos);
		} else if (this.gibMarkiert(pos) == z) {
			this.markiereKette(this.gibKette(pos), Zustand.frei);
		} else if (this.gibMarkiert(pos) == Zustand.frei && this.gib(pos) != z) {
			this.markiereKette(this.gibKette(pos), z);
		}
		updateUI();
	}

	public void loescheMarkierungen() {
		for (int i = 0; i < this.feld.length; i++) {
			for (int j = 0; j < this.feld.length; j++) {
				if (markiert[i][j] != Zustand.frei) {
					markiert[i][j] = Zustand.frei;
					this.setzeGeaendert(true, i, j);
				}
			}
		}
		updateUI();
	}

	/**
	 * Funktion die die Kette zurückgibt, in der sich Position p befindet.
	 * 
	 * @param p
	 *            Position, zu der die Kette bestimmt wird.
	 * @return Kette zur Position p
	 */
	public Kette gibKette(Position p) {
		Kette kette;
		Kette errorList = new Kette();
		errorList.add(new Position(-1, -1));
		boolean[][] besucht = new boolean[groesse + 1][groesse + 1];
		for (int i = 1; i <= groesse; i++) {
			for (int j = 1; j <= groesse; j++) {
				besucht[i][j] = false;
			}
		}
		Zustand farbe = feld[p.gibX()][p.gibY()];
		kette = gibKetteRek(p, besucht, farbe);
		kette.removeAll(errorList);
		return kette;
	}

	/**
	 * Hilfsfunktion für die gibKette Funktion
	 * 
	 * @param p
	 * @param b
	 * @param f
	 */
	private Kette gibKetteRek(Position p, boolean[][] b, Zustand f) {
		Kette help = new Kette();
		if (p.gibX() < 1 || p.gibX() > groesse || p.gibY() < 1
				|| p.gibY() > groesse) {
			help.add(new Position(-1, -1));
			return help;
		}
		if (feld[p.gibX()][p.gibY()] != f) {
			help.add(new Position(-1, -1));
			return help;
		}
		if (b[p.gibX()][p.gibY()] == true) {
			help.add(new Position(-1, -1));
			return help;
		}

		b[p.gibX()][p.gibY()] = true;
		help.add(p);
		help.addAll(gibKetteRek(new Position(p.gibX() - 1, p.gibY()), b, f));
		help.addAll(gibKetteRek(new Position(p.gibX() + 1, p.gibY()), b, f));
		help.addAll(gibKetteRek(new Position(p.gibX(), p.gibY() - 1), b, f));
		help.addAll(gibKetteRek(new Position(p.gibX(), p.gibY() + 1), b, f));
		return help;
	}

	/**
	 * Methode berechnet alle Ketten, die auf dem Spielbrett vorhanden sind und
	 * gibt sie zurück.
	 * 
	 * @return Die Liste aller Ketten auf dem Spielbrett.
	 */
	public List<Kette> gibKetten() {
		Zustand letzterZustand;
		List<Kette> ketten = new ArrayList<Kette>();
		Kette help;
		for (int i = 1; i <= groesse; i++) {
			letzterZustand = Zustand.frei;
			for (int j = 1; j <= groesse; j++) {
				if (feld[i][j] != letzterZustand && feld[i][j] != Zustand.frei) {
					help = gibKette(new Position(i, j));
					if (!ketten.contains(help))
						ketten.add(gibKette(new Position(i, j)));
				}
			}
		}

		return ketten;
	}

	/**
	 * Methode, die alle freien Felder des Brettes zurückgibt
	 * 
	 * @return Gibt eine Liste von freien Feldern zurück.
	 */
	public List<Position> gibFreieFelder() {
		List<Position> freieFelder = new ArrayList<Position>();
		for (int i = 1; i <= groesse; i++) {
			for (int j = 1; j <= groesse; j++) {
				if (gib(new Position(i, j)) == Zustand.frei)
					freieFelder.add(new Position(i, j));
			}
		}
		return freieFelder;
	}

	/**
	 * Methode zur Bestimmung aller Freiheiten eines Feldes.
	 * 
	 * @param p
	 *            Feld, dessen Freiheiten bestimmt werden sollen.
	 * @return Liste aller Freiheiten
	 */
	public List<Position> gibFreiheiten(Position p) {
		List<Position> freiheiten = new ArrayList<Position>();
		if ((feld[p.gibX()][p.gibY() - 1] == Zustand.frei)
				&& (p.gibY() - 1) >= 1)
			freiheiten.add(new Position(p.gibX(), p.gibY() - 1));
		if ((feld[p.gibX() - 1][p.gibY()] == Zustand.frei)
				&& (p.gibX() - 1) >= 1)
			freiheiten.add(new Position(p.gibX() - 1, p.gibY()));
		if ((p.gibX() + 1) <= groesse
				&& (feld[p.gibX() + 1][p.gibY()] == Zustand.frei))
			freiheiten.add(new Position(p.gibX() + 1, p.gibY()));
		if ((p.gibY() + 1) <= groesse
				&& (feld[p.gibX()][p.gibY() + 1] == Zustand.frei))
			freiheiten.add(new Position(p.gibX(), p.gibY() + 1));
		return freiheiten;
	}

	/**
	 * Berechnet die Freiheiten einer Position inklusive der Steine der
	 * übergebenen Farbe.
	 * 
	 * @param p
	 *            Position dessen Freiheiten bestimmt werden sollen
	 * @param zustand
	 *            Farbe welche ebenfalls als "frei" gilt
	 * @return Liste aller Freiheiten + Positionen mit "zustand" der Position p.
	 */
	public List<Position> gibFreiheiten(Position p, Zustand zustand) {
		List<Position> freiheiten = new ArrayList<Position>();
		if ((feld[p.gibX()][p.gibY() - 1] == zustand || feld[p.gibX()][p.gibY() - 1] == Zustand.frei)
				&& (p.gibY() - 1) >= 1)
			freiheiten.add(new Position(p.gibX(), p.gibY() - 1));
		if ((feld[p.gibX() - 1][p.gibY()] == zustand || feld[p.gibX() - 1][p
				.gibY()] == Zustand.frei)
				&& (p.gibX() - 1) >= 1)
			freiheiten.add(new Position(p.gibX() - 1, p.gibY()));
		if ((p.gibX() + 1) <= groesse
				&& (feld[p.gibX() + 1][p.gibY()] == zustand || feld[p.gibX() + 1][p
						.gibY()] == Zustand.frei))
			freiheiten.add(new Position(p.gibX() + 1, p.gibY()));
		if ((p.gibY() + 1) <= groesse
				&& (feld[p.gibX()][p.gibY() + 1] == zustand || feld[p.gibX()][p
						.gibY() + 1] == Zustand.frei))
			freiheiten.add(new Position(p.gibX(), p.gibY() + 1));
		return freiheiten;
	}

	/**
	 * Methode zur Bestimmung aller Freiheiten einer Kette.
	 * 
	 * @param k
	 *            Kette, deren Freiheiten bestimmt werden sollen.
	 * @return Liste aller Freiheiten der Kette
	 */
	public List<Position> gibFreiheiten(Kette k) {
		List<Position> freiheiten = new ArrayList<Position>();
		List<Position> helpList = new ArrayList<Position>();
		for (int i = 1; i <= k.size(); i++) {
			helpList = gibFreiheiten(k.get(i - 1));
			for (int j = 1; j <= helpList.size(); j++) {
				if (!(freiheiten.contains(helpList.get(j - 1))))
					freiheiten.add(helpList.get(j - 1));
			}
		}
		return freiheiten;
	}

	/**
	 * Führt setChanged gefolgt von notifyObservers aus.
	 */
	public void updateUI() {
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * @see java.lang.Object
	 */
	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}

		Spielbrett other = (Spielbrett) o;
		if (other.feld.length != this.feld.length) {
			return false;
		}

		for (int i = 0; i < this.feld.length; i++) {
			for (int j = 0; j < this.feld.length; j++) {
				if (feld[i][j] != other.feld[i][j]) {
					return false;
				}
			}
		}

		return true;
	}
}
