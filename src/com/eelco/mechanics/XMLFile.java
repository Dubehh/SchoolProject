package com.eelco.mechanics;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLFile{
	
	/**
	 * @author Eelco - 2015
	 */
	
	private File file;
	private Document doc = null;
	
	/**
	 * Create new XMLFile
	 * @param file File
	 * @throws IOException if file couldn't be found
	 */
	public XMLFile(File file) throws IOException{
		this.file = file;
	}
	
	/**
	 * Returns original File object
	 * @return File
	 */
	public File getOriginalFile(){
		return file;
	}
	
	/**
	 * Creates document object out of file
	 * @return Document
	 */
	public Document getDocument(){
		if(doc==null){
			initiateFile();
		}
		return doc;
	}
	
	/**
	 * Uses docmentbuilder to parse document from File
	 */
	 public void initiateFile(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			this.doc = builder.parse(file);
			this.doc.getDocumentElement().normalize();
		} catch (Exception e) {
			throw new IllegalArgumentException("Het bestand kon niet correct worden omgezet naar XML.");
		}
	}
	
	 /**
	  * Returns nodelist from given nodeoption
	  * @param option NodeOption constant
	  * @return Nodelist
	  */
	public NodeList getNodeList(NodeOption option){
		return getDocument().getElementsByTagName(option.toString());
	}
	
	public enum NodeOption{
		KLANT("klant"),
		ARTIKELNR("artikelnummer"),
		ORDERNR("ordernummer"),
		DATUM("datum");
		
		private String tagName;
		private NodeOption(String tagName){
			this.tagName = tagName;
		}
		
		/**
		 * Returns tag name
		 */
		public String toString(){
			return tagName;
		}
	}
}
