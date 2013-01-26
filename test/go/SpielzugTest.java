/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package go;

import java.util.List;

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
public class SpielzugTest {

    public SpielzugTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setzePunkte method, of class Spielzug.
     */
    /*@Test
    public void testSetzePunkte() {
        System.out.println("setzePunkte");
        int i = 30;
        Spielzug instance = new Spielzug(new Position(1,2), new Spieler(Zustand.schwarz, "Testspieler", null));
        
        instance.setzePunkte(i);
        
        if ( instance.gibPunkte() != i )
        {
        	fail("Setzen und Gib stimmen nicht ueberein.");
        }
    }*/

    /**
     * Test of gibPunkte method, of class Spielzug.
     */
    @Test
    public void testGibPunkte() {
    	System.out.println("gibPunkte");
    	
        int i = 30;
        Spielzug instance = new Spielzug(new Position(1,2), new Spieler(Zustand.schwarz, "Testspieler", SpielerTyp.mensch));
        
        //instance.setzePunkte(i);
        
        if ( instance.gibPunkte() != i )
        {
        	fail("Setzen und Gib stimmen nicht ueberein.");
        }
    }
    
    /**
     * Test of gibGeschlageneSteine, of classSpielzug
     */
    @Test
    public void testGibGeschlageneSteine(){
    	System.out.println("gibGeschlageneSteine");
    	
    	/*Spieler testspieler = new Spieler(Zustand.frei,"testspieler",SpielerTyp.mensch);
    	Spielzug sz = new Spielzug(new Position(1, 2), testspieler);
    	List<Position> listPosition = sz.gibGeschlageneSteine();*/
    	
    }
    
    /**
     * Test of gibGesetztenStein, of class Spielzug.
     */
    @Test
    public void testGibGesetztenStein(){
    	System.out.println("gibGesetztenStein");
    	
    	Spieler testspieler = new Spieler(Zustand.schwarz, "Testspieler", SpielerTyp.mensch); 
    	Position pos = new Position(1,2);
    	Spielzug instance = new Spielzug(pos,testspieler);
    	Spielbrett brett = new Spielbrett(9);
    	brett.setzeZug(instance);
    	if(brett.gib(pos)!= testspieler.gibFarbe()){
    		 fail("Fehler bei der Methode gibGesetztenStein!");
    	}
    }
    
    /**
     * Test of gibSpieler, of class Spielzug.
     */
    @Test 
    public void testGibSpieler(){
    	System.out.println("gibSpieler");
    	
    	Spieler testspieler = new Spieler(Zustand.frei,"testspieler",SpielerTyp.mensch);
    	Spielzug instance = new Spielzug(new Position(1, 2), testspieler);
    	if(instance.gibSpieler() != testspieler){
    		fail("Fehler bei der Methode gibSpieler!");
    	}
    }

}