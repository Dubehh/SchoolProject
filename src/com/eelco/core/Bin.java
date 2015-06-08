package com.eelco.core;

import java.util.ArrayList;
import java.util.List;

public class Bin {
	
	/**
	 * @author Lars Baalmans, Darryn van Barneveld
	 */
	
	private final int binSize = 8;
	private List<Artikel> items;
	
	public Bin(){
        this.items = new ArrayList<>();
	}
	
	public Bin(Artikel artikel){
		this();
        items.add(artikel);
	}
	
	public void addItem(Artikel artikel){
		items.add(artikel);
	}
	
	public boolean fits(Artikel artikel)
	{
		int count = 0;
		for(Artikel a : items)
		{
			count += a.getVolume();
		}
		
		if(artikel.getVolume() <= (binSize - count))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void fitAndAdd(Artikel artikel)
	{
		if(this.fits(artikel))
		{
			this.addItem(artikel);
		}
	}
	
	public int getSpaceLeft()
	{
		int count = 0;
		for(Artikel a : items)
		{
			count += a.getVolume();
		}
		return (binSize - count); 
	}
	
	public List<Artikel> getItems(){
		return items;
	}
}