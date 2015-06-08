package com.eelco.swing.screen;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.eelco.mechanics.XMLData;
import com.eelco.swing.Alert;
import com.eelco.swing.Alert.AlertType;
import com.eelco.swing.BPP_Panel;
import com.eelco.swing.Button;
import com.eelco.swing.Button.ButtonType;
import com.eelco.swing.Panel;

public class Frame extends JFrame{
	
	/**
	 * @author Eelco - 2015
	 */
	private static Frame instance = null;
	private JFileChooser chooser; 
	private Log log; 
	private File file = null;
	private Panel panel;
	private BPP_Panel bppPanel;
	private ComboBox tspBox;
	private ComboBox bppBox;
	private Button startButton;
	private Button pakbonButton;
	private Button nextBin;
	private Log firstLog;
	private Log secondLog;
	private Log thirdLog;
	private JLabel tspDistance;
	private Log fourthLog;
	
	private static final String SCREEN_TITLE = "Magazijn Robot Simulator";
	private static final int WIDTH = 900;
	private static final int HEIGTH = 900;
	
	/**
	 *  We willen maar één Frame object. Vandaar de private constructor die alleen te benaderen is via
	 *  de static instance. (dit heet singleton)
	 */
	private Frame(){
		setTitle(SCREEN_TITLE);
		setSize(WIDTH, HEIGTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		add(label(30,15,250,25, "Menu"));
		add(new Button("UPLOAD", ButtonType.UPLOAD, this));
		add(new Button("QUIT", ButtonType.QUIT, this));
		add(startButton = new Button("START ROBOT", ButtonType.START_ROBOT, this, true));
		add(pakbonButton = new Button("PAKBON", ButtonType.PAKBON, this, true));
		Button.setPosy(50); /* Add space*/
		add(label(30, Button.getMinY(), 250 ,25, "Log"));
		Button.setPosy(20);/*Add space*/
		add(panel = new Panel(this));
		add(getScrollPane(log = new Log(this), 25, Button.getMinY()+10, 250, getHeight()-Button.getMinY()-550));
		add(tspBox = new ComboBox(this, 50 , "Selecteer Algoritme..","Clock", "Brute Force", "Nearest Neighbour"));
		add(bppBox = new ComboBox(this, 310 , "Selecteer Algoritme..","First Fit", "Best Fit", "First Fit Decreasing"));
		add(tspDistance = label(tspBox.getWidth()+panel.getWidth()+145, 90, 150, 25, "Distance: "+0));
		add(label(tspBox.getWidth()+panel.getWidth()+120, 15, 200, 25, "TSP Algoritme"));
		add(label(bppBox.getWidth()+panel.getWidth()+120, 278, 200, 25, "BPP Algoritme"));
		add(nextBin = new Button("NEXT 4 BINS", Button.ButtonType.NEXTBIN, this, true));
		nextBin.setBounds(bppBox.getWidth()+panel.getWidth()+115, 335, 200, 25);
		add(bppPanel = new BPP_Panel());
		bppPanel.setBounds(25, 360, 900, 315);
	    add(getScrollPane(firstLog = new Log(this), 25, 675, 202, 185));
	    add(getScrollPane(secondLog = new Log(this), 238, 675, 202, 185));
	    add(getScrollPane(thirdLog = new Log(this), 451, 675, 202, 185));
	    add(getScrollPane(fourthLog = new Log(this), 664, 675, 202, 185));
		setResizable(false);
		chooser = new JFileChooser();
		setVisible(true);
	}
	
	/**
	 * Instance of Frame
	 * @return Singleton object Frame
	 */
	public static Frame getInstance(){
		if(instance == null) instance = new Frame();
		return instance;
	}
	
	/**
	 * Instance of draw panel
	 * @return Panel object
	 */
	public Panel getPanel(){
		return panel;
	}
	
	/**
	 * Get scroll pane
	 * @param log Text log
	 * @param x int
	 * @param y int
	 * @param w int
	 * @param h int
	 * @return Jscrollpane
	 */
	private JScrollPane getScrollPane(Log log, int x, int y, int w, int h){
		JScrollPane pane = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBounds(x, y, w, h);
		return pane;
	}
	/**
	 * Instanceof BPP draw panel
	 * @return BPP_Panel object
	 */
	public BPP_Panel getBppPanel(){
		return bppPanel;
	}
	
	/**
	 * Get current log BPP is using
	 * @param log Id
	 * @return Log object
	 */
    public Log getCurrentLog(int log){
    	switch(log){
    	case 1: return firstLog;
    	case 2: return secondLog;
    	case 3: return thirdLog;
    	case 4: return fourthLog;
    	default: return null;
    	}
    }

	
	/**
	 * Geeft buttons terug
	 * @return Button object
	 */
	public Button getStartButton(){ return startButton; }
	public Button getNextBinBtn(){ return nextBin; }
	public Button getPakbonBtn(){ return pakbonButton; }
	
	public JLabel getTSPInfoLabel(){
		return tspDistance;
	}
	
	/**
	 * Saves file into cache
	 * @return false if file is not an XML file.
	 */
	public boolean setFile(File f){
		if(f.getAbsoluteFile().getName().endsWith(".xml")){
			this.file = f;
			return true;
		}
		new Alert(AlertType.ERROR, "Incorrect XML bestand");
		return false;
	}
	
	/**
	 * @return Log object from components
	 */
	public Log getLogger(){
		return log;
	}
	
	/**
	 * @return whether a file has been saved to cache or not
	 */
	public boolean hasFileSelected(){
		return file != null;
	}
	
	/**
	 * @return Object of XMLData
	 * @throws IOException if file cannot be found/wrongly formatted
	 * @throws NullPointerException if the file is null / equal to null
	 */
	public XMLData getXMLData(){
		try{
			return new XMLData(file);
		}catch(IOException  | NullPointerException e){
			new Alert(AlertType.ERROR, "Geen bestand geselecteerd");
			return null;
		}
	}
	
	/**
	 * @return Filechooser object of swing (obviously lol)
	 */
	public JFileChooser getFileChooser(){
		return chooser;
	}
	
	/**
	 * Maakt een JLabel
	 * @param x, y, width, hoogte, text
	 */
	private JLabel label(int x, int y, int w, int h, String txt){
		JLabel lbl = new JLabel(txt, SwingConstants.CENTER);
		int fontSize = 20;
		lbl.setOpaque(true);
		lbl.setBackground(Color.WHITE);
		lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
		lbl.setBounds(x-(fontSize-14), y, w, h);
		lbl.setFont(new Font(lbl.getFont().getFontName(), Font.BOLD, fontSize));
		return lbl;
	}
}
