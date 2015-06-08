package com.eelco.core;

public class Klant {
	
	/**
	 * @author Eelco
	 */
	private String naam, achternaam, adres, postcode, plaats;
	public Klant(String naam, String achternaam, String adres, String postcode, String plaats){
		this.naam = naam;
		this.achternaam = achternaam;
		this.adres = adres;
		this.postcode = postcode;
		this.plaats = plaats;
	}
	
	public String getNaam(){ return naam; }
	public String getAchternaam(){ return achternaam; }
	public String getAdres(){ return adres;}
	public String getPostcode(){ return postcode; }
	public String getWoonplaats(){ return plaats; }
	
	public int getId(){
		return -1; //TODO database connectie
	}
	public static final boolean isKlant(){
		//TODO kijk in database of het een klant is
		return false;
	}
	
}
