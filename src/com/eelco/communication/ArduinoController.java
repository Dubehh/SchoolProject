package com.eelco.communication;

import static com.eelco.communication.ArduinoManager.ArduinoCommand.*;

import java.util.ArrayList;
import java.util.List;

import com.eelco.communication.ArduinoManager.ArduinoCommand;
import com.eelco.mechanics.Coordinaat;

public class ArduinoController {
	
	/**
	 * @author Eelco
	 * @author Murat Aktasli
	 */
	private static ArduinoController controller = null;
	private ArrayList<String> commands;
	private ArduinoController(){
		commands = new ArrayList<>();
	}
	
	/**
	 * Retrieve single ton instance
	 * @return ArduinoController object (swag)
	 */
	public static final ArduinoController getController(){
		return controller != null ? controller : (controller = new ArduinoController());
	}
	
	/**
	 * Generate arduino command array
	 * @param from Coordinaat
	 * @param to Coordinaat
	 * @return Array containing every command
	 */
	public String[] moveToCoordinaat(Coordinaat from, Coordinaat to){
		List<String> cmd = new ArrayList<>();
		double xDistance = from.getX()-to.getX(); 
		double yDistance = from.getY()-to.getY();
		ArduinoCommand directionX = xDistance > 0 ? LEFT : RIGHT;
		ArduinoCommand directionY = yDistance > 0 ? DOWN : UP;
		cmd.add(directionX.timed((int) Math.abs(xDistance)));
		cmd.add(directionY.timed((int) Math.abs(yDistance)));
		return cmd.toArray(new String[cmd.size()]);
	}
	
	/**
	 * Add a single command to Arduino
	 * @param cmd String
	 */
	public void addCommand(ArduinoCommand cmd){
		commands.add(cmd.getCommand());
	}
	
	/**
	 * Add a command
	 * @param cmd String
	 */
	public void addCommand(String cmd){
		commands.add(cmd);
	}
	
	/**
	 * Get all commands in list form
	 * @return All commands
	 */
	public ArrayList<String> getCommands(){
		return commands;
	}
}
