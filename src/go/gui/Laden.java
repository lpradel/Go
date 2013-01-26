package go.gui;

import go.Controller;
import go.LadenException;
import go.Spielstand;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.*;
import java.io.FilenameFilter;
import javax.swing.JFileChooser;
import java.io.FileWriter;
import java.io.FileReader;

import java.lang.String;


public class Laden extends javax.swing.JPanel implements java.awt.event.ActionListener, ListSelectionListener, FilenameFilter{
	
	private javax.swing.JButton btnHauptmenue;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLaden;
    private javax.swing.JButton btnLoeschen;
    private javax.swing.JLabel lblTitel;
    private javax.swing.JList listSpielstaende;
    private javax.swing.JScrollPane splSpielstaende;
    private JFileChooser fileDialog;
    private File spielstandordner = new File(Controller.SPEICHER_ORDNER);
    
    private DefaultListModel model = new DefaultListModel();
    
    private class Spieldatei
    {
    	public Spielstand spiel;
    	public String dateipfad;
    	
    	public Spieldatei(Spielstand spiel, String dateipfad)
    	{
    		this.spiel = spiel;
    		this.dateipfad = dateipfad;
    	}
    	
    	public String toString()
    	{
    		return spiel.toString();
    	}
    }
    
	public Laden(Main main) {
        initComponents(main);
    }

