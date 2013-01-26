/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package go;

import go.Zustand;
import go.Spielstand;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sopr053
 */
public class SpielstandTest {
	
	private static Spielstand derSpielstand;
	private static Spieler Spieler1;
	private static Spieler Spieler2;
	private static Variante var;

    public SpielstandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    	/* Spieler erzeugen */
    	Spieler1 = new Spieler(Zustand.schwarz, "Spieler 1", SpielerTyp.mensch);
    	Spieler2 = new Spieler(Zustand.weiss, "Spieler 2", SpielerTyp.mensch);
    	
    	/* Variante */
    	var = new Steinschlag();
    	
    	/* einen leeren Spielstand erzeugen */
    	derSpielstand = new Spielstand(Spieler1, Spieler2, var, 9);
    	
    	/* ein paar default Spielzuege */
    	Spielzug sz;
    	
    	sz = new Spielzug(new Position(1,5), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(2,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	
    	sz = new Spielzug(new Position(3,2), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(3,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	
    	sz = new Spielzug(new Position(4,2), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(4,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    	/* Spieler erzeugen */
    	Spieler1 = new Spieler(Zustand.schwarz, "Spieler 1", SpielerTyp.mensch);
    	Spieler2 = new Spieler(Zustand.weiss, "Spieler 2", SpielerTyp.mensch);
    	
    	/* Variante */
    	var = new Steinschlag();
    	
    	/* einen leeren Spielstand erzeugen */
    	derSpielstand = new Spielstand(Spieler1, Spieler2, var, 9);
    	
    	/* ein paar default Spielzuege */
    	Spielzug sz;
    	
    	sz = new Spielzug(new Position(1,5), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(2,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	
    	sz = new Spielzug(new Position(3,2), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(3,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	
    	sz = new Spielzug(new Position(4,2), Spieler1);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    	sz = new Spielzug(new Position(4,3), Spieler2);
    	derSpielstand.fuegeSpielzugHinzu(sz);
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test des Konstruktors ohne Laden.
     */
    @Test
    public void testKonstruktorOhneLaden() {
    	System.out.println("Konstruktor ohne Laden");
    	
    	assertEquals(derSpielstand.gibSpieler1(), Spieler1);
    	assertEquals(derSpielstand.gibSpieler2(), Spieler2);
    	assertEquals(derSpielstand.gibVariante(), var);
    }

    /**
     * Test of gibPunkte method, of class Spielstand.
     */
    @Test
    public void testGibPunkte() {
    	System.out.println("gibPunkte");
    	
    	/* Punkte von Spieler 1 */
    	assertEquals(derSpielstand.gibPunkte(Spieler1), 0);
    	
    	/* Punkte von Spieler 2 */
    	assertEquals(derSpielstand.gibPunkte(Spieler2), 0);

    }

    /**
     * Test of gibSpieler2 method, of class Spielstand.
     */
    @Test
    public void testGibSpieler2() {
        System.out.println("gibSpieler2");
        
        assertTrue(Spieler2.equals(derSpielstand.gibSpieler2()));
    }

    /**
     * Test of gibSpieler1 method, of class Spielstand.
     */
    @Test
    public void testGibSpieler1() {
    	System.out.println("gibSpieler1");
        
        assertTrue(Spieler1.equals(derSpielstand.gibSpieler1()));
    }


    /**
     * Test of gibVariante method, of class Spielstand.
     */
    @Test
    public void testGibVariante() {
        System.out.println("gibVariante");
        
        assertEquals(derSpielstand.gibVariante(), var);
    }

    /**
     * Test of gibAktuellenSpieler method, of class Spielstand.
     */
    @Test
    public void testGibAktuellenSpieler() {
        System.out.println("gibAktuellenSpieler");
        
        /* Fall 1: noch keine Zuege */
        derSpielstand.zuruecksetzen();
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler1));
        
        /* Fall 2: schwarz (Spieler 1) am Zug */
        derSpielstand.zuruecksetzen();
        derSpielstand.fuegeSpielzugHinzu(new Spielzug(new Position(5,7), Spieler1));
        derSpielstand.fuegeSpielzugHinzu(new Spielzug(new Position(1,3), Spieler2));
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler1));
        
        /* Fall 3: weiß (Spieler 2) am Zug */
        derSpielstand.zuruecksetzen();
        derSpielstand.fuegeSpielzugHinzu(new Spielzug(new Position(5,7), Spieler1));
        derSpielstand.fuegeSpielzugHinzu(new Spielzug(new Position(1,3), Spieler2));
        derSpielstand.fuegeSpielzugHinzu(new Spielzug(new Position(4,4), Spieler1));
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler2));  
    }
    
    /**
     * Test of undo method, of class Spielstand.
     */
    @Test
    public void testUndo() throws Exception {
    	Spielzug zurueckgenommen;
    	
    	System.out.println("undo");
    	
    	/* Einige Testzuege */
    	Spielzug zug1 = new Spielzug(new Position(5,7), Spieler1);
    	Spielzug zug2 = new Spielzug(new Position(1,3), Spieler2);
        
        /* einen geeigneten Spielstand konfigurieren */
        derSpielstand.zuruecksetzen();
        derSpielstand.fuegeSpielzugHinzu(zug1);
        derSpielstand.fuegeSpielzugHinzu(zug2);
        
        /* 1. undo-Test: eigentlicher undo-Test */
        zurueckgenommen = derSpielstand.undo();
        /* es soll der zurueckgenommene Zug zurueckgegeben werden */
        assertTrue(zurueckgenommen.equals(zug2));
        /* nun muss Spieler2 am Zug sein */
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler2));
        
        /* 2. undo-Test: kein undo moeglich! */
        derSpielstand.zuruecksetzen();
        try {
        	derSpielstand.undo();
        	fail("Hier sollte eine KeinUndoMoeglichException geworfen werden!");
        }
        catch (KeinUndoMoeglichException e)
        {
        	;	// Alles OK
        }
    }

    /**
     * Test of redo method, of class Spielstand.
     */
    @Test
    public void testRedo() throws Exception {
    	Spielzug wiederhergestellt;
    	
        System.out.println("redo");
        
        /* Einige Testzuege */
    	Spielzug zug1 = new Spielzug(new Position(5,7), Spieler1);
    	Spielzug zug2 = new Spielzug(new Position(1,3), Spieler2);
        
        /* einen geeigneten Spielstand konfigurieren */
        derSpielstand.zuruecksetzen();
        derSpielstand.fuegeSpielzugHinzu(zug1);
        derSpielstand.fuegeSpielzugHinzu(zug2);
        
        /* 1. redo-Test: undo, redo */
        derSpielstand.undo();
        wiederhergestellt = derSpielstand.redo();
        derSpielstand.fuegeSpielzugHinzu(wiederhergestellt);
        
        /* es soll der wiederhergestellte Zug zurueckgegeben werden */
        assertTrue(wiederhergestellt.equals(zug2));
        /* nun muss Spieler2 am Zug sein */
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler1));
        
        /* 2. redo-Test: undo, undo, redo */
        derSpielstand.undo();
        derSpielstand.undo();
        wiederhergestellt = derSpielstand.redo();
        derSpielstand.fuegeSpielzugHinzu(wiederhergestellt);
        
        /* es soll der wiederhergestellte Zug zurueckgegeben werden */
        assertTrue(wiederhergestellt.equals(zug1));
        /* nun muss Spieler2 am Zug sein */
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler2));
        
        /* 3. redo-Test: undo, undo, redo, redo */
        /* neues setup */
        derSpielstand.zuruecksetzen();
        derSpielstand.fuegeSpielzugHinzu(zug1);
        derSpielstand.fuegeSpielzugHinzu(zug2);
        
        derSpielstand.undo();
        derSpielstand.undo();
        derSpielstand.redo();
        wiederhergestellt = derSpielstand.redo();
        derSpielstand.fuegeSpielzugHinzu(wiederhergestellt);
        
        /* es soll der wiederhergestellte Zug zurueckgegeben werden */
        assertTrue(wiederhergestellt.equals(zug2));
        /* nun muss Spieler1 am Zug sein */
        assertTrue(derSpielstand.gibAktuellenSpieler().equals(Spieler1));
        
        /* 4. redo-Test: kein redo moeglich! */
        derSpielstand.zuruecksetzen();
        try {
        	derSpielstand.redo();
        	fail("Hier sollte eine KeinRedoMoeglichException geworfen werden!");
        }
        catch (KeinRedoMoeglichException e)
        {
        	; // Alles OK
        }
    }

    /**
     * Test of fuegeSpielzugHinzu method, of class Spielstand.
     */
    @Test
    public void testFuegeSpielzugHinzu() {
        System.out.println("fuegeSpielzugHinzu");
        
        /* nicht wirklich erschoepfend moeglich */
        assertFalse(derSpielstand.istRedoMoeglich());
    }

    /**
     * Test of speichern method, of class Spielstand.
     */
    @Test
    public void testSpeichern() {
        System.out.println("speichern");
        
        String pfad, pfad_speichern;
        
        pfad = "/sopra/sopgr05/sopr053/workspace/Go/xml/save.xml";
        pfad_speichern = "/sopra/sopgr05/sopr053/workspace/Go/xml/save_test.xml";
        
     
        /* 1. Test: Speichern eines hier angelegten Test-Spielstandes */
        derSpielstand.setzeKommentar("Ein Kommentar!");
        derSpielstand.setzeDatum("2010-03-24");
        derSpielstand.setzeTitel("Toller Titel!");
        derSpielstand.setzeSetupSteineFuerSteinschlag();
        
        try {
        	derSpielstand.speichern(pfad);
        }
        catch (SpeichernException e)
        {
        	fail("Hier haette gespeichert werden sollen!");
        }
        
        
        /* 2. Test: Laden eines Beispiel-Spielstands, anschließend direkt speichern. 
         * Wenn die Dateien identisch sind, alles OK!
         */
        // Laden
        Spielstand geladen = null;
        try {
        	geladen = derSpielstand.ladeSpielstand(pfad);
        	
        }
        catch (LadenException e)
        {
        	fail("Laden der Datei sollte funktionieren: " + e.getMessage());
        }
        /*
         * Um direkt wieder speichern zu koennen, muss wie im echten Spielablauf
         * erst redo ausgefuehrt werden.
         */
        for (int i = 0; i < 6; i++)
        {
        	try {
        		Spielzug redone = geladen.redo();
        		geladen.fuegeSpielzugHinzu(redone);
        	}
        	catch (KeinRedoMoeglichException e)
        	{
        		fail("Hier haette redo moeglich sein muessen");
        	}
        }
        /* Direkt wieder speichern */
        try {
        	geladen.speichern(pfad_speichern);
        }
        catch (SpeichernException e)
        {
        	fail("Das Speichern des geladenen Spielstandes hätte funktionieren sollen!");
        }
        /* Vergleich 
         * Anm.: Vorerst von Hand den Inhalt der Dateien vergleichen */
        
        /* 3. Test: Speichern an unzulaessigem Pfad */
        try {
        	derSpielstand.speichern("dev/null");
        	fail("Hier sollte eine Exception geworfen werden!!");
        }
        catch (SpeichernException e)
        {
        	// Alles OK
        }
    }
    
    /**
     * Test of ladeSpielstand method, of class Spielstand.
     */
    @Test
    public void testLadeSpielstand() {
        System.out.println("ladeSpielstand");
        
        String pfad;
        Spielstand geladen;
        
        /* Test-Pfad */
        
        
        /* 1. ladeSpielstand-Test: Alles OK */
        pfad = "/sopra/sopgr05/sopr053/workspace/Go/xml/example.xml";
        try
        {
        	geladen = derSpielstand.ladeSpielstand(pfad);
        }
        catch (LadenException e)
        {
        	System.out.println(e.getMessage());
        	fail("Hier sollte laden funktionieren!");
        }
        
        /* 2. Falscher Pfad */
        pfad = "/sopra/sopgr05/sopr053/workspace/Go/xml/not_a_file.xml";
        try
        {
        	geladen = derSpielstand.ladeSpielstand(pfad);
        	fail("Es muesste eine Exception geworfen werden, da ungueltiger Pfad!");
        }
        catch (LadenException e)
        {
        	// Alles OK!
        }
        
        /* 3. Syntax Fehler im XML-File */
        pfad = "/sopra/sopgr05/sopr053/workspace/Go/xml/invalid_type_error.xml";
        try
        {
        	geladen = derSpielstand.ladeSpielstand(pfad);
        	fail("Es muesste eine Exception geworfen werden, da ungueltier Spieltyp!");
        }
        catch (LadenException e)
        {
        	// Alles OK!
        }
    }

}