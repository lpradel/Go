package go;

import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.LinkedList;

import java.io.StringWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;

/* XML-Imports */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;



/**
 * Die Spielstandklasse; wird vom Controller beobachtet.
 * Bietet die besprochenen Methoden.
 * 
 * @author sopr053
 *
 */
public class Spielstand extends Observable {

	/* Die beiden Spieler */
	private Spieler Spieler1;
	private Spieler Spieler2;
	
	/* Undo und Redo und Setup Stack */
	private Stack<Spielzug> undoStack;
	private Stack<Spielzug> redoStack;
	private Stack<Spielzug> setupStack;
	
	/* Die Spielvariante */
	private Variante Variante;
	
	/* Groesse des Bretts im Spielstand */
	private int Groesse;
	
	/* Zustandsflag fuer Spielausgang */
	private Zustand SpielAusgang = null;
	
	/* Andere Infos zum Spielstand */
	private String Kommentar;
	private String Titel;
	private String Datum;
	
	/* XML-Konstanten */
	private final static String XML_DOCTYPE_SYSTEM = "http://ls10-www.cs.tu-dortmund.de/~schultze/goxml.dtd";
	private final static String XML_DOCTYPE_PUBLIC = "GoXML";
	
	/* Flag */
	private boolean ist_redo = false;
	
	/* Konstruktoren */
	
	/**
	 * Konstruktor (ohne Laden aus Datei!) fuer Spielstand mit allen noetigen Infos.
	 * 
	 * @param Spieler1 Der erste Spieler.
	 * @param Spieler2 Der zweite Spieler.
	 * @param Variante Die Spielvariante dieses Spielstandes.
	 */
	public Spielstand(Spieler Spieler1, Spieler Spieler2, Variante Variante, int Groesse)
	{
		/* Input uebernehmen */
		this.Spieler1 = Spieler1;
		this.Spieler2 = Spieler2;
		this.Variante = Variante;
		this.Groesse = Groesse;
		
		/* Inits */
		this.undoStack = new Stack<Spielzug>();
		this.redoStack = new Stack<Spielzug>();
		this.setupStack = new Stack<Spielzug>();

		/* Defaults */
		this.Kommentar = "";
		this.Titel = "";
		this.Datum = "";
	}
	
	/**
	 * Konstruktor (ohne Laden aus Datei!) fuer Spielstand mit allen noetigen Infos
	 * plus Kommentar
	 * 
	 * @param Spieler1 Der erste Spieler.
	 * @param Spieler2 Der zweite Spieler.
	 * @param Variante Die Spielvariante dieses Spielstandes.
	 * @param Kommentar Der Kommentar zum Spielstand.
	 */
	public Spielstand(Spieler Spieler1, Spieler Spieler2, Variante Variante, int Groesse, String Kommentar)
	{
		/* Input uebernehmen */
		this.Spieler1 = Spieler1;
		this.Spieler2 = Spieler2;
		this.Variante = Variante;
		this.Groesse = Groesse;
		this.Kommentar = Kommentar;
		
		/* Inits */
		this.undoStack = new Stack<Spielzug>();
		this.redoStack = new Stack<Spielzug>();
		this.setupStack = new Stack<Spielzug>();
		
		/* Defaults */
		this.Titel = "";
		this.Datum = "";
	}
	
	@Override
	public String toString(){
		String beschreibung = "";
		beschreibung += this.Datum + " ";
		beschreibung += this.Spieler1.gibName() + " vs. " +this.Spieler2.gibName();
		beschreibung += ": " + this.Variante.toString();
		beschreibung += " (\"" + this.gibKommentar() + "\")";
		return beschreibung; // + ": " + this.Variante.toString();
	}

	/**
	 * Fuehrt die redo-Operation auf dem Spielstand aus.
	 * 
	 * Nimmt den ersten wiederherzustellenden Spielzug vom redoStack,
	 * schiebt ihn auf den undoStack und gibt ihn als Wiedergabewert an den Aufrufer.
	 * Da sich etwas am Spielstand geaendert hat, werden auch die Observer benachrichtigt.
	 * Falls kein redo vorgenommen werden kann, da es keine ausgefuehrten Zuege mehr gibt,
	 * wird null zurueckgegeben und eine Exception geworfen.
	 * 
	 * @return Der wiederhergestellte Spielzug
	 */
	public Spielzug redo() throws KeinRedoMoeglichException {
		Spielzug wiederhergestellt;

		/* Fehlerfall abfangen */
		if (!istRedoMoeglich())
		{
			throw new KeinRedoMoeglichException("Spielstand: Es kann kein Zug " +
					"wiederhergestellt werden!");
		}
		
		/* hier kann redo ausgefuehrt werden */
		ist_redo = true;	// Flag setzen
		wiederhergestellt = redoStack.pop();
		
		
		// undoStack.push(wiederhergestellt);
		
		/* Observer-Update */
		setChanged();
		notifyObservers();
		
		return wiederhergestellt;
	}

