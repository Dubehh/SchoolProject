package com.eelco.algoritme;

import java.util.ArrayList;

import com.eelco.mechanics.Coordinaat;

public class BruteForce extends TSPAlgorithmBase{
	
	/**
	 * @author Darryn van Barneveld
	 */
	private static ArrayList<Coordinaat> magazijn;
	private final static int currentNest_DONT_EDIT = 1;		//This should always be 1!!!
	
	public final ArrayList<Coordinaat> getFinalResult(double drop_x, double drop_y)		//Brute force = test every possibility
	{
		//Fail save if the passed through arraylist is empty
		magazijn = Coordinaat.getCoordinaten();
		
		//Make a new ArrayList to sort the new route products in
		ArrayList<Coordinaat> newRouteDiscription = new ArrayList<Coordinaat>();
		
		
		//This is to keep track of the shortest route. It will save the route in package numbers and the total distance
		String winningRouteString = "";
		double winningRouteLength = Math.pow(10, 10); //Chose a high number for this to make sure a better route is selected
		int size = magazijn.size();
		
		
		//get all numbers & erase all duplicate numbers
		ArrayList<String> numberList = getAllPossibleRoutesNumbers(size);
		
		
		//get distance & update to the shortest route length
		for(String routeString : numberList)
		{
			//Example:
			// B-1234-E
			// =
			// B -> 1 = 2		Beginning to First Box distance
			//
			// 1 -> 2 = 4		First Box to Box	\
			// 2 -> 3 = 2		Box to Box 			 |----> Box to Box distances
			// 3 -> 4 = 5		Box to Last Box 	/
			//
			// 4 -> E = 1		Last Box to End distance
			// _________+
			// 2+4+2+5+1 = 14 Total distance
			
			//Initialize
			String[] routeArray = routeString.split(",");
			int routeLength = 0;
			
			//Begin to first distance
			Coordinaat firstBox = magazijn.get(Integer.parseInt(routeArray[0]) - 1);
			routeLength += firstBox.distanceTo(drop_x, drop_y);
			
			//Box to box loop distances
			for(int x = 1; x < routeArray.length; x++)
			{
				Coordinaat first =  magazijn.get(Integer.parseInt(routeArray[x]) - 1);
				Coordinaat second = magazijn.get(Integer.parseInt(routeArray[x - 1]) - 1);
				routeLength += first.distanceTo(second.getX(), second.getY());
			}
			
			//Last to end distance
			Coordinaat last = magazijn.get(Integer.parseInt(routeArray[routeArray.length - 1]) - 1);
			routeLength += last.distanceTo(drop_x,drop_y);
			
			//Checking if the current route is shorter than the shortest saved route.
			if(routeLength < winningRouteLength)
			{
				winningRouteLength = routeLength;
				winningRouteString = routeString;
			}
		}
		
		
		//Using the shortest route calculated to make a new route
		String[] winningRouteArray = winningRouteString.split(",");
		for(String packageNumber : winningRouteArray)
		{
			newRouteDiscription.add(magazijn.get(Integer.parseInt(packageNumber) - 1));
		}
		
		
		return newRouteDiscription;
	}
	
	private ArrayList<String> getAllPossibleRoutesNumbers(int topNumber)
	{
		//Get all possible number combinations
		ArrayList<String> allRouteNumbers = getAllRouteNumbers(topNumber, currentNest_DONT_EDIT);
		
		//!! Starts from the top as to not disturb the index
		//This loop will take out any route numbers that have duplicate numbers in them (1223 or 1241 or 2344 or 4333 will be taken out)
		for(int routeCounter = allRouteNumbers.size()-1; routeCounter >= 0; routeCounter--)
		{
			//Gets all numbers within a single route number
			String[] testingArray = allRouteNumbers.get(routeCounter).split(",");
			
			
			//Takes a single number from a route and compares it to all others
			//If it finds a duplicate, it'll delete it out of the total route number array (allRouteNumbers)
			boolean duplicate = false;
			for(String number : testingArray)
			{
				//Duplicate counter
				int count = 0;
				
				//It will compare it to all other numbers in the route
				for(String number2 : testingArray)
				{
					//Comparing to the other number
					if(number.equals(number2))
					{
						count++;
					}
				}
				//The number here is set to 1, because it will go through ALL numbers, including itself, in the route number
				if(count > 1)
				{
					duplicate = true;
				}
			}
			
			//Deleting the duplicate
			if(duplicate)
			{
				allRouteNumbers.remove(routeCounter);
			}
		}
		
		//Returning all non-duplicate filled route numbers
		return allRouteNumbers;
	}
	private ArrayList<String> getAllRouteNumbers(int topNumber, int currentNest)
	{
		//
		ArrayList<String> numbers = new ArrayList<String>();
		
		//If the currentNest  that is passed through is lower than 1, set it to 1.
		//This is to prevent it from doing to many unnecessary loops
		if(currentNest < 1)
		{
			currentNest = 1;
		}
		
		//Go through every number between 1 and the maximum number.
		for(int x = 1; x <= topNumber; x++)
		{
			//If the currentNest is the topNumber loop (the last loop) it will add the last number
			if(currentNest < topNumber)
			{
				//If this is not the last nest, it will call itself to give him numbers
				ArrayList<String> nextNestNumbers = getAllRouteNumbers(topNumber, currentNest+1);
				
				//After that it will add a his own loop number to the list...
				for(int y = 0; y < nextNestNumbers.size(); y++)
				{
					nextNestNumbers.set(y, (nextNestNumbers.get(y) + String.valueOf(x) + ","));
				}
				
				//...and add it to the total list
				numbers.addAll(nextNestNumbers);
			}
			else
			{
				//If this is the last nest, it will just add his loop number to the total list
				numbers.add(String.valueOf(x)+",");
			}
		}
		
		//After the loop has added his own loop numbers to all the numbers from the nested loops,
		//it'll return his own total list of numbers
		return numbers;
	}
}
