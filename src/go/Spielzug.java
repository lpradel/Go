package go;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, die alle für den Spielzug relevanten Informationen merkt und dementsprechende Methoden zur Verfügung stellt. 
 * @author sopr056 Sarah
 */
public class Spielzug {
	private Position gesetzterStein;
	private ArrayList<Position> geschlageneSteine = new ArrayList<Position>();
	private Spieler spieler;

	/**
	 * Konstruktor der die Klasse mit den wichtigsten Daten initialisiert: Den gesetzten Stein und dem Spieler
	 * @param gesetzterStein gibt die Position des Steins, der gesetzt werden soll 
	 * @param spieler gibt den Spieler der diesen Spielzug ausgeführt hat
	 */
	public Spielzug(Position gesetzterStein, Spieler spieler)
	{
		this.gesetzterStein = gesetzterStein;
		this.spieler = spieler;
	}
	/**
	 * setzt die Punkte die in diesem Spielzug erlangt wurden
	 * @param i die Punkte die zu setzen sind
	 */
	
	/**
	 * Gibt die Punkte, welche in diesem Spielzug erlangt wurden
	 * @return Punkte
	 */
	public int gibPunkte() {
		return geschlageneSteine.size();
	}
	
	/**
	 * Fügt in den Spielzug die Liste der geschlagenen Steine hinzu
	 * @param geschlageneSteine
	 */
	public void setzeGeschlageneSteine(List<Position> geschlageneSteine)
	{
		this.geschlageneSteine.addAll(geschlageneSteine);
	}
	
	/**
	 * Gibt die Liste der geschlagenen Steine
	 * @return geschlageneSteine
	 */
	public List<Position> gibGeschlageneSteine()
	{
		return geschlageneSteine;
	}
	
	/**
	 * gibt die Position des gesetzten Steins 
	 * @return gesetzterStein
	 */
	public Position gibGesetztenStein()
	{
		return gesetzterStein;
	}
	
	/**
	 * gibt den Spieler der den Zug gemacht hat 
	 * @return Spieler
	 */
	public Spieler gibSpieler()
	{
		return spieler;
	}
	
}