	/**
	 * Fuehrt die undo-Operation auf dem Spielstand aus.
	 * 
	 * Nimmt den letzten ausgefuehrten Spielzug vom undoStack,
	 * schiebt ihn auf den redoStack und gibt ihn als Wiedergabewert an den Aufrufer.
	 * Da sich etwas am Spielstand geaendert hat, werden auch die Observer benachrichtigt.
	 * Falls kein undo vorgenommen werden kann, da keine Zuege mehr zurueckgenommen werden
	 * koennen, wird null zurueckgegeben und eine Exception geworfen.
	 * 
	 * @return Der zurueckgenommene Spielzug
	 */
	public Spielzug undo() throws KeinUndoMoeglichException {
		Spielzug zurueckgenommen;
		
		/* Fehlerfall abfangen */
		if (!istUndoMoeglich())
		{
			throw new KeinUndoMoeglichException("Spielstand: Es kann kein Zug " +
					"zurückgenommen werden!");
		}
		
		/* hier kann undo ausgefuehrt werden */
		zurueckgenommen = undoStack.pop();
		redoStack.push(zurueckgenommen);
		
		/* Observer-Update */
		setChanged();
		notifyObservers();
		
		/* Zurueckgenommen Zug an Aufrufer geben */
		return zurueckgenommen;
	}

	/**
	 * Fuegt einen neuen Spielzug zum Spielstand hinzu.
	 * 
	 * Loescht den redoStack und legt den neuen Spielzug auf den undoStack.
	 * Anschliessend muessen die Observer ueber die Aenderung informiert werden.
	 * 
	 * @param s Der Spielzug, der zum Spielstand hinzugefuegt werden soll
	 */
	public void fuegeSpielzugHinzu(Spielzug s) {
		/* Aenderungen vornehmen */
		
		if (!ist_redo)
		{
			redoStack.clear();
		}
		undoStack.push(s);
	
		ist_redo = false;	// Flag zuruecknehmen
		
		/* Observer-Update */
		setChanged();
		notifyObservers();
	}

	/**
	 * Speichert den Spielstand gemaess der SoPra-Vorgabe in eine XML-Format Datei unter
	 * dem angegebenen Pfad.
	 * Falls an diesem Pfad keine Datei angelegt werden kann, wird eine Exception geworfen.
	 * 
	 * @param pfad Der Pfad, unter dem die XML-Datei gespeichert werden soll.
	 */
	public void speichern(String pfad) throws SpeichernException {
		/* Ggf. SetupSteine setzen */
		if (this.gibVariante() instanceof Steinschlag)
		{
			this.setzeSetupSteineFuerSteinschlag();
		}
		
		
		/* Variablen fuer XML-Output */
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document document;
		
		/* Erzeugen des Dokuments */
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		}
		catch (Exception e)
		{
			throw new SpeichernException("Fehler beim Erzeugen des XML-Files!");
		}
		
		/**
		 * 
		 * Erzeugen der XML-Struktur
		 * 
		 */
		
		
		/* XML-Version */
		document.setXmlVersion("1.0");

        /*
         * Wurzel-Element: game
         * */
        Element game = document.createElement("game");
        game.setAttribute("title", this.gibTitel());	/* Titel */
        game.setAttribute("date", this.gibDatum());		/* Datum  */
        
        /*
         * Spieltyp
         * */
        if (this.gibVariante() instanceof Steinschlag)
        	game.setAttribute("type", "capture-go");
        else
        	game.setAttribute("type", "go");
        document.appendChild(game);
        
        /*
         * Kommentar
         * */
        Element comment = document.createElement("comment");
        Text comment_text = document.createTextNode(this.gibKommentar());
        comment.appendChild(comment_text);
        game.appendChild(comment);
        
        /*
         * Brett-Info
         * */
        Element board = document.createElement("board");
        board.setAttribute("size", String.valueOf(this.gibGroesse()));
        game.appendChild(board);
        
        /*
         * Spieler
         * */
        Element players = document.createElement("players");
        Element player1 = null, player2 = null;
        
