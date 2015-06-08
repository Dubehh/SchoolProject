package com.eelco.algoritme;

import java.util.List;

import com.eelco.mechanics.Coordinaat;

public abstract class TSPAlgorithmBase {
	/**
	 * @author Eelco
	 */
	public abstract List<Coordinaat> getFinalResult(double x, double y);
	
	/**
	 * Gets total distance of path
	 * @param x double
	 * @param y double
	 * @return double
	 */
	public double getTotalDistance(double x, double y){
		List<Coordinaat> coordinaten = getFinalResult(x, y);
		double distance = 0;
		for(int i=0; i < coordinaten.size(); i++){
			try{
				distance += coordinaten.get(i).distanceTo(coordinaten.get(i+1).getX(), coordinaten.get(i+1).getY());
			}catch(IndexOutOfBoundsException e){
				break;
			}
		}
		return round(distance, 2);
	}
	
	/**
	 * Rounds double to 2 decimals
	 * @param value original value
	 * @param places Amount of decimals 
	 * @return double obviously
	 */
	private double round(double value, int places) {
	    if (places < 0) return value;
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
