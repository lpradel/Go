 package go;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatistikTest {

	@Test
	public final void testGibStatistik() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (StatistikException e) {}
		if(neu==null)
		{
			fail("Statistik nicht erzeugt.");
		}
	}

	@Test
	public final void testSpeichern() {
        System.out.println("speichereStatistik");
        Statistik statistik = null;
        
        try
        {
        	
        	statistik = Statistik.gibStatistik();
        	Spieler daniel = new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch);
        	Spieler loser = new Spieler(Zustand.weiss,"Loser",SpielerTyp.mensch);
        	statistik.aktualisiereEintrag(daniel,Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(daniel,Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(daniel,Spielausgang.niederlage,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(daniel,Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(loser,Spielausgang.sieg,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(loser,Spielausgang.sieg,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(loser,Spielausgang.niederlage,Statistikunterteilung.Steinschlag);
        	statistik.aktualisiereEintrag(loser,Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
        	statistik.speichern();
        }
        catch (StatistikException e) {}
        catch (Exception e) {fail("Speichern fehlgeschlagen");}
	}

	@Test
	public final void testAktualisiereEintrag() {
        System.out.println("aktualisiereStatistik");
        Spieler test = new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch);
        Statistik instance = null;
        try
        {
        	instance = Statistik.gibStatistik();
        }
        catch (Exception e) {}
        instance.setzeZurueck();
        if(instance.sortiereNach(1,0))
        {
        	fail("Statistik gab an, dass Spieler in der Liste vorhanden sind, dies stimmt jedoch nicht. (Bei leerem Statistikfile)");
        }
        instance.aktualisiereEintrag(test,Spielausgang.sieg,Statistikunterteilung.Steinschlag);
        if(!instance.sortiereNach(1,0))
        {
        	fail("Statistik gab an, dass es leer ist, ist es aber nicht.");
        }
        if(!(instance.gibNamen().equals("Daniel")))
        {
        	fail("Name wurde nicht richtig abgespeichert.");
        }
        if(instance.gibWert(1,0)!=1)
        {
        	fail("Sieg wurde nicht richtig registriert.");
        }
        if(!instance.sortiereNach(0,0))
        {
        	fail("Statistik gab an, dass es leer ist, ist es aber nicht.");
        }
        if(instance.gibWert(0,0)!=1)
        {
        	fail("Noch keine Niederlage erlitten, aber keine 0 in den Daten.");
        }
        instance.aktualisiereEintrag(test,Spielausgang.niederlage,Statistikunterteilung.Steinschlag);
        if(instance.gibWert(0,0)!=2)
        {
        	fail("Niederlage wurde nicht registriert.");
        }
	}

	@Test
	public final void testSetzeZurueck() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (Exception e) {}
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		neu.setzeZurueck();
		if(neu.sortiereNach(0,0))
		{
			fail("Statistik nicht leer, obwohl zurueckgesetzt!");
		}
	}

	@Test
	public final void testSortiereNach() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (Exception e) {}
		neu.setzeZurueck();
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Aufgeber",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"ProzentGewinner",SpielerTyp.mensch), Spielausgang.sieg,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Winner",SpielerTyp.mensch), Spielausgang.sieg,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Winner",SpielerTyp.mensch), Spielausgang.sieg,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Winner",SpielerTyp.mensch), Spielausgang.niederlage,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Vielspieler",SpielerTyp.mensch), Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Vielspieler",SpielerTyp.mensch), Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Vielspieler",SpielerTyp.mensch), Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Vielspieler",SpielerTyp.mensch), Spielausgang.unentschieden,Statistikunterteilung.Steinschlag);
		neu.sortiereNach(0,0);
		if(!neu.gibNamen().equals("Vielspieler"))
		{
			fail("Es wurde nicht richtig nach Gesamtanzahl Spielen sortiert.");
		}
		neu.sortiereNach(1,0);
		if(!neu.gibNamen().equals("Winner"))
		{
			fail("Es wurde nicht richtig nach Gesamtanzahl Siegen sortiert.");
		}
		neu.sortiereNach(2,0);
		if(!neu.gibNamen().equals("ProzentGewinner"))
		{
			fail("Es wurde nicht richtig nach den Prozent der gewonnenen Spiele sortiert.");
		}
		neu.sortiereNach(3,0);
		if(!neu.gibNamen().equals("Aufgeber"))
		{
			fail("Es wurde nicht richtig nach Anzahl Aufgegeben sortiert.");
		}
	}

	@Test
	public final void testNaechsterEintrag() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (Exception e) {}
		neu.setzeZurueck();
		if(neu.naechsterEintragMoeglich(0))
		{
			fail("Angabe, dass Daten ausgelesen werden koennen, obwohl Statistik leer ist");
		}
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		neu.sortiereNach(0,0);
		if(!neu.naechsterEintragMoeglich(0))
		{
			fail("Angabe, dass Daten ausgelesen werden koennen, obwohl Pointer ausserhalb des Lesebereichs gerutscht ist");
		}
		neu.sortiereNach(0,0);
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"David",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		if(!neu.naechsterEintragMoeglich(0))
		{
			fail("Angabe, dass alle Daten ausgelesen wurden, obwohl ein weiterer Eintrag ausgelesen werden kann!");
		}
	}

	@Test
	public final void testGibNamen() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (Exception e) {}
		neu.setzeZurueck();
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		neu.sortiereNach(0,0);
		if(!neu.gibNamen().equals("Daniel"))
		{
			fail("Name nicht richtig gespeichert oder ausgelesen!");
		}
	}

	@Test
	public final void testGibWert() {
		Statistik neu = null;
		try
		{
			neu = Statistik.gibStatistik();
		}
		catch (Exception e) {}
		neu.setzeZurueck();
		neu.aktualisiereEintrag(new Spieler(Zustand.schwarz,"Daniel",SpielerTyp.mensch), Spielausgang.aufgegeben,Statistikunterteilung.Steinschlag);
		neu.sortiereNach(0,0);
		if(neu.gibWert(3,0)!=1 || neu.gibWert(0,0)!=1 || neu.gibWert(1,0)!=0)
		{
			fail("Wert fuer Spieler nicht richtig gespeichert oder ausgelesen!");
		}
	}
    

}