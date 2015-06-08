package com.eelco.core;

import com.eelco.mechanics.Coordinaat;
import com.eelco.mechanics.Database;


public class Artikel{
	
	/**
	 * @author  Eelco, Darryn van Barneveld
	 */
	private String desc;
	private Coordinaat coordinaat;
	private int volume;
	private int binNumber;
	
	/**
	 * Create new artikel
	 * @param desc Naam / Description
	 * @param coordinaat Coordinaat / Coordinate / Placement
	 * @param volume size of the package / grootte
	 */
	public Artikel(String desc, Coordinaat coordinaat, int volume){
		this.coordinaat = coordinaat;
		this.desc = desc;
		this.volume = volume;
	}
	
	/**
	 * @return Name / description of the package
	 */
	public String getDesc(){
		return desc;
	}
	
	/**
	 * Get the coordinate object / coordinaat terugkrijgen van artikel
	 * @return Coordinaat / coordinaat
	 */
	public Coordinaat getCoordinaat(){
		return coordinaat;
	}
	
	/**
	 * @return size/volume of the package
	 */
	public int getVolume(){
		return volume;
	}
	
	/**
	 * Gets bin number
	 * @return int
	 */
	public int getBin()
	{ return binNumber+2; }
	
	
	/**
	 * Sets binnumber
	 * @param binNumber int
	 */
	public void setBin(int binNumber)
	{ this.binNumber = binNumber; }
	
	/**
	 * Get Artikel from coordinaat
	 * @param c Coordinaat
	 * @return Artikel
	 */
	public static final Artikel getFromCoordinaat(Coordinaat c){
		int x = (int) c.getX();
		int y = (int) c.getY();
		Database base = Database.getInstance();
		int artikelID =  Integer.parseInt(base.getResult("artikelID", "`positie`", "`x_coordinaat` = "+x+" AND `y_coordinaat` = "+y).toString());
		String desc = base.getResult("artikel_naam", "`artikel`", "`artikelID` = "+artikelID).toString();
		int volume = Integer.parseInt(base.getResult("volume", "`artikel`", "`artikelID` = "+artikelID).toString());
		return new Artikel(desc, c, volume);
	}
}
