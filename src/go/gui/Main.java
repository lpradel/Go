package go.gui;

import go.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JFrame implements Observer, ActionListener,
        MouseListener, WindowListener {

    private enum panelNummer {

        hauptmenue, simulation, neuesSpiel, laden, speichern, spiel, statistik, regeln, spielMenue, spielEndeMenue, laenge
    }
    private JLabel overlay;
    private JLayeredPane main;
    private JPanel[] panels;
    private Controller ctrl;
    private boolean steinNichtSetzbar = false;
    private boolean zuEnde = false;

    public Main(Controller ctrl) {
        super("Go");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.ctrl = ctrl;
        this.setResizable(false);
        this.setSize(800, 600);
        this.panels = new JPanel[panelNummer.laenge.ordinal()];
        this.main = new JLayeredPane();
        this.initializeComponents();
        this.setVisible(true);
    }

    private void initializeComponents() {
        this.main.setSize(800, 600);
        this.add(main);
        this.overlay = new JLabel();
        this.overlay.setIcon(new ImageIcon("img/hintergrund.png"));
        this.overlay.setBounds(0, 0, 800, 600);
        this.overlay.setVisible(false);
        this.main.add(this.overlay, JLayeredPane.MODAL_LAYER);
        this.erstellePanel(panelNummer.hauptmenue);
        this.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        if (o instanceof Warten) {
            if (((Warten) o).p == panelNummer.laden) {
                this.steinNichtSetzbar = false;
                ctrl.laden();
                this.loeschePanels(panelNummer.spiel);
            } else if (((Warten) o).p == panelNummer.neuesSpiel) {
                this.steinNichtSetzbar = false;
                ctrl.neuesSpielStarten();
                this.loeschePanels(panelNummer.spiel);
            }
            ((Spiel) this.panel(panelNummer.spiel)).entsperreButtons();
            this.repaint();
        } else {
            ((Spiel) this.panel(panelNummer.spiel)).update((go.Spielbrett) o,
                    arg);
        }
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());
        if (!this.overlay.isVisible()) {
            if (e.getActionCommand().equals("Spiel_Undo")) {
                ctrl.undoGeklickt();
            } else if (e.getActionCommand().equals("Spiel_Redo")) {
                ctrl.redoGeklickt();
            } else if (e.getActionCommand().equals("Spiel_Menue")) {
                this.steinNichtSetzbar = true;
                ((Spiel) this.panel(panelNummer.spiel)).sperreButtons();
                this.erstellePanel(panelNummer.spielMenue);
            } else if (e.getActionCommand().equals("Spiel_Passen")) {
                ctrl.passenGeklickt();
            } else if (e.getActionCommand().equals("Spiel_Aufgeben")) {
                int auswahl = JOptionPane.showConfirmDialog(this,
                        "Wollen Sie wirklich aufgeben?",
                        "Aufgeben bestätigen", JOptionPane.YES_NO_OPTION);
                if (auswahl == 0) {
                    ctrl.aufgegebenGeklickt();
                }
            } else if (e.getActionCommand().equals("Spiel_Ende")) {
                zuEnde = true;
                ((Spiel) this.panel(panelNummer.spiel)).spielEnde();
            } else if (e.getActionCommand().equals("Spiel_Klassisch")) {
                zuEnde = true;
                ((Spiel) this.panel(panelNummer.spiel)).setzeButtonsKlassisch();
                //uneinigkeit = true;
                JOptionPane.showMessageDialog(
                        null,
                        "Markieren Sie nun bitte gemeinsam die schwarzen Gebiete.\n"
                        + "Wenn Sie fertig sind, klicken Sie wieder auf \"Weiter\",\n"
                        + "Wenn Sie sich uneinig sind, klicken Sie auf \"Uneinig\".\n"
                        + "Mit nochmaligem Klick können schwarze Markierungen wieder zurückgenommen werden.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getActionCommand().equals("Spiel_markierenWeiss")) {
                ctrl.einigGeklickt();
                ((Spiel) this.panel(panelNummer.spiel)).setRedoCommand("Spiel_einig");
                JOptionPane.showMessageDialog(
                        null,
                        "Markieren Sie nun bitte gemeinsam die weißen Gebiete.\n"
                        + "Wenn Sie fertig sind, klicken Sie wieder auf \"Weiter\",\n"
                        + "Wenn Sie sich uneinig sind, klicken Sie auf \"Uneinig\".\n"
                        + "Mit nochmaligem Klick können weiße Markierungen wieder zurückgenommen werden.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getActionCommand().equals("Spiel_einig")) {
                ctrl.einigGeklickt();
            } else if (e.getActionCommand().equals("Spiel_uneinig")) {
                ((Spiel) this.panel(panelNummer.spiel)).setzeButtonsWeiterspielen();
                ctrl.weiterspielen();
            } else if (e.getActionCommand().equals("Spiel_MenueEnde")) {
                this.steinNichtSetzbar = true;
                ((Spiel) this.panel(panelNummer.spiel)).sperreButtons();
                this.erstellePanel(panelNummer.spielEndeMenue);
            } else if (e.getActionCommand().equals("NeuesSpiel_Starten")) {
                this.steinNichtSetzbar = true;
                this.zuEnde = false;
                this.erstellePanel(panelNummer.spiel);
                ((Spiel) this.panel(panelNummer.spiel)).sperreButtons();
                Warten w = new Warten();
                w.p = panelNummer.neuesSpiel;
                w.addObserver(this);
                new Thread(w).start();
            } else if (e.getActionCommand().equals("Laden_Laden")) {
                this.steinNichtSetzbar = true;
                this.zuEnde = false;
                this.erstellePanel(panelNummer.spiel);
                ((Spiel) this.panel(panelNummer.spiel)).sperreButtons();
                Warten w = new Warten();
                w.p = panelNummer.laden;
                w.addObserver(this);
                new Thread(w).start();
            } else if (e.getActionCommand().equals("")) {
            }
        } else {
            if (e.getActionCommand().equals("SpielMenue_Fortsetzen")) {
                this.loeschePanels(panelNummer.spiel);
                ((Spiel) this.panel(panelNummer.spiel)).entsperreButtons();
                this.steinNichtSetzbar = false;
            } else if (e.getActionCommand().equals("SpielMenue_Speichern")) {
                ctrl.speichern();
            } else if (e.getActionCommand().equals("HauptMenue_Beenden")) {
                this.setVisible(false);
                this.dispose();
            } else if (e.getActionCommand().equals("HauptMenue_Spielregeln")) {
                String url = "http://de.wikipedia.org/wiki/Go_%28Spiel%29";
                try {
                    java.awt.Desktop.getDesktop().browse(
                            java.net.URI.create(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Der Standardbrowser konnte nicht geöffnet werden. Sie können aber selbst auf \n"
                            + url + " die Regeln ansehen.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getActionCommand().equals("HauptMenue_Statistiken")) {
                this.loeschePanels(null);
                this.erstellePanel(panelNummer.statistik);
            } else if (e.getActionCommand().equals("HauptMenue_SpielLaden")) {
                this.loeschePanels(null);
                this.erstellePanel(panelNummer.laden);
            } else if (e.getActionCommand().equals("HauptMenue_NeuesSpiel")) {
                this.loeschePanels(null);
                this.erstellePanel(panelNummer.neuesSpiel);
            }
        }
        if (e.getActionCommand().equals("Zum_Hauptmenue")) {
            this.loeschePanels(null);
            this.erstellePanel(panelNummer.hauptmenue);
        }
    }

    public void mouseClicked(MouseEvent e) {
        //if (!steinNichtSetzbar || uneinigkeit) {
        if (!steinNichtSetzbar) {
            //System.out.println(steinNichtSetzbar + " " + e.getComponent().getName());
            ctrl.positionGeklickt(new go.Position(e.getComponent().getName()));
        }
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        if (panel(panelNummer.spiel) != null) {
            if (!zuEnde) {
                int antwort = JOptionPane.showConfirmDialog(
                        null,
                        "Wenn Sie speichern und beenden möchten, klicken Sie ja.\n"
                        + "Wenn Sie aufgeben und beenden möchten, drücken Sie Nein");
                if (antwort == JOptionPane.OK_OPTION) {
                    this.ctrl.speichern();
                    this.loeschePanels(null);
                    this.setVisible(false);
                    this.dispose();
                } else if (antwort == JOptionPane.CANCEL_OPTION) {
                    ;
                } else {
                    this.ctrl.aufgegebenGeklickt();
                    this.loeschePanels(null);
                    this.setVisible(false);
                    this.dispose();
                }
            } else {
                this.setVisible(false);
                this.dispose();
            }
        } else {
            this.loeschePanels(null);
            this.setVisible(false);
            this.dispose();
        }
    }

    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public void setzeSteinNichtSetzbar(boolean steinNichtSetzbar) {
        this.steinNichtSetzbar = steinNichtSetzbar;


    }

    /**
     * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels. Name
     * Spieler 1.
     *
     * @return Name von Spieler 1.
     */
    public String neuesSpiel_gibSpieler1Name() {
        if (panel(panelNummer.neuesSpiel) == null) {
            return ((Laden) panel(panelNummer.laden)).gibSpielstand().gibSpieler1().gibName();


        } else {
            if (((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibSpieler1Name().equals("")) {
                return "Player 1";


            } else {
                return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibSpieler1Name();


            }
        }
    }

    /**
     * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels. Name
     * Spieler 2.
     *
     * @return Name von Spieler 2.
     */
    public String neuesSpiel_gibSpieler2Name() {
        if (panel(panelNummer.neuesSpiel) == null) {
            return ((Laden) panel(panelNummer.laden)).gibSpielstand().gibSpieler2().gibName();


        } else {
            return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibSpieler2Name();


        }
    }

    /**
     * Funktion zum Auslesen der Größe.
     *
     * @return Größe des Spielfeldes, -1 bei Fehler.
     */
    public int neuesSpiel_gibGroesse() {
        if (panel(panelNummer.neuesSpiel) == null) {
            return ((Laden) panel(panelNummer.laden)).gibSpielstand().gibGroesse();


        } else {
            return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibGroesse();


        }
    }

    /**
     * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels.
     *
     * @return KI Stärke. NULL bei keiner KI.
     */
    public go.SpielerTyp neuesSpiel_gibKI() {
        return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibKI();


    }

    public go.Zustand neuesSpiel_gibKIFarbe() {
        return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibKIFarbe();


    }

    /**
     * Funktion zum Auslesen der Eingaben vom Starten eines neuen Spiels.
     * Spielvariante.
     *
     * @return SPIELART_STEINSCHLAG bei Steinschlag, SPIELART_KLASSISCH bei
     *         Klassisch, -1 bei Fehler.
     */
    public int neuesSpiel_gibVariante() {
        return ((NeuesSpiel) panel(panelNummer.neuesSpiel)).gibVariante();


    }

    public void spiel_buttonsFuerSteinschlagKonfigurieren() {
        ((Spiel) panel(panelNummer.spiel)).buttonsFuerSteinschlagKonfigurieren();


    }

    public void spiel_undoButtonAktivieren(boolean aktiviert) {
        ((Spiel) panel(panelNummer.spiel)).undoButtonAktivieren(aktiviert);


    }

    public void spiel_redoButtonAktivieren(boolean aktiviert) {
        ((Spiel) panel(panelNummer.spiel)).redoButtonAktivieren(aktiviert);


    }

    public void spiel_setzePunkteSpieler1(int punkte) {
        ((Spiel) panel(panelNummer.spiel)).setzePunkteSpieler1(punkte);


    }

    public void spiel_setzePunkteSpieler2(int punkte) {
        ((Spiel) panel(panelNummer.spiel)).setzePunkteSpieler2(punkte);


    }

    public void spiel_spielzugNichtZulaessig(go.RegelBruch r) {
        ((Spiel) panel(panelNummer.spiel)).spielzugNichtZulaessig(r);


    }

    public void spiel_setzeNachricht(String s) {
        ((Spiel) panel(panelNummer.spiel)).setzeNachricht(s);


    }

    public void spiel_spielerWechsel(go.Spieler spieler) {
        if (spieler.gibTyp() != go.SpielerTyp.mensch) {
            this.steinNichtSetzbar = true;


        } else {
            this.steinNichtSetzbar = false;


        }
        ((Spiel) this.panel(panelNummer.spiel)).spielerWechsel(spieler);


    }

    public go.Spielstand laden_gibSpielstand() {
        return ((Laden) (panel(panelNummer.laden))).gibSpielstand();


    }

    public void zeigeFehlerAn(String s) {
        JOptionPane.showMessageDialog(null, s, "Error",
                JOptionPane.ERROR_MESSAGE);


    }

    public void speichern_erfolgreich() {
        if (JOptionPane.showConfirmDialog(null,
                "Speichern erfolgreich. Zurück zum Menü?") == JOptionPane.OK_OPTION) {
            this.actionPerformed(new ActionEvent(this, 0, "Zum_Hauptmenue"));


        } else {
            this.actionPerformed(new ActionEvent(this, 0,
                    "SpielMenue_Fortsetzen"));


        }
    }

    public String speichern_gibKommentar() {
        String comment = "";

        /* Kommentar einlesen */
        comment = JOptionPane.showInputDialog("Bitte einen Kommentar zur Partie angeben:");


        if (comment == null) { /* Abbrechen geklickt */
            JOptionPane.showMessageDialog(null,
                    "Es muss ein Kommentar angegeben werden!",
                    "Kommentar-Fehler", JOptionPane.ERROR_MESSAGE);
            /*
             * Solange nachfragen, bis Input kommt! TODO: Alternativ: Speichern
             * abbrechen
             */


            return speichern_gibKommentar();


        }

        return comment;


    }

    /**
     * Man steckt eine panelNummer rein und bekommt das zugehörige JPanel raus
     */
    private JPanel panel(panelNummer p) {
        if (p.ordinal() == panelNummer.neuesSpiel.ordinal()) {
            return panels[panelNummer.neuesSpiel.ordinal()];


        } else if (p.ordinal() == panelNummer.hauptmenue.ordinal()) {
            return panels[panelNummer.hauptmenue.ordinal()];


        } else if (p.ordinal() == panelNummer.simulation.ordinal()) {
            return panels[panelNummer.simulation.ordinal()];


        } else if (p.ordinal() == panelNummer.laden.ordinal()) {
            return panels[panelNummer.laden.ordinal()];


        } else if (p.ordinal() == panelNummer.regeln.ordinal()) {
            return panels[panelNummer.regeln.ordinal()];


        } else if (p.ordinal() == panelNummer.speichern.ordinal()) {
            return panels[panelNummer.speichern.ordinal()];


        } else if (p.ordinal() == panelNummer.spiel.ordinal()) {
            return panels[panelNummer.spiel.ordinal()];


        } else if (p.ordinal() == panelNummer.statistik.ordinal()) {
            return panels[panelNummer.statistik.ordinal()];


        } else if (p.ordinal() == panelNummer.spielMenue.ordinal()) {
            return panels[panelNummer.spielMenue.ordinal()];


        } else if (p.ordinal() == panelNummer.spielEndeMenue.ordinal()) {
            return panels[panelNummer.spielEndeMenue.ordinal()];


        } else {
            return null;


        }
    }

    private void erstellePanel(panelNummer p) {
        this.main.setVisible(false);


        switch (p) {
            case hauptmenue:
                this.panels[p.ordinal()] = new HauptMenue(this);
                this.panel(p).setBounds(0, 0, 250, 600);
                this.addOverlay();
                this.main.add(this.panel(p), JLayeredPane.POPUP_LAYER);
                this.starteNeueSimulation();
                break;
            case laden:
                this.panels[p.ordinal()] = new Laden(this);
                this.panel(p).setBounds(0, 0, 800, 600);
                this.main.add(this.panel(p), JLayeredPane.DEFAULT_LAYER);
                break;
            case neuesSpiel:
                this.panels[p.ordinal()] = new NeuesSpiel(this);
                this.panel(p).setBounds(0, 0, 800, 600);
                this.main.add(this.panel(p), JLayeredPane.DEFAULT_LAYER);
                break;
            case statistik:
                this.panels[p.ordinal()] = new Statistik(this);
                this.panel(p).setBounds(0, 0, 800, 600);
                this.main.add(this.panel(p), JLayeredPane.DEFAULT_LAYER);
                break;
            case spiel:
                this.panels[p.ordinal()] = new Spiel(this.neuesSpiel_gibSpieler1Name(), this.neuesSpiel_gibSpieler2Name(),
                        this.neuesSpiel_gibGroesse(), this);
                this.panel(p).setBounds(0, 0, 800, 600);
                this.main.add(this.panel(p), JLayeredPane.PALETTE_LAYER);
                break;
            case spielMenue:
                this.panels[p.ordinal()] = new SpielMenue(this);
                this.panel(p).setBounds(330, 235, 140, 110);
                this.addOverlay();
                this.main.add(this.panel(p), JLayeredPane.POPUP_LAYER);
                break;
            case spielEndeMenue:
                this.panels[p.ordinal()] = new SpielEndeMenue(this);
                this.panel(p).setBounds(330, 255, 140, 90);
                this.addOverlay();
                this.main.add(this.panel(p), JLayeredPane.POPUP_LAYER);
                break;
        }
        this.main.setVisible(true);
        this.repaint();
        if (p == panelNummer.spiel) {
        }
    }

    protected void starteNeueSimulation() {
        if (this.panel(panelNummer.simulation) == null) {
            this.panels[panelNummer.simulation.ordinal()] = new SpielSimulator(this);
            this.panel(panelNummer.simulation).setBounds(280, 50, 500, 500);
            this.main.add(this.panel(panelNummer.simulation), JLayeredPane.DEFAULT_LAYER);
        } else {
            ((SpielSimulator) this.panel(panelNummer.simulation)).dispose();
            this.main.remove(panel(panelNummer.simulation));
            this.panels[panelNummer.simulation.ordinal()] = null;
            this.starteNeueSimulation();
        }
    }

    /**
     * löscht alle Panels, außer dem except.
     *
     * @param except
     *            (darf null sein)
     */
    private void loeschePanels(panelNummer except) {
        for (int i = 0; i
                < panels.length; i++) {
            if (i == panelNummer.simulation.ordinal() && panel(panelNummer.simulation) != null) {
                ((SpielSimulator) this.panels[i]).dispose();
            }
            if (((except != null && i != except.ordinal()) || except == null)
                    && panels[i] != null) {
                this.main.remove(panels[i]);
                this.panels[i].removeAll();
                this.panels[i] = null;
            }
        }
        this.removeOverlay();
        this.repaint();
    }

    private void addOverlay() {
        this.overlay.setVisible(true);
    }

    private void removeOverlay() {
        if (this.overlay != null) {
            this.overlay.setVisible(false);
        }
    }

    private class Warten extends Observable implements Runnable {

        public panelNummer p;

        public void run() {
            try {
                Thread.sleep(5800);
                setChanged();
                notifyObservers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