        /* Farben */
        if (this.gibSpieler1().gibFarbe() == Zustand.schwarz)
        {
        	player1 = document.createElement("black");
        	player2 = document.createElement("white");
        }
        else if (this.gibSpieler1().gibFarbe() == Zustand.weiss)
        {
        	player1 = document.createElement("white");
        	player2 = document.createElement("black");
        }
        else
        {
        	throw new SpeichernException("Unzulaessige Spielerfarben!");
        }
        
        /* Namen */
        player1.setAttribute("name", this.gibSpieler1().gibName());
        player2.setAttribute("name", this.gibSpieler2().gibName());

        players.appendChild(player1);
        players.appendChild(player2);
        
        /* Ggf. KI */
        if (this.gibSpieler1().gibTyp() == SpielerTyp.leicht ||
        	this.gibSpieler1().gibTyp() == SpielerTyp.schwer)
        {
        	Element ai = document.createElement("ai");
        	ai.setAttribute("color", farbe2String(this.gibSpieler1().gibFarbe()));
        	ai.setAttribute("difficulty", typ2String(this.gibSpieler1().gibTyp()));
        	players.appendChild(ai);
        }
        if (this.gibSpieler2().gibTyp() == SpielerTyp.leicht ||
            	this.gibSpieler2().gibTyp() == SpielerTyp.schwer)
            {
            	Element ai = document.createElement("ai");
            	ai.setAttribute("color", farbe2String(this.gibSpieler2().gibFarbe()));
            	ai.setAttribute("difficulty", typ2String(this.gibSpieler2().gibTyp()));
            	players.appendChild(ai);
            }
        
        /* Uebernehmen */
        game.appendChild(players);
        
        
        /*
         * Setup
         * */
        if (!this.setupStack.empty())
        {
        	Element setups = document.createElement("setup");
        	
        	/* Ueber Setup-Zuege iterieren */
        	for (Spielzug s : this.setupStack)
        	{
        		Element setup = document.createElement("put");
        		setup.setAttribute("color", farbe2String(s.gibSpieler().gibFarbe()));
        		setup.setAttribute("pos", s.gibGesetztenStein().gibPosition());
        		
        		setups.appendChild(setup);
        	}
        	
        	/* Uebernehmen */
            game.appendChild(setups);
        }
        
        
        /*
         * Zuege
         * */
        Element moves = document.createElement("moves");
        
        /* Durch Zuege iterieren */
        for (Spielzug s : this.undoStack)
        {
        	Element move;
        	
        	if (s.gibGesetztenStein() != null)
        	{	/* Es wurde gesetzt */
        		move = document.createElement("move");
        		move.setAttribute("color", farbe2String(s.gibSpieler().gibFarbe()));
        		move.setAttribute("pos", s.gibGesetztenStein().gibPosition());
        	}
        	else
        	{	/* Es wurde gepasst */
        		move = document.createElement("pass");
        		move.setAttribute("color", farbe2String(s.gibSpieler().gibFarbe()));
        	}

        	moves.appendChild(move);
        }
        
        /* Ggf. Spielausgang */
        if (this.gibVariante().istZuEnde() || this.gibSpielAusgang() != null)
        {
        	Element move;
        	
        	switch (this.gibSpielAusgang())
        	{
        	case frei:
        		move = document.createElement("draw");
        		break;
        	case weiss:
        		move = document.createElement("white-wins");
        		break;
        	case schwarz:
        		move = document.createElement("black-wins");
        		break;
        	default:
        		throw new SpeichernException("Fehler beim Spielausgang-Speichern!");
        	}
        	
        	moves.appendChild(move);
        }
        
        /* Uebernehmen */
        game.appendChild(moves);

        /////////////////
        // XML-Output

