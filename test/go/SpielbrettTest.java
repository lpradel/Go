/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package go;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import java.util.Random;

/**
 * 
 * @author David, Jan Eric, Sarah
 */
public class SpielbrettTest {

	public SpielbrettTest() {
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
	 * Test of setzeZug method, of class Spielbrett.
	 */
	@Test
	public void testSetzeZug() {
		System.out.println("setzeZug");
		Spielzug sz = new Spielzug(new Position(3, 5), new Spieler(
				Zustand.schwarz, "bob", SpielerTyp.mensch));
		Spielbrett instance = new Spielbrett(9);
		instance.setzeZug(sz);
		for (int i = 0; i < instance.gibGroesse(); i++) {
			for (int j = 0; j < instance.gibGroesse(); j++) {
				if (i == 3 && j == 5) {
					assertEquals(instance.gib(new Position(i, j)),
							Zustand.schwarz);
				} else {
					assertEquals(instance.gib(new Position(i, j)), Zustand.frei);
				}
			}
		}
	}

	/**
	 * Test of nimmZugZurueck method, of class Spielbrett.
	 */
	@Test
	public void testNimmZugZurueck() {
		System.out.println("nimmZugZurueck");
		Spielbrett instance = new Spielbrett(9);
		Spieler alice = new Spieler(Zustand.schwarz, "Alice", SpielerTyp.mensch);
		Spieler bob = new Spieler(Zustand.weiss, "Bob", SpielerTyp.mensch);
		instance.setzeZug(new Spielzug(new Position(5, 5), alice));
		Spielzug sz = new Spielzug(new Position(5, 5), alice);
		ArrayList<Position> list = new ArrayList<Position>();
		list.add(new Position(4, 5));
		list.add(new Position(3, 5));
		sz.setzeGeschlageneSteine(list);
		instance.nimmZugZurueck(sz);
		Spielbrett expected = new Spielbrett(9);
		expected.setzeZug(new Spielzug(new Position(4, 5), bob));
		expected.setzeZug(new Spielzug(new Position(3, 5), bob));
		assertEquals(instance, expected);
	}

	/**
	 * Test of kopiereSpielbrett method, of class Spielbrett.
	 */
	@Test
	public void testKopiereSpielbrett() { // fertig
		System.out.println("kopiereSpielbrett");
		Spielbrett instance = new Spielbrett(9);
		Spieler alice = new Spieler(Zustand.schwarz, "Alice", SpielerTyp.mensch);
		Spieler bob = new Spieler(Zustand.weiss, "Bob", SpielerTyp.mensch);
		instance.setzeZug(new Spielzug(new Position(5, 5), alice));
		instance.setzeZug(new Spielzug(new Position(5, 6), bob));
		Spielbrett expResult = new Spielbrett(9);
		expResult.setzeZug(new Spielzug(new Position(5, 5), alice));
		expResult.setzeZug(new Spielzug(new Position(5, 6), bob));
		Spielbrett result = instance.kopiereSpielbrett();
		assertEquals(expResult, result);
	}

	@Test
	public void testEquals() {
		System.out.println("equals");
		Spielbrett instance = new Spielbrett(9);
		Spielbrett expResult = new Spielbrett(9);
		Spielbrett notExpResult = new Spielbrett(12);

		assertFalse(instance.equals(notExpResult));
		assertTrue(instance.equals(expResult));
		assertFalse(instance.equals(new Position(0, 0)));
	}

	/**
	 * Test of gibKetten method, of class Spielbrett.
	 */
	@Test
	public void testGibKetten() {
		System.out.println("gibKetten");
		Spielbrett instance = (new Steinschlag()).initialisiereSpielbrett(9);
		List<Kette> result = instance.gibKetten();
		System.out.println(result.size());
		if(result.size()!=4) 
				fail("Erkennt die vier Initialketten nicht.");
		instance = (new Klassisch()).initialisiereSpielbrett(19);
		result = instance.gibKetten();
		if(result.size()!=0) fail("Erkennt Ketten, obwohl keine da sind");
		instance.setzeStein(new Position(1,1), Zustand.schwarz);
		instance.setzeStein(new Position(2,1), Zustand.schwarz);
		instance.setzeStein(new Position(3,1), Zustand.schwarz);
		instance.setzeStein(new Position(2,2), Zustand.schwarz);
		instance.setzeStein(new Position(3,2), Zustand.weiss);
		instance.setzeStein(new Position(3,3), Zustand.weiss);
		result = instance.gibKetten();
		if(result.size()!=2) fail("Erkennt 2 Ketten nicht");
		if(result.get(0).size()!=4 || result.get(1).size()!=2) fail("Erkennt Ketten, obwohl keine da sind");
		

	}

	/**
	 * Test of gibFreieFelder method, of class Spielbrett.
	 */
	@Test
	public void testGibFreieFelder() {
		System.out.println("gibFreieFelder");
		Spielbrett instance = (new Steinschlag()).initialisiereSpielbrett(9);
		List<Position> result = instance.gibFreieFelder();
		if(result.size()!=77) fail("Nicht alle 77 freien Felder erkannt");
		instance.setzeStein(new Position(1,1), Zustand.schwarz);
		instance.setzeStein(new Position(1,9), Zustand.schwarz);
		instance.setzeStein(new Position(9,1), Zustand.schwarz);
		instance.setzeStein(new Position(9,9), Zustand.schwarz);
		result = instance.gibFreieFelder();
		if(result.size()!=73) fail("Nicht alle 73 freien Felder erkannt");
		instance = (new Klassisch()).initialisiereSpielbrett(19);
		for(int i=1;i<=19;i++) {
			for(int j=1; j<=19;j++) {
				instance.setzeStein(new Position(i,j), Zustand.schwarz);
			}
		}
		result = instance.gibFreieFelder();
		if(result.size()!=0) fail("Freie Felder die nicht vorhanden sind erkannt");
	}

	/**
	 * Test of gibFreiheiten method, of class Spielbrett.
	 */
	@Test
	public void testGibFreiheiten_Position() {
		System.out.println("gibFreiheiten");
		Random rnd = new Random();
		Spielbrett instance = new Spielbrett(19);
		Spieler bob = new Spieler(Zustand.weiss, "Bob", SpielerTyp.mensch);
		int x = rnd.nextInt(19) + 1;
		int y = rnd.nextInt(19) + 1;
		instance.setzeZug(new Spielzug(new Position(x, y), bob));
		List<Position> expectedList = new ArrayList<Position>();
		if ((x - 1) >= 1)
			expectedList.add(new Position(x - 1, y));
		if ((x + 1) <= 19)
			expectedList.add(new Position(x + 1, y));
		if ((y - 1) >= 1)
			expectedList.add(new Position(x, y - 1));
		if ((y + 1) <= 19)
			expectedList.add(new Position(x, y + 1));
		int setzen = rnd.nextInt(expectedList.size());
		for (int i = 0; i < setzen; i++) {
			int gesetztePos = rnd.nextInt(expectedList.size());
			Spielzug sz = new Spielzug(expectedList.get(gesetztePos), bob);
			instance.setzeZug(sz);
			expectedList.remove(gesetztePos);
		}
		List<Position> list = instance.gibFreiheiten(new Position(x, y));
		if (!list.containsAll(expectedList)) {
			fail("Bei Test mit Stein auf ["
					+ x
					+ ","
					+ y
					+ "] Randbedingungen von gesetzten Steinen leider random und deshalb nicht angebbar! ;)");
		}
	}
	
	@Test
	public void testTestGibFreiheiten_Position(){
		System.out.println("gibFreiheiten");
		Spielbrett instance =  (new Klassisch()).initialisiereSpielbrett(19);
		instance.setzeStein(new Position(1,1), Zustand.schwarz);
		instance.setzeStein(new Position(2,1), Zustand.schwarz);
		instance.setzeStein(new Position(3,1), Zustand.weiss);
		
		List<Position> k = instance.gibFreiheiten(new Position(1,1));
		if(k.size()!=1 || !k.get(0).equals(new Position(1,2)))
			fail("Freiheit von erstem Stein nicht erkannt.");
		
		k = instance.gibFreiheiten(new Position(2,1));
		if(k.size()!=1 || !k.get(0).equals(new Position(2,2)))
			fail("Freiheit von zweiten Stein nicht erkannt.");
		
		k = instance.gibFreiheiten(new Position(3,1));
		if(k.size()!=2)
			fail("Freiheit von dritten Stein nicht erkannt.");
	}

	/**
	 * Test of gibFreiheiten method, of class Spielbrett.
	 */
	@Test
	public void testGibFreiheiten_Kette() {
		System.out.println("gibFreiheiten");
		Spielbrett instance =  (new Klassisch()).initialisiereSpielbrett(19);
		instance.setzeStein(new Position(1,1), Zustand.schwarz);
		instance.setzeStein(new Position(2,1), Zustand.schwarz);
		instance.setzeStein(new Position(3,1), Zustand.schwarz);
		
		instance.setzeStein(new Position(1,2), Zustand.weiss);
		instance.setzeStein(new Position(2,2), Zustand.weiss);
		instance.setzeStein(new Position(3,2), Zustand.weiss);
		Kette k = instance.gibKette(new Position(1,1));
		
		List<Position> result = instance.gibFreiheiten(k);
		if(result.size()!=1 || !result.get(0).equals(new Position(4,1)))
			fail("Freiheit von schwarzer Kette nicht erkannt.");
		k = instance.gibKette(new Position(1,2));
		result = instance.gibFreiheiten(k);
		if(result.size()!=4)
			fail("Freiheiten von weisser Kette nicht erkannt.");
	}

	/**
	 * Test of gibKette method, of class Spielbrett.
	 */
	@Test
	public void testGibKette() {
		System.out.println("gibKette");
		Spielbrett instance =  (new Klassisch()).initialisiereSpielbrett(19);
		instance.setzeStein(new Position(1,1), Zustand.schwarz);
		instance.setzeStein(new Position(2,1), Zustand.schwarz);
		instance.setzeStein(new Position(3,1), Zustand.schwarz);
		
		instance.setzeStein(new Position(1,2), Zustand.weiss);
		instance.setzeStein(new Position(2,2), Zustand.weiss);
		
		instance.setzeStein(new Position(5,5), Zustand.weiss);
		
		Kette k = instance.gibKette(new Position(3,1));
		if(k.size()!=3) fail("Schwarze Kette nicht erkannt");
		k = instance.gibKette(new Position(2,2));
		if(k.size()!=2) fail("Weisse Kette nicht erkannt");
		k = instance.gibKette(new Position(5,5));
		if(k.size()!=1 || !k.get(0).equals(new Position(5,5))) 
			fail("Einzelne Position nicht erkannt.");
	}
}