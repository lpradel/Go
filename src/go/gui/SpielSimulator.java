package go.gui;

import go.SimulationController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SpielSimulator extends JPanel implements Observer {

    private Spielbrett spielbrett;
    private SimulationController ctrl;
    private GroupLayout layout;
    private Main main;

    public SpielSimulator(Main main) {
        this.main = main;
        this.init();
    }

    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        this.spielbrett.update((go.Spielbrett) o, arg);
    }

    public void spielEnde() {
        this.main.starteNeueSimulation();
    }

    private void init() {
        this.ctrl = new SimulationController(this);
        this.spielbrett = new Spielbrett(9, null, true);
        this.spielbrett.setBounds(0, 0, 500, 500);
        layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER).addComponent(spielbrett));
        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER).addComponent(spielbrett));
        this.ctrl.neuesSpielStarten();
        this.setVisible(true);
        this.repaint();
    }

    public void dispose() {
        this.ctrl.stoppe();
    }
}
