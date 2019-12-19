package algorithms;

import graph.UndirectedGraphAsAdjacencyMatrix;

/************************************************************************
 * 
 * 
 * 
 * class XXXXX
 * 
 * Determine a tour with your own algorithm. Used to try a new algorithm.
 * 
 * @author Student, Date.
 * 
 * 
 ***********************************************************************/
public class XXXXX implements AlgorithmTSP {

	/** + + + Variables + + + */

//The length of the Tour.
	private double tourLength;
//The tour (Hamilton cycle).
	private int[] tour;
	
	private UndirectedGraphAsAdjacencyMatrix undirGraph;

	/************************************************************************
	 * Execute Your-Algorithm.
	 * 
	 * @param undirGraph - An undirected graph.
	 */
	// kante löschen: -9
	public void execute(UndirectedGraphAsAdjacencyMatrix undirGraph) {
		this.undirGraph = undirGraph;
		StartTourProcessor startTourProcessor = new StartTourProcessor(undirGraph);
//		NearestInsertionScheme minimumInsertionScheme = new NearestInsertionScheme(undirGraph, startTourProcessor.getStartTour(), startTourProcessor.getTourLength());
//		tour = minimumInsertionScheme.getshortestTour();	
		FahrtestInsertionScheme fahrtestInsertionScheme = new FahrtestInsertionScheme(undirGraph, startTourProcessor.getStartTour(), startTourProcessor.getTourLength());
		tour = fahrtestInsertionScheme.getshortestTour();
	}



	/************************************************************************
	 * Return the length of the HamiltonTour.
	 * 
	 * @return tourLength - The total length of the Tour.
	 */
	public double getTourLength() {
		tourLength=0;
		double[][] adj = undirGraph.getAdjacencyMatrix();
		for(int indexOfTour = 0; indexOfTour < tour.length; indexOfTour++) {
			int thisCity = tour[indexOfTour];
			
			if(indexOfTour<tour.length-1) {
				int nextCity = tour[indexOfTour+1];
				tourLength = tourLength + adj[thisCity][nextCity];
			}
			else{
				int nextCity = tour[0];
				tourLength = tourLength + adj[thisCity][nextCity];
			}
		}
		return tourLength;
	}

	/************************************************************************
	 * Returns the HamiltonTour as an int [].
	 * 
	 * @return tour - The HamiltonTour represented as an int [].
	 */
	public int[] getHamiltonTour() {
		return tour;
	}

}