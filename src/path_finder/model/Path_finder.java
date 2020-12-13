package path_finder.model;

import java.io.IOException;

import path_finder.misc.tCoord;

public class Path_finder {

	//CONSTRAINTS
	private static final int height = 40;
	private static final int width = 40;
	
	private static final String START = "StartPoint";
	private static final String END = "EndPoint";
	
	private tCoord startPoint;
	private tCoord endPoint;
	
	private int [][] matrix;
	
	private Searcher search;
	
	public Path_finder() {
		this.matrix = new int[height][width];
		this.startPoint = new tCoord(-1, -1);
		this.endPoint = new tCoord(-1, -1);;
		this.search = new Searcher();
		initializeMatrix();
	}
	
	private void initializeMatrix() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	
	public void addObserver(canvasObserver co) {
		this.search.addObserver(co);
	}
	
	
	// ------------- MATRIX MANAGEMENT ------------------------------------

	public void addWall(int i, int j) {
		if(!overwritePoint(i, j, this.startPoint) && !overwritePoint(i, j, this.endPoint)) {
			matrix[i][j] = 1;			
		}
	}
	
	public void removeWall(int i, int j) {
		if(!overwritePoint(i, j, this.startPoint) && !overwritePoint(i, j, this.endPoint)) {
			matrix[i][j] = 0;			
		}
	}	
	
	public void addPoint(int i, int j, String point_type) {
		if(point_type.equals(START) && !overwritePoint(i, j, this.endPoint)) {
			checkPoint(this.startPoint);
			this.startPoint.setCoordinates(i, j);
			matrix[i][j] = 2;
		}
		else if(point_type.equals(END) && !overwritePoint(i, j, this.startPoint)){
			checkPoint(this.endPoint);
			this.endPoint.setCoordinates(i, j);
			matrix[i][j] = 3;
		}
	}
	
	//checks if the point that we are trying to place is already placed and has to be moved
	private void checkPoint(tCoord point) {
		if(point.getX() != -1) {
			matrix[point.getX()][point.getY()] = 0;
		}
	}
	
	//this method is called with the start/end point and is used to check if we are trying 
	//to write one of them above the other
	private boolean overwritePoint(int i, int j, tCoord point) {
		return this.endPoint.getX() == i && this.endPoint.getY() == j;
	}
	
	// ------------- SEARCHER CALLS ---------------------------------------
	
	public void start(int d) throws IOException {
		if(this.startPoint.getX() != -1 && this.endPoint.getX() != -1) {
			search.addValues(this.startPoint, this.endPoint, this.matrix, Path_finder.height, Path_finder.width, d);
			search.bfs();			
		}
		else {
			throw new IOException("setup the start and end points");
		}
	}
	
	/*public void start2() {
		if(this.startPoint.getX() != -1 && this.endPoint.getX() != -1) {
			search.addValues(this.startPoint, this.endPoint, this.matrix, Path_finder.height, Path_finder.width);
			//search.Dijkstra();			
		}
		else {
			System.out.println("Define los puntos de comienzo y final");
		}
	}*/
	
	public void reset(int w) {
		if(w == 1) { 
			this.initializeMatrix();			
		}
		this.search.reset();
	}
}
