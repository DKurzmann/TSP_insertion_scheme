package algorithms;

import graph.UndirectedGraphAsAdjacencyMatrix;

public class MinimumInsertionSchemeWithoutNotInTourList {
		
		double[][] adj;
		int[] tourArray;
		int tourLength;
		double overallMinimumCost;
		int indexToInsert;
		int notInTourCityToInsert;
		int firstCityNotInTour;
		private boolean firstTrueInIteration;

		MinimumInsertionSchemeWithoutNotInTourList(UndirectedGraphAsAdjacencyMatrix undirGraph, int[] startTour, int tourLength){
			this.adj = undirGraph.getAdjacencyMatrix();
			this.tourArray = startTour;
			this.tourLength = tourLength;
		}
		
		int[] getshortestTour(){
			tourArray = useInsertionScheme();
			return tourArray;
		}
		
		private int[] useInsertionScheme() {
			firstCityNotInTour = 0;
			while (tourLength < tourArray.length) {
				firstTrueInIteration = true; 
				getMinCityAndIndexToInsert();
				tourArray = insertAndRemoveCity();
			}
			return tourArray;
		}
		
		private void getMinCityAndIndexToInsert() { 
			overallMinimumCost = Double.MAX_VALUE;
			for (int city = firstCityNotInTour; city < tourArray.length; city++) {
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
			if(firstTrueInIteration()) {
				firstCityNotInTour = city;	
			}
			return true;
		}

		private boolean firstTrueInIteration() {
			if(firstTrueInIteration) {
				firstTrueInIteration = false; 
				return true;
			}
			return false;
		}

		private void getMinCostForInsertion(int cityNotInTour) {
			double currentCost;
			int cityInTour1;
			int cityInTour2;
			double Kante1;
			double Kante2;
			for (int indexOfInTour = 0; indexOfInTour < tourLength - 1; indexOfInTour++) { //inTour.size-1 weil mit 0 beginnt
				cityInTour1 = tourArray[indexOfInTour];
				cityInTour2 = tourArray[indexOfInTour + 1];
				Kante1 = adj[cityNotInTour][cityInTour1];
				Kante2 = adj[cityNotInTour][cityInTour2];
				currentCost = Kante1 + Kante2;
				if(overallMinimumCost > currentCost) {
						overallMinimumCost = currentCost;
						indexToInsert = indexOfInTour;
						notInTourCityToInsert =  cityNotInTour;
				}
			}
			// von der letzten Stadt zur ersten Stadt
			cityInTour1 = tourArray[tourLength - 1];
			cityInTour2 = tourArray[0];
			Kante1 = adj[cityNotInTour][cityInTour1];
			Kante2 = adj[cityNotInTour][cityInTour2];
			currentCost = Kante1 + Kante2;
			if(overallMinimumCost > currentCost) {
					overallMinimumCost = currentCost;
					indexToInsert = tourLength + 1;
					notInTourCityToInsert =  cityNotInTour;
			}
		}

		private int[] insertAndRemoveCity() {
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
