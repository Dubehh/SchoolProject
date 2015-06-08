package com.eelco.algoritme.packing;

import java.util.ArrayList;
import java.util.Arrays;

import com.eelco.core.Artikel;
import com.eelco.core.Bin;
import com.eelco.core.Order;

public class BestFit extends BPPAlgorithmBase 
{
	/**
	 * @author Lars Baalmans
	 * @author Darryn van Barneveld
	 */
	private static ArrayList<Artikel> artikelLijst;
	private static ArrayList<Bin> bins;
	
	public BestFit()
	{
		if (Order.getCurrentOrder() != null){
			artikelLijst = new ArrayList<>(Arrays.asList(Order.getCurrentOrder().getArtikelen()));
			bins =  new ArrayList<Bin>();
			int bestSpaceLeft;
			int currentSpaceLeft;
			
			bins.add(new Bin());
			
			//Loop to assign a product to a bin
			for(Artikel a : artikelLijst)
			{
				bestSpaceLeft = 0;
				Bin bestBin = new Bin();
				//Go through all the bins
				for(Bin b : bins)
				{
					currentSpaceLeft = b.getSpaceLeft();
					if (currentSpaceLeft > bestSpaceLeft){
						if (currentSpaceLeft >= a.getVolume()){
							bestBin = b;
							bestSpaceLeft = currentSpaceLeft;
						}
					} else if (currentSpaceLeft == bestSpaceLeft){
						if (currentSpaceLeft >= a.getVolume()){
							bestSpaceLeft = currentSpaceLeft;
						}
					}
				}
				
				if(bins.contains(bestBin))
				{	
					bins.get(bins.indexOf(bestBin)).addItem(a);
				}
				//If not, create a new bin
				else
				{
					bins.add(new Bin(a));
				}
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

	public ArrayList<Bin> getBins(){
		return bins;
	}
	
	public ArrayList<Artikel> getProducts()
	{
		return artikelLijst;
	}
}