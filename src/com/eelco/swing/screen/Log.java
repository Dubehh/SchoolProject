package com.eelco.swing.screen;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class Log extends JTextArea{

	/**
	 * @author Eelco - 2015, Lars Baalmans
	 */
	
	private final int MAX_CHAR_LENGTH = 40;
	
	/**
	 * Gives a JTextArea component working as a log component 
	 * @param frame Parent frame instantion
	 */
	public Log(Frame frame){
		setEditable(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Adds a line of text to the Log component
	 * @param msg String message you want to send
	 */
	public void log(String msg){
		String[] message = msg.split(" ");
		int letterCount = 0;
		append("- ");
		for(String msg_ : message){
			letterCount+= msg_.length()+1;
			if((letterCount-1) > MAX_CHAR_LENGTH){
				append("\n   ");
				letterCount = 0;
			}
			append(msg_+" ");
		}
		append("\n");
	}
	
}
