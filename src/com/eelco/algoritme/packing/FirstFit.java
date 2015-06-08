package com.eelco.algoritme.packing;

import java.util.ArrayList;
import java.util.Arrays;

import com.eelco.core.Artikel;
import com.eelco.core.Bin;
import com.eelco.core.Order;

public class FirstFit extends BPPAlgorithmBase 
{
	
	/**
	 * @author Lars Baalmans
	 * @author Darryn van Barneveld
	 * @author Jorrit Overweg
	 */
	private static ArrayList<Artikel> artikelLijst;
	private static ArrayList<Bin> bins;
	
	public FirstFit()
	{
		if (Order.getCurrentOrder() != null){
			artikelLijst = new ArrayList<>(Arrays.asList(Order.getCurrentOrder().getArtikelen()));
			bins =  new ArrayList<Bin>();
			
			//Loop to assign a product to a bin
			for(Artikel a : artikelLijst)
			{
				boolean added = false;
				
				for(Bin b : bins)
				{
					if(b.fits(a))
					{
						b.addItem(a);
						added = true;
						break;
					}
				}
				
				//If the product has not been added, it will create a new bin for the product
				if(!added)
				{
					bins.add(new Bin(a));
				}
			}
			
			//Go through each bin
			for(int x=0; x < bins.size(); x++)
			{
				//Go through each product in a bin
				for(Artikel a : bins.get(x).getItems())
				{
					//Find the product in the productList that matches the product in a certain bin...
					for(Artikel artikel : artikelLijst)
					{
						if(a.equals(artikel))
						{
							//...and give it the correct bin number
							artikel.setBin(x-1);
							break;
						}
					}
				}
			}
		}
	}
	
	public ArrayList<Bin> getBins(){
		return bins;
	}
	public ArrayList<Artikel> getProducts()
	{
		return artikelLijst;
	}
}