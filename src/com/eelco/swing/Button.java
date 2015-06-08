package com.eelco.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.eelco.communication.ArduinoManager;
import com.eelco.core.Order;
import com.eelco.swing.Alert.AlertType;
import com.eelco.swing.screen.Frame;
import com.eelco.swing.screen.OrderFrame;

public class Button extends JButton implements ActionListener{

	/**
	 *  @author Eelco
	 *  @author Lars Baalmans
	 */
	
	private static int posy = 45;
	private final int WIDTH = 250;
	private final int HEIGHT = 25;
	private final int GAP_SIZE = 5;
	private Frame frame;
	private ButtonType type;
	
	/**
	 * Hoofdmenu button constructor
	 * @param tekst
	 * @param type
	 */
	public Button(String tekst, ButtonType type){
		setText(tekst);
		this.type = type;
		setBounds(25, posy, WIDTH, HEIGHT);
		posy += HEIGHT+GAP_SIZE;
		addActionListener(this);
	}
	
	/**
	 * Afwijkende buttons, Hierbij kan je eigen X en Y positie meegeven.
	 * @param tekst
	 * @param type, ButtonType
	 * @param x, positie X
	 * @param y, positie Y
	 */
	public Button(String tekst, ButtonType type, int x, int y){
		setText(tekst);
		this.type = type;
		setBounds(x, y, WIDTH, HEIGHT);
		addActionListener(this);
	}
	
	/**
	 * Hoofdmenu button constructor met hoofdscherm.
	 * Hierdoor kan het hoofdscherm in buttons gesloten worden.
	 * @param tekst
	 * @param type
	 * @param hoofdscherm
	 */
	public Button(String tekst, ButtonType type, Frame frame){
		this(tekst, type);
		this.frame = frame;
	}
	
	/**
	 * Hoofdmenu button constructor met een optie voor niet active buttons.
	 * @param tekst
	 * @param type
	 * @param hoofdscherm
	 * @param greyed, True of false voor actief/passief
	 */
	public Button(String tekst, ButtonType type, Frame frame, boolean greyed){
		this(tekst, type);
		this.frame = frame;
		setEnabled(!greyed);
	}
	
	/**
	 * Huidige Y positie
	 * @return Huidige Y positie
	 */
	public static int getMinY(){
		return posy;
	}
	
	/**
	 * Verhoogt position Y met value.
	 * @param value, verhoging voor Y position.
	 */
	public static void setPosy(int value){
		posy += value;
	}
	
	/**
	 * Enumeration
	 * (Scheelt hoop code)
	 */
	public enum ButtonType{ PAKBON, QUIT, CLOSE, UPLOAD, ORDER, START_ROBOT, RUN, NEXTBIN; }
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() instanceof JButton){
			switch(type){
			case UPLOAD:
				int returnVal = frame.getFileChooser().showOpenDialog(frame);
				if(returnVal ==JFileChooser.APPROVE_OPTION){
					if(frame.setFile(frame.getFileChooser().getSelectedFile())) {
						frame.getLogger().log("Geselecteerde bestand: "+frame.getFileChooser().getSelectedFile().getName());
						frame.getStartButton().setEnabled(true);
						frame.getPakbonBtn().setEnabled(true);
						new Alert(AlertType.NOTIFICATION, "Kies rechts een algoritme om te tekenen!");
						try {
							Order order = Order.readOrder();
							frame.getLogger().log("Succesvol order gelezen uit "+order.getXMLData().getOriginalFile().getName());
						} catch (SQLException e) {
							new Alert(AlertType.ERROR, "Er kon geen Order gecreeërd worden, is de database connectie correct?");
						}
					}
				}
				break;
			case START_ROBOT:
				ArduinoManager.start();
				frame.getLogger().log("Started the robot");
				break;
			case PAKBON:
				frame.getLogger().log("Pakbon wordt opgehaald.");
				OrderFrame.getFrame(frame);
				break;
			case QUIT:
				System.exit(0); break;
			case CLOSE:
				break;
			case NEXTBIN:
				Frame.getInstance().getBppPanel().nextPage();
				break;
			default: break;
			}
		}
	}

}
