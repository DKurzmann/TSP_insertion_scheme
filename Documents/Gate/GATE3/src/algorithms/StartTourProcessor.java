package algorithms;

import graph.UndirectedGraphAsAdjacencyMatrix;

public class StartTourProcessor {
	
	int[][] startTourMatrix;
	int[] tourArray;
	double[][] adj;
	int tourLength;
	
	StartTourProcessor(UndirectedGraphAsAdjacencyMatrix undirGraph){
		this.startTourMatrix = new int[undirGraph.numberOfVertices()][undirGraph.numberOfVertices()];	
		this.adj = undirGraph.getAdjacencyMatrix();
	}
	
	int[] getStartTour(){
		startTourMatrix = getStartTourMatrix(); //Matrix mit 0en und 1en
		return tourArray = getStartTourList();
	}

	private int[][] getStartTourMatrix() { //muss verbunden sein
		startTourMatrix[1][2] = 1;
		startTourMatrix[2][1] = 1;
//		startTourMatrix[44][3] = 1;
//		startTourMatrix[3][40] = 1;
//		startTourMatrix[40][7] = 1;
//		startTourMatrix[7][16] = 1;
//		startTourMatrix[16][44] = 1;
		return startTourMatrix;	
	}	

	private int[] getStartTourList() {
		tourArray = new int[adj.length];
		for(int i = 0; i < adj.length; i++) {
			tourArray[i]=-9;
		}
		tourArray = getFirstCity();
		tourArray = getRemainingCitiesInStartTour();
		return tourArray;
	}
	
	private int[] getFirstCity() {
		outerLoop: for (int zeile = 0; zeile < startTourMatrix.length; zeile++) {
			for (int spalte = 0; spalte < startTourMatrix.length; spalte++) {
				if (startTourMatrix[zeile][spalte] == 1) {
					tourArray[0] = zeile;
					tourArray[1] = spalte;
					tourLength = 2;
					break outerLoop;
				}
			}
		}
		return tourArray;
	}

	private int[] getRemainingCitiesInStartTour() {
		whileLoop: while (true) {
			int neueZeile = tourArray[tourLength - 1];
			for (int spalte = 0; spalte < startTourMatrix.length; spalte++) {
				if (startTourMatrix[neueZeile][spalte] == 1) {

					if ((tourArray[tourLength] == spalte) == false) {
						break whileLoop;
					}
					tourArray[tourLength] = spalte;
					tourLength++;
					break;
				}
			}
		}
		return tourArray;
	}
	int getTourLength() {
		return tourLength;
	}
}
