package algorithms;

import graph.UndirectedGraphAsAdjacencyMatrix;

public class NearestInsertionSchemeNeo {
	
	double[][] adj;
	double[][] cityIndexMinCostMatrix;
	int[] tourArray;
	int tourLength;
	int indexToInsert;
	int lastIndexToInsert;
	int notInTourCityToInsert;
	double overallMinimumCost;
	boolean firstIteration = true;
	double cityMinimumCost;
	int minIndex;
	
	double currentCost;
	int cityInTour1;
	int cityInTour2;
	double Kante1;
	double Kante2;

	NearestInsertionSchemeNeo(UndirectedGraphAsAdjacencyMatrix undirGraph, int[] startTour, int tourLength){
		this.adj = undirGraph.getAdjacencyMatrix();
		this.cityIndexMinCostMatrix = new double[adj.length][3];
		this.tourArray = startTour;
		this.tourLength = tourLength;
	}
			
	int[] getshortestTour() {
		fillMatrixWithcitiesInFirstColumn(cityIndexMinCostMatrix);
		while (tourLength < tourArray.length) { 
			if(firstIteration) {
				getMinCityAndIndexToInsert();
				firstIteration = false;
			}
			else {
				getMinCityAndIndexToInsertNew();
			}
			tourArray = insertCity();
		}
		return tourArray;
	}
	
	private void getMinCityAndIndexToInsertNew() {
		overallMinimumCost = Double.MAX_VALUE;
		lastIndexToInsert = indexToInsert;
		for (int city = 0; city < tourArray.length; city++) {
			if(cityNotInTour(city)) {
				if(cityIndexMinCostMatrix[city][1] != lastIndexToInsert) {
					getMinCostForInsertionNew(city);					
				}
				else {
					getMinCostForInsertion(city);
				}

			}
		}
	}

	private void getMinCostForInsertionNew(int cityNotInTour) {
		cityMinimumCost = cityIndexMinCostMatrix[cityNotInTour][2];
		if(cityIndexMinCostMatrix[cityNotInTour][1] > lastIndexToInsert) {
			cityIndexMinCostMatrix[cityNotInTour][1] += 1;
		}
		for (int indexOfInTour = lastIndexToInsert-1; indexOfInTour < lastIndexToInsert + 1; indexOfInTour++) {
			if(indexOfInTour != tourLength - 1 && indexOfInTour < cityIndexMinCostMatrix[cityNotInTour][1]) {
				cityInTour1 = tourArray[indexOfInTour];
				cityInTour2 = tourArray[indexOfInTour + 1];
				Kante1 = adj[cityNotInTour][cityInTour1];
				Kante2 = adj[cityNotInTour][cityInTour2];
				currentCost = Kante1 + Kante2;
				if(cityIndexMinCostMatrix[cityNotInTour][2] >= currentCost) { // >= um gleiche Reihenfolge zu garantieren, index wird nur bei gleichen kosten überschrieben wenn er vor dem index liegt 
					cityIndexMinCostMatrix[cityNotInTour][1] = indexOfInTour+1;
					cityIndexMinCostMatrix[cityNotInTour][2] = currentCost;
				}
			}
			if(indexOfInTour != tourLength - 1 && indexOfInTour >= cityIndexMinCostMatrix[cityNotInTour][1]) {
				cityInTour1 = tourArray[indexOfInTour];
				cityInTour2 = tourArray[indexOfInTour + 1];
				Kante1 = adj[cityNotInTour][cityInTour1];
				Kante2 = adj[cityNotInTour][cityInTour2];
				currentCost = Kante1 + Kante2;
				if(cityIndexMinCostMatrix[cityNotInTour][2] > currentCost) {
					cityIndexMinCostMatrix[cityNotInTour][1] = indexOfInTour+1;
					cityIndexMinCostMatrix[cityNotInTour][2] = currentCost;
				}
			}
			if(indexOfInTour == tourLength - 1) {
				// von der letzten Stadt zur ersten Stadt
				cityInTour1 = tourArray[tourLength - 1];
				cityInTour2 = tourArray[0];
				Kante1 = adj[cityNotInTour][cityInTour1];
				Kante2 = adj[cityNotInTour][cityInTour2];
				currentCost = Kante1 + Kante2;
				if(cityIndexMinCostMatrix[cityNotInTour][2] > currentCost) {
					cityIndexMinCostMatrix[cityNotInTour][1] = tourLength;
					cityIndexMinCostMatrix[cityNotInTour][2] = currentCost;
				}	
			}
		}
		if(overallMinimumCost > cityIndexMinCostMatrix[cityNotInTour][2]) {
			overallMinimumCost = cityIndexMinCostMatrix[cityNotInTour][2];
			indexToInsert = (int) cityIndexMinCostMatrix[cityNotInTour][1];
			notInTourCityToInsert =  cityNotInTour;
	}
		
	}

