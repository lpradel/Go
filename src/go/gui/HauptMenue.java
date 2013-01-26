package go.gui;

@SuppressWarnings("serial")
public class HauptMenue extends javax.swing.JPanel {

    private javax.swing.JButton btnBeenden;
    private javax.swing.JButton btnNeuesSpiel;
    private javax.swing.JButton btnSpielLaden;
    private javax.swing.JButton btnSpielregeln;
    private javax.swing.JButton btnStatistiken;
    private javax.swing.JLabel lblGo;
    private javax.swing.JPanel panel;
    
    /** Creates new form Hauptmenue */
    public HauptMenue(Main main) {
        initComponents(main);
    }

    private void initComponents(Main main) {
    	this.panel = new javax.swing.JPanel();
        this.panel.setBounds(0,50,160,580);
        lblGo = new javax.swing.JLabel();
        btnBeenden = new javax.swing.JButton();
        btnSpielregeln = new javax.swing.JButton();
        btnStatistiken = new javax.swing.JButton();
        btnSpielLaden = new javax.swing.JButton();
        btnNeuesSpiel = new javax.swing.JButton();

        lblGo.setIcon(new javax.swing.ImageIcon("img/go.png"));
        lblGo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblGo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnBeenden.setText("Beenden");
        btnBeenden.setAlignmentX(0.5f);
        btnBeenden.setAlignmentY(0.5f);
        btnBeenden.setMaximumSize(new java.awt.Dimension(1200, 600));
        btnBeenden.setMinimumSize(new java.awt.Dimension(150, 50));
        btnBeenden.setPreferredSize(new java.awt.Dimension(300, 65));
        btnBeenden.setPressedIcon(null);
        btnBeenden.addActionListener(main);
        btnBeenden.setActionCommand("HauptMenue_Beenden");

        btnSpielregeln.setText("Spielregeln");
        btnSpielregeln.setAlignmentX(0.5f);
        btnSpielregeln.setAlignmentY(0.5f);
        btnSpielregeln.setMaximumSize(new java.awt.Dimension(1200, 600));
        btnSpielregeln.setMinimumSize(new java.awt.Dimension(150, 50));
        btnSpielregeln.setPreferredSize(new java.awt.Dimension(300, 65));
        btnSpielregeln.setPressedIcon(null);
        btnSpielregeln.addActionListener(main);
        btnSpielregeln.setActionCommand("HauptMenue_Spielregeln");

        btnStatistiken.setText("Statistiken");
        btnStatistiken.setAlignmentX(0.5f);
        btnStatistiken.setAlignmentY(0.5f);
        btnStatistiken.setMaximumSize(new java.awt.Dimension(1200, 600));
        btnStatistiken.setMinimumSize(new java.awt.Dimension(150, 50));
        btnStatistiken.setPreferredSize(new java.awt.Dimension(300, 65));
        btnStatistiken.setPressedIcon(null);
        btnStatistiken.addActionListener(main);
        btnStatistiken.setActionCommand("HauptMenue_Statistiken");

        btnSpielLaden.setText("Spiel Laden");
        btnSpielLaden.setAlignmentX(0.5f);
        btnSpielLaden.setAlignmentY(0.5f);
        btnSpielLaden.setMaximumSize(new java.awt.Dimension(1200, 600));
        btnSpielLaden.setMinimumSize(new java.awt.Dimension(150, 50));
        btnSpielLaden.setPreferredSize(new java.awt.Dimension(300, 65));
        btnSpielLaden.setPressedIcon(null);
        btnSpielLaden.addActionListener(main);
        btnSpielLaden.setActionCommand("HauptMenue_SpielLaden");

        btnNeuesSpiel.setText("Neues Spiel");
        btnNeuesSpiel.setAlignmentX(0.5f);
        btnNeuesSpiel.setAlignmentY(0.5f);
        btnNeuesSpiel.setFocusable(false);
        btnNeuesSpiel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNeuesSpiel.setMaximumSize(new java.awt.Dimension(1200, 600));
        btnNeuesSpiel.setMinimumSize(new java.awt.Dimension(150, 50));
        btnNeuesSpiel.setPreferredSize(new java.awt.Dimension(300, 65));
        btnNeuesSpiel.setPressedIcon(null);
        btnNeuesSpiel.addActionListener(main);
        btnNeuesSpiel.setActionCommand("HauptMenue_NeuesSpiel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.panel);
        this.panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGo, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnNeuesSpiel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnSpielLaden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnSpielregeln, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnStatistiken, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnBeenden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNeuesSpiel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpielLaden, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpielregeln, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStatistiken, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBeenden, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );
        this.add(panel);
    }
}
