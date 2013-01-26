package go;

import java.util.ArrayList;

/**
 * DatenhaltungsKlasse einer Kette
 * 
 * @author sopr054
 */
@SuppressWarnings("serial")
public class Kette extends ArrayList<Position> {

	/**
	 * Vergleichsfunktion die eine Kette mit anderen Ketten vergleicht
	 * @param other Eine andere Kette
	 */
	public boolean equals(Object other) {
		if (!this.getClass().equals(other.getClass())) {
			return false;
		}
		Kette otherKette = (Kette) other;
		if (this.containsAll(otherKette) && otherKette.containsAll(this))
			return true;

		return false;
	}
}
