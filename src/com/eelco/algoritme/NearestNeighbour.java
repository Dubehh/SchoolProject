package com.eelco.algoritme;

import java.util.ArrayList;
import java.util.List;

import com.eelco.mechanics.Coordinaat;

public class NearestNeighbour extends TSPAlgorithmBase{
	
	/**
	 * @author Eelco
	 * @return Coordinaten array in goede volgorde
	 */
	public final List<Coordinaat> getFinalResult(double x, double y){
		List<Coordinaat> orderedList = new ArrayList<>();
		Coordinaat loop = Coordinaat.fromData(x, y);
		orderedList.add(loop);
		for(int i = 0; i < Coordinaat.getCoordinaten().size(); i++){
			for(Coordinaat c : Coordinaat.getCoordinaten()){
				Coordinaat nearest = getNearest(loop.getX(), loop.getY(), orderedList);
				if(c == nearest){
					loop = c;
					orderedList.add(c);
				}
			}
		}
		return orderedList;
	}
	
	/**
	 * Gets nearest Coordinaat
	 * @param x double
	 * @param y double
	 * @param list ordered list
	 * @return Coordinaat
	 */
	private Coordinaat getNearest(double x, double y, List<Coordinaat> list){
		Coordinaat c = null;
		for(Coordinaat cur : Coordinaat.getCoordinaten()){
			if(cur.getX()==x && cur.getY()==y) continue;
			if(list.contains(cur)) continue;
			if(c==null || c.distanceTo(x, y) > cur.distanceTo(x, y)){
				c = cur;
			}
		}
		return c;
	}
}
