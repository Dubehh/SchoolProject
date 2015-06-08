package com.eelco.swing;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.eelco.algoritme.packing.BPPAlgorithmBase;
import com.eelco.core.Artikel;
import com.eelco.core.Bin;
import com.eelco.swing.screen.Frame;

public class BPP_Panel extends JPanel {
	
	/**
	 * @author Lars Baalmans, Darryn van Barneveld
	 */
	
    private final int WIDTH = 900;
    private final int HEIGHT = 330;
    private ArrayList<Bin> bins;
    private int amountOfBins = 25;
    private boolean nextPage = false;
    private int modulo = 5;
    private BPPAlgorithmBase base = null;


    public BPP_Panel(){
        super.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.bins = new ArrayList<Bin>();
        for(int x = 0; x < amountOfBins; x++)
        {
        	bins.add(new Bin());
        }
    }
    
    public void swapColor(Graphics graph){
    	Graphics2D g = (Graphics2D) graph;
    	g.setColor(Color.CYAN);
    }
        
    public void paintComponent(Graphics graph){
    	super.paintComponent(graph);
    	int i = 35;
    	int s = 0;
        Graphics2D g = (Graphics2D) graph;
        
        /**
         * tekenen van de bins en border van de bins.
         */
        for (int y = 0; y < 4; y++){
        	g.setColor(Color.BLACK);
        	g.drawRect(s, 10, 200, 300);
        	g.setColor(Color.GRAY);
        	g.fillRect(s+15, 25, 172, 272);
        	s += 213;
        	
        }
        
        /**
         * tekenen van de items.
         * absolute 0 punt: 556
         * 
         */
        if(base != null)
        {
        	s = 15;
        	bins = base.getBins();
        	
        	int binCounter = 1;
        	int packageCounter = 1;
            for (Bin b: bins){
            	int location = 0;
        		for (Artikel a: b.getItems())
        		{
        			switch(packageCounter % 8)
        			{
        			case 0:
        				g.setColor(Color.DARK_GRAY);
        				break;
        			case 1:
        				g.setColor(Color.BLUE);
        				break;
        			case 2:
        				g.setColor(Color.PINK);
        				break;
        			case 3:
        				g.setColor(Color.RED);
        				break;
        			case 4:
        				g.setColor(Color.YELLOW);
        				break;
        			case 5:
        				g.setColor(Color.GREEN);
        				break;
        			case 6:
        				g.setColor(Color.ORANGE);
        				break;
        			case 7:
        				g.setColor(Color.CYAN);
        				break;
        			}
        			
        			g.fillRect(s, 298-34*location, 172, -(34*a.getVolume())-1);
        			location += a.getVolume();
        			packageCounter++;
        		}
        		
        		binCounter++; //The current bin that is being used
        		s += 213; //Increase the x-coordinate
        		
        		//temp		-- Replace this to fit more than 4 bins
        		if(binCounter % modulo == 0) //If the next bin is bin 5 or higher, exit the loop
        		{
					if (nextPage == true){
        				s = 15;
            			for (int p = 0; p < 4; p++){
            				g.setColor(Color.GRAY);
            	        	g.fillRect(s, 25, 172, 272);
            				s += 213;
            			}
            			s = 15;
            			modulo += 4;
            			nextPage = false;
        			}
        			else {
        				break;
        			}
        		}
        	}
        }
        
        /*   Volume bars */
        int h = 186;
        s = 15;
        i = 25;
        for (int y = 0; y < 4; y++){
	        for (int x = 0; x < 9; x++){
	    		g.setColor(Color.BLACK);
	        	g.drawLine(s , i, h, i);
	        	i += 34;
	        }
	    	i = 25;
	    	s += 213;
	    	h += 213;
        }
    }
    public void setAlgorithm(BPPAlgorithmBase base){
    	this.base = base;
    	this.repaint();
    	this.printBins();
    }
    
    public void resetPages(){
    	modulo = 5;
    }
    
    public void nextPage()
    {
    	if (modulo + 4 > bins.size()){
    		nextPage = true;
    		this.repaint();
    		Frame.getInstance().getNextBinBtn().setEnabled(false);
    	} else {
    		nextPage = true;
        	this.repaint();
    	}
    }
    
	public void printBins(){
		int binCount = 1;
		int totalBins = 1;
		for(Bin b : base.getBins())
		{
			Frame.getInstance().getCurrentLog(binCount).log("This Bin (bin: " + totalBins +") contains: ");
			for(Artikel a : b.getItems())
			{
				Frame.getInstance().getCurrentLog(binCount).log(" -" + a.getDesc() + " (" + a.getVolume() + ")");
			}
			Frame.getInstance().getCurrentLog(binCount).log("End of bin " + totalBins + ".\n");
			binCount++;
			totalBins++;
			if (binCount > 4){
				binCount = 1;
			}
		}
	}

}