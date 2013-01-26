package go;

import java.util.ArrayList;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;

@SuppressWarnings("serial")
public class Statistik implements Serializable{
	/**
	 * Beschreibt den aktuellen Speicherort der Statistikdatei
	 */
	static final String PFAD = "statistik";
	
	private static Statistik instanz = null; 
	
	/**
	 * Singleton-Entwurfsmuster stellt sicher, dass nur eine Instanz gleichzeitig existieren kann
	 */
	
	public static Statistik gibStatistik() throws StatistikException
	{
		if (instanz == null) 
	    {
			instanz = laden();
	    }
		return instanz;
	}
	
	/**
	 * Initialisieren der Statistik-Instanz
	 */
	
	private Statistik()
	{
		eintraege = new ArrayList<Eintrag>();
	}

	private ArrayList<Eintrag> eintraege;
	
	private int auslesePosition = 0;

	private class Eintrag implements Serializable{
		private String name;
		private int[][] ergebnisse;

		/**
		 * Konstruktor, der einen neuen Eintrag mit gegebenem Namen erzeugt
		 * @param name
		 */
		public Eintrag(String name) {
			this.name = name;
			this.ergebnisse = new int[4][4];
		}
		
		/**
		 * Gibt den Namen des Eintrags zurueck.
		 * @return
		 */
		public String gibName(){
			return this.name;
		}
		
		/**
		 * Gibt die Ergebnisliste des Eintrags zurueck.
		 * @return
		 */
		public int[][] gibErgebnisse(){
			return this.ergebnisse;
		}

		/**
		 * Nimmt einen konkreten Spielausgang und erhoeht den zugehoerigen Wert in der Liste um 1.
		 * @param kategorie
		 */
		public void aktualisiere(Spielausgang kategorie, Statistikunterteilung variante)
		{
			this.ergebnisse[kategorie.ordinal()][variante.ordinal()]++;
		}

	}
	
	private class EintragComparator implements Comparator<Eintrag>, Serializable
	{
		private int kriterium;
		private int variante;
		/**
		 * Erstellt neuen EintragComparator, der nach kriterium sortiert.
		 * @param kriterium
		 */
		public EintragComparator(int kriterium, int variante)
		{
			this.kriterium = kriterium;
			this.variante = variante;
		}
		
		/**
		 * Vergleicht zwei Eintraege mithilfe des gewaehlten Kriteriums
		 */
		public int compare(Eintrag e1, Eintrag e2)
		{
			int gesamt1 = e1.ergebnisse[0][variante]+e1.ergebnisse[1][variante]+e1.ergebnisse[2][variante]+e1.ergebnisse[3][variante];
			int gesamt2 = e2.ergebnisse[0][variante]+e2.ergebnisse[1][variante]+e2.ergebnisse[2][variante]+e2.ergebnisse[3][variante];
			if(kriterium == 0)
			{
				return comp(gesamt1,gesamt2);
			}
			else if(kriterium == 1)
			{
				if(e1.ergebnisse[0][variante] != 0 || e2.ergebnisse[0][variante] != 0)
				{
					return comp(e1.ergebnisse[0][variante],e2.ergebnisse[0][variante]);
				}
				else
				{
					return comp(gesamt1,gesamt2);
				}
			}
			else if(kriterium == 2)
			{
				int prozent1 = (int)(((double)e1.ergebnisse[0][variante]/(double)gesamt1)*100);
				int prozent2 = (int)(((double)e2.ergebnisse[0][variante]/(double)gesamt2)*100);
				if(prozent1 != 0 || prozent2 != 0)
				{
					return comp(prozent1,prozent2);
				}
				else
				{
					return comp(gesamt1,gesamt2);
				}
			}
			else
			{
				if(e1.ergebnisse[1][variante] != 0|| e2.ergebnisse[1][variante] != 0)
				{
					return comp(e1.ergebnisse[1][variante],e2.ergebnisse[1][variante]);
				}
				else
				{
					return comp(gesamt1,gesamt2);
				}
			}
		}
		
		private int comp(int a, int b)
		{
			if(a > b) return -1;
			if(a==b) return 0;
			return 1;
		}
	}

