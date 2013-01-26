package go;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementierung der KIStrategie "Leicht".
 * 
 * @author sopr055
 */
public class Leicht extends KIStrategie {

	/**
	 * 
	 * @param farbe
	 */
	public Leicht(Zustand farbe, KIVariante kivari) {
		super(farbe, kivari);
	}

	/**
	 * Überladene Funktion um sucheVieleFreiheiten auch für einen "Gegner" zu
	 * ermöglichen
	 * 
	 * @param brett
	 * @param farbe
	 * @return Position des Feldes
	 */
	public Position sucheVieleFreiheiten(Spielbrett brett, Zustand farbe) {
		Position aktPos = null;

		if (this.gibFarbe() != farbe) {
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
			aktPos = this.sucheVieleFreiheiten(brett);
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
		} else {
			aktPos = this.sucheVieleFreiheiten(brett);
		}

		return aktPos;
	}

	/**
	 * Führt eine Mischung aus Kontaktzug und Gebietserweiterung aus, beide
	 * Sachen sind gleich gewichtet
	 * 
	 * @param brett
	 * @return Position des Feldes
	 */
	public Position sucheVieleFreiheiten(Spielbrett brett) {
		// Versuche dem Gegner viele Freiheiten an einer Position zu entziehen
		// und selbst möglichst viele dabei zu erlangen
		// und dann aus den besten Kandidaten Random selektieren

		Random randomGenerator = new Random();

		List<Position> freieFelder = brett.gibFreieFelder();
		Spielbrett brettKopie = null;
		List<PositionKapsel> moeglicheSpielzuege = new ArrayList<PositionKapsel>();

		for (int i = 0; i < freieFelder.size(); i++) {
			brettKopie = brett.kopiereSpielbrett();
			brettKopie.setzeStein(freieFelder.get(i), this.gibFarbe());

			Integer gegnerFreiheiten = 0, eigeneFreiheiten = 0;

			List<Kette> Ketten = brettKopie.gibKetten();
			for (int y = 0; y < Ketten.size(); y++) {
				if ((brettKopie.gib(Ketten.get(y).get(0)) == Zustand.weiss && this
						.gibFarbe() == Zustand.schwarz)
						|| (brettKopie.gib(Ketten.get(y).get(0)) == Zustand.schwarz && this
								.gibFarbe() == Zustand.weiss)) {
					gegnerFreiheiten = (brettKopie.gibFreiheiten(Ketten.get(y))
							.size() > gegnerFreiheiten) ? brettKopie
							.gibFreiheiten(Ketten.get(y)).size()
							: gegnerFreiheiten;
				} else {
					eigeneFreiheiten = (brettKopie.gibFreiheiten(Ketten.get(y))
							.size() > eigeneFreiheiten) ? brettKopie
							.gibFreiheiten(Ketten.get(y)).size()
							: eigeneFreiheiten;
				}
			}

			moeglicheSpielzuege.add(new PositionKapsel(
					(eigeneFreiheiten - gegnerFreiheiten), freieFelder.get(i)
							.gibX(), freieFelder.get(i).gibY()));
		}

		// Höchsten Wert ermitteln

		Integer besteZugstaerke = 0;
		List<PositionKapsel> besteSpielzuege = new ArrayList<PositionKapsel>();

		for (int i = 0; i < moeglicheSpielzuege.size(); i++) {
			if (besteZugstaerke == moeglicheSpielzuege.get(i).gibAnzahl()) {
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
			} else if (besteZugstaerke < moeglicheSpielzuege.get(i).gibAnzahl()) {
				besteSpielzuege = new ArrayList<PositionKapsel>();
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
				besteZugstaerke = moeglicheSpielzuege.get(i).gibAnzahl();
			}
		}

		// ... und aus allen mit diesem hohen Wert Random aussuchen

		if (besteSpielzuege.size() > 0) {
			PositionKapsel aktPos = besteSpielzuege.get(randomGenerator
					.nextInt(besteSpielzuege.size()));

			return new Position(aktPos.gibX(), aktPos.gibY());
		} else {
			List<Position> freieFelder3 = brett.gibFreieFelder();

			if (freieFelder3.size() == 0) {
				return null;
			}

			Position endPos = freieFelder3.get(randomGenerator
					.nextInt(freieFelder3.size()));

			return new Position(endPos.gibX(), endPos.gibY());
		}
	}

