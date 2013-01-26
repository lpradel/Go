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
 * @author sopr055
 */
public class SpielerTest {

    public SpielerTest() {
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
     * Test of getFarbe method, of class Spieler.
     */
    @Test
    public void testGibFarbe() {
        System.out.println("gibFarbe");
        
        Spieler instance = new Spieler(Zustand.schwarz, new String("Testspieler"), SpielerTyp.mensch);
        
        if ( instance.gibFarbe() != Zustand.schwarz ) 
        {
        	fail("Farbe stimmt nicht ueberein.");
        }
    }

    /**
     * Test of getName method, of class Spieler.
     */
    @Test
    public void testGibName() {
        System.out.println("gibName");
        
        Spieler instance = new Spieler(Zustand.schwarz, new String("Testspieler"), SpielerTyp.mensch);
        
        if ( instance.gibName() != "Testspieler" ) 
        {
        	fail("Name des Spielers stimmt nicht ueberein.");
        }
    }
    
    /**
     * Test of equals method, of class Spieler.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        Spieler instance = new Spieler(Zustand.schwarz, new String("Testspieler"), SpielerTyp.mensch);
        
        if ( ! instance.equals(new Spieler(Zustand.schwarz, new String("Testspieler"), SpielerTyp.mensch)) )
        {
        	fail("Spieler stimmen nicht ueberein.");
        }
    }

}