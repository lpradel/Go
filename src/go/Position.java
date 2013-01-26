package go;

/**
 * Objekt was quasi ein Tupel darstellt zur Verwaltung der x und y Koordinaten
 */
public class Position {
	private int x;
	private int y;

	/**
	 * Konstruktor der die Position mit x und y Werten initialisiert
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Konstruktor der auch Positionen über einen String erstellen kann
	 * @param pos
	 */
	public Position(String pos) {
		/* Fehlerfall: Syntax ist z.B. a7 */
		if (pos.length() < 2)
		{
			this.x = -1;
			this.y = -1;
			return;
		}
		
		/* Umwandeln */
		int zeile = (int)pos.charAt(0) - 96;
		int spalte = Integer.parseInt(pos.substring(1));
		
		this.x = zeile;
		this.y = spalte;
	}

	/**
	 * Gibt die X Koordinate der Position zurück
	 * @return X Koordinate (int)
	 */
	public int gibX() {
		return x;
	}

	/**
	 * Gibt die Y Koordinate der Position zurück
	 * @return Y Koordinate (int)
	 */
	public int gibY() {
		return y;
	}
	
	/**
	 * Gibt die Position codiert in der Form z.B. a5 zurück
	 * @return Der codierte String der Position (String)
	 */
	public String gibPosition() {
		return String.valueOf((char)(x + 96)) + String.valueOf(y);
	}

	/**
	 * Vergleichsfunktion die die Koordinaten miteinander vergleicht
	 */
	@Override
	public boolean equals(Object other) {
		if (!this.getClass().equals(other.getClass())) {
			return false;
		}
		Position otherPos = (Position) other;
		if (otherPos.gibX() != this.gibX()) {
			return false;
		}
		if (otherPos.gibY() != this.gibY()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		return "x: "+this.gibX() + "; y: "+this.gibY();
	}
}
