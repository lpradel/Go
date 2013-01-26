package go;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung der KIStrategie "Schwer".
 * 
 * Als Baukasten angelegt um so viel mit den Einstellungen zu spielen.
 * Auf diese Weise sind auch viele weitere einfachere KI Abarten
 * möglich (dafür muss die erzeugePosition Funktion überschrieben werden).
 * @author sopr055
 */
public class Schwer extends KIStrategie
{
	private Spielbrett brett = null;
	private Spieler spieler1, spieler2;
	private List<PositionKapsel> posBewertung;
	
	/**
	 * Konstruktor der den Superkonstruktor von KIStrategie auf
	 * @param farbe
	 */
	public Schwer (Zustand farbe, KIVariante kivari)
	{
		super(farbe, kivari);
		
		this.spieler1 = new Spieler(this.gibFarbe(), "David", SpielerTyp.schwer);
		this.spieler2 = new Spieler(this.toggleFarbe(this.gibFarbe()), "Goliath", SpielerTyp.mensch);
	}

	/**
	 * Generiert den naechsten Spielzug den die schwere KI machen moechte
	 * 
	 * Anmerkung der Redaktion: sehr experimentell und work in progress gefährdet!
	 * @param brett Spielbrett (aktuelles)
	 * @return Position (welche den Spielzug beinhaltet)
	 */
	public Position erzeugePosition(Spielbrett brett)
	{
		this.brett = brett;
		
		Position gewinnPos = this.sucheGewinnSpielzug(this.brett);
		if ( gewinnPos != null )
		{
			return gewinnPos;
		}
		
		Position schutzPos = this.selbstSchutz(brett);
		
		if ( schutzPos != null && this.gibKIVariante().istZulaessig(new Spielzug(schutzPos, this.spieler1)) == RegelBruch.keiner)
		{
			return schutzPos;
		}
		
		List<Kette> Ketten = brett.gibKetten();
		int gegnerFreiheiten = 0, eigeneFreiheiten = 0, tempFreiheiten = 0, alleGegnerFreiheiten = 0;
		Kette aktKette = null;
		this.posBewertung = new ArrayList<PositionKapsel>();
		
		for (int y = 0; y < Ketten.size(); y++)
		{
			aktKette = Ketten.get(y);
			tempFreiheiten = brett.gibFreiheiten(aktKette).size();
			
			if ( (brett.gib(aktKette.get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.schwarz)
					|| (brett.gib(aktKette.get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.weiss) )
			{
				alleGegnerFreiheiten = alleGegnerFreiheiten + tempFreiheiten;
				
				if ( tempFreiheiten < gegnerFreiheiten )
				{
					gegnerFreiheiten = tempFreiheiten;
				}
			} else
			{
				if ( tempFreiheiten < eigeneFreiheiten )
				{
					eigeneFreiheiten = tempFreiheiten;
				}
			}
		}
		
		List<Position> freieFelder = brett.gibFreieFelder();
		Spielbrett brettKopie = null;
		Position besterZug = null, aktPos = null;
		int besterZugBewertung = -1000000;
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			aktPos = freieFelder.get(i);
			if ( this.gibKIVariante().istZulaessig(new Spielzug(aktPos, this.gibRichtigenSpieler(this.gibFarbe()))) == RegelBruch.keiner )
			{
				brettKopie = brett.kopiereSpielbrett();

				new Thread(new HeuristikRunnable(0, aktPos, brettKopie, this.gibFarbe(), gegnerFreiheiten, eigeneFreiheiten, alleGegnerFreiheiten)).start();
			}
		}
		
		while ( getPositionen().size() < freieFelder.size()){
			;
		}
		
		for (int i = 0; i < freieFelder.size(); i++)
		{
			//System.out.println("bewertung > besterZugBewertung:" + getPositionen().get(i).gibAnzahl() + " > " + besterZugBewertung + " : " + (getPositionen().get(i).gibAnzahl() > besterZugBewertung));
			
			if ( getPositionen().get(i).gibAnzahl() > besterZugBewertung )
			{
				besterZugBewertung = getPositionen().get(i).gibAnzahl();
				besterZug = new Position(getPositionen().get(i).gibX(), getPositionen().get(i).gibY());
			}
		}
		
		return besterZug;
	}
	
	/**
	 * Gibt den der Farbe zugehörigen Spieler innerhalb der Klasse aus
	 * @param farbe
	 * @return Spieler
	 */
	private Spieler gibRichtigenSpieler (Zustand farbe)
	{
		if ( this.spieler1.gibFarbe() == farbe )
		{
			return this.spieler1;
		} else {
			return this.spieler2;
		}
	}
	
	/**
	 * 
	 * Anmk.: Es sollte vorher überprüft werden ob der Zug gültig ist!
	 * @param aktuelleTiefe
	 * @param spielzug
	 * @param brett
	 * @param aktuellerSpieler
	 * @return int (Bewertung)
	 */
	private int heuristik (int aktuelleTiefe, Position spielzug, Spielbrett brett, Zustand aktuellerSpieler, int aktGegnerFreiheiten, int aktEigeneFreiheiten, int aktAlleGegnerFreiheiten)
	{
		int rekursionsTiefe = 2;
		aktuelleTiefe++;
		
		brett.setzeStein(spielzug, aktuellerSpieler);
		
		int bewertung = 0;
		
		List<Kette> Ketten = brett.gibKetten();
		int gegnerFreiheiten = 100, eigeneFreiheiten = 100, tempFreiheiten = 0, alleGegnerFreiheiten = 0;
		Kette aktKette = null;
		Position freiheitsPos = null;
		
		for (int y = 0; y < Ketten.size(); y++)
		{
			aktKette = Ketten.get(y);
			tempFreiheiten = brett.gibFreiheiten(aktKette).size();
			
			if ( (brett.gib(aktKette.get(0)) == Zustand.weiss && this.gibFarbe() == Zustand.schwarz)
					|| (brett.gib(aktKette.get(0)) == Zustand.schwarz && this.gibFarbe() == Zustand.weiss) )
			{
				alleGegnerFreiheiten = alleGegnerFreiheiten + tempFreiheiten;
				if ( tempFreiheiten < gegnerFreiheiten )
				{
					gegnerFreiheiten = tempFreiheiten;
				}
			} else
			{
				if ( tempFreiheiten < eigeneFreiheiten )
				{
					eigeneFreiheiten = tempFreiheiten;
					if ( tempFreiheiten > 0 ) {
						freiheitsPos = brett.gibFreiheiten(aktKette).get(0);
					}
				}
			}
		}
		
		if ( eigeneFreiheiten < 2 && aktuellerSpieler == this.gibFarbe() )
		{
			if ( brett.gibFreiheiten(freiheitsPos).size() == 1 )
			{
				bewertung = -500;
			} else
			{
				bewertung = -1;
			}
		} else {
			if ( gegnerFreiheiten < 2 && aktuellerSpieler == this.gibFarbe() )
			{
				bewertung = bewertung + 2;
			} else if ( gegnerFreiheiten == 2 && aktuellerSpieler == this.gibFarbe() )
			{
				bewertung++;
			}
			
			if ( eigeneFreiheiten < 2 && aktuellerSpieler != this.gibFarbe() )
			{
				bewertung = bewertung - 4;
			} else if ( eigeneFreiheiten == 2 && aktuellerSpieler != this.gibFarbe() )
			{
				bewertung = bewertung - 2;
			}
			
			bewertung = bewertung + ((aktGegnerFreiheiten - gegnerFreiheiten) * 3 / 2 ) + (eigeneFreiheiten - aktEigeneFreiheiten) + (rekursionsTiefe-aktuelleTiefe+1) * ((int) factorial(rekursionsTiefe-aktuelleTiefe+4) * ((int) factorial(rekursionsTiefe-aktuelleTiefe+1) * (aktAlleGegnerFreiheiten - alleGegnerFreiheiten) + (alleGegnerFreiheiten * -1)));
			
			/*
			if ( ((spielzug.gibX() == 6 && spielzug.gibY() == 5 ) || (spielzug.gibX() == 5 && spielzug.gibY() == 6 )) && aktuelleTiefe == 1 )
			{
				System.out.println(spielzug);
				System.out.println("Allgemeine Bewertung:" + bewertung);
				System.out.println("(aktGegnerFreiheiten - gegnerFreiheiten) * 3 / 2 ):" + ((aktGegnerFreiheiten - gegnerFreiheiten) * 3 / 2 ));
				System.out.println("(eigeneFreiheiten - aktEigeneFreiheiten):" + (eigeneFreiheiten - aktEigeneFreiheiten));
				System.out.println("(rekursionsTiefe-aktuelleTiefe+1):" + (rekursionsTiefe-aktuelleTiefe+1));
				System.out.println("(aktAlleGegnerFreiheiten - alleGegnerFreiheiten):" + ((int) factorial(rekursionsTiefe-aktuelleTiefe+1) * (aktAlleGegnerFreiheiten - alleGegnerFreiheiten) + (alleGegnerFreiheiten * -1)));
				System.out.println("(rekursionsTiefe-aktuelleTiefe+1) * (aktAlleGegnerFreiheiten - alleGegnerFreiheiten):" + ((rekursionsTiefe-aktuelleTiefe+1) * (aktAlleGegnerFreiheiten - alleGegnerFreiheiten)));
			} */
			
			if ( aktuelleTiefe <= rekursionsTiefe )
			{
				List<Position> freieFelder = brett.gibFreieFelder();
				Spielbrett brettKopie = null;
				
				for (int i = 0; i < freieFelder.size(); i++)
				{
					if ( this.gibKIVariante().istZulaessig(new Spielzug(freieFelder.get(i), this.gibRichtigenSpieler(this.toggleFarbe(aktuellerSpieler)))) == RegelBruch.keiner )
					{
						brettKopie = brett.kopiereSpielbrett();
						bewertung = bewertung + this.heuristik(aktuelleTiefe, freieFelder.get(i), brettKopie, this.toggleFarbe(aktuellerSpieler), gegnerFreiheiten, eigeneFreiheiten, alleGegnerFreiheiten);
					}
				}
			} 
		}
		
		return bewertung;
		
		//return  (rekursionsTiefe-aktuelleTiefe+1) * bewertung;
	}
	
	private synchronized void addPosition (int bewertung, Position aktPos)
	{
		this.posBewertung.add(new PositionKapsel(bewertung, aktPos.gibX(), aktPos.gibY()));
	}
	
	private synchronized List<PositionKapsel> getPositionen ()
	{
		return this.posBewertung;
	}
	
	private class HeuristikRunnable implements Runnable{
		private Position aktPos;
		private Spielbrett brett;
		private int aktuelleTiefe, gegnerFreiheiten, eigeneFreiheiten, alleGegnerFreiheiten;
		private Zustand aktuellerSpieler;
		
		public HeuristikRunnable(int aktuelleTiefe, Position spielzug, Spielbrett brett, Zustand aktuellerSpieler, int aktGegnerFreiheiten, int aktEigeneFreiheiten, int alleGegnerFreiheiten){
			this.aktPos = spielzug;
			this.brett = brett;
			this.aktuellerSpieler = aktuellerSpieler;
			this.eigeneFreiheiten = aktEigeneFreiheiten;
			this.gegnerFreiheiten = aktGegnerFreiheiten;
			this.aktuelleTiefe = aktuelleTiefe;
			this.alleGegnerFreiheiten = alleGegnerFreiheiten;
		}

		public void run() {
			int bewertung;
			bewertung = heuristik(this.aktuelleTiefe, this.aktPos, this.brett, this.aktuellerSpieler, this.gegnerFreiheiten, this.eigeneFreiheiten, this.alleGegnerFreiheiten);
			addPosition(bewertung, aktPos);
		}
	}
	
}
