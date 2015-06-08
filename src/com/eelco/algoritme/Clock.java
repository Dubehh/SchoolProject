package com.eelco.algoritme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.eelco.mechanics.Coordinaat;

public class Clock extends TSPAlgorithmBase{
	
	/**
	 * @author Eelco - 2015
	 */
	
	
	/**
	 * Krijg gesorteerde Array terug.
	 * @return Coordinaat object
	 */
	private final ArrayList<Coordinaat> getFinalResult(){
		Coordinaat center = getMiddleCoordinaat(Coordinaat.getCoordinaten().toArray(new Coordinaat[Coordinaat.getCoordinaten().size()]));
		ArrayList<Coordinaat> order = new ArrayList<Coordinaat>(Coordinaat.getCoordinaten());
		Collections.sort(order, byAngleComperator(center));
		Collections.reverse(order);
		return order;
	}
	
	/**
	 * Geeft result terug, alleen dan met aangegeven start punt.
	 * @param x X coordinaat van start punt
	 * @param y Y coordinaat van start punt
	 * @return Array met coordinaten gesorteerd vanaf een start waarde
	 */
	public final List<Coordinaat> getFinalResult(double x, double y){
		Coordinaat temp = Coordinaat.fromData(x, y); /* Betrek startwaarde bij cirkel*/
		List<Coordinaat> coordList = new ArrayList<Coordinaat>(getFinalResult());
		int start = coordList.indexOf(temp);
		List<Coordinaat> orderedList = new ArrayList<Coordinaat>();
		for(int i = start; i < coordList.size(); i++){ /* Kijk eerst vanaf het start getal, en re-order de array */
			orderedList.add(coordList.get(i));
		}
		for(int i = 0; i < start; i++){ /*Kijk daarna vóór het start getal, en voeg die achteraan de array */
			orderedList.add(coordList.get(i));
		}
		return orderedList;
	}
	
	/**
	 * Print de (gesorteerde) waarden in de juiste volgorde.
	 */
	public final void printResult(double x, double y){
		List<Coordinaat> array = x == -1 || y == -1 ? getFinalResult() : getFinalResult(x, y);
		String row = "";
		for(Coordinaat allCoords : array){
			row += allCoords.toString()+" | ";
		}
		System.out.println(row.substring(0, row.length()-3));
	}
	/**
	 *  Krijg middelste coordinaat terug
	 */
	public final Coordinaat getMiddleCoordinaat(Coordinaat... allCoordinaten){
		List<Double> xCoords = new ArrayList<Double>();
		List<Double> yCoords = new ArrayList<Double>();
		for(Coordinaat c : allCoordinaten){
			xCoords.add(c.getX());
			yCoords.add(c.getY());
		}
		double xMax = Collections.max(xCoords);
		double xMin = Collections.min(xCoords);
		double yMax = Collections.max(yCoords);
		double yMin = Collections.min(yCoords);
		Coordinaat finalCenter = new Coordinaat((xMax+xMin) / 2, (yMax+yMin) /2);
		Coordinaat.getCoordinaten().remove(finalCenter);
		return finalCenter;
	}
	
	public final Comparator<Coordinaat> byAngleComperator(Coordinaat center){
		final double centerX = center.getX();
		final double centerY = center.getY();
		return new Comparator<Coordinaat>(){
			public int compare(Coordinaat c0, Coordinaat c1) {
				double firstAngle = Coordinaat.angleToX(centerX, centerY, c0.getX(), c0.getY());
				double secondAngle = Coordinaat.angleToX(centerX, centerY, c1.getX(), c1.getY());
				return Double.compare(firstAngle, secondAngle);
			}
		};
	}
	
}
