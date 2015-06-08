package com.eelco.mechanics;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eelco.core.Klant;

public class XMLData extends XMLFile{
	
	/**
	 * @author Eelco
	 */
	
	private String achterNaam, voorNaam, adres, postCode, plaats; /* Klanten gegevens */
	private List<Integer> artikelNummers;
	
	public XMLData(File file) throws IOException{
		super(file);
		initiateArtikelNummers();
		initiateKlantData();
	}
	
	/**
	 *  Zoek naar de klant data, en sla deze vervolgens op in cache.
	 */
	public void initiateKlantData(){
		NodeList list = super.getNodeList(NodeOption.KLANT);
		for(int i = 0; i < list.getLength(); i++){
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) list.item(i);
				this.voorNaam = getContent(e, "voornaam");
				this.achterNaam = getContent(e, "achternaam");
				this.adres = getContent(e, "adres");
				this.postCode = getContent(e, "postcode");
				this.plaats = getContent(e, "plaats");
			}
		}
	}
	
	/**
	 * Get order nummer
	 * @return int ordernummer
	 */
	public int getOrderNummer(){
		NodeList list = super.getNodeList(NodeOption.ORDERNR);
		return Integer.parseInt(((Element) list.item(0)).getTextContent());
	}
	
	/**
	 * Get date
	 * @return Date
	 */
	public Date getDatum(){
		String datum = ((Element) super.getNodeList(NodeOption.DATUM).item(0)).getTextContent();
		DateFormat df = new SimpleDateFormat(datum.replaceAll("-", "/"));
		try {
			return df.parse(datum);
		} catch (ParseException e){
			return new Date();
		}
	}
	
	/**
	 * Creates artikelnummers from XML
	 */
	public void initiateArtikelNummers(){
		artikelNummers = new ArrayList<Integer>();
		NodeList list = super.getNodeList(NodeOption.ARTIKELNR);
		for(int i = 0; i < list.getLength(); i++){
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) list.item(i);
				artikelNummers.add(Integer.parseInt(e.getTextContent()));
			}
		}
	}
	
	/**
	 * Geeft waarde terug uit element aan de hand van gegeven pad.
	 */
	private String getContent(Element e, String path){
		return e.getElementsByTagName(path).item(0).getTextContent();
	}
	
	/**
	 * Creates customer from XML
	 * @return Klant
	 */
	public Klant getKlant(){
		return new Klant(voorNaam, achterNaam, adres, postCode, plaats);
	}
	
	/**
	 * List with artikel nummers
	 * @return List<Integer> with all the artikelnummers
	 */
	public List<Integer> getArtikelNummers(){
		return artikelNummers;
	}	

}
