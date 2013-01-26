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
 * @author Jan Eric Lenssen
 */
public class KlassischTest {

    public KlassischTest() {
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
     * Test of initialisiereSpielbrett method, of class Klassisch.
     */
    @Test
    public void testInitialisiereSpielbrett() {
        System.out.println("initialisiereSpielbrett");
        
        Klassisch instance = new Klassisch();
        
        Spielbrett brettnine = new Spielbrett(9);
        Spielbrett brettthirteen = new Spielbrett(13);
        Spielbrett brettnineteen = new Spielbrett(19);
        
        if ( ! instance.initialisiereSpielbrett(9).equals(brettnine) )
        {
        	fail("Spielbrett wird nicht richtig initialisiert.");
        }
        if ( ! instance.initialisiereSpielbrett(13).equals(brettthirteen) )
        {
        	fail("Spielbrett wird nicht richtig initialisiert.");
        }
        if ( ! instance.initialisiereSpielbrett(19).equals(brettnineteen) )
        {
        	fail("Spielbrett wird nicht richtig initialisiert.");
        }
    }

    /**
     * Test of berechnePunkte method, of class Klassisch.
     */
    @Test
    public void testBerechnePunkte() {
        System.out.println("berechnePunkte");
        Klassisch instance = new Klassisch();

        if(!(instance.berechnePunkte(new Spieler(Zustand.weiss,"",SpielerTyp.mensch))==0)) {
        	fail("Punkte werden falsch berechnet.");
        }
        
       
    }

    /**
     * Test of istZuEnde method, of class Klassisch.
     */
    @Test
    public void testIstZuEnde() throws UngueltigerZugException {
        System.out.println("istZuEnde");
        Klassisch instance = new Klassisch();
        Spielbrett brett= instance.initialisiereSpielbrett(19);
        if(instance.istZuEnde()==true) {
        	fail("Spiel ist nicht zuende, aber Methode entscheidet so.");
        }
        brett = instance.initialisiereSpielbrett(19);
        for(int i=1;i<=19;i++) {
        	for(int j=1; j<=19;j++) {
        		brett.setzeStein(new Position(i,j), Zustand.schwarz);
        	}
        }
        if(instance.istZuEnde()==false){
        	fail("Brett ist voll und Spiel läuft weiter");
        }
        // noch zu fuellen
        brett = instance.initialisiereSpielbrett(19);
        Spielzug s = instance.erzeugeSpielzug(new Spielzug(null,new Spieler(Zustand.schwarz,"",SpielerTyp.mensch)));
        brett.setzeZug(s);
        s = instance.erzeugeSpielzug(new Spielzug(null,new Spieler(Zustand.weiss,"",SpielerTyp.mensch)));
        brett.setzeZug(s);
        if(instance.istZuEnde()==false){
        	fail("2mal gepasst und Spiel läuft weiter");
        }

        
    }

    /**
     * Test of gibGeschlageneSteine method, of class Klassisch.
     */
    @Test
    public void testGibGeschlageneSteine() {
        System.out.println("gibGeschlageneSteine");
        Klassisch instance = new Klassisch();
        Spielbrett brett = instance.initialisiereSpielbrett(9);
        
        brett.setzeStein(new Position(1,1), Zustand.weiss);
        brett.setzeStein(new Position(1,2), Zustand.schwarz);
        //2,1
        
        brett.setzeStein(new Position(4,4), Zustand.schwarz);
        brett.setzeStein(new Position(4,5), Zustand.schwarz);
        brett.setzeStein(new Position(5,4), Zustand.weiss);
        brett.setzeStein(new Position(5,5), Zustand.weiss);
        brett.setzeStein(new Position(4,6), Zustand.weiss);
        brett.setzeStein(new Position(3,5), Zustand.weiss);
        brett.setzeStein(new Position(3,4), Zustand.weiss);
        //4,3
        
        Spielzug s = new Spielzug(new Position(2,1), new Spieler(Zustand.schwarz,"", SpielerTyp.mensch));
        
        List<Position> list = instance.gibGeschlageneSteine(s);
        if(list.size()!=1 || !list.get(0).equals(new Position(1,1))) {
        	fail("Einzelner Stein (1,1) wurde nicht korrekt entfernt");
        }
        s = new Spielzug(new Position(4,3), new Spieler(Zustand.weiss,"", SpielerTyp.mensch));
        list = instance.gibGeschlageneSteine(s);
        if(list.size()!=2 || !(list.get(0).equals(new Position(4,4))&&list.get(1).equals(new Position(4,5)))) {
        	
        	fail("Kette (4,4),(4,5) wurde nicht korrekt entfernt");
        }
        
        
    }

    /**
     * Test of erzeugeSpielzug method, of class Klassisch.
     */
    @Test
    public void testErzeugeSpielzug() {
        System.out.println("erzeugeSpielzug");
        Klassisch instance = new Klassisch();
    }

    /**
     * Test of istZulaessig method, of class Klassisch.
     */
    @Test
    public void testIstZulaessig() {
    	System.out.println("istZulaessig");
        
        Variante instance = new Klassisch();
        
        Spieler s1 = new Spieler(Zustand.schwarz, "Schwarz",SpielerTyp.mensch);
        Spieler s2 = new Spieler(Zustand.weiss, "Weiss",SpielerTyp.mensch);
        
        // Zulaessige Spielzuege
        Spielzug sz1 = new Spielzug(new Position(2,2), s1);
        Spielzug sz2 = new Spielzug(new Position(1,1), s2);
        
        // Unzulaessige Spielzuege
        Spielzug sz3 = new Spielzug(new Position(-1,-1), s1);
        Spielzug sz4 = new Spielzug(new Position(4,4), s2);
        
        instance.initialisiereSpielbrett(9);
        
        // 1.Test: Zulaessiger Spielzug
        if (instance.istZulaessig(sz1) != RegelBruch.keiner)
        {
        	fail("Der Spielzug sollte moeglich sein!");
        }
        
        // 2. Test: unzulaessige Koordinaten
        if (instance.istZulaessig(sz3) != RegelBruch.ausserhalbDesSpielfeldes)
        {
        	fail("Der Spielzug sollte ausserhalb des Spielfeldes sein!");
        }
        
        // 3.Test: unzulaessig, da schon Stein vorhanden
        instance.lnkSpielbrett.setzeZug(sz4);
        if (instance.istZulaessig(sz4) != RegelBruch.feldBesetzt)
        {
        	fail("Der Spielzug sollte einen vorhandenen Stein ueberschreiben!");
        }
        
        // 4.Test: Selbstmordregel
        instance.lnkSpielbrett.setzeStein(new Position(1,2), Zustand.schwarz);
        instance.lnkSpielbrett.setzeStein(new Position(2,1), Zustand.schwarz);
        instance.lnkSpielbrett.setzeStein(new Position(2,2), Zustand.schwarz);
        
        if (instance.istZulaessig(sz2) != RegelBruch.selbstMord)
        {
        	fail("Der Spielzug ist Selbstmord!");
        }
        
        // 5.Test: KO-Regel
        instance = new Klassisch();
        instance.initialisiereSpielbrett(9);
        
        
        Spielzug ko1 = new Spielzug(new Position(8,2), s2);
        Spielzug ko2 = new Spielzug(new Position(8,3), s1);
        
        instance.lnkSpielbrett.setzeStein(new Position(7,2), Zustand.schwarz);
        instance.lnkSpielbrett.setzeStein(new Position(8,1), Zustand.schwarz);
        instance.lnkSpielbrett.setzeStein(new Position(8,3), Zustand.schwarz);
        instance.lnkSpielbrett.setzeStein(new Position(9,2), Zustand.schwarz);
        
        instance.lnkSpielbrett.setzeStein(new Position(7,3), Zustand.weiss);
        instance.lnkSpielbrett.setzeStein(new Position(8,4), Zustand.weiss);
        instance.lnkSpielbrett.setzeStein(new Position(9,3), Zustand.weiss);
        
        System.out.println(instance.istZulaessig(ko1));
        try
        {
        	instance.erzeugeSpielzug(ko1);
        	instance.lnkSpielbrett.setzeStein(new Position(8,3), Zustand.frei);
        	instance.erzeugeSpielzug(ko2);
        }
        catch (UngueltigerZugException e)
        {
        	System.out.println(e.gibRegelBruch());
        	if (e.gibRegelBruch() == RegelBruch.ko)
        	{
        		// Alles OK
        	}
        	else
        	{
        		fail("KO-Regel sollte verletzt worden sein!");
        	}
        }
        
    }

}