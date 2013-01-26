package go.gui;

/*
 * Spielmenue.java
 *
 * Created on Mar 8, 2011, 12:55:09 PM
 */

/**
 *
 * @author sopr057
 */
public class SpielMenue extends javax.swing.JPanel {

    private javax.swing.JButton btnFortsetzen;
    private javax.swing.JButton btnSpeichern;
    private javax.swing.JButton btnSpielregeln;
    
    /** Creates new form Spielmenue */
    public SpielMenue(Main main) {
        initComponents(main);
    }

    private void initComponents(Main main) {
    	this.setOpaque(false);
        btnFortsetzen = new javax.swing.JButton();
        btnSpeichern = new javax.swing.JButton();
        btnSpielregeln = new javax.swing.JButton();

        btnFortsetzen.setText("Fortsetzen");
        btnFortsetzen.addActionListener(main);
        btnFortsetzen.setActionCommand("SpielMenue_Fortsetzen");

        btnSpeichern.setText("Speichern");
        btnSpeichern.addActionListener(main);
        btnSpeichern.setActionCommand("SpielMenue_Speichern");

        btnSpielregeln.setText("Spielregeln");
        btnSpielregeln.addActionListener(main);
        btnSpielregeln.setActionCommand("HauptMenue_Spielregeln");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSpielregeln, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSpeichern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFortsetzen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFortsetzen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpeichern)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpielregeln)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
