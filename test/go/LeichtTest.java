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
 * @author sopr055 + sopr054
 */
public class LeichtTest {

    public LeichtTest() {
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
     * Test of erzeugePosition method, of class Leicht.
     */
    @Test
    public void testErzeugePosition() {
        System.out.println("erzeugePosition:");
        
        // Sicheren Spielzug testen
        
        Steinschlag variante = new Steinschlag();
        Spielbrett brett = variante.initialisiereSpielbrett(9);
        
        Leicht instance = new Leicht(Zustand.schwarz, new KIVariante(variante));
        
        brett.setzeStein(new Position(2, 2), Zustand.schwarz);
        brett.setzeStein(new Position(1, 3), Zustand.schwarz);
        brett.setzeStein(new Position(1, 1), Zustand.weiss);
        brett.setzeStein(new Position(1, 2), Zustand.weiss);
        
        if (brett.gibKetten().size() != 7)
        {
        	fail("Kettenfunktion falsch?");
        }
        
        List<Kette> ketten = brett.gibKetten();
        boolean found = false;
        for (int i = 0; i < ketten.size(); i++)
        {
        	if ( brett.gibFreiheiten(ketten.get(i)).size() == 1 )
        	{
        		found = true;
        	}
        }
        
        if ( found == false )
        {
        	fail("Keine Kette mit nur einer Freiheit gefunden! > gibFreiheiten falsch?");
        }
        
        Position result = instance.erzeugePosition(brett);
        if ( result == null || !(result.gibX() > 0 && result.gibY() > 0 && result.gibX() <= 9 && result.gibY() <= 9) )
        {
        	fail("Ungueltige oder keine Position zurueckgegeben.");
        } else if ( ! (result.gibX() == 2 && result.gibY() == 1) )
        {
        	fail("Sicherer Spielzug fail: Falsches Feld zurueckgegeben: " + result.gibX() + "|" + result.gibY());
        }
        
        variante = new Steinschlag();
        brett = variante.initialisiereSpielbrett(9);
        
        brett.setzeStein(new Position(5, 4), Zustand.schwarz);
        brett.setzeStein(new Position(4, 4), Zustand.weiss);
        brett.setzeStein(new Position(4, 5), Zustand.schwarz);
        brett.setzeStein(new Position(5, 5), Zustand.weiss);
        
        // Testspiel ....
        
        instance = new Leicht(Zustand.schwarz, new KIVariante(variante));
        
        result = instance.erzeugePosition(brett);
        
        if ( result == null || ! (result.gibX() > 0 && result.gibY() > 0 && result.gibX() <= 9 && result.gibY() <= 9) )
        {
        	fail("Ungueltige oder keine Position zurueckgegeben.");
        }
        
        System.out.println("Moegliche Wahl: " + result.gibX() + "|" + result.gibY());
        
        // Selbstschutz testen ...
        
        variante = new Steinschlag();
        brett = variante.initialisiereSpielbrett(9);
        
        brett.setzeStein(new Position(1, 1), Zustand.schwarz);
        brett.setzeStein(new Position(1, 2), Zustand.schwarz);
        brett.setzeStein(new Position(1, 3), Zustand.weiss);
        brett.setzeStein(new Position(2, 2), Zustand.weiss);
        
        instance = new Leicht(Zustand.schwarz, new KIVariante(variante));
        result = instance.erzeugePosition(brett);
        
        if ( result == null || !(result.gibX() > 0 && result.gibY() > 0 && result.gibX() <= 9 && result.gibY() <= 9) )
        {
        	fail("Ungueltige oder keine Position zurueckgegeben.");
        } else if ( ! (result.gibX() == 2 && result.gibY() == 1) )
        {
        	fail("Selbstschutz fail: Falsches Feld zurueckgegeben: " + result.gibX() + "|" + result.gibY());
        }
    }

}