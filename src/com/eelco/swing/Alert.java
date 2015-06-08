package com.eelco.swing;

import javax.swing.JOptionPane;

import com.eelco.swing.screen.Frame;

public class Alert{
	
	/**
	 * @author Eelco - 2015, Murat Aktasli
	 */
	
	/**
	 * Creates a new Alert with given alert type
	 * @param type Enumeration of an alert type with given icon
	 * @param msg Message you want to send with it
	 */
	public Alert(AlertType type, String msg){
		JOptionPane.showMessageDialog(Frame.getInstance(), msg, null, type.getType());
		
	}
	
	/**
	 * Enums from alertType
	 * @author Eelco Eikelboom - 2015
	 */
	public enum AlertType{
		ERROR(JOptionPane.ERROR_MESSAGE), WARNING(JOptionPane.WARNING_MESSAGE), NOTIFICATION(JOptionPane.INFORMATION_MESSAGE);
		
		private int type;
		private AlertType(int type){
			this.type = type;
		}
		
		/**
		 * @return original file name of enum constant
		 */
		public int getType(){
			return type;
		}
	}

}
