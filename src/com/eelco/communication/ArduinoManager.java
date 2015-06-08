package com.eelco.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eelco.core.Artikel;
import com.eelco.mechanics.Coordinaat;
import com.eelco.swing.Panel;
import com.eelco.swing.screen.Frame;

public class ArduinoManager {
	
	/**
	 * @author Eelco
	 */
	private static ArduinoManager instance = null;
	private Arduino arduino;
	private ArduinoController control;
	private ArduinoManager(){ 
		this.arduino = Arduino.getArduino();
		this.control = ArduinoController.getController();
	}
	
	public static ArduinoManager getManager(){
		if(instance == null) instance = new ArduinoManager();
		return instance;
	}
	
	/**
	 * Get arduino instance
	 * @return Arduino object
	 */
	public Arduino getArduino(){
		return this.arduino;
	}
	
	/**
	 * Send a command
	 * @param command ArduinoCommand
	 */
	public void cmd(ArduinoCommand command){
		arduino.sendData(command.getCommand());
	}
	
	/**
	 * Add commands here
	 */
	public void initiateCommands(){
		Panel p = Frame.getInstance().getPanel();
		List<Coordinaat> order = p.getCurrentAlgorithm().getFinalResult(p.getStartX(), p.getStartY());
		/* Loop door het grid */
		int startBin = 1;
		for(Coordinaat c : order){
			try{
				cmd(ArduinoCommand.PUSH_PACKAGE.timed(1));
				String[] commands = control.moveToCoordinaat(c, order.get(order.indexOf(c)+1));
				for(String s : commands){
					cmd(s);
				}
				Artikel a = Artikel.getFromCoordinaat(order.get(order.indexOf(c)+1));
				int bin = a.getBin() > 4 ? a.getBin()-4 : a.getBin();
				if(bin - startBin < 0) bin=+1;
				cmd(ArduinoCommand.BPP_LEFT.timed(Math.abs(bin)));
			}catch(Exception e){
				System.out.println(e.getMessage());
				exit();
			}
		}
	}
	
	/**
	 * Exit arduino connection
	 */
	public void exit(){
		getArduino().close();
	}
	
	/**
	 * Start proces
	 */
	public static final void start(){
		ArduinoManager man = ArduinoManager.getManager();
		man.initiateCommands();
		man.startRobot();
	}
	
	/**
	 * End proces
	 */
	public static final void end(){
		ArduinoManager man = ArduinoManager.getManager();
		man.exit();
	}
	
	/**
	 * Send a command
	 * @param command String
	 */
	public void cmd(String command){
		arduino.sendData(command);
	}
	
	/**
	 * Start robot 
	 */
	public void startRobot(){
		for(String s : control.getCommands()){
			cmd(s);
			System.out.println(s);
		}
	}
	
	public enum ArduinoCommand{
		
		/**
		 * Nieuwe commands toevoegen ONDERAAN! niet zomaar er tussen proppen
		 */
		
		/**
		 * Moves robot up
		 * @timed: Amount of movements up
		 */
		UP,
		/**
		 * Moves robot down
		 * @timed: Amount of movements down
		 */
		DOWN,
		/**
		 * Moves robot left
		 * @timed: Amount of movements left
		 */
		LEFT,
		/**
		 * Moves robot right
		 * @timed: Amount of movements right
		 */
		RIGHT, 
		/**
		 * Pushes package
		 * @timed: No purpose for adding time
		 */
		PUSH_PACKAGE,
		/**
		 * Moves BPP rotation to left
		 * @timed: Amount of rotations in multiply by 90 degrees
		 */
		BPP_LEFT,
		/**
		 * Moves BPP rotation to right
		 * @timed: Amount of rotations in multiply by 90 degrees
		 */
		BPP_RIGHT;
		private ArduinoCommand(){}
		
		public String toString(){ 
			List<ArduinoCommand> list = new ArrayList<>(Arrays.asList(ArduinoCommand.values()));
			return list.indexOf(this)+"";
		}
		public String getCommand(){ return this.toString().toLowerCase(); }
		/**
		 * Return a correct string with given 'i'
		 * @param i Either the time in ticks or an increment for a movement
		 * @return String
		 */
		public String timed(int i){ return this.getCommand()+":"+i;}
	}
	
}