	private void fillMatrixWithcitiesInFirstColumn(double[][] Matrix) {
			for(double i = 0; i < adj.length; i++) {
				Matrix[(int) i][0]=i;
				Matrix[(int) i][2]=Double.MAX_VALUE;//brauche ich das? 
			}
	}

	private void getMinCityAndIndexToInsert() { 
		overallMinimumCost = Double.MAX_VALUE;
		for (int city = 0; city < tourArray.length; city++) {
			if(cityNotInTour(city)) {
				getMinCostForInsertion(city);
			}
		}
	}
	
	private boolean cityNotInTour(int city) {
		for(int indexOfTour = 0; indexOfTour < tourArray.length; indexOfTour++) {
			if(city == tourArray[indexOfTour]) {
				return false;
			}
		}
		return true;
	}

	private void getMinCostForInsertion(int cityNotInTour) {
		cityMinimumCost = Double.MAX_VALUE;
		for (int indexOfInTour = 0; indexOfInTour < tourLength - 1; indexOfInTour++) { //inTour.size-1 weil mit 0 beginnt
			cityInTour1 = tourArray[indexOfInTour];
			cityInTour2 = tourArray[indexOfInTour + 1];
			Kante1 = adj[cityNotInTour][cityInTour1];
			Kante2 = adj[cityNotInTour][cityInTour2];
			currentCost = Kante1 + Kante2;
			if(cityMinimumCost > currentCost) {
				cityMinimumCost = currentCost;
				minIndex = indexOfInTour+1;
				cityIndexMinCostMatrix[cityNotInTour][1] = minIndex;
				cityIndexMinCostMatrix[cityNotInTour][2] = cityMinimumCost;
			}
			if(overallMinimumCost > currentCost) {
					overallMinimumCost = currentCost;
					indexToInsert = indexOfInTour + 1;
					notInTourCityToInsert =  cityNotInTour;
			}
		}
		// von der letzten Stadt zur ersten Stadt
		cityInTour1 = tourArray[tourLength - 1];
		cityInTour2 = tourArray[0];
		Kante1 = adj[cityNotInTour][cityInTour1];
		Kante2 = adj[cityNotInTour][cityInTour2];
		currentCost = Kante1 + Kante2;
		if(cityMinimumCost > currentCost) {
			cityMinimumCost = currentCost;
			minIndex = tourLength;
			cityIndexMinCostMatrix[cityNotInTour][1] = minIndex;
			cityIndexMinCostMatrix[cityNotInTour][2] = cityMinimumCost;
		}
		if(overallMinimumCost > currentCost) {
				overallMinimumCost = currentCost;
				indexToInsert = tourLength;
				notInTourCityToInsert =  cityNotInTour;
		}
	}

	private int[] insertCity() {
		if(indexToInsert == tourLength) {
			tourArray[tourLength] = notInTourCityToInsert;
			tourLength++;
		}
		else {
			int cache1 = notInTourCityToInsert;
			int cache2; 
			for(int indexToChange = indexToInsert; indexToChange <= tourLength; indexToChange++) {
				cache2 = tourArray[indexToChange];
				tourArray[indexToChange] = cache1;
				cache1 = cache2;
			}
			tourLength++;
		}
//		if(tourLength == 90) { //test
//			String tourAsString = "";
//			for(int indexOfTour = 0; indexOfTour < tourLength; indexOfTour++) {
//				tourAsString = tourAsString + "_" + tourArray[indexOfTour];}
//			System.out.println(tourAsString);
//		}
		return tourArray; 
	}
}

