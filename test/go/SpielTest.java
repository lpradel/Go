/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package go;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sopr051
 */
public class SpielTest {

	static Spiel dasSpiel;
	static Spieler sp1;
	static Spieler sp2;
	static Spielbrett brett;
	static Variante var;
	static Spielstand stand;
	static Spielzug sz1, sz2, sz3, sz4;
	
	public SpielTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    	sp1 = new Spieler(Zustand.schwarz, "spieler 1", SpielerTyp.mensch);
    	sp2 = new Spieler(Zustand.weiss, "spieler 2", SpielerTyp.mensch);
    	var = new Steinschlag();
    	brett = var.initialisiereSpielbrett(9);
    	stand = new Spielstand(sp1, sp2, var, 9);
    	dasSpiel = new Spiel(var, stand, brett);
    	
    	/* Test-Spielzuege */
    	sz1 = new Spielzug(new Position("a5"), sp1);
    	sz2 = new Spielzug(new Position("a6"), sp2);
    	sz3 = new Spielzug(new Position("b2"), sp1);
    	sz4 = new Spielzug(new Position("b3"), sp2);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    	sp1 = new Spieler(Zustand.schwarz, "spieler 1", SpielerTyp.mensch);
    	sp2 = new Spieler(Zustand.weiss, "spieler 2", SpielerTyp.mensch);
    	var = new Steinschlag();
    	brett = var.initialisiereSpielbrett(9);
    	stand = new Spielstand(sp1, sp2, var, 9);
    	dasSpiel = new Spiel(var, stand, brett);
    	
    	/* Test-Spielzuege */
    	sz1 = new Spielzug(new Position("a5"), sp1);
    	sz2 = new Spielzug(new Position("a6"), sp2);
    	sz3 = new Spielzug(new Position("b2"), sp1);
    	sz4 = new Spielzug(new Position("b3"), sp2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of redo method, of class Spiel.
     */
    @Test
    public void testRedo() {
        System.out.println("redo");
        
        /* Test:einen Zug ausführen,dann undo und dannach redo*/
        try{
        	dasSpiel.fuehreSpielzugAus(sz1);
        }
        catch (UngueltigerZugException e) {
        	fail("Das sollte funktionieren!");
		}
        try{
        	dasSpiel.undo();
        }
        catch(KeinUndoMoeglichException e){
        	fail("Das sollte funktionieren!");
        }
        try{
        	dasSpiel.redo();
        }
        catch(Exception e){
        	fail("Das sollte funktionieren!");
        }
        /* Spielstand pruefen*/
        if(stand.istRedoMoeglich()){
        	fail("Es duerfte kein redo mehr moeglich sein!");
        }
        /*Brett pruefen*/
        if(brett.gib(new Position("a5")) != Zustand.schwarz){
        	fail("Der Zug hätte im Brett hinzugefuegt werden müssen!");
        }
    }

    /**
     * Test of undo method, of class Spiel.
     */
    @Test
    public void testUndo() {
        System.out.println("undo");
        
        /* Test: einen Zug vornehmen, dann undo */
        try {
        	dasSpiel.fuehreSpielzugAus(sz1);
        }
        catch (UngueltigerZugException e)
        {
        	fail("Das sollte funktionieren!");
        }
        try {
        	dasSpiel.undo();
        }
        catch (KeinUndoMoeglichException e)
        {
        	fail("Das sollte funktionieren!");
        }
        /* Spielstand pruefen */
        if (stand.istUndoMoeglich())
        {
        	fail("Es duerfte kein undo mehr moeglich sein!");
        }
        /* Brett pruefen */
        if (brett.gib(new Position("a5")) != Zustand.frei)
        {
        	fail("Der Zug hätte vom Brett zurückgenommen werden müssen!");
        }
    }

    /**
     * Test of fuehreSpielzugAus method, of class Spiel.
     */
    @Test
    public void testFuehreSpielzugAus() {
        System.out.println("fuehreSpielzugAus");
        
        /* 1. Test alles sollte OK sein */
        try {
        	dasSpiel.fuehreSpielzugAus(sz1);
        	
        	// Neuen Spielstand pruefen
        	if (!stand.istUndoMoeglich())
        	{
        		fail("Der Spielzug wurde nicht zum Spielstand hinzugefuegt!");
        	}
        	// Brett-Aenderung pruefen
        	if(brett.gib(new Position("a5")) != Zustand.schwarz){
        		fail("Der Spielzug wurde nicht zum Brett hinzugefuegt!");
        	}
        }
        catch (UngueltigerZugException e)
        {
        	fail("Hier haette der Zug durchgefuehrt werden sollen.");
        }
        
        /* 2. Test: an schon besetzter Position */
        try  {
        	dasSpiel.fuehreSpielzugAus(sz1);
        	fail("Hier muesste Exception geworfen werden!");
        }
        catch (UngueltigerZugException e)
        {
        	// Alles OK
        }
    }

}