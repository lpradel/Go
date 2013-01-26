package go;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstrakte Klasse um der KI die einzelnen Varianten unterzuordnen (beispielsweise Leicht, Mittel, Schwer).
 * @author sopr055
 */
public abstract class KIStrategie {
	private Zustand farbe;
	private KIVariante kivari;
	
	/**
	 * Konstruktor, setzt gleichzeitig noch die Farbe damit die KI weiss, wer Freund oder Feind ist.
	 * @param farbe
	 */
	public KIStrategie (Zustand farbe, KIVariante kivari) {
		this.farbe = farbe;
		this.methodenZaehler = 0;
		this.kivari = kivari;
	}
	
	/**
	 * Gibt die KIVariante zurück
	 * @return KIVariante
	 */
	protected KIVariante gibKIVariante ()
	{
		return this.kivari;
	}
	
	/**
	 * Gibt die Farbe der KI zurück.
	 * @return Farbe der KI
	 */
	public Zustand gibFarbe () {
		return this.farbe;
	}
	
	/**
	 * Setzt die Farbe der KI
	 * @param farbe
	 */
	public void setzeFarbe (Zustand farbe) {
		this.farbe = farbe;
	}
	
	/**
	 * Funktionsüberladung um den Zufallszug auch für den Gegner anzubieten
	 * @param brett
	 * @param schwarz
	 * @return Position
	 */
	protected Position zufallsZug (Spielbrett brett, Zustand schwarz)
	{
		Position aktPos = null;
		
		if ( this.gibFarbe() != farbe )
		{
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
			aktPos =  this.zufallsZug(brett);
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
		}
		else
		{
			aktPos =  this.zufallsZug(brett);
		}
		
		return aktPos;
	}
	
	/**
	 * Führt einen wirklichen Zufallszug nur unter Betrachtung
	 * der Selbstmordregel aus
	 * @param brett
	 * @return Position
	 */
	protected Position zufallsZug (Spielbrett brett)
	{
		List<Position> freieFelder = brett.gibFreieFelder();
		List<Position> moeglicheFelder = new ArrayList<Position>();
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			if ( brett.gibFreiheiten(freieFelder.get(i), this.gibFarbe()).size() > 0 )
			{
				moeglicheFelder.add(freieFelder.get(i));
			}
		}
		