	/**
	 * Speichert die gesamten Einträge des Statistikobjekts in eine
	 * Statistikdatei, deren Ort durch „pfad“ angegeben ist. Beim Erzeugen des
	 * Statistik-Objekts alle bisherigen Einträge laden und am Ende alle
	 * zurückschreiben, indem man die Datei überschreibt Möglicherweise ist
	 * danach das Statistik-Objekt entfernt, um mehrmaliges Speichern zu
	 * verhindern.
	 */
	public void speichern() throws IOException
	{
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		try
		{
			fout = new FileOutputStream(PFAD);
			out = new ObjectOutputStream(fout);
			out.writeObject(this);
		}
		finally
		{
			try
			{
				out.close();
				fout.close();
			}
			catch(Exception e) {}
		}
	}
	/**
	 * Laedt aus der Statistik-Datei und erzeugt bei fehlender Datei ein leeres Statistik-Objekt
	 * @return Liefert die Statistikdatei,die geladen wird
	 */
	private static Statistik laden() throws StatistikException
	{
		Statistik objekt = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(PFAD);
			in = new ObjectInputStream(fis);
			objekt = (Statistik)in.readObject();
		}
		catch(Exception e)
		{
			objekt = new Statistik();
			throw new StatistikException("Statistik wurde neu angelegt.");
		}
		finally
		{
			try
			{
				in.close();
				fis.close();
			}
			catch(Exception e) {}
			return objekt;
		}
	}

	/**
	 * Falls der Spieler bereits in der Statistik erfasst ist, wird der Eintrag
	 * aktualisiert, indem der jeweilige Wert für die Anzahl der
	 * Siege/Niederlagen/Aufgegeben inkrementiert wird. Gibt es ihn nicht, wird
	 * ein neuer Eintrag mit diesem Namen erzeugt.
	 * 
	 * @param s
	 * @param ergebnis
	 */
	public void aktualisiereEintrag(Spieler s, Spielausgang ergebnis, Statistikunterteilung variante) {
		boolean found = false;
		int i=0;
		while(i<eintraege.size() && !found)
		{
			found = ((eintraege.get(i).gibName()).equals(s.gibName()));
			if(!found)
			{
				i++;
			}
		}
		if(found)
		{
			eintraege.get(i).aktualisiere(ergebnis, variante);
		}
		else
		{
			Eintrag neu = new Eintrag(s.gibName());
			neu.aktualisiere(ergebnis, variante);
			{
				eintraege.add(neu);
			}
		}
	}
	
	public int gibAnzahlEintraege()
	{
		return eintraege.size();
	}
	
	/**
	 * Setzt die Statistik zurueck und loescht damit alle bisher vorhandenen Eintraege und Daten des Objekts (nicht der Datei). 
	 * 
	 */
	public void setzeZurueck()
	{
		eintraege = new ArrayList<Eintrag>();
		auslesePosition = 0;
	}
	
	/**
	 * Sortiert die Liste der Eintraege nach einem gewissen Kriterium je nach gewaehltem Tab absteigend,
	 * sodass anschliessend Eintrag fuer Eintrag durchgegangen, ausgelesen und in der GUI ausgegeben werden kann
	 * Danach kann man ganz vorne in der Liste anfangen, auszulesen
	 * @return boolean-Wert, der angibt, ob ein Element ausgelesen werden kann oder kein Spieler in der Statistik ist
	 */
	public boolean sortiereNach(int gewaehlterTab, int variante)
	{
		EintragComparator sortierer = new EintragComparator(gewaehlterTab, variante);
		Collections.sort(eintraege,sortierer);
		auslesePosition = 0;
		return (auslesePosition < eintraege.size());
	}
	
	/**
	 * Gibt zurueck, ob man noch nicht alle
	 * Eintraege ausgelesen hat (also ob naechsterEintrag() moeglich ist)
	 */
	public boolean naechsterEintragMoeglich(int variante)
	{
		if(auslesePosition < eintraege.size() && auslesePosition >= 0)
		{
			int[][] ergebnisse = eintraege.get(auslesePosition).gibErgebnisse();
			if(ergebnisse[0][variante]==0 && ergebnisse[1][variante]==0 && ergebnisse[2][variante]==0 && ergebnisse[3][variante]==0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Geht zum naechsten Eintrag der Liste.
	 */
	public void naechsterEintrag()
	{
		auslesePosition++;
	}
	
	
	/**
	 * Gibt den Namen des Spielers an, der in der Eintragliste an der aktuellen auslesePosition ist 
	 */
	public String gibNamen()
	{
		return eintraege.get(auslesePosition).gibName();
	}
	
	/**
	 * Gibt den Zahlenwert zum Spieler je nach gewaehltem Tab an
	 */
	public int gibWert(int tab, int variante)
	{
		int[][] feld = eintraege.get(auslesePosition).gibErgebnisse();
		int gesamt = feld[0][variante]+feld[1][variante]+feld[2][variante]+feld[3][variante];
		if(tab==0)
		{
			return gesamt;
		}
		else if(tab==1)
		{
			return eintraege.get(auslesePosition).gibErgebnisse()[0][variante];
		}
		else if(tab==2)
		{
			double prozent = (double)feld[0][variante]/(double)gesamt;
			return (int)(prozent*10000);
		}
		else
		{
			return feld[1][variante];
		}
	}
}
