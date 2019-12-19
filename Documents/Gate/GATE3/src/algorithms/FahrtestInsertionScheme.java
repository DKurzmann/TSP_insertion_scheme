package algorithms;

import graph.UndirectedGraphAsAdjacencyMatrix;

public class FahrtestInsertionScheme {
	
	double[][] adj;
	int[] tourArray;
	int tourLength;
	int indexToInsert;
	int notInTourCityToInsert;
	double overallMaxiimumCost;
	
	double currentCost;
	int cityInTour1;
	int cityInTour2;
	double Kante1;
	double Kante2;

	public FahrtestInsertionScheme(UndirectedGraphAsAdjacencyMatrix undirGraph, int[] startTour, int tourLength) {
		this.adj = undirGraph.getAdjacencyMatrix();
		this.tourArray = startTour;
		this.tourLength = tourLength;
	}
			
	int[] getshortestTour() {
		while (tourLength < tourArray.length) { 
			getMinCityAndIndexToInsert();
			tourArray = insertCity();
		}
		return tourArray;
	}
	
	private void getMinCityAndIndexToInsert() { 
		overallMaxiimumCost = Double.MIN_VALUE;
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
		for (int indexOfInTour = 0; indexOfInTour < tourLength - 1; indexOfInTour++) { //inTour.size-1 weil mit 0 beginnt
			cityInTour1 = tourArray[indexOfInTour];
			cityInTour2 = tourArray[indexOfInTour + 1];
			Kante1 = adj[cityNotInTour][cityInTour1];
			Kante2 = adj[cityNotInTour][cityInTour2];
			currentCost = Kante1 + Kante2;
			if(overallMaxiimumCost < currentCost) {
					overallMaxiimumCost = currentCost;
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
		if(overallMaxiimumCost < currentCost) {
				overallMaxiimumCost = currentCost;
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
		return tourArray; 
	}

}