        try {
        	/* Transformer erzeugen */
        	TransformerFactory transfac = TransformerFactory.newInstance();
        	transfac.setAttribute("indent-number", 4);
        	Transformer trans = transfac.newTransformer();
        	trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, XML_DOCTYPE_SYSTEM);
        	trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, XML_DOCTYPE_PUBLIC);
        	trans.setOutputProperty(OutputKeys.INDENT, "yes");
        	trans.setOutputProperty(OutputKeys.METHOD, "xml");
        	trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        	/* XML-file in String speichern */
        	StringWriter sw = new StringWriter();
        	StreamResult result = new StreamResult(sw);
        	DOMSource source = new DOMSource(document);
        	trans.transform(source, result);
        	String xmlString = sw.toString();

        	/* Ausgabe */
        	// quick-n-dirty fix fuer newline
        	xmlString = xmlString.replace("<game", "\n<game");
        	//System.out.println("XML-Output:\n\n" + xmlString);
        	
        	/* Datei-Ausgabe */
        	FileWriter fstream = new FileWriter(pfad);
        	BufferedWriter out = new BufferedWriter(fstream);
        	out.write(xmlString);
        	/* Output-Stream schliessen */
        	out.close();
        }
        catch (Exception e)
        {
        	throw new SpeichernException("Fehler beim Speichern in Datei!");
        }
		
		return;
	}

	/**
	 * Erledigt das Parsen der XML-Datei gemaess der SoPra-Spezifikation unter dem
	 * angegebenen Pfad.
	 * 
	 * @param pfad Der Pfad, unter dem die zu ladende Datei zu finden ist.
	 */
	private static Spielstand laden(String pfad) throws LadenException {
		/* Objekte fuer XML-Parse */
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document document;
		
		/* Variablen zum auslesen */
		Element game;
		NodeList nl;
		
		/* Variablen fuer zu erzeugenden Spielstand */
		Spielstand spielstand;
		Spieler spieler1 = new Spieler(Zustand.schwarz, "Spieler 1", SpielerTyp.mensch);
		Spieler spieler2 = new Spieler(Zustand.weiss, "Spieler 2", SpielerTyp.mensch);
		Variante variante;
		int groesse = 9;
		String kommentar = "";
		String titel = "";
		String datum = "";
		LinkedList<Spielzug> zuege = new LinkedList<Spielzug>();
		LinkedList<Spielzug> setup = new LinkedList<Spielzug>();
		
		try {
			factory = DocumentBuilderFactory.newInstance();
		    builder = factory.newDocumentBuilder();
		    document = builder.parse(pfad);
		}
		catch (Exception e)
		{
			throw new LadenException("Fehler beim Oeffnen/Lesen der Datei!");
		}
		

	    /* Wurzelelement, <game>, NodeName=game */
	    game = document.getDocumentElement();
	    
	    /* Spielattribute */

	    /* Spieltitel */
	    if (game.hasAttribute("title"))
	    {
	    	titel = game.getAttribute("title");
	    }
	    /* Datum */
	    if (game.hasAttribute("date"))
	    {
	    	datum = game.getAttribute("date");
	    }
	    /* Variante */
	    if (game.hasAttribute("type"))
	    {
	    	if (game.getAttribute("type").equals("go"))
	    	{	/* go bedeutet Klassisch */
	    		variante = new Klassisch();
	    	}
	    	else if (game.getAttribute("type").equals("capture-go"))
	    	{	/* capture-go bedeutet Steinschlag */
	    		variante = new Steinschlag();
	    	}
	    	else
	    	{	/* ungueltiger Spieltyp */
	    		throw new LadenException("Ungueltiger Spiel-Typ!");
	    	}
	    }
	    else
	    {
	    	/* Wenn type nicht gesetzt, implizit klassisches Go (go) */
	    	variante = new Klassisch();
	    }
	    
	    /* Kinderelemente auslesen */
	    nl = game.getChildNodes();
	    for (int i = 0; i < nl.getLength(); i++)
	    {
	    	Node node = nl.item(i);
	    	
	    	if (node.getNodeType() == Node.ELEMENT_NODE)
	    	{
	    		/* 
	    		 * Kommentar zum Spiel 
	    		 * */
	    		if (node.getNodeName().equals("comment"))
	    		{
	    			if (!node.hasChildNodes())
	    			{
	    				throw new LadenException("Korruptes Savegame: Kein Kommentar!");
	    			}
	    			kommentar = node.getFirstChild().getNodeValue();
	    		}
	    		/* 
	    		 * Brett-Infos 
	    		 * */
	    		else if (node.getNodeName().equals("board"))
	    		{
	    			NamedNodeMap nnm = node.getAttributes();
	    			groesse = Integer.parseInt(nnm.item(0).getNodeValue());
	    		}
	    		/* 
	    		 * Spieler-Infos
	    		 * */
	    		else if (node.getNodeName().equals("players"))
	    		{
	    			NodeList players = node.getChildNodes();

	    			/* Ueber Spieler iterieren */
	    			for (int j = 0; j < players.getLength(); j++)
	    			{
	    				Node player = players.item(j);
	    				NamedNodeMap nnm = null;
	    				
	    				/* Anm.: Default ist Mensch */
	    				
	    				if (player.getNodeName().equals("black"))
	    				{
	    					nnm = player.getAttributes();
	    					
	    					spieler1 = new Spieler(	Zustand.schwarz,
	    											nnm.item(0).getNodeValue(),
	    											SpielerTyp.mensch);
	    				}
	    				else if (player.getNodeName().equals("white"))
	    				{
	    					nnm = player.getAttributes();
	    					
	    					spieler2 = new Spieler(	Zustand.weiss,
									nnm.item(0).getNodeValue(),
									SpielerTyp.mensch);
	    				}
	    				else if (player.getNodeName().equals("ai"))
	    				{	/* KI-Informationen */
	    					nnm = player.getAttributes();
	    					
	    					Zustand farbe = Zustand.frei;
	    					SpielerTyp schwierigkeit = SpielerTyp.mensch;
	    					
	    					/* Auslesen der Properties */
	    					for (int k = 0; k < nnm.getLength(); k++)
	    					{
	    						Node property = nnm.item(k);
	    						
	    						if (property.getNodeName().equals("color"))
	    						{	/* KI-Farbe */
	    							if (property.getNodeValue().equals("black"))
	    								farbe = Zustand.schwarz;
	    							else if (property.getNodeValue().equals("white"))
	    								farbe = Zustand.weiss;
	    							else
	    								throw new LadenException("Ungueltige KI-Farbe!");
	    						}
	    						else if (property.getNodeName().equals("difficulty"))
	    						{	/* KI-Schwierigkeit */
	    							if (property.getNodeValue().equals("easy"))
	    								schwierigkeit = SpielerTyp.leicht;
	    							else if (property.getNodeValue().equals("hard"))
	    								schwierigkeit = SpielerTyp.schwer;
	    							else	/* Alles andere per def. easy */
	    								schwierigkeit = SpielerTyp.leicht;
	    						}
	    					}
	    					
	    					/* Auswerten der Properties */
	    					if (spieler1.gibFarbe() == farbe)
	    					{
	    						spieler1.setzeTyp(schwierigkeit);
	    					}
	    					else if (spieler2.gibFarbe() == farbe)
	    					{
	    						spieler2.setzeTyp(schwierigkeit);
	    					}
	    				}
	    			}
	    		}
	    		/* 
	    		 * Setup 
	    		 * */
	    		else if (node.getNodeName().equals("setup"))
	    		{
	    			NodeList moves = node.getChildNodes();
	    			
	    			/* Ueber Zuege iterieren */
	    			for (int j = 0; j < moves.getLength(); j++)
	    			{
	    				Node move = moves.item(j);
	    				NamedNodeMap nnm = null;
	    				
	    				if (move.getNodeName().equals("put"))
	    				{
	    					nnm = move.getAttributes();
	    					Zustand farbe = Zustand.frei;
	    					String pos = "";
	    					
	    					// Farbe
	    					if (nnm.item(0).getNodeValue().equals("black"))
	    						farbe = Zustand.schwarz;
	    					else if (nnm.item(0).getNodeValue().equals("white"))
	    						farbe = Zustand.weiss;
	    					/* TODO: Fehlerfall */
	    					
	    					// Position
	    					pos = nnm.item(1).getNodeValue();
	    					
	    					/* Auswertung */
	    					if (spieler1.gibFarbe() == farbe)
	    					{
	    						setup.addFirst(new Spielzug(new Position(pos), spieler1));
	    					}
	    					else if (spieler2.gibFarbe() == farbe)
	    					{
	    						setup.addFirst(new Spielzug(new Position(pos), spieler2));
	    					}
	    					else
	    					{	/* Fehlerfall */
	    						throw new LadenException("Ungueltige Setup-Farbe!");
	    					}
	    				}
	    			}
	    		}
	    		/* 
	    		 * Zuege 
	    		 * */
	    		else if (node.getNodeName().equals("moves"))
	    		{
	    			NodeList moves = node.getChildNodes();
	    			
	    			/* Ueber Zuege iterieren */
	    			for (int j = 0; j < moves.getLength(); j++)
	    			{
	    				Node move = moves.item(j);
	    				NamedNodeMap nnm = null;
	    				
	    				if (move.getNodeName().equals("move"))
	    				{	/* Stein gesetzt */
	    					nnm = move.getAttributes();
	    					Zustand farbe = Zustand.frei;
	    					String pos = "";
	    					
	    					// Farbe
	    					if (nnm.item(0).getNodeValue().equals("black"))
	    						farbe = Zustand.schwarz;
	    					else if (nnm.item(0).getNodeValue().equals("white"))
	    						farbe = Zustand.weiss;
	    					else
	    						throw new LadenException("Ungueltige Zugfarbe!");
	    					
	    					// Position
	    					pos = nnm.item(1).getNodeValue();
	    					
	    					/* Auswertung */
	    					if (spieler1.gibFarbe() == farbe)
	    					{
	    						zuege.addFirst(new Spielzug(new Position(pos), spieler1));
	    					}
	    					else if (spieler2.gibFarbe() == farbe)
	    					{
	    						zuege.addFirst(new Spielzug(new Position(pos), spieler2));
	    					}
	    					else
	    					{	/* Fehlerfall */
	    						throw new LadenException("Ungueltige Zug-Farbe!");
	    					}
	    				}
	    				else if (move.getNodeName().equals("pass"))
	    				{	/* gepasst */
	    					nnm = move.getAttributes();
	    					Zustand farbe = Zustand.frei;
	    					
	    					// Farbe
	    					if (nnm.item(0).getNodeValue().equals("black"))
	    						farbe = Zustand.schwarz;
	    					else if (nnm.item(0).getNodeValue().equals("white"))
	    						farbe = Zustand.weiss;
	    					else
	    						throw new LadenException("Ungueltige Passen-Farbe!");
	    					
	    					/* Auswertung */
	    					if (spieler1.gibFarbe() == farbe)
	    					{
	    						zuege.addFirst(new Spielzug(null, spieler1));
	    					}
	    					else if (spieler2.gibFarbe() == farbe)
	    					{
	    						zuege.addFirst(new Spielzug(null, spieler2));
	    					}
	    					else
	    					{	/* Fehlerfall */
	    						throw new LadenException("Ungueltige Passen-Farbe!");
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    /*
	     * Gesamt-Auswertung: Erzeugung des Spielstand-Objekts
	     * */
	    spielstand = new Spielstand(spieler1, spieler2, variante, groesse, kommentar);
	    spielstand.setzeDatum(datum);
	    spielstand.setzeTitel(titel);
	    /* Setup-Zuege hinzufuegen */
	    for (Spielzug s : setup)
	    {
	    	spielstand.setupStack.push(s);
	    }
	    
	    /* Spielzuege hinzufuegen */
	    for (Spielzug s : zuege)	/* n.b.: Spielzuege sind in umgekehrter Reihenfolge in zuege */
	    {
	    	spielstand.redoStack.push(s);
	    }
	    
	    
	    
	    
	    /*
	     * TODO: rm
	     * Test-Ausgabe der Infos
	     * 
	     * */
	    /*
	    System.out.println("\n\n\nInfos aus Datei:");
	    
	    System.out.println("Titel: " + spielstand.gibTitel());
	    System.out.println("Datum: " + spielstand.gibDatum());
	    System.out.println("Kommentar: "+spielstand.gibKommentar());
	    System.out.println("Groesse: "+spielstand.gibGroesse());
	    System.out.println("Variante: " + spielstand.gibVariante().toString());
	    System.out.println("Spieler 1: " + spielstand.gibSpieler1().gibName()
	    					+ ", " + spielstand.gibSpieler1().gibFarbe().toString()
	    					+ ", " + spielstand.gibSpieler1().gibTyp().toString());
	    System.out.println("Spieler 2: " + spielstand.gibSpieler2().gibName()
				+ ", " + spielstand.gibSpieler2().gibFarbe().toString()
				+ ", " + spielstand.gibSpieler2().gibTyp().toString());
	    // Setup
	    for (int i = spielstand.setupStack.size()-1; i >= 0; i--)
	    {
	    	Spielzug s = spielstand.setupStack.get(i);
	    	if (s.gibGesetztenStein() == null)
	    	{
	    		System.out.println("Gepasst von " + s.gibSpieler().gibFarbe().toString());
	    	}
	    	else
	    	{
	    		System.out.println("Setup: " + s.gibSpieler().gibFarbe() +
	    			", " + s.gibGesetztenStein().gibX() + "," + s.gibGesetztenStein().gibY());
	    	}
	    }
	    
	    // Spielzuege
	    for (int i = spielstand.redoStack.size()-1; i >= 0; i--)
	    {
	    	Spielzug s = spielstand.redoStack.get(i);
	    	if (s.gibGesetztenStein() == null)
	    	{
	    		System.out.println("Gepasst von " + s.gibSpieler().gibFarbe().toString());
	    	}
	    	else
	    	{
	    		System.out.println("Spielzug: " + s.gibSpieler().gibFarbe() +
	    			", " + s.gibGesetztenStein().gibX() + "," + s.gibGesetztenStein().gibY());
	    	}
	    }
	    */
	    
	    
		return spielstand;
	}
	
	/**
	 * Laedt einen Spielstand mit laden aus einer XML-Datei und versieht
	 * ein Spielstand-Objekt mit den gewonnen Informationen.
	 * 
	 * @param s Der Pfad der zu ladenden XML-Datei.
	 * @return Ein vollstaendiges Spielstand-Objekt mit allen Werten aus der angegebenen Datei.
	 */
	public static Spielstand ladeSpielstand(String s) throws LadenException {
		Spielstand geladenerSpielstand = null;
		
		try {
			geladenerSpielstand = laden(s);
			return geladenerSpielstand;
		}
		catch (LadenException e)
		{
			// System.err.println("Fehler beim Laden aus Datei!");
			throw new LadenException("Laden aus Datei fehlgeschlagen: " + e.getMessage());
		}
	}
	
	/**
	 * Loescht alle bisherigen Spielzuege. Behaelt die Spieler und die Variante
	 * bei.
	 * 
	 * @deprecated
	 */
	public void zuruecksetzen()
	{
		undoStack.clear();
		redoStack.clear();
		setupStack.clear();
	}
	
	/* Setter
	 * 
	 * 
	 * 
	 * 
	 * */
	
	/**
	 * Gibt dem Spielstand die Information, wie das Spiel ausging,
	 * wenn es zu Ende ist.
	 * frei = draw, schwarz = schwarz gewonnen, weiss = weiss gewonnen
	 * 
	 * @param Ausgang Der neue Spielausgang.
	 */
	public void setzeSpielAusgang(Zustand ausgang)
	{
		this.SpielAusgang = ausgang;
	}
	
	/**
	 * Versieht den Spielstand mit dem angegebenen Kommentar.
	 * 
	 * @param kommentar Der Kommentar zum Spielstand
	 */
	public void setzeKommentar(String kommentar) {
		this.Kommentar = kommentar;
	}
	
	/**
	 * Versieht den Spielstand mit dem angegebenen Titel.
	 * 
	 * @param titel Der Titel des Spielstands
	 */
	public void setzeTitel(String titel) {

		this.Titel = titel;
	}
	
	/**
	 * Versieht den Spielstand mit dem angegebenen Datum.
	 * 
	 * @param datum Das Datum des Spielstands.
	 */
	public void setzeDatum(String datum) {
		this.Datum = datum;
	}
	
	/**
	 * Versieht den Spielstand mit der angegebenen Brett-Groesse.
	 * 
	 * @param groesse Die Groesse des Bretts im Spielstand.
	 */
	public void setzeGroesse(int groesse) {
		this.Groesse = groesse;
	}
	
	/**
	 * Uebernimmt eine Menge von SetupSteinen (fuer Handycap).
	 * Die vorigen Setup-Steine werden geloescht!
	 * 
	 * @param setupSteine Liste von Spielzuegen mit SetupSteinen.
	 */
	public void setzeSetupSteine(List<Spielzug> setupSteine)
	{
		/* Bisherige verwerfen */
		this.setupStack.clear();
		
		/* Uebernehmen der setUpSteine */
		for (Spielzug s : setupSteine)
		{
			this.setupStack.push(s);
		}
	}
	
	/**
	 * Setzt die Handycap-Steine gemaess der Steinschlag-Go-Konvention.
	 * 
	 */
	public void setzeSetupSteineFuerSteinschlag()
	{
		LinkedList<Spielzug> setup = new LinkedList<Spielzug>();
		
		/* Die vier default Steine fuer Steinschlag-Go */
		setup.add(new Spielzug(new Position("d4"), this.gibSpieler1()));
		setup.add(new Spielzug(new Position("e5"), this.gibSpieler1()));
		setup.add(new Spielzug(new Position("d5"), this.gibSpieler2()));
		setup.add(new Spielzug(new Position("e4"), this.gibSpieler2()));
		
		/* Uebernehmen */
		this.setzeSetupSteine(setup);
	}
	
	/* Getter
	 * 
	 * 
	 * 
	 * 
	 * */
	
	/**
	 * Liefert die Summe aller Punkte der Spielzuege im aktuellen Spielstand fuer den
	 * angegebenen Spieler.
	 * 
	 * @param s Der Spieler, dessen Punkte bestimmt werden soll.
	 */
	public int gibPunkte(Spieler s) {
		int punkte = 0;
		
		/* alle bisherigen Spielzuege betrachten */
		for (Spielzug sz : undoStack)
		{
			/* nur die vom angegebenen Spieler vorgenommenen beruecksichtigen */
			if (sz.gibSpieler().equals(s))
			{
				punkte += sz.gibPunkte();
			}
		}
		
		return punkte;
	}
	
	/**
	 * Liefert den ersten Spieler.
	 * 
	 * @return Der erste Spieler
	 */
	public Spieler gibSpieler1() {
		return this.Spieler1;
	}

	/**
	 * Liefert den zweiten Spieler.
	 * 
	 * @return Der zweite Spieler
	 */
	public Spieler gibSpieler2() {
		return this.Spieler2;
	}

	/**
	 * Liefert die Spielvariante dieses Spielstandes.
	 * 
	 * @return Die Variante dieses Spielstandes
	 */
	public Variante gibVariante() {
		return this.Variante;
	}
	
	/**
	 * Liefert den Spielausgang. Nur sinnvoll, wenn beendet!
	 * 
	 * @return Der Ausgang des Spiels.
	 */
	private Zustand gibSpielAusgang() {
		return this.SpielAusgang;
	}
	
	/**
	 * Liefert die Groesse des Spielbretts im Spielstand.
	 * 
	 * @return Die Groesse des Bretts
	 */
	public int gibGroesse() {
		return this.Groesse;
	}
	
	/**
	 * Liefert den Kommentar zu diesem Spielstand.
	 * 
	 * @return Der Kommentar zum Spielstand
	 */
	public String gibKommentar() {
		return this.Kommentar;
	}
	
	/**
	 * Liefert den Titel des Spielstands.
	 * 
	 * @return Der Titel des Spielstands.
	 */
	public String gibTitel() {
		return this.Titel;
	}
	
	/**
	 * Liefert das Datum des Spielstandes.
	 * 
	 * @return Das Datum des Spielstands.
	 */
	public String gibDatum() {
		return this.Datum;
	}

	/**
	 * Liefert den Spieler, der im aktuellen Spielstand nach dem zuletzt ausgefuehrten
	 * Zug an der Reihe ist.
	 * 
	 * @return der Spieler, der im Moment am Zug ist
	 */
	public Spieler gibAktuellenSpieler() {
		/* Spieler bestimmen, der den letzten Zug gemacht hat */
		Spieler letzterSpieler;
		
		/* Ausnahmefall: Noch keine Zuege */
		if (!istUndoMoeglich())
		{
			if (this.gibSpieler1().gibFarbe() == Zustand.schwarz)
			{
				return Spieler1;
			}
			else if (this.gibSpieler2().gibFarbe() == Zustand.schwarz)
			{
				return Spieler2;
			}
			else
			{
				/* kein Spieler ist schwarz */
				return null;
			}
		}
		
		/* ab hier gibt es schon Spielzuege */
		letzterSpieler = undoStack.peek().gibSpieler();
		
		/* Der aktuelle Spieler ist der andere */
		if (letzterSpieler.equals(Spieler1))
		{
			return Spieler2;
		}
		else
		{
			return Spieler1;
		}
	}
	
	/**
	 * Gibt Auskunft darueber, ob undo moeglich ist.
	 * 
	 * @return true, wenn undo moeglich ist, false sonst.
	 */
	public boolean istUndoMoeglich()
	{
		return (!undoStack.empty());
	}
	
	/**
	 * Gibt Auskunft darueber, ob redo moeglich ist.
	 * 
	 * @return true, wenn redo moeglich ist, false sonst.
	 */
	public boolean istRedoMoeglich()
	{
		return (!redoStack.empty());
	}
	
	/**
	 * Gibt einen Zustand als String zurueck.
	 * @param farbe
	 * @return Farbe als String
	 */
	private String farbe2String(Zustand farbe)
	{
		if (farbe == Zustand.schwarz)		return "black";
		else if (farbe == Zustand.weiss)	return "white";
		else return "";
	}
	
	/**
	 * Gibt einen Spielertyp als String zurueck
	 * @param typ
	 * @return Spielertyp als String
	 */
	private String typ2String(SpielerTyp typ)
	{
		if (typ == SpielerTyp.leicht)		return "easy";
		else if (typ == SpielerTyp.schwer)	return "hard";
		else return "";
	}
	

}