		Random randomGen = new Random();
		return moeglicheFelder.get(randomGen.nextInt(moeglicheFelder.size()));
	}
	
	/**
	 * Sucht vom Gegner eine Kette bzw. einen Stin mit nur einer Freiheit
	 * und diese Freiheit returnt er, ansonsten null. Überladen um auch
	 * vom "Gegner" ausgeführt werden zu können.
	 * @param brett
	 * @param farbe
	 * @return Position oder null
	 */
	public Position sucheGewinnSpielzug (Spielbrett brett, Zustand farbe)
	{
		Position aktPos = null;
		
		if ( this.gibFarbe() != farbe )
		{
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
			aktPos =  this.sucheGewinnSpielzug(brett);
			this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
		}
		else
		{
			aktPos =  this.sucheGewinnSpielzug(brett);
		}
		
		return aktPos;
	}
	
	/**
	 * Sucht vom Gegner eine Kette bzw. einen Stin mit nur einer Freiheit
	 * und diese Freiheit returnt er, ansonsten null
	 * @param brett
	 * @return Position oder Null
	 */
	public Position sucheGewinnSpielzug(Spielbrett brett) {
		List<Kette> Ketten = brett.gibKetten();
		List<Position> aktFreiheiten = null;
		
		for (int i = 0; i < Ketten.size(); i++)
		{
			if ( (brett.gib(Ketten.get(i).get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.schwarz)
					|| (brett.gib(Ketten.get(i).get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.weiss) )
			{
				aktFreiheiten = brett.gibFreiheiten(Ketten.get(i));
				if ( aktFreiheiten.size() == 1 )
				{
					return aktFreiheiten.get(0);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Wechselt die Spielerperspektive und versucht dem Untergang vorzubeugen
	 * @param brett
	 * @return Position der möglichen Schutzmaßnahme
	 */
	public Position selbstSchutz (Spielbrett brett)
	{
		this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
		Position sichererTreffer = this.sucheGewinnSpielzug(brett);
		this.setzeFarbe(this.toggleFarbe(this.gibFarbe()));
		
		if ( sichererTreffer != null )
		{
			boolean check = false;
			Spielbrett brettKopie = brett.kopiereSpielbrett();
			brettKopie.setzeStein(sichererTreffer, this.gibFarbe());
			List<Kette> Ketten = brettKopie.gibKetten();
			
			for (int i = 0; i < Ketten.size(); i++)
			{
				if ( (brettKopie.gib(Ketten.get(i).get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.weiss)
						|| (brettKopie.gib(Ketten.get(i).get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.schwarz) )
				{
					if ( (brett.gibFreiheiten(Ketten.get(i)).size() == 1) || (brett.gibFreiheiten(Ketten.get(i)).size() == 0) && check == false )
					{
						check = true;
					}
				}
			}
			
			if ( check == false )
			{
				return sichererTreffer;
			} 
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
		
	
	/**
	 * Interne Klasse als Kapsel, die Anzahl, X und Y bereitstellt
	 */
	protected class PositionKapsel extends Position {
		private int anzahl;
		
		/**
		 * Analog zu Position plus vorangestellte Anzahl/Bewertung
		 * @param anzahl
		 * @param x
		 * @param y
		 */
		public PositionKapsel (int anzahl, int x, int y)
		{
			super(x, y);
			this.anzahl = anzahl;
		}
		
		/**
		 * Gibt die Anzahl bzw. die Bewertung der Position zurueck
		 * @return int Anzahl
		 */
		public int gibAnzahl ()
		{
			return this.anzahl;
		}
		
		/**
		 * Vergleichsfunktion
		 */
		public boolean equals(Object other) {
			if (! this.getClass().equals(other.getClass())) {
				return false;
			}
			PositionKapsel otherPos = (PositionKapsel) other;
			if (otherPos.gibX() != this.gibX()) {
				return false;
			}
			if (otherPos.gibY() != this.gibY()) {
				return false;
			}
			if (otherPos.gibAnzahl() != this.gibAnzahl()) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Dreht die Spielerfarben einmal um
	 * @param farbe
	 * @return Zustand umgedrehte Farbe
	 */
	protected Zustand toggleFarbe (Zustand farbe)
	{
		if ( farbe == Zustand.weiss) {
			return Zustand.schwarz;
		}
		return Zustand.weiss;
	}
	
	/**
	 * Fakultät berechnen als rek. Funktion
	 * @param n
	 * @return Fakultät
	 */
	protected long factorial (int n)
	{
	  return n == 0 ? 1 : n * factorial (n-1);
	}
	
	/**
	 * Generiert den naechsten Spielzug den die KI machen moechte
	 * @param brett Spielbrett (aktuelles)
	 * @return Position (welche den Spielzug beinhaltet)
	 */
	public abstract Position erzeugePosition(Spielbrett brett);
	
	/**
	 * Führt einen Zug aus der die größte Freiheit in den eigenen
	 * Reihen erzügt (sprich, wohl eine eigene Kette erweitert)
	 * @param brett
	 * @return Position oder null
	 */
	protected Position besteGebietserweiterung (Spielbrett brett)
	{
		return this.kontaktZug(brett, 0);
	}
	
	/**
	 * Sucht sich die besten Kontaktzüge, die eigenen Gebietserweiterungen
	 * sind sekundär
	 * @param brett
	 * @return Position oder null
	 */
	protected Position kontaktZug (Spielbrett brett)
	{
		return this.kontaktZug(brett, 2);
	}
	
	/**
	 * Sucht sich die besten Kontaktzüge, über das Gewicht kann die
	 * Bedeutung der eigenen Gebietserweiterungen gesteuert werden.
	 * Falls als Gewicht 0 verwendet wird, verliert der Gegnergebiets-
	 * verlust völlig die Bedeutung.
	 * @param brett
	 * @param gewicht
	 * @return Position oder null
	 */
	protected Position kontaktZug (Spielbrett brett, Integer gewicht)
	{
		Random randomGenerator = new Random();
		
		List<Position> freieFelder = brett.gibFreieFelder();
		
		// Beste Kombination aus Angriff und eigener Gebietserweiterung herausfinden
		// bei gleicher Gewichtung
		// Hier wird eine Liste für mögliche Spielzüge benutzt, um nachher
		// Auswahl zu haben, denn Auswahl erfreut das Herz
		
		Spielbrett brettKopie = null;
		List<PositionKapsel> moeglicheSpielzuege = new ArrayList<PositionKapsel>();
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			brettKopie = brett.kopiereSpielbrett();
			brettKopie.setzeStein(freieFelder.get(i), this.gibFarbe());
			
			Integer gegnerFreiheiten = 0, eigeneFreiheiten = 0;
			
			List<Kette> Ketten = brettKopie.gibKetten();
			for (int y = 0; y < Ketten.size(); y++)
			{
				if ( (brettKopie.gib(Ketten.get(y).get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.schwarz)
						|| (brettKopie.gib(Ketten.get(y).get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.weiss) )
				{
					gegnerFreiheiten = (brettKopie.gibFreiheiten(Ketten.get(y)).size() > gegnerFreiheiten) ? brettKopie.gibFreiheiten(Ketten.get(y)).size() : gegnerFreiheiten;
				} else
				{
					eigeneFreiheiten = (brettKopie.gibFreiheiten(Ketten.get(y)).size() > eigeneFreiheiten) ? brettKopie.gibFreiheiten(Ketten.get(y)).size() : eigeneFreiheiten;
				}
			}
			
			// Hier fließt das Gewicht mit ein ...
			if ( gewicht == 0 )
			{
				moeglicheSpielzuege.add(new PositionKapsel((100-gegnerFreiheiten), freieFelder.get(i).gibX(), freieFelder.get(i).gibY()));
			} else
			{
				moeglicheSpielzuege.add(new PositionKapsel((eigeneFreiheiten-(gegnerFreiheiten/gewicht)), freieFelder.get(i).gibX(), freieFelder.get(i).gibY()));
			}
		}
		
		// Höchsten Wert ermitteln
		
		Integer besteZugstaerke = 0;
		List<PositionKapsel> besteSpielzuege = new ArrayList<PositionKapsel>();
		
		for (int i = 0; i < moeglicheSpielzuege.size(); i++)
		{
			if ( besteZugstaerke == moeglicheSpielzuege.get(i).gibAnzahl() )
			{
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
			} else if ( besteZugstaerke < moeglicheSpielzuege.get(i).gibAnzahl() )
			{
				besteSpielzuege = new ArrayList<PositionKapsel>();
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
				besteZugstaerke = moeglicheSpielzuege.get(i).gibAnzahl();
			}
		}
		
		// ... und aus allen mit diesem hohen Wert Random aussuchen, falls
		// keine existiert returne NULL damit KI anderen Zug wählen kann
		
		if ( besteSpielzuege.size() > 0 )
		{
			PositionKapsel aktPos = besteSpielzuege.get(randomGenerator.nextInt(besteSpielzuege.size()));
			
			return new Position(aktPos.gibX(), aktPos.gibY());
		}
		else {
			return null;
		}
	}
	
	/**
	 * Takefu: Leave No Man Behind Spielzug, falls niemand behind geleaved wurde,
	 * returne null (vgl. http://de.wikibooks.org/wiki/Go:_Verbinden#Takefu)
	 * @param brett
	 * @return Position oder null
	 */
	protected Position takefu (Spielbrett brett)
	{
		Random randomGenerator = new Random();
		
		// Eigene längste Kette zählen
		
		List<Kette> Ketten = brett.gibKetten();
		Integer eigeneLaengsteKette = 0;
		for (int y = 0; y < Ketten.size(); y++)
		{
			if ( (brett.gib(Ketten.get(y).get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.weiss)
					|| (brett.gib(Ketten.get(y).get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.schwarz) )
			{
				eigeneLaengsteKette = ( Ketten.get(y).size() > eigeneLaengsteKette ) ? Ketten.get(y).size() : eigeneLaengsteKette;
			}
		}
		
		// Mögliche Spielzüge durchgehen und Ketten zählen
		
		List<Position> freieFelder = brett.gibFreieFelder();
		
		Spielbrett brettKopie = null;
		List<PositionKapsel> moeglicheSpielzuege = new ArrayList<PositionKapsel>();
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			brettKopie = brett.kopiereSpielbrett();
			brettKopie.setzeStein(freieFelder.get(i), this.gibFarbe());
			
			Integer moeglicheLaengsteKette = 0;
			
			Ketten = brettKopie.gibKetten();
			for (int y = 0; y < Ketten.size(); y++)
			{
				if ( (brettKopie.gib(Ketten.get(y).get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.weiss)
						|| (brettKopie.gib(Ketten.get(y).get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.schwarz) )
				{
					moeglicheLaengsteKette = ( Ketten.get(y).size() > moeglicheLaengsteKette ) ? Ketten.get(y).size() : moeglicheLaengsteKette;
				}
			}
			
			moeglicheSpielzuege.add(new PositionKapsel(moeglicheLaengsteKette, freieFelder.get(i).gibX(), freieFelder.get(i).gibY()));
		}
		
		// Jetzt durch alle möglichen Spielzüge iterieren und 
		// nachsehen wie groß die Differenz zur eigenen Kettenanzahl
		// ist; der gewünschte Sollwert beträgt 2, falls dieser
		// nicht erreicht wird, werden wir null returnen sodass
		// die KI was sinnvolleres tun kann
		
		Integer besteZugstaerke = 0;
		List<PositionKapsel> besteSpielzuege = new ArrayList<PositionKapsel>();
		
		for (int i = 0; i < moeglicheSpielzuege.size(); i++)
		{
			if ( besteZugstaerke == moeglicheSpielzuege.get(i).gibAnzahl() )
			{
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
			} else if ( besteZugstaerke < moeglicheSpielzuege.get(i).gibAnzahl() )
			{
				besteSpielzuege = new ArrayList<PositionKapsel>();
				besteSpielzuege.add(moeglicheSpielzuege.get(i));
				besteZugstaerke = moeglicheSpielzuege.get(i).gibAnzahl();
			}
		}
		
		if ( besteSpielzuege.size() > 0 )
		{
			Random randomGen = new Random();
			if ( (besteSpielzuege.get(randomGen.nextInt(besteSpielzuege.size())).gibAnzahl() - eigeneLaengsteKette) >= 2 )
			{
				PositionKapsel aktPos = besteSpielzuege.get(randomGenerator.nextInt(besteSpielzuege.size()));
				
				return new Position(aktPos.gibX(), aktPos.gibY());
			}
		}
		
		return null;
	}
	
	/**
	 * Sucht sich ein hübsches Feld mitten in der Pampas raus
	 * @param brett
	 * @return Position oder null
	 */
	protected Position zufallsZugMitFreiheiten (Spielbrett brett)
	{
		return this.zufallsZugMitFreiheiten(brett, Zustand.frei);
	}
	
	/**
	 * Gibt ein Feld mit möglichst vielen Freiheiten aus, über
	 * den Parameter freiheitsZustand ist regulierbar, ob nur
	 * freie Felder oder die eigene Farbe mit zu den Freiheiten
	 * gezählt werden soll
	 * @param brett
	 * @param freiheitsZustand
	 * @return Position oder null
	 */
	protected Position zufallsZugMitFreiheiten (Spielbrett brett, Zustand freiheitsZustand)
	{
		List<Position> freieFelder = brett.gibFreieFelder();
		
		Spielbrett brettKopie = null;
		List<Position> moeglicheSpielzuege = new ArrayList<Position>();
		
		Integer groessteFreiheit = 0, aktFreiheit = 0;
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			brettKopie = brett.kopiereSpielbrett();
			brettKopie.setzeStein(freieFelder.get(i), this.gibFarbe());
			
			aktFreiheit = brettKopie.gibFreiheiten(freieFelder.get(i), freiheitsZustand).size();
			
			if ( aktFreiheit > groessteFreiheit )
			{
				moeglicheSpielzuege = new ArrayList<Position>();
				moeglicheSpielzuege.add(freieFelder.get(i));
				
				groessteFreiheit = aktFreiheit;
			} else if ( aktFreiheit > groessteFreiheit )
			{
				moeglicheSpielzuege.add(freieFelder.get(i));
			}
			
		}
		
		if ( moeglicheSpielzuege.size() > 0 )
		{
			Random randomGen = new Random();
			return moeglicheSpielzuege.get(randomGen.nextInt(moeglicheSpielzuege.size()));
		}
		
		return null;
	}
	
	/**
	 * Integer um die randomisierte Auswahl der Methoden etwas zu unterstützen
	 */
	protected Integer methodenZaehler;
	
	/**
	 * Gibt den aktuellen Zaehlerstand zurueck
	 * @return methodenZähler
	 */
	protected Integer gibMethodenZaehler ()
	{
		return this.methodenZaehler;
	}
	
	/**
	 * Erhöht den Methodenzähler mit Zahlen zwischen 1 und 3
	 */
	protected void erhoeheMethodenZaehler ()
	{
		Random randomGen = new Random();
		this.methodenZaehler = this.methodenZaehler + randomGen.nextInt(11) + 1;
	}

}