	/**
	 * Generiert den naechsten Spielzug den die leichte KI machen moechte.
	 * 
	 * Falls ein Feld des Gegners existiert mit nur einer Freiheit dann gebe
	 * diese Freiheit zurueck Sonst suche innerhalb der freien Felder nach einem
	 * Feld mit den meisten Freiheiten und gebe dieses Feld zurueck
	 * 
	 * @param brett
	 *            Spielbrett (aktuelles)
	 * @return Position (welche den Spielzug beinhaltet)
	 * @deprecated Neue Funktion die auf die KIStrategie Methoden zugreift
	 *             vorhanden
	 */
	public Position erzeugePosition_alt(Spielbrett brett) {
		// Sichere Gewinnzuege suchen und ausfuehren ...

		Position gewinnPos = this.sucheGewinnSpielzug(brett);
		if (gewinnPos != null) {
			return gewinnPos;
		}

		// Auf Angriffsvektoren überprüfen ...

		gewinnPos = this.selbstSchutz(brett);
		if (gewinnPos != null) {
			return gewinnPos;
		}
		;

		// Falls kein sinnvoller Spielzug möglich ist ...

		Position aktPos = null;
		boolean zugErfolgreich = false;

		while (zugErfolgreich == false) {
			aktPos = this.sucheVieleFreiheiten(brett);

			if (aktPos != null) {
				zugErfolgreich = true;
			} else {
				aktPos = this.zufallsZug(brett);
				if (aktPos != null) {
					zugErfolgreich = true;
				}
			}
		}

		return aktPos;
	}

	/**
	 * Generiert den naechsten Spielzug den die schwere KI machen moechte
	 * 
	 * Anmerkung der Redaktion: sehr experimentell und work in progress
	 * gefährdet!
	 * 
	 * @param brett
	 *            Spielbrett (aktuelles)
	 * @return Position (welche den Spielzug beinhaltet)
	 */
	public Position erzeugePosition(Spielbrett brett) {
		// Lucky Punch

		Position gewinnPos = this.sucheGewinnSpielzug(brett);
		if (gewinnPos != null) {
			return gewinnPos;
		}

		// Leben

		Position schutzPos = this.selbstSchutz(brett);

		if (schutzPos != null
				&& this.gibKIVariante().istZulaessig(
						new Spielzug(schutzPos, new go.Spieler(this.gibFarbe(),
								"DerLukas", SpielerTyp.schwer))) == RegelBruch.keiner) {
			return schutzPos;
		}

		// Leave No Man Behind
		// (@comment: evtl. mit in den Random Bereich mit reinnehmen - dunno
		// yet)

		Position takefu = this.takefu(brett);
		if (takefu != null
				&& this.gibKIVariante().istZulaessig(
						new Spielzug(takefu, new go.Spieler(this.gibFarbe(),
								"DerLukas", SpielerTyp.schwer))) == RegelBruch.keiner) {
			// System.out.println(takefu);
			return takefu;
		}

		Position aktPos = null;
		boolean zugErfolgreich = false;

		while (zugErfolgreich == false) {
			this.erhoeheMethodenZaehler();

			if (aktPos == null && (this.gibMethodenZaehler() % 10) < 9 )
			{
				aktPos = this.besteGebietserweiterung(brett);
			} else
			{
				aktPos = this.zufallsZugMitFreiheiten(brett);
			}

			// aktPos = this.kontaktZug(brett);
			// aktPos = this.zufallsZugMitFreiheiten(brett);
			// aktPos = this.zufallsZug(brett);
			
			if ( this.gibKIVariante().istZulaessig(
						new Spielzug(takefu, new go.Spieler(this.gibFarbe(),
								"DerLukas", SpielerTyp.schwer))) != RegelBruch.keiner )
								{
									aktPos = null;
								}

			if (aktPos != null) {
				zugErfolgreich = true;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return aktPos;
	}
}
