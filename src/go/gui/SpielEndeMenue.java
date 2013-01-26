package go.gui;

/*
 * SpielEndemenue.java
 *
 * Created on Mar 8, 2011, 12:55:09 PM
 */

/**
 *
 * @author sopr057
 */
@SuppressWarnings("serial")
public class SpielEndeMenue extends javax.swing.JPanel {

    private javax.swing.JButton btnHauptmenue;
    private javax.swing.JButton btnSpeichern;
    
    /** Creates new form SpielEndeMenue */
    public SpielEndeMenue(Main main) {
        initComponents(main);
    }

    private void initComponents(Main main) {
    	this.setOpaque(false);
        btnHauptmenue = new javax.swing.JButton();
        btnSpeichern = new javax.swing.JButton();

        btnHauptmenue.setText("Zum Hauptmenü");
        btnHauptmenue.addActionListener(main);
        btnHauptmenue.setActionCommand("Zum_Hauptmenue");

        btnSpeichern.setText("Speichern");
        btnSpeichern.addActionListener(main);
        btnSpeichern.setActionCommand("SpielMenue_Speichern");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSpeichern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHauptmenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHauptmenue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpeichern)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