    private void initComponents(Main main) {

        splSpielstaende = new javax.swing.JScrollPane();
        listSpielstaende = new javax.swing.JList();
        btnLaden = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        lblTitel = new javax.swing.JLabel();
        btnHauptmenue = new javax.swing.JButton();
        btnLoeschen = new javax.swing.JButton();
        fileDialog = new javax.swing.JFileChooser(spielstandordner);

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("Form"); // NOI18N
        setRequestFocusEnabled(false);

        splSpielstaende.setMaximumSize(new java.awt.Dimension(100, 270));
        splSpielstaende.setMinimumSize(new java.awt.Dimension(100, 270));
        splSpielstaende.setName("splSpielstaende"); // NOI18N
        splSpielstaende.setPreferredSize(new java.awt.Dimension(100, 270));
        
        listeLaden();
        
        listSpielstaende.setModel(model);
        listSpielstaende.setMaximumSize(new java.awt.Dimension(100, 270));
        listSpielstaende.setMinimumSize(new java.awt.Dimension(100, 270));
        listSpielstaende.setName("listSpielstaende"); // NOI18N
        listSpielstaende.setPreferredSize(new java.awt.Dimension(100, 270));
        listSpielstaende.addListSelectionListener(this); 
        
        splSpielstaende.setViewportView(listSpielstaende);

        btnLaden.setText("Laden");
        btnLaden.setMaximumSize(new java.awt.Dimension(300, 30));
        btnLaden.setMinimumSize(new java.awt.Dimension(300, 30));
        btnLaden.setName("btnLaden"); // NOI18N
        btnLaden.setPreferredSize(new java.awt.Dimension(300, 30));
        btnLaden.addActionListener(main);
        btnLaden.setActionCommand("Laden_Laden");
        btnLaden.setEnabled(false);
        
        btnImport.setText("Externen Spielstand importieren");
        btnImport.setMaximumSize(new java.awt.Dimension(300, 30));
        btnImport.setMinimumSize(new java.awt.Dimension(300, 30));
        btnImport.setName("btnImport"); // NOI18N
        btnImport.setPreferredSize(new java.awt.Dimension(300, 30));
        btnImport.addActionListener(this);
        btnImport.setActionCommand("Importieren"); //für lokale Liste von Name zu Dateipfad

        lblTitel.setName("lblTitel"); // NOI18N
        lblTitel.setFont(new java.awt.Font(this.getFont().getFontName(),0,25));
        lblTitel.setText("Spielstand laden");
        
        btnHauptmenue.setText("Zum Hauptmenü"); // NOI18N
        btnHauptmenue.setMaximumSize(new java.awt.Dimension(300, 30));
        btnHauptmenue.setMinimumSize(new java.awt.Dimension(300, 30));
        btnHauptmenue.setName("btnHauptmenue"); // NOI18N
        btnHauptmenue.setPreferredSize(new java.awt.Dimension(300, 30));
        btnHauptmenue.addActionListener(main);
        btnHauptmenue.setActionCommand("Zum_Hauptmenue");
        
		
        btnLoeschen.setText("Spielstand löschen");
        btnLoeschen.setMaximumSize(new java.awt.Dimension(300, 30));
        btnLoeschen.setMinimumSize(new java.awt.Dimension(300, 30));
        btnLoeschen.setName("btnLoeschen"); // NOI18N
        btnLoeschen.setPreferredSize(new java.awt.Dimension(300, 30));
        btnLoeschen.addActionListener(this);  
        btnLoeschen.setActionCommand("Loeschen");
        btnLoeschen.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitel)
                    .addComponent(btnLaden, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHauptmenue, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(287, 287, 287)
                        .addComponent(btnLoeschen, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(splSpielstaende, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblTitel)
                .addGap(18, 18, 18)
                .addComponent(splSpielstaende, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLaden, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHauptmenue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoeschen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>

	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Importieren"))
		{
			int ergebnis = fileDialog.showOpenDialog(this);
			Spielstand s = null;
			if(ergebnis == JFileChooser.APPROVE_OPTION)
			{
				File gewaehlt = fileDialog.getSelectedFile();
				String gewaehltPfad = gewaehlt.getAbsolutePath();
				String gewaehltVerboten = System.getProperty("user.dir") + Controller.SPEICHER_ORDNER.substring(1) + gewaehlt.getName();
				if(!gewaehltPfad.equals(gewaehltVerboten))
				{
					
				try
				{
					s = Spielstand.ladeSpielstand(gewaehlt.getAbsolutePath());
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(this, "Spielstand ist defekt!", "Spielstand defekt!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try
				{
					FileReader leser = new FileReader(gewaehlt);
					int zaehler = 1;
					String zusatz = "1";
					String name = gewaehlt.getName().substring(0,gewaehlt.getName().lastIndexOf("."));
					if((new File(Controller.SPEICHER_ORDNER + gewaehlt.getName())).exists())
					{
						while((new File(Controller.SPEICHER_ORDNER + name + zusatz + ".xml")).exists())
						{
							zaehler++;
							zusatz = String.valueOf(zaehler);
						}
					}
					else
					{
						zusatz = "";
					}
					File outputFile = new File(Controller.SPEICHER_ORDNER + name + zusatz + ".xml");
					FileWriter schreiber = new FileWriter(outputFile);
					int c;
					while((c = leser.read()) != -1)
					{
						schreiber.write(c);
					}
					schreiber.close();
					leser.close();
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(this, "Spielstand ist defekt!", "Spielstand defekt!", JOptionPane.ERROR_MESSAGE);
				}
				try {
	        		s = Spielstand.ladeSpielstand(gewaehlt.getAbsolutePath());
	        		Spieldatei dat = new Spieldatei(s,gewaehlt.getAbsolutePath());
	        		model.addElement(dat);
	        	} catch (Exception exce) {
	        		JOptionPane.showMessageDialog(this, "Spielstand ist defekt!", "Spielstand defekt!", JOptionPane.ERROR_MESSAGE);
				}
	        	listSpielstaende.setModel(model);
	        	btnLaden.setEnabled(false);
	        	btnLoeschen.setEnabled(false);
				}
			}
			else if(ergebnis == JFileChooser.ERROR_OPTION)
			{
				
			}
		}
		else if (e.getActionCommand().equals("Loeschen"))
		{
			int auswahl = JOptionPane.showConfirmDialog(this,
					"Wollen Sie diesen Spielstand wirklich löschen?",
					"Löschen bestätigen", JOptionPane.YES_NO_OPTION);
			if (auswahl == 0) 
			{
				Spieldatei element = (Spieldatei) listSpielstaende.getSelectedValue();
				File datei = new File(element.dateipfad);
				datei.delete();
				model.removeElement(element);
				listSpielstaende.setModel(model);
				btnLaden.setEnabled(false);
				btnLoeschen.setEnabled(false);
			}
		}
	}
	
	public Spielstand gibSpielstand(){
		return ((Spieldatei)listSpielstaende.getSelectedValue()).spiel;
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		btnLaden.setEnabled(true);
		btnLoeschen.setEnabled(true);
	}
	
	public boolean accept(File dir, String name)
	{
		int position = name.lastIndexOf(".");
		String extension =  name.substring(position + 1);
		return extension.equals("xml");
	}
	
	private void listeLaden()
	{
		if(!spielstandordner.exists())
		{
			try
			{
				spielstandordner.createNewFile();
			}
			catch (Exception e) {}
		}
		File[] spielstanddateien = spielstandordner.listFiles(this);
        model = new DefaultListModel();
        List<String> fehlerListe = new ArrayList<String>();
        List<String> fehlerhafteDateien = new ArrayList<String>();
        for(File f:spielstanddateien){
        	try {
        		Spielstand s = Spielstand.ladeSpielstand(f.getAbsolutePath());
        		Spieldatei dat = new Spieldatei(s,f.getAbsolutePath());
        		model.addElement(dat);
        	} catch (LadenException e) {
				fehlerListe.add("Spielstand konnte nicht geladen werden: " + f.getName());
				fehlerhafteDateien.add(f.getAbsolutePath());
			}
        }
        if (!fehlerListe.isEmpty()) {
        	String message = "";
        	for (String f : fehlerListe) {
        		message += f + "\n";
        	}
        	message += "Wollen sie diese Dateien loeschen?";
        	int antwort = JOptionPane.showConfirmDialog(this, message, "Spielstände konnten nicht geladen werden!", JOptionPane.YES_NO_OPTION);
        	if(antwort == 0)
        	{
        		for(String pfad : fehlerhafteDateien)
        		{
    				File datei = new File(pfad);
    				datei.delete();
        		}
        	}
        }
	}
	

}


