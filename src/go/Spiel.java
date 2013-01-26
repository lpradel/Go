package go;

/**
 * Spielklasse, aus ihr werden einzelne Spiele instanziiert. Besitzt Operationen, die vom Controler aufgerufen werden.
 * @author sopr054
 *
 */
public class Spiel {

	private Variante dieVariante;

	private Spielstand derSpielstand;

	private Spielbrett dasSpielbrett;

	private boolean zuEnde = false;
	/**
	 * Initialisierung von Attributen .Erzeugt ein Spiel mit den angegebenen Eigenschaften.
	 * @param var Die Variante des Spiel:entweder klassisches Go oder Steinschlag.
	 * @param spielstand Der Stand des Spiels.
	 * @param brett Das brett des Spiels.
	 */
	public Spiel (Variante var, Spielstand spielstand, Spielbrett brett)
	{
		this.dieVariante = var;
		this.derSpielstand = spielstand;
		this.dasSpielbrett = brett;
	}
	
	/**
	 * Erzeugt ein Spielobjekt aus einem geladenen Spielstand.
	 * Dafuer wird noch durch die Variante ein Brett erzeugt,
	 * anschliessend werden noch die geladenen Spielzuege vom Spielstand
	 * entsprechend ausgefuehrt.
	 * 
	 * @param spielstand Der Spielstand, von dem aus das Spiel konstruiert werden soll.
	 */
	public Spiel(Spielstand spielstand){
		this.derSpielstand = spielstand;
		this.dieVariante = spielstand.gibVariante();
		this.dasSpielbrett = dieVariante.initialisiereSpielbrett(derSpielstand.gibGroesse());
		
		while (derSpielstand.istRedoMoeglich())
		{
			try {
				this.redo();
			}
			catch (Exception e)
			{
				System.err.println("Fehler beim Erzeugen des Spiels aus Spielstand!");
			}
		}
	}
	
	public void setzeVariante(Variante var){
		dieVariante = var;
	}
	
	public Variante gibVariante(){
		return this.dieVariante;
	}
	
	public void setzeSpielstand(Spielstand spielstand){
		derSpielstand = spielstand;
	}
	
	public Spielstand gibSpielstand(){
		return this.derSpielstand;
	}
	
	public void setzeSpielbrett(Spielbrett brett){
		dasSpielbrett = brett;
	}
	
	public Spielbrett gibSpielbrett(){
		return this.dasSpielbrett;
	}
	
	/**
	 * Liefert true, genau dann, wenn dieses Spiel zu Ende ist,
	 * sonst false.
	 * 
	 * @return Das Ergebnis oO
	 */
	public boolean istSpielZuEnde(){
		return zuEnde;
	}
	
	/**
	 * @param s Spieler, dessen Punkte zurückgegeben werden soll.
	 * @return Gibt Punkte des Spielers s zurück.
	 */
	public int gibPunkte(Spieler s) {
			return derSpielstand.gibPunkte(s)+dieVariante.berechnePunkte(s);
	}
	
	/**
	 * Wird vom Controller aufgerufen, falls Redo gemacht wird. Ruft Redo auf dem Spielstand auf.
	 */
	public void redo() throws KeinRedoMoeglichException, UngueltigerZugException {
		Spielzug wiederherzustellen;
		wiederherzustellen = derSpielstand.redo();
		wiederherzustellen.gibGeschlageneSteine().clear();	// dirty fix fuer Punkte
		this.fuehreSpielzugAus(wiederherzustellen);
	}
	
	/**
	 * Wird vom Controller aufgerufen, falls Undo gemacht wird. Ruft Undo auf dem Spielstand auf und ändert das Brett.
	 */
	public void undo() throws KeinUndoMoeglichException{
		//eigentlich dirty, aber egal!
		this.dieVariante.zuEnde = false;
		Spielzug zurueckgenommen = derSpielstand.undo();
		dasSpielbrett.nimmZugZurueck(zurueckgenommen);
	}
	
	/**
	 * Lässt den Spielzug von der Variante überprüfen, vervollständigen und ruft "fügeSpielzughinzu" auf dem Spielstand auf.
	 * @param s Spielzug, der von der Variante überprüft und vervollständigt, und von dem Spielstand hinzugefügt werden soll.
	 */
	public void fuehreSpielzugAus(Spielzug s) throws UngueltigerZugException {
		Spielzug vollstaendig = dieVariante.erzeugeSpielzug(s); // auf der Variante gibt es eine Methode erzeugeSpielzug()! Die soll hier aufgerufen werden.
		this.zuEnde = this.dieVariante.istZuEnde();
		dasSpielbrett.setzeZug(s);
		derSpielstand.fuegeSpielzugHinzu(vollstaendig);
	}

	/**
	 * Lädt einen gespeicherten Spielstand.
	 * @param s Der Spielstand,der geladen wird.
	 */
	public void ladeSpielstand(Spielstand s) throws LadenException {
		derSpielstand = s;
	}
	
	protected void setzeEnde(boolean zuEnde){
		this.zuEnde = zuEnde;
	}

}
