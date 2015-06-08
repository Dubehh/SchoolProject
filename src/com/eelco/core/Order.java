package com.eelco.core;

import java.sql.SQLException;

import com.eelco.mechanics.Coordinaat;
import com.eelco.mechanics.Database;
import com.eelco.mechanics.XMLData;
import com.eelco.swing.screen.Frame;

public class Order {
	
	/**
	 * @author Eelco - 2015
	 */
	private XMLData data;
	private Artikel[] artikelen;
	private Klant klant;
	private static Order order = null;
	
	private Order(XMLData data, Klant klant, Artikel... artikelen){
		this.data = data;
		this.klant = klant;
		this.artikelen = artikelen;
	}
	
	/**
	 * Automatically creates an XMLData object out of the file
	 * @return XMLData object
	 */
	public XMLData getXMLData(){
		return data;
	}
	
	/**
	 * Get the customer assigned to the order
	 * @return Klant object
	 */
	public Klant getKlant(){
		return klant;
	}
	
	/**
	 * Get all artikels regarding this order
	 * @return Array with the artikelen
	 */
	public Artikel[] getArtikelen(){
		return artikelen;
	}
	
	public static Order getCurrentOrder(){
		return order;
	}
	
	/**
	 * Create new order object out of database / XML data
	 * @return Order object
	 * @throws SQLException If the database is incorrect or throws an error
	 */
	public static final Order readOrder() throws SQLException{
		XMLData data = Frame.getInstance().getXMLData();
		Database base = Database.getInstance();
		if(data!=null){
			Klant klant = data.getKlant();
			Artikel[] artikels = new Artikel[data.getArtikelNummers().size()];
			for(int i : data.getArtikelNummers()){
				String condition = "`artikelID` = "+i+"";
				String naam = base.getResult("artikel_naam", "`artikel`", condition).toString();
				int x = Integer.parseInt(base.getResult("x_coordinaat", "`positie`", condition).toString());
				int y = Integer.parseInt(base.getResult("y_coordinaat", "`positie`", condition).toString());
				int volume = Integer.parseInt(base.getResult("volume", "`artikel`", condition).toString());
				artikels[i-1] = new Artikel(naam, new Coordinaat(x, y), volume);
			}
			return order = new Order(data, klant, artikels);
		}
		return null;
	}
}
