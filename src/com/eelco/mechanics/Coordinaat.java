package com.eelco.mechanics;

import java.util.ArrayList;

public class Coordinaat{
	
	/**
	 * @author Eelco - 2015
	 */
	private double x, y;
	private static ArrayList<Coordinaat> coordinaten = null;
	public Coordinaat(double x, double y){
		this.x = x;
		this.y = y;
		if(coordinaten == null) coordinaten = new ArrayList<Coordinaat>(); 
		coordinaten.add(this);
	}
	
	/**
	 * X Coordinaat
	 * @return Double x
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * y coordinaat
	 * @return double y
	 */
	public double getY(){
		return y;
	}
	
	/* Creates coordinaat if it doesn't exist, else
	 * it uses existing coordinaat */
	public static Coordinaat fromData(double x, double y){
		if(getCoordinaten()==null) return new Coordinaat(x,y);
		for(Coordinaat c : getCoordinaten()){
			if(c.getX() == x && c.getY() == y) return c;
		}
		return new Coordinaat(x, y);
	}
	
	/**
	 * Checks if two coordinates are the same
	 * @param c1 Coordinaat
	 * @param c2 Coordinaat
	 * @return true if they're equal
	 */
	public static boolean isSame(Coordinaat c1, Coordinaat c2){
		return c1.getX() == c2.getX() && c1.getY() == c2.getY();
	}
	
	/**
	 * List filled with coordinates
	 * @return ArrayList containing coordinates
	 */
	public static ArrayList<Coordinaat> getCoordinaten(){
		return coordinaten;
	}
	
	/**
	 * Calculates distance to coordinate
	 * @param x double x
	 * @param y double y
	 * @return double with the distance
	 */
	public double distanceTo(double x, double y){
		double _x = x-getX();
		double _y = y-getY();
		return Math.sqrt((_x*_x) + (_y*_y));
	}
	
	/**
	 * Gets coordinaat in (xx,yy) form
	 */
	public String toString(){
		return "("+getX()+", "+getY()+")";
	}
	
	/**
	 * Calculates angle (polair) to coordinaat
	 * @param x0 Coordinaat X (1)
	 * @param y0 Coordinaat Y (1)
	 * @param x1 Coordinaat X (2)
	 * @param y1 Coordinaat Y(2)
	 * @return double with the angle value
	 */
	public static final double angleToX(double x0, double y0, double x1, double y1){
		double dx = x1 - x0;
	    double dy = y1 - y0;
	    return Math.atan2(dy, dx); 
	}
	
}
