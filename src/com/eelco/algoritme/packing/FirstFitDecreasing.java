package com.eelco.algoritme.packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.SwingUtilities;

import com.eelco.core.Artikel;
import com.eelco.core.Bin;
import com.eelco.core.Order;
import com.eelco.swing.screen.Frame;

public class FirstFitDecreasing extends BPPAlgorithmBase 
{
	
	/**
	 * @author Lars Baalmans
	 * @author Darryn van Barneveld
	 */
	private static ArrayList<Artikel> artikelLijst;
	private static ArrayList<Bin> bins;
	
	public FirstFitDecreasing()
	{
		if (Order.getCurrentOrder() != null){
			artikelLijst = new ArrayList<>(Arrays.asList(Order.getCurrentOrder().getArtikelen()));
			Collections.sort(artikelLijst, orderByVolume());
			
			bins =  new ArrayList<Bin>();
			
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
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run(){
						//TODO: make a timer thats refreshes the panel every second.
						Frame.getInstance().getPanel().repaint();
					}
				});
				
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
	
	private final static Comparator<Artikel> orderByVolume(){
		return new Comparator<Artikel>(){
			public int compare(Artikel c1, Artikel c2){
				return Double.compare(c2.getVolume(), c1.getVolume());
			}
		};
	}
	

	public ArrayList<Bin> getBins(){
		return bins;
	}
	
	public ArrayList<Artikel> getProducts()
	{
		return artikelLijst;
	}
}