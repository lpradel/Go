package go;

import java.util.Observable;
import java.util.Observer;

public class SimulationController implements Observer {

    private KI lnkKIHaupt;
    private KI lnkKINeben;
    private go.gui.SpielSimulator sp;
    private boolean stop;

    /**
     * Der Controller hat die GUI, um diese zu aktualisieren je nach Spielstand.
     */
    public SimulationController(go.gui.SpielSimulator sp) {
        super();
        this.sp = sp;
    }
    /**
     * Der Controller hat das Spiel und führt auf diesem Aktionen aus.
     */
    private Spiel lnkSpiel;
    private Spielstand lnkSpielstand;

    public void neuesSpielStarten() {
        Spieler spieler1, spieler2;
        Variante var;
        // 1. Spieler soll immer schwarz sein
        spieler1 = new Spieler(Zustand.schwarz, "Bob", SpielerTyp.leicht);
        spieler2 = new Spieler(Zustand.weiss, "Bob2", SpielerTyp.leicht);
        // Variante erzeugen
        var = new Steinschlag();
        this.lnkSpielstand = new Spielstand(spieler1, spieler2, var, 9);
        this.lnkSpiel = new Spiel(var, lnkSpielstand, var.initialisiereSpielbrett(9));
        this.lnkKIHaupt = new KI(spieler1, new Leicht(Zustand.schwarz,
                new KIVariante(var)), this.lnkSpiel.gibSpielbrett());
        this.lnkKINeben = new KI(spieler2, new Leicht(Zustand.weiss,
                new KIVariante(var)), this.lnkSpiel.gibSpielbrett());
        // Observer setzen
        this.lnkSpiel.gibSpielbrett().addObserver(this.sp);
        this.lnkSpielstand.addObserver(this);
        this.lnkKIHaupt.addObserver(this);
        this.lnkKINeben.addObserver(this);
        this.update(lnkSpielstand, lnkSpielstand.gibAktuellenSpieler());
    }
    /**
     *
     * Methode, die vom Spielstand aufgerufen wird, wenn ein Zug durchgeführt
     * wurde. Es wird überprüft, ob im Spiel das Attribut „zuEnde“ gesetzt ist;
     * in dem Fall wird spielEnde() zum Ermitteln des Gewinners durchgeführt.
     * Falls nicht, wird die GUI über den Spielerwechsel informiert, um den
     * nächsten Spieler zum Zug aufzufordern. Sonst wird das Spielende
     * eingeleitet. Zusätzlich wird ein boolean-Wert übergeben, der angibt, ob
     * der Spieler an der Reihe die KI ist, damit die GUI ggf. Buttons sperren
     * kann. Falls der Spieler keine KI ist, ist die Methode hier beendet. In
     * dem Fall, dass der aktuelle Spieler KI ist und zuletzt kein Redo
     * durchgeführt wurde (Redo-Flag auf false), wird die Zug-Methode der KI
     * ausgeführt, die einen Spielzug zurückgibt. Dieser Spielzug wird
     * anschließend mit der „fuehreSpielzugAus“-Methode vom Spiel angewandt.
     *
     * @param spielstand
     *            Wird durch das Observerpattern beim update der Methode als
     *            geupdatetes Objekt übergeben
     * @param aktSpieler
     *            gibt den aktuellen Spieler an
     */
    private int counter = 0;

    public synchronized void update(Observable observable, Object aktSpieler) {
        try {
            if (counter > 0) {
                Thread.sleep(1000);
            }
            counter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!stop) {
            if (observable instanceof Spielstand) {
                // Spielstand update
                Spieler spieler = (Spieler) aktSpieler;
                if (spieler == null) {
                    // weil notify nicht vernünftig implementiert wurde ...
                    spieler = this.lnkSpielstand.gibAktuellenSpieler();
                }
                // KI
                if (spieler.equals(this.lnkKIHaupt.gibSpieler())
                        && !lnkSpiel.istSpielZuEnde()) {
                    this.lnkKIHaupt.erzeugeSpielzug();
                } else if (spieler.equals(this.lnkKINeben.gibSpieler())) {
                    this.lnkKINeben.erzeugeSpielzug();
                }
                if (lnkSpiel.istSpielZuEnde()) {
                    this.spielEnde();
                }
            } else if (observable instanceof KI) {
                // KI update
                try {
                    if (this.lnkKIHaupt.equals(observable)) {
                        this.lnkSpiel.fuehreSpielzugAus(this.lnkKIHaupt.gibSpielzug());
                    } else {
                        this.lnkSpiel.fuehreSpielzugAus(this.lnkKINeben.gibSpielzug());
                    }
                } catch (UngueltigerZugException e) {
                    System.out.println("Wir haben mist gemacht, die KI kann nicht vernünftig spielen!\n"
                            + e.gibRegelBruch());
                    if (this.lnkKIHaupt.equals(observable)) {
                        lnkKIHaupt.erzeugeSpielzug();
                    } else {
                        lnkKINeben.erzeugeSpielzug();
                    }
                }
            }
        }
    }

    private void spielEnde() {
        this.stop = true;
        this.sp.spielEnde();
    }

    public void stoppe() {
        this.stop = true;
    }
}
