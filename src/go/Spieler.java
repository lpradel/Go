package go;

public class Spieler {

	private Zustand farbe;
	private String name;
	private SpielerTyp typ;
	
	/**
	 * Konstruktur: Erzeugt Spieler unter Angabe von Farbe und Namen
	 * @param zustand farbe Die Farbe, die der Spieler haben soll.
	 * @param name Der Name, den der Spieler tragen soll und der spaeter auch in der Statistik auftaucht.
	 * @param typ Der Typ, den der Spieler haben soll
	 */
	public Spieler(Zustand zustand, String name, SpielerTyp typ)
	{
		this.farbe = zustand;
		this.name = name;
		this.typ = typ;
	}
	
	/**
	 * Gibt die Farbe des Spielers als Farbe zurueck
	 * @return Spielerfarbe:Farbe
	 */
	public Zustand gibFarbe() {
		return farbe;
	}
	
	/**
	 * Gibt den Namen des Spielers als String zurueck
	 * @return Spielername:String
	 */
	public String gibName() {
		return name;
	}
	
	/**
	 * Gibt den Typ des Spielers im enum zurueck
	 * @return typ:SpielerTyp Der Typ des Spielers.
	 */
	public SpielerTyp gibTyp() {
		return typ;
	}
	
	/**
	 * equals-Methode fuer allgemeine Objekte vom Typ Object.
	 * Gibt true zurueck wenn das gegebene Spielerobjekt mit diesem uebereinstimmt, sonst false
	 * 
	 * @param o Das Object, mit dem der Spieler verglichen werden soll
	 */
	public boolean equals(Object o)
	{
		/* Falls kein Spieler-Objekt, unmoeglich gleich */
		if (o.getClass() != this.getClass())
		{
			return false;
		}
		
		return this.equals((Spieler) o);
	}
	
	/**
	 * Gibt true zurueck wenn das gegebene Spielerobjekt mit diesem uebereinstimmt, sonst false
	 * @param anderer Spieler, der mit dem Spieler des Objekts verglichen werden soll.
	 * @return boolean
	 */
	public boolean equals(Spieler anderer)
	{
		return ( (name == anderer.gibName() ) && (farbe == anderer.gibFarbe()) && (typ == anderer.gibTyp()) );
	}
	
	/**
	 * Setzt den neuen Typ des Spielers
	 * 
	 * @param typ Der neue Typ des Spielers
	 */
	public void setzeTyp(SpielerTyp typ) {
		this.typ = typ;
	}
	
	@Override
	public String toString(){
		return "Name: " + this.gibName() + ", Farbe: " + this.gibFarbe() + ", Typ: " + this.gibTyp();
	}

}
