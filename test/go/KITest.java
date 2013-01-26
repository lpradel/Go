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
 * @author sopr055 + sopr054
 */
public class KITest {

    public KITest() {
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
     * Test of erzeugePosition method, of class KI.
     */
    @Test
    public void testErzeugePosition() {
        System.out.println("erzeugePosition");
        
        Spielbrett brett = new Spielbrett(9);
        KI instance = new KI(new Spieler(Zustand.schwarz, "Testspieler", SpielerTyp.leicht), new Leicht(Zustand.schwarz, new KIVariante(new Steinschlag())), new Spielbrett(9));
        
        instance.erzeugeSpielzug();
    }

}